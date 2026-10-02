<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.List" %>
<%!
    // Escapa HTML para evitar XSS; null se muestra como cadena vacía
    private static String esc(Object valor) {
        if (valor == null) {
            return "";
        }
        String s = String.valueOf(valor);
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '&':  sb.append("&amp;");  break;
                case '<':  sb.append("&lt;");   break;
                case '>':  sb.append("&gt;");   break;
                case '"':  sb.append("&quot;"); break;
                case '\'': sb.append("&#39;");  break;
                default:   sb.append(c);
            }
        }
        return sb.toString();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mi Arquetipo Jakarta</title>
</head>
<body>

    <h1>Consulta de usuarios</h1>

    <form action="<%= esc(request.getContextPath()) %>/usuarios" method="get">
        <button type="submit">Consultar usuarios</button>
    </form>

    <%
        List<?> columnas = (List<?>) request.getAttribute("columnas");
        List<?> filas = (List<?>) request.getAttribute("filas");
        String error = (String) request.getAttribute("error");

        if (columnas != null && filas != null) {
    %>
        <h2>Usuarios</h2>
        <table border="1">
            <tr>
                <% for (Object columna : columnas) { %>
                    <th><%= esc(columna) %></th>
                <% } %>
            </tr>
            <% for (Object filaObj : filas) {
                   List<?> fila = (List<?>) filaObj; %>
                <tr>
                    <% for (Object valor : fila) { %>
                        <td><%= esc(valor) %></td>
                    <% } %>
                </tr>
            <% } %>
        </table>
    <% } %>

    <% if (error != null) { %>
        <div class="error"><%= esc(error) %></div>
    <% } %>

</body>
</html>
