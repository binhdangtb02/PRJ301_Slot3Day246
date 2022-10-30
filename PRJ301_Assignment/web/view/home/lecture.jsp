<%-- 
    Document   : lecture
    Created on : Oct 29, 2022, 11:19:44 PM
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
         <section class="section">
            <jsp:include page="header.jsp"/>
            <h2>
                View Profile of student:  ${sessionScope.account.lecture.lectureName}
            </h2>
            <ul>
                <li>
                    <a href="../student/timetable?studentid=${sessionScope.account.lecture.lectureCode}">View weekly timetable</a>
                </li>
                <li>
                    <a href="../student/report?studentid=${sessionScope.account.lecture.lectureCode}">View attendence report</a>
                </li>
            </ul>
        </section>
    </body>
</html>
