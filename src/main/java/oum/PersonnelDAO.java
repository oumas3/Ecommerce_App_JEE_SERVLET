package oum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonnelDAO {
    private static final String SELECT_ALL_PERSONNEL = "SELECT  nom FROM personnel";
    private Connection connection;

    public PersonnelDAO() {
        connection = DatabaseConnection.getConnection();
    }

    public List<String> getAllUserNames() {
        List<String> userNames = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PERSONNEL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userName = resultSet.getString("nom");
                userNames.add(userName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
      return userNames;
    }
    public int getUserIdByName(String userName) {
        int userId = -1;

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT numero FROM personnel "
        		+ "WHERE nom = ?")) {
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt("numero");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
     return userId;
    }}
