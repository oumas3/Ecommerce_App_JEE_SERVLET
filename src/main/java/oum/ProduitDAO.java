package oum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO {
    private static final String SELECT_ALL_PRODUCTS = "SELECT id, nom, prix FROM produit";
    private static Connection connection;

    public ProduitDAO() {
        connection = DatabaseConnection.getConnection();
    }

    public static List<Produit> getAllProducts() {
        List<Produit> products = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                double prix = resultSet.getDouble("prix");
                Produit product = new Produit(id, nom, prix);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
}
