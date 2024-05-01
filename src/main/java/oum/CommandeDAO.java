package oum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CommandeDAO {
    private static final String INSERT_COMMAND_SQL = "INSERT INTO commande (id_produit, qui) VALUES (?, ?)";
    private Connection connection;

    public CommandeDAO() {
        connection = DatabaseConnection.getConnection();
    }

    public void ajouterCommande(int name, int userId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMMAND_SQL)) {
            preparedStatement.setInt(1, name);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public void ajouterCommande(String nom, List<Produit> panier) {
		
		
	}
}
