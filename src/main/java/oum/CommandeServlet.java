package oum;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/commande")
public class CommandeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CommandeDAO commandeDAO;
    private ProduitDAO produitDAO;

    public void init() throws ServletException {
        commandeDAO = new CommandeDAO();
        produitDAO = new ProduitDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Produit> products = produitDAO.getAllProducts();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Commande</h2>");
        out.println("<form method='post'>");
        out.println("<select name='productId'>");
        for (Produit product : products) {
            out.println("<option value='" + product.getId() + "'>" + product.getNom() + "</option>");
        }
        out.println("</select>");
        out.println("<input type='submit' value='Ajouter à la commande'>");
        out.println("</form>");
        out.println("</body></html>");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String nom = (String) session.getAttribute("nom");

       
        int productId = Integer.parseInt(request.getParameter("productId"));

        Integer userIdObject = (Integer) session.getAttribute("userId");
        int userId = userIdObject != null ? userIdObject : 0; 

        commandeDAO.ajouterCommande(productId, userId);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Bonjour " + nom + ", voici votre commande</h2>");
        out.println("<p>Vous pouvez <a href='commande'>commander à nouveau</a>.</p>");
        out.println("<p>Vous pouvez <a href='achat'>retourner à la page menu</a>.</p>");
        out.println("</body></html>");
    }

}
