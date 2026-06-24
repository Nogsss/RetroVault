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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import dao.OrdineDao;
import dao.OrdineDaoImpl;
import model.Carrello;
import model.DettaglioOrdine;
import model.ElementoCarrello;
import model.Ordine;
import model.Utente;

@WebServlet("/common/checkout")
public class CheckoutControl extends HttpServlet {
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
        //Redirezione alla pagina di checkout avvenuto con successo
    	String status = request.getParameter("status");
        if ("success".equals(status)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/common/successo.jsp");
            dispatcher.forward(request, response);
            return;
        }
        //Recupero della sessione
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utente") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        //Recupero il carrello
        Carrello cart = (Carrello) session.getAttribute("carrello");
        if (cart == null || cart.getNumeroElementi() == 0) {
            response.sendRedirect(request.getContextPath() + "/catalogo");
            return;
        }
        //Se non si tratta di una chiamata di avvenuto successo, la sessione esiste e il carrello è pieno mandiamo al form di checkout
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/common/checkout.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //Recupero la sessione
    	HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utente") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        //Recupero dati utente e dati carrello
        Utente utente = (Utente) session.getAttribute("utente");
        Carrello cart = (Carrello) session.getAttribute("carrello");

        if (cart == null || cart.getNumeroElementi() == 0) {
            response.sendRedirect(request.getContextPath() + "/catalogo");
            return;
        }
        //Recupero e valido i dati di fatturazione dal form di checkout
        List<String> errors = new ArrayList<>();
        String nome = validateField(request.getParameter("nome"), "Nome", errors);
        String cognome = validateField(request.getParameter("cognome"), "Cognome", errors);
        String telefono = validateField(request.getParameter("telefono"), "Telefono", errors);
        String viaNum = validateField(request.getParameter("via_num"), "Via e Numero", errors);
        String citta = validateField(request.getParameter("citta"), "Città", errors);
        String cap = validateField(request.getParameter("cap"), "CAP", errors);
        String provincia = validateField(request.getParameter("provincia"), "Provincia", errors);
        String metodoPagamento = validateField(request.getParameter("metodo_pagamento"), "Metodo di Pagamento", errors);
        //Se ci sono stati errori salva i dati inseriti per ripopolare il form
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("nome", nome);
            request.setAttribute("cognome", cognome);
            request.setAttribute("telefono", telefono);
            request.setAttribute("via_num", viaNum);
            request.setAttribute("citta", citta);
            request.setAttribute("cap", cap);
            request.setAttribute("provincia", provincia);
            request.setAttribute("metodo_pagamento", metodoPagamento);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/common/checkout.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Creazione dell'ordine
        Ordine ordine = new Ordine();
        ordine.setIdUtente(utente.getId());
        ordine.setNome(nome);
        ordine.setCognome(cognome);
        ordine.setTelefono(telefono);
        ordine.setViaNum(viaNum);
        ordine.setCitta(citta);
        ordine.setCap(cap);
        ordine.setProvincia(provincia.toUpperCase());
        ordine.setMetodoPagamento(metodoPagamento);
        // ID transazione fittizio generato randomicamente
        ordine.setIdTransazione(UUID.randomUUID().toString().substring(0, 10));
        
        double totale = 0.0;
        List<DettaglioOrdine> dettagli = new ArrayList<>();
        //Creazione dei dettagli ordine
        synchronized (session) {
            totale = cart.prezzoTotale();
            for (ElementoCarrello item : cart.getProdotti()) {
                DettaglioOrdine dettaglio = new DettaglioOrdine();
                //L'id dell'ordine viene inserito dal dao
                dettaglio.setIdProdotto(item.getProdotto().getId());
                dettaglio.setQuantita(item.getQuantità());
                dettaglio.setPrezzoAcquisto(item.getProdotto().getPrezzo());
                dettagli.add(dettaglio);
            }
        }
        ordine.setTotale(totale);
        ordine.setDettagli(dettagli);

        try {
            //Salvataggio dell'ordine e dei dettagli
            ordineDao.doSave(ordine);
            
            //Svuoto il carrello nella sessione
            synchronized (session) {
                cart.svuota();
                session.setAttribute("carrello", cart);
            }
            
            //Redirect alla pagina di checkout avvenuto con successo che verrà catturato dal metodo doGet
            response.sendRedirect(request.getContextPath() + "/common/checkout?status=success");
        } catch (SQLException e) {
            errors.add("Si è verificato un errore durante il salvataggio dell'ordine. Riprova più tardi.");
            request.setAttribute("errors", errors);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/common/checkout.jsp");
            dispatcher.forward(request, response);
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
