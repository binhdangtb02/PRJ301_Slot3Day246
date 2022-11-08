<%-- 
    Document   : report
    Created on : Oct 20, 2022, 9:46:18 PM
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
            margin : 0;
            padding: 0;
            box-sizing: border-box;
        }
        body{
            background-color: hsl(210, 36%, 96%);
        }
        .title{
            margin: 40px 0px;
        }
        .section{
            width: 80%;
            margin: 50px auto;
        }
        a{
            text-decoration: none;
            color: blue;
        }
        .links-container{
            list-style-type: none;
            text-decoration: none
        }
        .link{
            margin-bottom: 10px;
        }
        .report-table{
            margin: 0 10%;
            ;
            border-collapse: collapse;
            width: max-content;
        }
        .head-table{
            width: 100px;
        }
        td{
            padding: 3px 5px;
        }
        .active{
            color:black;
        }
        .header{
            display: flex;
            justify-content: space-between;
        }
    </style>
    <body>
        <section class="section">
            <div class="header">
                <h1>FPT University Academic Portal</h1>
                <div>
            <a style="margin: 0px 10px;" href="../auth/logout">Log Out</a>
            <a href="../auth/login">Back To Home</a>
            </div>
            </div>
            <div class="title">
                <h1>Attendence report for lecture</h1>
                <h1>Lecturer: ${sessionScope.account.lecture.lectureName}</h1>
            </div>
            <div class="main-content">
                <div>
                    <ul class="links-container">
                        <c:forEach items="${requestScope.groups}" var="group">
                            <li class="link">
                                <a 
                                    <c:if test="${group.groupId == requestScope.groupId}">class="active"</c:if>
                                    href="report?groupId=${group.groupId}">
                                    ${group.groupName} -  ${group.subject.subjectName}(${group.subject.subjectCode})
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </section>
        <c:set var="size" value="${requestScope.listSession.size()}"/>
        <c:if test="${requestScope.listSession != null}">
            <table border="1px" class="report-table">
                <tr>
                    <th class="head-table"></th>
                        <c:forEach items="${requestScope.listSession}" var="session">
                        <th class="head-table"><fmt:formatDate pattern="dd/MM/yyyy" value="${session.date}"/></th>
                        </c:forEach>
                    <th>Absent</th>
                </tr>
                <c:set var="count" value="0"/>
                <c:forEach items="${requestScope.listAttendence}" var="attendence">
                    <c:if test="${attendence.session.num == 1}">
                        <tr>
                            <td>${attendence.student.name}(${attendence.student.id})</td>
                            <c:set var="count" value="0"/>
                        </c:if>

                        <c:if test="${attendence.session.num != 30}">
                            <c:if test="${attendence.status==1}">
                                <td style="color:green;">V</td>
                            </c:if>
                            <c:if test="${attendence.status==0 && attendence.session.status == true}">
                                <td style="color:red;">X</td>
                                <c:set var="count" value="${count+1}"/>
                            </c:if>
                            <c:if test="${attendence.status==0 && attendence.session.status == false}">
                                <td>-</td>
                            </c:if>
                        </c:if>

                        <c:if test="${attendence.session.num == size}">
                            <c:if test="${attendence.status==1}">
                                <td style="color:green;">V</td>
                            </c:if>
                            <c:if test="${attendence.status==0 && attendence.session.status == true}">
                                <td style="color:red;">X</td>
                                <c:set var="count" value="${count+1}"/>
                            </c:if>
                            <c:if test="${attendence.status==0 && attendence.session.status == false}">
                                <td>-</td>
                            </c:if>
                            <td><fmt:formatNumber value="${count/size}" type="PERCENT"/></td>
                        </tr>
                    </c:if> 
                </c:forEach>
            </table>
        </c:if> 

    </body>
</html>
