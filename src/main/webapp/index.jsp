<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
<head>

    <meta charset="UTF-8">

    <title>Mi Arquetipo Jakarta</title>

</head>

<body>

    <h1>Consulta de usuarios</h1>

    <form action="usuarios" method="get">

        <button type="submit">
            Consultar usuarios
        </button>

    </form>


    <% if (request.getAttribute("tabla") != null) { %>

        <h2>Usuarios</h2>

        <%= request.getAttribute("tabla") %>

    <% } %>


    <% if (request.getAttribute("error") != null) { %>

        <div class="error">

            <%= request.getAttribute("error") %>

        </div>

    <% } %>

</body>

</html>