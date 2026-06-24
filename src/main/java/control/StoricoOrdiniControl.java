package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import dao.OrdineDao;
import dao.OrdineDaoImpl;
import model.Ordine;
import model.Utente;

@WebServlet("/common/ordini")
public class StoricoOrdiniControl extends HttpServlet {
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
        //Recupero la sessione
    	HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utente") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        //Recupero i dati utente
        Utente utente = (Utente) session.getAttribute("utente");
        try { //Recupero gli ordini dell'utente
            List<Ordine> ordini = ordineDao.doRetrieveByUser(utente.getId());
            request.setAttribute("ordini", ordini);
        } catch (SQLException e) {
            System.out.println("Errore nel recupero dello storico ordini: " + e.getMessage());
            request.setAttribute("error", "Impossibile recuperare lo storico degli ordini al momento.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/common/ordini_utente.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
