package filter;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthFilter extends HttpFilter{
	private static final long serialVersionUID = 1L;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        String path = request.getServletPath();

        if (!path.startsWith("/admin/") && !path.startsWith("/common/")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        boolean autorizzato = false;
        
        if (role != null) {
            if (path.startsWith("/admin/")) {
                autorizzato = role.equals("admin");
            } else if (path.startsWith("/common/")) {
                autorizzato = role.equals("user") || role.equals("admin");
            }
        }
        if (autorizzato) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
