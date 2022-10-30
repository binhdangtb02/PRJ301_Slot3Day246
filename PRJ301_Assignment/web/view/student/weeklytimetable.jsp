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
        body{
            margin: 100px auto;
            width: 80%;
            background-color: hsl(210, 36%, 96%);
        }
        table{
            width:100%;
            border-collapse: collapse;


        }
        td{
            padding: 4px 6px;
        }
        .header{
            display: flex;
            justify-content: space-between;
        }
    </style>
    <body>
        <div class="header">
            <h1>FPT University Academic Portal</h1>
            <a href="../auth/logout">log out</a>
        </div>
        <h2 class="attendence-title">View Weekly timetable for student ${requestScope.student.name}(${requestScope.student.id})</h2>

        <table border="1px">

            <tr>

                <th rowspan="2">
                    <form action="timetable" id="date-form">
                        <input type="hidden" value="${requestScope.student.id}" name="id"/>
                        YEAR: <select name="year"  onchange="submitForm()">
                            <c:forEach begin="${requestScope.year-3}" end="${requestScope.year+1}" var="i">
                                <option value="${i}" <c:if test="${requestScope.year == i}">selected</c:if>>${i}</option>
                            </c:forEach>
                        </select>
                        <br/>
                        WEEK: <select name="week"  onchange="submitForm()">
                            <c:set var="w" value="1"/>
                            <c:forEach  items="${requestScope.weeks}" var="week">
                                <option
                                    <c:if test="${requestScope.selectedWeek.get(0).equals(week)}">selected</c:if>
                                    value="${w}">${week.dayOfMonth}/${week.monthValue} to ${week.plusDays(6).dayOfMonth}/${week.plusDays(6).monthValue}
                                </option>
                                <c:set var="w" value="${w+1}"/>
                            </c:forEach>
                        </select>
                    </form>

                </th>
                <c:set value="${requestScope.selectedWeek}" var="selectedWeek"/>
                <c:forEach items="${selectedWeek}" var="i">
                    <th>
                        ${i.dayOfWeek}
                    </th>

                </c:forEach>
            </tr> 
            <tr>

                <c:forEach items="${selectedWeek}" var="i">
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
                            <c:set value="${attendence.session.date}" var="attendenceday"/>

                            <c:if test="${attendence.session.timeSlot == slot && day.compareTo(attendenceday) == 0}">
                                <td>
                                    ${attendence.session.group.subject.subjectCode} at ${attendence.session.room}<br/>
                                    <c:if test="${attendence.status ==1}"><span style="color:green;">Attended</span></c:if>
                                    <c:if test="${attendence.status==0}"><span style="color:red;">Absent</span></c:if>
                                    <c:if test="${attendence.status==-1}"><span style="color:black;">Future</span></c:if>
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
    <script>
        function submitForm() {
            var form = document.getElementById("date-form");
            form.submit();
        }
    </script>
</html>
