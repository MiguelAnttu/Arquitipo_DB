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

import com.miempresa.conexion.Conexion;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

	@Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String sql = "SELECT * FROM usuarios";

        try (
            Connection conexion = Conexion.conectar();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultado = sentencia.executeQuery()
        ) {

            ResultSetMetaData metaDatos = resultado.getMetaData();

            int numeroColumnas = metaDatos.getColumnCount();

            StringBuilder tabla = new StringBuilder();

            tabla.append("<table border='1'>");

            tabla.append("<tr>");

            for (int i = 1; i <= numeroColumnas; i++) {
                tabla.append("<th>");
                tabla.append(metaDatos.getColumnName(i));
                tabla.append("</th>");
            }

            tabla.append("</tr>");

            while (resultado.next()) {

                tabla.append("<tr>");

                for (int i = 1; i <= numeroColumnas; i++) {

                    tabla.append("<td>");
                    tabla.append(resultado.getObject(i));
                    tabla.append("</td>");
                }

                tabla.append("</tr>");
            }

            tabla.append("</table>");

            request.setAttribute("tabla", tabla.toString());

            request.getRequestDispatcher("/index.jsp")
                   .forward(request, response);

        } catch (Exception e) {

            request.setAttribute(
                    "error",
                    "Error al consultar la base de datos: "
                    + e.getMessage()
            );

            request.getRequestDispatcher("/index.jsp")
                   .forward(request, response);
        }
    }
}