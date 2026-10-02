package com.miempresa.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // Configurables por variables de entorno; los valores por defecto son solo para desarrollo local.
    private static final String URL = env("DB_URL", "jdbc:mysql://localhost:3306/mi_base_datos");
    private static final String USUARIO = env("DB_USER", "root");
    private static final String PASSWORD = env("DB_PASSWORD", "root");

    private Conexion() {
    }

    public static Connection conectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL en el classpath", e);
        }
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }

    private static String env(String nombre, String porDefecto) {
        String valor = System.getenv(nombre);
        return (valor == null || valor.isBlank()) ? porDefecto : valor;
    }
}