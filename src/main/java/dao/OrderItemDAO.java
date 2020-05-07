package dao;

import connection.ConnectionFactory;
import model.OrderItem;
import model.Products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * This class contains some specific queries used only by OrderItem table
 * @Author : Cofaru Vlad
 */
public class OrderItemDAO extends AbstractDAO<OrderItem> {



    public List<OrderItem> FindAllProducts(String name)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "Select * FROM OrderItem where (clientName)='" +name.strip()+"'";
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();
            if(resultSet.next()) {
                resultSet.previous();
                return createObjects(resultSet);
            }

            return null;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,  "DAO:findByNameProducts " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }


    public OrderItem findByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "Select * FROM OrderItem where (clientName)='" +name.strip()+"'";
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
            LOGGER.log(Level.WARNING,  "DAO:findByName " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }




}
