package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Utente;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dao.UtenteDao;
import dao.UtenteDaoImpl;


@WebServlet("/login")
public class LoginControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtenteDao utenteDao;
       
	@Override
	public void init(ServletConfig servletConfig) throws ServletException{
		super.init(servletConfig);
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		if(ds == null) throw new ServletException("DataSource non disponibile nel contesto");
		utenteDao = new UtenteDaoImpl(ds);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Se qualcuno prova ad accedere tramite get viene rimandato alla pagina di login
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/common/login.jsp");
        dispatcher.forward(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        email = validateField(email, "email", errors);
        password = validateField(password, "password", errors);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/common/login.jsp");
        
        //Se ci sono stati degli errori nel formato delle credenziali si viene rimandati alla pagina di login
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            dispatcher.forward(request, response);
            return;
        }
        
        String passwordDigest = LoginControl.toDigest(password);
        try {
            Utente utente = utenteDao.doRetrieveByEmailPassword(email, passwordDigest);
            if (utente != null) {	//Se utente non è vuoto c'è sicuramente stata una corrispondenza di credenziali nel db quindi sono valide
                HttpSession session = request.getSession(true);
                session.setAttribute("utente", utente);	//Inserisco i dati dell'utente nella sessione per comodità
                //Setto il ruolo dell'utente nella sessione ma rimando comunque al catalogo
                if (utente.isAdmin()) {
                    session.setAttribute("role", "admin");
                } else {
                    session.setAttribute("role", "user");
                }
                response.sendRedirect(request.getContextPath() + "/catalogo");
            } else {
                errors.add("Email o password non validi.");
                request.setAttribute("errors", errors);
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            errors.add("Errore del server. Riprova più tardi.");
            request.setAttribute("errors", errors);
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
	
	public static String toDigest(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] digestBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digestBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo SHA-512 non disponibile", e);
        }
    }
}
