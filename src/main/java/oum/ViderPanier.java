package oum;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ViderPanier extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DatabaseConnection database;

    public void init() throws ServletException {
        super.init();
        database = DatabaseConnection.getInstance();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, 
    ServletException {
        String nom = null;
        Cookie[] cookies = request.getCookies();
        nom = Identification.verifier(cookies);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<head>");
        out.println("<title> Vider le panier </title>");
        out.println("</head>");
        out.println("<body bgcolor=\"white\">");
        out.println("<h3>" + "Bonjour "+ nom + ", nous vidons votre panier." + "</h3>");

        HttpSession session = request.getSession();
        session.invalidate();

        out.println("<A HREF=achat> Vous pouvez commander à nouveau </A>");
        out.println("<br> <A HREF=sinscrire> Vous pouvez retourner à la page menu </A>");
        out.println("</body>");
        out.println("</html>");
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
