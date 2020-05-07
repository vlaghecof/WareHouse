package dao;

import connection.ConnectionFactory;
import model.OrderItem;

import model.Products;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;


/**
 *
 * This class contains  some specifiec queries used by Products class only
 * @Author : Cofaru Vlad
 */
public class ProductsDAO extends AbstractDAO<Products> {






    public Products findByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "Select * FROM Products where ltrim(name)='" +name.strip()+"'";
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


    /**
     * It creates an update query for the products class
     * @param t
     * @return the query
     */
    public String createUpdateQuery(Products t) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append("Products");
        sb.append(" SET ");
        Boolean passedFirstArg = false;
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(t);
                if(passedFirstArg == false) {
                    passedFirstArg = true;
                    continue;
                }
                else {
                    sb.append(field.getName() + "='" + value + "',");
                }

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        sb.setLength(sb.length() - 1);
        sb.append(" WHERE ");
        int count = 0;
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(t);
                if(count == 0) {
                    count++;
                }
                else if(count == 1){
                    sb.append(" ltrim("+field.getName() + ")='" + value + "'");
                    count++;
                }
                else
                    break;

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * It executes the update of an object of type Products
     * @param t
     * @return null or the object
     */
    public Products update(Products t)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createUpdateQuery(t);
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            System.out.println(statement);

            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,  "DAO:Update  " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }


}
