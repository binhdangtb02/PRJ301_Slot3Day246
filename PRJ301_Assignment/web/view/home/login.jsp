<%-- 
    Document   : login
    Created on : Oct 29, 2022, 10:52:03 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1 style="color: red">${requestScope.error}</h1>
        <form action="login" method="post">
            Username: <input type="text" name="user" value="${requestScope.user}"/><br/>
            Password:  <input type="password" name="user" value="=${requestScope.pass}"/><br/>
            <input type="submit" value="submit"/>
        </form>
    </body>
</html>
