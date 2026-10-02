package com.miempresa.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.miempresa.conexion.Conexion;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(UsuarioServlet.class.getName());
    private static final String SQL = "SELECT * FROM usuarios LIMIT 1000";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (Connection conexion = Conexion.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(SQL);
             ResultSet resultado = sentencia.executeQuery()) {

            ResultSetMetaData metaDatos = resultado.getMetaData();
            int numeroColumnas = metaDatos.getColumnCount();

            List<String> columnas = new ArrayList<>();
            for (int i = 1; i <= numeroColumnas; i++) {
                columnas.add(metaDatos.getColumnLabel(i));
            }

            List<List<Object>> filas = new ArrayList<>();
            while (resultado.next()) {
                List<Object> fila = new ArrayList<>();
                for (int i = 1; i <= numeroColumnas; i++) {
                    fila.add(resultado.getObject(i));
                }
                filas.add(fila);
            }

            request.setAttribute("columnas", columnas);
            request.setAttribute("filas", filas);

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error al consultar la base de datos", e);
            request.setAttribute("error", "No se pudo consultar la base de datos.");
        }

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}