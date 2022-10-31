<%-- 
    Document   : student
    Created on : Oct 29, 2022, 11:19:39 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <style>
        *{
            margin: 0px;
            padding: 0px;
            box-sizing: border-box;
        }
        .section{
            width: 80%;
            margin: 100px auto;
        }
        body{
            background-color: hsl(210, 36%, 96%);
        }
        h1{
            font-size: 2.5rem;
        }
        h2{
            font-size: 2rem;
        }
        li{
            margin: 20px 20px;
        }
        a{
            text-decoration: none;
        
            font-size: 1.25rem;
        }
    </style>
    <body>
        <section class="section">
            <jsp:include page="header.jsp"/>
            <h2>
                View Profile of student:  ${sessionScope.account.student.name}
            </h2>
            <ul>
                <li>
                    <a href="../student/timetable">View weekly timetable</a>
                </li>
                <li>
                    <a href="../student/report">View attendence report</a>
                </li>
            </ul>
        </section>
    </body>
</html>
