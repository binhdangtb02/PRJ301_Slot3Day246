<%-- 
    Document   : login.jsp
    Created on : Oct 3, 2022, 8:31:25 PM
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
        <h2 style="color:red">${requestScope.mess}</h2>
        <form action="login" method="post">
            Enter username<input type="text" name="user"/> <br/>
            Enter password<input type="password" name="pass"/><br/>
            <input type="submit" value="submit"/>
        </form>
    </body>
</html>
