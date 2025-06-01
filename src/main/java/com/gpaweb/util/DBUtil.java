package com.gpaweb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    // IMPORTANTE: Reemplaza con tus credenciales y URL de MySQL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/agric_app_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root"; // Ejemplo: root, o el usuario que creaste
    private static final String DB_PASSWORD = "123456789"; // Ejemplo: admin, o la contraseña de tu usuario

    static {
        try {
            // Cargar el driver JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // En una aplicación real, esto debería ser manejado más robustamente (logs, excepción de runtime)
            System.err.println("Error Crítico: No se pudo cargar el driver JDBC de MySQL.");
            System.err.println("Asegúrate de que mysql-connector-java.jar esté en el classpath o definido en pom.xml.");
            e.printStackTrace();
            // throw new RuntimeException("No se pudo cargar el driver de MySQL.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Método de prueba (opcional, para verificar la conexión rápidamente desde la consola)
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("¡Conexión a la base de datos '" + DB_URL + "' establecida exitosamente!");
            } else {
                System.out.println("Falló la conexión a la base de datos.");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            System.err.println("Verifica la URL, usuario, contraseña y que el servidor MySQL esté corriendo.");
            e.printStackTrace();
        }
    }
} 