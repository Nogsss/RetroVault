package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Carrello;
import model.Prodotto;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.json.JSONObject;

import dao.ProdottoDao;
import dao.ProdottoDaoImpl;

@WebServlet("/carrello")
public class CarrelloControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ProdottoDao prodottoDao;
       
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException{
		super.init(servletConfig);
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		if(ds == null) throw new ServletException("DataSource non disponibile nel contesto");
		prodottoDao = new ProdottoDaoImpl(ds);
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
        Carrello cart = (Carrello) session.getAttribute("carrello");
        if (cart == null) {
            cart = new Carrello();
            session.setAttribute("carrello", cart);
        }

        String action = request.getParameter("action");
        
        //ajax
        try {
            if ("addAjax".equals(action)) {
                int idProdotto = Integer.parseInt(request.getParameter("id"));
                int quantita = Integer.parseInt(request.getParameter("quantita"));
                Prodotto prodotto = prodottoDao.doRetrieveByKey(idProdotto);
                
                if (prodotto != null) {
                    cart.aggiungiProdotto(prodotto, quantita);
                    session.setAttribute("carrello", cart);
                }

                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                JSONObject json = new JSONObject();
                
                json.put("nuovoTotale", cart.getProdotti().size()); 
                out.print(json.toString());
                
                return;
            }
            
           //Inserire altre operazioni

        } catch (SQLException e) {
            System.out.println("Errore Database: " + e.getMessage());
        }
        
        
        //Non chiamata ajax
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/common/carrello.jsp");
        dispatcher.forward(request, response);
    }
}
