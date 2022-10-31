<%-- 
    Document   : login
    Created on : Oct 29, 2022, 10:52:03 PM
    Author     : Dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <style>
         body{
            background-color: hsl(210, 36%, 96%);
        }
        .error{
            height: 50px;
        }
        .user-input{
            
        }
        .user-pass{
            margin: 10px 2px;
        }
        .input-submit{
            border: 1px solid;
            padding: 2px 4px;
        }
    </style>
    <body> 
        <div class="error">
            <h1 style="color: red">${requestScope.error}</h1>
        </div>
        <form action="login" method="post">
            Username: <input class="user-input "type="text" name="user" <c:if  test="${requestScope.user != null}">value="${requestScope.user}"</c:if> /><br/>
            Password: <input class="user-pass" type="password" name="pass" <c:if  test="${requestScope.pass != null}">value="${requestScope.pass}"</c:if>/><br/>
            <input class="input-submit" type="submit" value="submit"/>
        </form>
    </body>
</html>
