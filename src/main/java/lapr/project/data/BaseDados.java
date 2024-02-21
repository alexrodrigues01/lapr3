package lapr.project.data;

import java.sql.*;

/**
 * Classe cujas instâncias manipulam dados de BD Oracle.
 */
public class BaseDados {

    private BaseDados(){
    }

    /**
     * O URL da BD.
     */
    private static  String jdbcUrl ;

    /**
     * O nome de utilizador da BD.
     */
    private static  String username ;

    /**
     * A password de utilizador da BD.
     */
    private static String password ;

    /**
     * A ligação à BD.
     */
    private static Connection connection;

    /**
     * A invocação de "stored procedures".
     */
    private static CallableStatement callStmt;

    /**
     * Conjunto de resultados retornados por "stored procedures".
     */
    private static ResultSet rSet;

    /**
     * Estabelece a ligação à BD.
     */
    private static void openConnection() {
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fecha os objetos "ResultSet", "CallableStatement" e "Connection", e
     * retorna uma mensagem de erro se alguma dessas operações não for bem
     * sucedida. Caso contrário retorna uma "string" vazia.
     */
    public static String closeAll() {

        StringBuilder message = new StringBuilder();

        if (rSet != null) {
            try {
                rSet.close();
            } catch (SQLException ex) {
                message.append(ex.getMessage());
                message.append("\n");
            }
            rSet = null;
        }

        if (callStmt != null) {
            try {
                callStmt.close();
            } catch (SQLException ex) {
                message.append(ex.getMessage());
                message.append("\n");
            }
            callStmt = null;
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                message.append(ex.getMessage());
                message.append("\n");
            }
            connection = null;
        }
        return message.toString();
    }

    /**
     * getter para a connection
     * @return connection
     */
    public static Connection getConnection() {
        if (connection == null)
            openConnection();
        return connection;
    }

    /**
     * fecha a connection
     * @return mensagem
     */
    public static String closeConnection(){

        StringBuilder message = new StringBuilder();

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                message.append(ex.getMessage());
                message.append("\n");
            }
            connection = null;
        }
        return message.toString();
    }

    /**
     * setter para jdbcurl
     * @param jdbcUrl url do jdbc
     */
    public static void setJdbcUrl(String jdbcUrl) {
        BaseDados.jdbcUrl = jdbcUrl;
    }

    /**
     * setter para username
     * @param username username
     */
    public static void setUsername(String username) {
        BaseDados.username = username;
    }

    /**
     * setter para password
     * @param password palavra chave
     */
    public static void setPassword(String password) {
        BaseDados.password = password;
    }

    /**
     * getter para jdbcurl
     * @return jdbcurl
     */
    public static String getJdbcUrl() {
        return jdbcUrl;
    }

    /**
     * getter para username
     * @return username
     */
    public static String getUsername() {
        return username;
    }

    /**
     * getter para password
     * @return password
     */
    public static String getPassword() {
        return password;
    }
}
