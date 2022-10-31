<%-- 
    Document   : takeatt
    Created on : Oct 19, 2022, 9:28:26 AM
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
        *{
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body{
            margin: 100px auto;
            width: 80%;
            background-color: hsl(210, 36%, 96%);
        }
        .main-title{
            margin-bottom: 20px;
        }
        a{
            text-decoration: none;
            color: blue;
        }
        ul{
            list-style-type: none;
            text-decoration: none
        }
        li{
            margin-bottom: 10px;
        }
        .section{
            margin-top: 50px;
            display: grid;
            grid-template-columns: 2fr 3fr;
        }

        .breakpoint{
            margin: 0px 10px;
        }
        .header{
            display: flex;
            justify-content: space-between;
        }
    </style>
    <body>
        <div class="header">
            <h1>FPT University Academic Portal</h1>
            <div>
                <a style="margin: 0px 10px;" href="../auth/logout">Log Out</a>
                <a href="../auth/login">Back To Home</a>
            </div>
        </div>
        <h1 class="main-title">Take Attendence for students</h1>
        <h1>Lecturer: ${requestScope.lecture.lectureName}</h1>
        <section class="section">
            <div>
                <ul class="links-container">
                    <c:forEach items="${requestScope.groups}" var="group">
                        <li class="link"><a href="takeatt?groupId=${group.key.groupId}">${group.key.groupName} -  ${group.key.subject.subjectName}(${group.value})</a></li>
                        </c:forEach>
                </ul>
            </div>
            <c:if test="${requestScope.sessions != null}">
                <div>
                    <ul>
                        <c:forEach items="${requestScope.sessions}" var="session">
                            <li>
                                ${session.group.groupName} - ${session.group.subject.subjectCode}  - Slot:${session.timeSlot} - <fmt:formatDate pattern="dd/MM/yyyy" value="${session.date}"/> 
                                <span class="breakpoint">|</span>

                                <c:if test="${session.status}">
                                    Take Attendence
                                    <span class="breakpoint">|</span>
                                    <a href="edit?sessionid=${session.sessionid}">Edit Attendence</a>
                                </c:if>
                                <c:if test="${!session.status}">
                                    <a href="edit?sessionid=${session.sessionid}">Take Attendence</a>
                                    <span class="breakpoint">|</span>
                                    Edit Attendence

                                </c:if>

                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>
        </section>
    </body>
</html>
