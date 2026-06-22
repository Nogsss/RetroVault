package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import dao.ProdottoDao;
import dao.ProdottoDaoImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;

@WebServlet("/catalogo")
public class ProdottoControl extends HttpServlet {
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
		try {
			List<Prodotto> prodotti = prodottoDao.doRetrieveAll();
			request.setAttribute("prodotti", prodotti);
		} catch (SQLException e) {
			System.out.println("Errore nel catalogo prodotti: " + e.getMessage());
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/common/catalogo.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
