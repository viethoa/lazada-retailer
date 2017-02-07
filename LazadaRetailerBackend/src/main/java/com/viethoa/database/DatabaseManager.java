package com.viethoa.database;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by VietHoa on 17/01/2017.
 */
public class DatabaseManager {

    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/lazada";  // database name
    private static final String driverClassName = "org.postgresql.Driver";
    private static final String DB_USERNAME = "postgres";                                   // user
    private static final String DB_PASSWORD = "";

    private static DataSource dataSource;
    private static JdbcTemplate jdbcTemplate;

    public static DataSource getDataSource() {
        if (dataSource != null) {
            return dataSource;
        }

        DriverManagerDataSource managerDataSource = new DriverManagerDataSource();
        managerDataSource.setDriverClassName(driverClassName);
        managerDataSource.setUrl(DB_CONNECTION);
        managerDataSource.setUsername(DB_USERNAME);
        managerDataSource.setPassword(DB_PASSWORD);
        dataSource = managerDataSource;
        return dataSource;
    }

    public static JdbcTemplate getJdbcTemplate() {
        if (jdbcTemplate != null) {
            return jdbcTemplate;
        }

        jdbcTemplate = new JdbcTemplate(getDataSource());
        return jdbcTemplate;
    }

    public void createTables() throws SQLException {
        Connection dbConnection = null;
        try {
            dbConnection = getDataSource().getConnection();
            createTableUser(dbConnection);
            createTableOrder(dbConnection);
            createTableStore(dbConnection);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    private void createTableUser(Connection connection) throws SQLException {
        String createTableSQL = String.format("CREATE TABLE %s("
                        + "%s SERIAL UNIQUE, "
                        + "%s VARCHAR(100) NOT NULL, "
                        + "%s VARCHAR(100) NOT NULL, "
                        + "%s VARCHAR(100) NOT NULL, "
                        + "%s VARCHAR(50) NOT NULL, "
                        + "%s VARCHAR(300), "
                        + "%s VARCHAR(50) NOT NULL, "
                        + "%s BIGINT NOT NULL, "
                        + "PRIMARY KEY (%s))",
                UserDao.TABLE_NAME,
                UserDao.ID,
                UserDao.EMAIL,
                UserDao.PASSWORD,
                UserDao.NAME,
                UserDao.PHONE,
                UserDao.ADDRESS,
                UserDao.AUTHORITY,
                UserDao.CREATED_AT,
                UserDao.ID);

        PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL);
        System.out.println(createTableSQL);
        preparedStatement.executeUpdate();
        System.out.println("Table \"t_user\" is created!");
        preparedStatement.close();
    }

    private void createTableOrder(Connection connection) throws SQLException {
        String createTableSQL = String.format("CREATE TABLE %s("
                        + "%s SERIAL UNIQUE, "
                        + "%s VARCHAR(100) NOT NULL, "
                        + "%s VARCHAR(100) NOT NULL, "
                        + "%s INTEGER NOT NULL, "
                        + "%s BIGINT NOT NULL, "
                        + "%s BIGINT NOT NULL, "
                        + "PRIMARY KEY (%s))",
                OrderDao.TABLE_NAME,
                OrderDao.ID,
                OrderDao.ORDER_NO,
                OrderDao.STATE,
                OrderDao.STORE_ID,
                OrderDao.INSERT_AT,
                OrderDao.UPDATE_AT,
                UserDao.ID);

        PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL);
        System.out.println(createTableSQL);
        preparedStatement.executeUpdate();
        System.out.println("Table \"t_order\" is created!");
        preparedStatement.close();
    }

    private void createTableStore(Connection connection) throws SQLException {
        String createTableSQL = String.format("CREATE TABLE %s("
                        + "%s SERIAL UNIQUE, "
                        + "%s VARCHAR(200) NOT NULL, "
                        + "%s INTEGER NOT NULL, "
                        + "%s BIGINT NOT NULL, "
                        + "PRIMARY KEY (%s))",
                StoreDao.TABLE_NAME,
                StoreDao.ID,
                StoreDao.NAME,
                StoreDao.USER_ID,
                StoreDao.CREATED_AT,
                StoreDao.ID);

        PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL);
        System.out.println(createTableSQL);
        preparedStatement.executeUpdate();
        System.out.println("Table \"t_store\" is created!");
        preparedStatement.close();
    }
}
