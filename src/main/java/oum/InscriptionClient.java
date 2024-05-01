package oum;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class InscriptionClient extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomRecu = request.getParameter("nom");
        String motPasseRecu = request.getParameter("motdepasse");
        String nomCookie = getCookieValue(request, "nom");
        String motPasseCookie = getCookieValue(request, "motdepasse");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Inscription d'un client</title>");
        out.println("</head>");
        out.println("<body bgcolor= #F3EFE0>");
        out.println("<h3>Valeurs des cookies :</h3>");
        out.println("<p>Nom cookie : " + nomCookie + "</p>");
        out.println("<p>Mot de passe cookie : " + motPasseCookie + "</p>");
//CAS 1
        if (nomCookie == null && nomRecu == null) {
            showInscriptionForm(out);
 
        } else if (nomCookie == null && nomRecu != null) {
            createCookies(response, "nom", nomRecu);
            createCookies(response, "motdepasse", motPasseRecu);
            int userId = getUserIdFromDatabase(nomRecu, motPasseRecu);
            if (userId != 0) {
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", userId);
                response.sendRedirect(request.getContextPath() + "/servlet/commande");
            } else {
                showIdentificationForm(out);
            }
        } else if (identique(nomRecu, nomCookie) && identique(motPasseRecu, motPasseCookie)) {
            response.sendRedirect(request.getContextPath() + "/servlet/commande");
        } else {
            showIdentificationForm(out);
        }
    }

    private void showInscriptionForm(PrintWriter out) {
        out.println("<h3>Bonjour, vous devez vous inscrire</h3>");
        out.println("<h3>Attention mettre nom et le mot de passe avec plus de 3 caractères</h3>");
        out.println("<form action='sinscrire' method='GET' >");
        out.println("nom :<input type='text' size='20' name='nom'><br>");
        out.println("mot de passe : <input type='password' size='20' name='motdepasse'><br>");
        out.println("<input type='submit' value='inscription'>");
        out.println("</form>");
    }
    private void showIdentificationForm(PrintWriter out) {
        out.println("<h3>Merci de vous identifier</h3>");
        out.println("<form action='sinscrire' method='GET' >");
        out.println("nom : <input type='text' size='20' name='nom'><br>");
        out.println("mot de passe :<input type='password' size='20' name='motdepasse'><br>");
        out.println("<input type='submit' value='identifier'>");
        out.println("</form>");
    }
    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    private void createCookies(HttpServletResponse response, String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        response.addCookie(cookie);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
    boolean identique(String recu, String cookie) {
        return (recu != null && recu.length() > 3 && cookie != null && recu.equals(cookie));
    }

    private int getUserIdFromDatabase(String username, String password) {
        if (username.equals("example") && password.equals("password")) {
            return 1; 
        } else {
            return 0; 
        }
    }
}
