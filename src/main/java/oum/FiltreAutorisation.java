package oum;
import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FiltreAutorisation implements Filter {
	private FilterConfig filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest hrequest = (HttpServletRequest) request;
        HttpServletResponse hresponse = (HttpServletResponse) response;
        Cookie[] cookies = hrequest.getCookies();

        if (cookies == null || !cookieExists(cookies, "nom")) {
            hresponse.sendRedirect("/servlet/inscription");
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean cookieExists(Cookie[] cookies, String name) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void destroy() {
        this.filterConfig = null;
    }
}