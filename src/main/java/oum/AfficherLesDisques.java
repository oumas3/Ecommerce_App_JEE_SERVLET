package oum;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class AfficherLesDisques extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Stock uneVente = new Stock();
        String nom = null;
        Cookie[] cookies = request.getCookies();
        nom = Identification.chercheNom(cookies);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<head>");
        out.println("<title> Commande de disques </title>");
        out.println("</head>");
        out.println("<body bgcolor=\"white\">");
        out.println("<h3>" + "Bonjour " + nom + ", vous pouvez commander un disque" + "</h3>");
        
        out.println("<form action=\"commande\"method=\"post\">");
        uneVente.vente(out);
        out.println("</form>");
        
        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

}
