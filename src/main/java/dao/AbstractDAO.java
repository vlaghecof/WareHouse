package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;


/**
 *
 *  This is the class resposible for handling generic queries for each class of type T
 * @param <T>
 *
 *   @Author:Cofaru Vlad George
 */
public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	}

	/**
	 * This function recives the field of the class as a string and generates a select query based on that field
	 * @param field  of the table we wand to serch by
	 * @return The query
	 */
	private String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE " + field + " =?");
		return sb.toString();
	}

	/**
	 * This function recives the field of the class as a string and generates a delete query based on that field
	 * @param field  of the table we wand to delete
	 * @return The query
	 */
	private String createDeleteByNameQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("Delete ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE " + field + " =?");
		return sb.toString();
	}

	private String createSelectAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		return sb.toString();
	}


	/** This function recives an object of the class Insert query based on that field
	 * @param t  the object with the data we want to insert
	 * @return the query
	 */
	public String createInsertQuery(T t) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(type.getSimpleName() + "(");
		for (Field field : t.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try {
				sb.append(field.getName() + ",");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

		}
		sb.setLength(sb.length()-1);
		sb.append(") VALUES (");
		for (Field field : t.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			Object value;
			try {
				value = field.get(t);
				sb.append("'" +value + "',");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}
		sb.setLength(sb.length()-1);
		sb.append(")");
		return sb.toString();
	}

	private String createDeleteQuery2(T t) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE ");
		sb.append("FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE ");
		Boolean count = false;
		for (Field field : t.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			Object value;
			try {
				value = field.get(t);
				if(count == false) {
					count = true;
					continue;
				}
				else {
					sb.append(field.getName() + "='" + value + "' AND ");
				}

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}
		sb.setLength(sb.length() - 4);
		return sb.toString();
	}


	/**
	 * This function recives the field of the class as an object and generates an Update query based on that field
	 * @param t
	 * @return
	 */
	public String createUpdateQuery(T t) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(type.getSimpleName());
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
					sb.append(field.getName() + "='" + value + "'");
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
	 * This function retuens all entried from a table of type T
	 * @return A list of all the elements
	 */
	public List<T> findAll() {


		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectAll();
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();

			return createObjects(resultSet);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;

	}

	/**
	 * This function executes the find by id function in the database
	 * @param id
	 * @return the value of the entry at index id if there is one
	 */
	public T findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("id");
		System.out.println(query);
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			System.out.println(statement);


			return createObjects(resultSet).get(0);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}


	/**
	 * It looks for an entry in the database with the name specified in name
	 * @param name
	 * @return the entry
	 */
	public T findByName(String name) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("name");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setString (1, name);
			resultSet = statement.executeQuery();
			System.out.println(statement);
			if(createObjects(resultSet).isEmpty())
				return null;

			return createObjects(resultSet).get(0);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findByName " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	public List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();

		try {
			while (resultSet.next()) {
				T instance = type.newInstance();
				for (Field field : type.getDeclaredFields()) {
					Object value = resultSet.getObject(field.getName());
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * This functions creates a new entry in the database with the data from object t
	 * @param t
	 * @return
	 */
	public T insert(T t) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createInsertQuery(t);
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.executeUpdate();
			System.out.println(statement);
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				return t;
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:Insert  " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}


	public T delete2(T t)
	{

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createDeleteQuery2(t);

		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.executeUpdate();

			return null;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:Delete  " + e.getMessage());
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	private String createDeleteQuery(T t) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE ");
		sb.append("FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE ");
		Boolean passedFirstArg = false;
		for (Field field : t.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			Object value;
			Integer nul = 0;
			Double nul2 = 0.0;
			try {
				value = field.get(t);
				if(passedFirstArg == false) {
					passedFirstArg = true;
					continue;
				}
				else {

					if(value != null && !(value.equals(nul)) && !(value.equals(nul2))  )
						sb.append(field.getName() + "='" + value + "' AND ");
				}

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}
		sb.setLength(sb.length() - 4);
		System.out.println(sb.toString());
		return sb.toString();
	}


	/**
	 * It removes the object with the same data as object T from the database
	 * @param t
	 * @return
	 */
	public T delete(T t) {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = createDeleteQuery(t);
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.executeUpdate(query);
			System.out.println(statement);
			return t;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:Delete " + e.getMessage());
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	public void deleteBy(String name) {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = createDeleteByNameQuery("name");

		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, name);
			System.out.println(statement);
			statement.executeUpdate();

				return ;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:DeleteBy " + e.getMessage());
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return ;
	}

	/**
	 * It updates an element from the database with the data found in T
	 * @param t
	 * @return
	 */
	public T update(T t)
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


			return t;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:Delete  " + e.getMessage());
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}






}
