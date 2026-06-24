package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dao.OrdineDao;
import dao.OrdineDaoImpl;
import model.Ordine;

@WebServlet("/admin/ordini")
public class AdminOrdineControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrdineDao ordineDao;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto");
        }
        ordineDao = new OrdineDaoImpl(ds);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Recupero eventuali criteri di ricerca
    	String idClienteStr = request.getParameter("idCliente");
        String dataInizioStr = request.getParameter("dataInizio");
        String dataFineStr = request.getParameter("dataFine");

        List<Ordine> ordini = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        
        try {
            //Ricerca per id cliente
            if (idClienteStr != null && !idClienteStr.trim().isEmpty()) {
                try {
                    int idCliente = Integer.parseInt(idClienteStr.trim());
                    ordini = ordineDao.doRetrieveByUser(idCliente);
                } catch (NumberFormatException e) {
                    errors.add("L'ID Cliente deve essere un valore numerico intero.");
                }
            } 
            //Ricerca per intervallo date
            else if (dataInizioStr != null && !dataInizioStr.trim().isEmpty() &&
                       dataFineStr != null && !dataFineStr.trim().isEmpty()) {
                try {
                    Date inizio = Date.valueOf(dataInizioStr.trim());
                    Date fine = Date.valueOf(dataFineStr.trim());
                    ordini = ordineDao.doRetrieveByDate(inizio, fine);
                } catch (IllegalArgumentException e) {
                    errors.add("Formato data non valido. Utilizzare il formato aaaa-mm-gg.");
                }
            } 
            //Nessun filtro mostra tutti gli ordini
            else {
                ordini = ordineDao.doRetrieveAll();
            }

            request.setAttribute("ordini", ordini);
            request.setAttribute("errors", errors);
            
            //Rimando i parametri per pre-compilare il form
            request.setAttribute("idCliente", idClienteStr);
            request.setAttribute("dataInizio", dataInizioStr);
            request.setAttribute("dataFine", dataFineStr);

        } catch (SQLException e) {
            System.out.println("Errore Database nella ricerca ordini admin: " + e.getMessage());
            errors.add("Errore del server durante il recupero dei dati.");
            request.setAttribute("errors", errors);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/ordini_admin.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
