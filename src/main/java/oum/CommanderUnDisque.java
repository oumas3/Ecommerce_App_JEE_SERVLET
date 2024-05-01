package oum;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CommanderUnDisque extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CommandeDAO commandeDAO;
    private PersonnelDAO personnelDAO;

    public void init() throws ServletException {
        commandeDAO = new CommandeDAO();
        personnelDAO = new PersonnelDAO();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Votre commande</title>");
        out.println("<style>");
        out.println("table {");
        out.println("    border-collapse: collapse;");
        out.println("    width: 50%;");
        out.println("}");
        out.println("th, td {");
        out.println("    border: 1px solid black;");
        out.println("    padding: 8px;");
        out.println("    text-align: center;");
        out.println("}");
        out.println("th {");
        out.println("    background-color: #FFA500;"); // Orange
        out.println("}");
        out.println("td {");
        out.println("    background-color: #F5F5DC;"); // Beige
        out.println("}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>Bonjour Oumaima, voici votre commande</h3>");

        
        HttpSession session = request.getSession();
        int nombreArticles = 0;
        Enumeration<String> attributs = session.getAttributeNames();
        while (attributs.hasMoreElements()) {
            String attribut = attributs.nextElement();
            if (attribut.startsWith("produit_")) {
                nombreArticles++;
            }
        }
        out.println("<p>Votre panier contient " + nombreArticles + " article(s)</p>");

        out.println("<h2>Votre Commande</h2>");
        out.println("<table>");
        out.println("<tr><th>Produit</th><th>Prix</th></tr>");

        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT commande.id, produit.nom AS nom_produit, produit.prix AS prix_produit FROM commande JOIN produit ON commande.id_produit = produit.id");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String nomProduit = resultSet.getString("nom_produit");
                double prixProduit = resultSet.getDouble("prix_produit");
                out.println("<tr><td>" + nomProduit + "</td><td>" + prixProduit + " €</td></tr>");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("</table>");
        out.println("<br><a href=\"achat\">Vous pouvez commander à nouveau</a>");
        out.println("<br><a href=\"vider\">Vider le panier</a>");
        out.println("<br><a href=\"sinscrire\">Vous pouvez retourner à la page menu</a>");
        out.println("</body>");
        out.println("</html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
