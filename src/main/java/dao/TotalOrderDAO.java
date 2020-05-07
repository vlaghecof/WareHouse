package dao;

import connection.ConnectionFactory;
import model.Products;
import model.TotalOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 *
 * This class contains  some specific queries for the TotalOrder class
 * @Author : Cofaru Vlad
 */
public class TotalOrderDAO extends AbstractDAO<TotalOrder> {

    /**
     * It executes a find by name in the total order table
     * @param name
     * @return the object if found
     */
    public TotalOrder findByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "Select * FROM TotalOrder where trim(name)='" +name.strip()+"'";
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();
            if(resultSet.next()) {
                resultSet.previous();
                return createObjects(resultSet).get(0);
            }

            return null;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,  "DAO:findByNameD " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

}
