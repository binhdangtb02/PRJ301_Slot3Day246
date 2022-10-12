<%-- 
    Document   : weeklytimetable
    Created on : Oct 11, 2022, 1:54:41 PM
    Author     : Dell
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
            border-collapse: collapse;
            margin:100px auto;
          
        }
    </style>
    <body>
     
      
        <table border="1px" width="80%">

            <tr>
                <th width="15%"></th>
                    <c:set value="${requestScope.week}" var="week"/>
                    <c:forEach items="${week}" var="i">
                    <th>
                        ${i.dayOfWeek}
                    </th>

                </c:forEach>
            </tr> 
            <tr>
                <th width="15%"></th>
                    <c:forEach items="${week}" var="i">
                    <th>
                        ${i}
                    </th>

                </c:forEach>
            </tr>

            <c:forEach begin="1" end="8" var="slot">
                <tr>
                    <td>slot ${slot}</td>
                    <c:forEach items="${sqlWeek}" var="day">
                        <c:set value="true" var="check"/>
                        <c:forEach items="${requestScope.weeklyTimetable}" var="attendence">
                            <c:set value="${attendence.session.date}" var="date"/>
                          
                        <c:if test="${attendence.session.timeSlot == slot && day.compareTo(date) == 0}">
                            <td>
                                ${attendence.session.room}
                                <c:set value="false" var="check"/>
                            </td>
                        </c:if>
                       
                    </c:forEach>
                             <c:if test="${check}">
                            <td>
                                -
                            </td>
                        </c:if>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
