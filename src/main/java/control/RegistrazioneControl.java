package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

@WebServlet("/registrazione")
public class RegistrazioneControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    UtenteDao utenteDao;
    
	@Override
	public void init(ServletConfig servletConfig) throws ServletException{
		super.init(servletConfig);
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		if(ds == null) throw new ServletException("DataSource non disponibile nel contesto");
		utenteDao = new UtenteDaoImpl(ds);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Se qualcuno prova ad accedere tramite get viene rimandato al form di registrazione
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/common/registrazione.jsp");
        dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<>();
        String nome = validateField(request.getParameter("nome"), "nome", errors);
        String cognome = validateField(request.getParameter("cognome"), "cognome", errors);
        String email = validateField(request.getParameter("email"), "email", errors);
        String password = validateField(request.getParameter("password"), "password", errors);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/common/registrazione.jsp");
        
        //Se ci sono stati degli errori nel formato delle credenziali si viene rimandati alla pagina di registrazione
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            dispatcher.forward(request, response);
            return;
        }

        String passwordDigest = toDigest(password);

        Utente nuovoUtente = new Utente();
        nuovoUtente.setNome(nome);
        nuovoUtente.setCognome(cognome);
        nuovoUtente.setEmail(email);
        nuovoUtente.setPassword(passwordDigest);
        nuovoUtente.setAdmin(false);

        try {
            utenteDao.doSave(nuovoUtente);
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (SQLException e) {
            errors.add("Errore durante la registrazione.");
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
	
	private static String toDigest(String password) {
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
