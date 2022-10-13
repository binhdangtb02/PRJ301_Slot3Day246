<%-- 
    Document   : attendencereport
    Created on : Oct 13, 2022, 7:21:17 AM
    Author     : Dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <style>
        table{
            border: 1px;
            width: 80%;
            margin: auto;
        }
        .date{
            text-transform: lowercase;
        }
        .date:first-letter {
            text-transform: capitalize;
        }
    </style>
    <body>
        <h1>Hello World!</h1>
        <table border="1px" width="80%">
            <tr>
                <th>NO</th>
                <th>DATE</th>
                <th>SLOT</th>
                <th>ROOM</th>
                <th>Group</th>
                <th>Lecture</th>
                <th>ATTENDENCE STATUS</th>
            </tr>

            <c:forEach items="${requestScope.listAttendence}" var="attendence">
                <tr>
                    <td >${attendence.session.num}</td>
                    <td class="date">${attendence.session.date.toLocalDate().dayOfWeek} ${attendence.session.date}</td>
                    <td>${attendence.session.timeSlot}</td>
                    <td >${attendence.session.room}</td>
                    <td>${attendence.session.group.subject.subjectCode}</td>
                    <td>${attendence.session.group.lecture.lectureCode}</td>
                    <td>
                        <c:if test="${attendence.status ==1}">Attended</c:if>
                        <c:if test="${attendence.status==2}">Absent</c:if>
                        <c:if test="${attendence.status==3}">Future</c:if>
                        </td>
                    </tr>
            </c:forEach>    
            <tr>
                <td colspan="5"><h2>Absent: ${requestScope.numberOfAbsent}: ${requestScope.percentageOfAbsent}% so far</h2></td>
            </tr>
        </table>
    </body>
</html>
