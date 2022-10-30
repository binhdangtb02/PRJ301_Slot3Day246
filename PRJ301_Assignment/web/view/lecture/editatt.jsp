<%-- 
    Document   : editatt
    Created on : Oct 19, 2022, 10:46:25 PM
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
        th{
            text-align: start;
        }

        tr{
            margin: 10px 0px;
        }
        table{
            width: 100%;
        }
        .submit-box{
            text-align: center;
        }
        .submit-btn{
            padding: 6px 30px;
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
            <a href="../auth/logout">log out</a>
            </div>
            <div class="title">
                <h1>
                    <c:if test="${requestScope.session.status}">Edit Attendence </c:if> 
                    <c:if test="${!requestScope.session.status}">Take Attendence </c:if> 
                    for group ${requestScope.session.group.groupName} - ${requestScope.session.group.subject.subjectCode} 
                    (Slot: ${requestScope.session.timeSlot} - Day: <fmt:formatDate value="${requestScope.session.date}" pattern="dd/MM/yyyy"/>)
                </h1>
                <br/>
                <h1>Status: 
                    <c:if test="${requestScope.session.status}">Have Been Taken Attedence Before</c:if>
                    <c:if test="${!requestScope.session.status}">Haven't Been Taken Attedence Before</c:if>
                </h1>
                </div>
                <form action="edit" method="post">
                    
                    <table >
                        <tr>
                            <th>Num</th>
                            <th>Group</th>
                            <th>RollNumber</th>
                            <th>FullName</th>
                            <th>Absent</th>
                            <th>Present</th>
                            <th>Comment</th>
                            <th>Show Image</th>

                        </tr>
                    <c:set var="count" value="1"/>
                    <c:forEach items="${requestScope.listAttendence}" var="attendence">
                        <input type="hidden" name="sessionid" value="${requestScope.session.sessionid}"/>
                        <tr>
                            <td>${count}</td>
                            <td>${attendence.session.group.groupName}</td>
                            <td>${attendence.student.id}</td>
                            <td>${attendence.student.name}</td>

                            <td>
                                <input type="hidden" value="${attendence.student.id}" name="studentid"/>
                                <input type="radio" name="attended?id=${attendence.student.id}" value="0" <c:if test="${attendence.status==0}">checked</c:if>/>Absent
                                </td>
                                <td>
                                    <input type="radio" name="attended?id=${attendence.student.id}" value="1" <c:if test="${attendence.status==1}">checked</c:if> />Present
                                </td>
                                <td>
                                    <input type=text name="description?id=${attendence.student.id}" value="${attendence.description}"/>
                            </td>
                            <td>
                                <img src="${attendence.student.image}"/>
                            </td>
                        </tr>

                        <c:set var="count" value="${count+1}"/>
                    </c:forEach>

                </table>
                <div class="submit-box">
                    <input class="submit-btn" type="submit" value="save"/>
                </div>
            </form>
        </section>
    </body>
</html>
