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


public class EnregistrerCommande extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CommandeDAO commandeDAO;
    private ProduitDAO produitDAO;

    public void init() throws ServletException {
        commandeDAO = new CommandeDAO();
        produitDAO = new ProduitDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String nom = (String) session.getAttribute("nom");
        @SuppressWarnings("unchecked")
		List<Produit> panier = (List<Produit>) session.getAttribute("panier");
     
       
        commandeDAO.ajouterCommande(nom, panier);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body style='background-color: #F5F5DC;'>");
        out.println("<h2>Bonjour " + nom + ", voici votre commande</h2>");
        out.println("<p>Votre panier contient " + panier.size() + " article(s).</p>");
        out.println("<table style='border-collapse: collapse; width: 100%;'>");
        out.println("<tr><th style='border: 1px solid #000; padding: 8px;'>Produit</th><th style='border: 1px solid #000; padding: 8px;'>Prix unitaire</th></tr>");
        for (Produit produit : panier) {
            out.println("<tr><td style='border: 1px solid #000; padding: 8px;'>" + produit.getNom() + "</td><td style='border: 1px solid #000; padding: 8px;'>" + produit.getPrix() + " €</td></tr>");
        }
        out.println("</table>");
        out.println("<p>Vous pouvez <a href='commande'>commander à nouveau</a>.</p>");
        out.println("<p>Vous pouvez <a href='achat'>retourner à la page menu</a>.</p>");
        out.println("</body></html>");
    }
}
