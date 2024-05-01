package oum;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Liste des Produits</title>");
        out.println("<style>");
        out.println("table { border-collapse: collapse; width: 50%; margin: 20px auto; }");
        out.println("th, td { border: 1px solid black; padding: 8px; text-align: left; }");
        out.println("th { background-color: orange; color: white; }");
        out.println("tr:nth-child(even) { background-color: beige; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<h1>Liste des Produits</h1>");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
                 connection = DatabaseConnection.getConnection();

            String query = "SELECT * FROM produit";
            preparedStatement = connection.prepareStatement(query);

            resultSet = preparedStatement.executeQuery();

            out.println("<table>");
            out.println("<tr><th>Nom</th><th>Prix</th><th>Action</th></tr>");
            while (resultSet.next()) {
                String productName = resultSet.getString("nom");
                double productPrice = resultSet.getDouble("prix");
                int productId = resultSet.getInt("id");
                out.println("<tr>");
                out.println("<td>" + productName + "</td>");
                out.println("<td>" + productPrice + " Euros</td>");
                out.println("<td><a href='commande?element=disque&code=" + productId + "&ordre=ajouter&prix=" 
                + productPrice + "'>Commander</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        out.println("</body></html>");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
