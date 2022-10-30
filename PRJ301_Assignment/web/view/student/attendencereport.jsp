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
        *{
            padding: 0;
            margin: 0;
            box-sizing: border-box;
        }
        body{
            width: 85%;
            margin: 100px auto;
            background-color: hsl(206, 33%, 96%);
        }
        .attendence-title{
            margin-top: 50px;
        }
        section{
            margin-top: 50px;
            display: grid;
            grid-template-columns: auto 1fr;
            gap: 100px;
        }
        .links-container{
            list-style-type: none;
            text-transform: lowercase;
        }
        .link:first-letter{
            text-transform: capitalize;
        }
        .link{
            margin-bottom: 10px;
            font-size: 1rem;
        }
        .link>.active{
            color: black;
        }
        a{
            text-decoration: none;
            color: blue;
        }
        table{
            border: 1px;
        }
        .date{
            text-transform: lowercase;
        }
        .date:first-letter {
            text-transform: capitalize;
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
        <h2 class="attendence-title">View attendence for student ${requestScope.student.name}(${requestScope.student.id})</h2>
        <section>
            <div class="course">
                <ul class="links-container">
                    <c:forEach items="${requestScope.listGroup}" var="g" varStatus="loop">
                        <li class="link">
                            <a 
                                href="report?studentid=${requestScope.studentId}&index=${loop.index}"
                                class="<c:if test="${requestScope.index == loop.index}">active</c:if>"
                                    >
                                ${g.subject.subjectName}

                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <c:if test="${requestScope.listAttendence !=null}">
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
                            <td>${attendence.session.lecture}</td>
                            <td>
                                <c:if test="${attendence.status ==1}">Attended</c:if>
                                <c:if test="${attendence.status==0}">Absent</c:if>
                                <c:if test="${attendence.status==-1}">Future</c:if>
                                </td>
                            </tr>
                    </c:forEach>    
                    <tr>
                        <td colspan="5"><h2>Absent ${requestScope.numberOfAbsent}: (${requestScope.percentageOfAbsent})% so far</h2></td>
                    </tr>

                </table>
            </c:if>
            <c:if test="${requestScope.listAttendence ==null}">
                <h2>${requestScope.error}</h2>
            </c:if>
        </section>
    </body>
</html>
