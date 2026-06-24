package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dao.ProdottoDao;
import dao.ProdottoDaoImpl;
import model.Prodotto;

@WebServlet("/admin/prodotti")
public class AdminProdottoControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProdottoDao prodottoDao;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if (ds == null) {
            throw new ServletException("DataSource non disponibile nel contesto");
        }
        prodottoDao = new ProdottoDaoImpl(ds);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Recupero l'azione da effettuare
    	String action = request.getParameter("action");

        try {
            if ("prepareInsert".equals(action)) {	
                request.setAttribute("mode", "insert");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/form_prodotto.jsp");
                dispatcher.forward(request, response);
                return;
            }

            if ("prepareUpdate".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Prodotto prodotto = prodottoDao.doRetrieveByKey(id);
                if (prodotto != null) {
                    request.setAttribute("prodotto", prodotto);
                    request.setAttribute("mode", "update");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/form_prodotto.jsp");
                    dispatcher.forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/prodotti");
                }
                return;
            }

            if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                prodottoDao.doDelete(id);
                response.sendRedirect(request.getContextPath() + "/admin/prodotti");
                return;
            }

            //Se non c'è nessuna azione da fare mostra tutti i prodotti inclusi quelli non attivi
            List<Prodotto> prodotti = prodottoDao.doRetrieveAllAdmin();
            request.setAttribute("prodotti", prodotti);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/catalogo_admin.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            System.out.println("Errore SQL nel controllo prodotti admin: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore del server.");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/prodotti");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("insert".equals(action) || "update".equals(action)) {
            //Recupero e valido gli attributi del prodotto
        	List<String> errors = new ArrayList<>();
            String nome = validateField(request.getParameter("nome"), "Nome", errors);
            String descrizione = validateField(request.getParameter("descrizione"), "Descrizione", errors);
            String categoria = validateField(request.getParameter("categoria"), "Categoria", errors);
            String prezzoStr = request.getParameter("prezzo");
            String quantitaStr = request.getParameter("quantita");
            String imgPath = request.getParameter("img_path");
            boolean attivo = "true".equals(request.getParameter("attivo"));
            double prezzo = 0.0;
            //Controllo se c'è qualche errore nel campo prezzo
            try {
                if (prezzoStr == null || prezzoStr.trim().isEmpty()) {
                    errors.add("Il campo Prezzo è obbligatorio");
                } else {
                    prezzo = Double.parseDouble(prezzoStr);
                    if (prezzo <= 0) {
                        errors.add("Il prezzo deve essere maggiore di 0");
                    }
                }
            } catch (NumberFormatException e) {
                errors.add("Formato prezzo non valido. Utilizzare il punto come separatore (es: 19.99)");
            }
            //Controllo se c'è qualche errore nel campo quantita
            int quantita = 0;
            try {
                if (quantitaStr == null || quantitaStr.trim().isEmpty()) {
                    errors.add("Il campo Quantità è obbligatorio");
                } else {
                    quantita = Integer.parseInt(quantitaStr);
                    if (quantita < 0) {
                        errors.add("La quantità disponibile non può essere negativa");
                    }
                }
            } catch (NumberFormatException e) {
                errors.add("Formato quantità non valido. Inserire un numero intero");
            }
            //Controllo se c'è l'immagine
            if (imgPath == null || imgPath.trim().isEmpty()) {
                imgPath = "no-image.png"; //Inserisco un'immagine di default
            } else {
                imgPath = imgPath.trim();
            }

            //Ricostruisco l'oggetto per rimandarlo in caso di errore
            Prodotto prodotto = new Prodotto();
            prodotto.setNome(nome);
            prodotto.setDescrizione(descrizione);
            prodotto.setCategoria(categoria);
            prodotto.setPrezzo(prezzo);
            prodotto.setQuantitàDisponibile(quantita);
            prodotto.setAttivo(attivo);
            prodotto.setImgPath(imgPath);
            //Se l'azione da fare è un update setto anche l'id
            if ("update".equals(action)) {
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    prodotto.setId(id);
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/admin/prodotti");
                    return;
                }
            }

            if (!errors.isEmpty()) {
                request.setAttribute("errors", errors);
                request.setAttribute("prodotto", prodotto);
                request.setAttribute("mode", action);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/form_prodotto.jsp");
                dispatcher.forward(request, response);
                return;
            }

            try {	//Dal form arriva insert o update quindi se non è insert faccio l'update
                if ("insert".equals(action)) {
                    prodottoDao.doSave(prodotto);
                } else {
                    prodottoDao.doUpdate(prodotto);
                }
                //Ritorno alla pagina dei prodotti dell'admin
                response.sendRedirect(request.getContextPath() + "/admin/prodotti");
            } catch (SQLException e) {
                errors.add("Errore nel salvataggio del prodotto nel database: " + e.getMessage());
                request.setAttribute("errors", errors);
                request.setAttribute("prodotto", prodotto);
                request.setAttribute("mode", action);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin/form_prodotto.jsp");
                dispatcher.forward(request, response);
            }
        } else { //Se l'azione da fare non ha valore nè insert ne update non faccio nulla e torno sempre al catalogo
            response.sendRedirect(request.getContextPath() + "/admin/prodotti");
        }
    }

    private String validateField(String value, String fieldName, List<String> errors) {
        if (value == null || value.trim().isEmpty()) {
            errors.add("Il campo " + fieldName + " non può essere vuoto");
            return "";
        }
        return value.trim();
    }
}
