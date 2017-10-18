<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Search Course"> </mt:header>

<div class="page mh100">


    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <sec:authentication property="principal.username" var="admin"/>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_USER')">
        <sec:authentication property="principal.username" var="user"/>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_TEACHER')">
        <sec:authentication property="principal.username" var="teacher"/>
    </sec:authorize>

    <c:if test="${admin != null}">

    <c:if test="${empty courses}">
        <div class="container pt1_5" align="center">
            <div class="crm-form mt60 full-width">
                <form:form cssClass="buttonText" method="post" commandName="courseForInfo" action="searchCourse">
                    <h1>Search Course</h1>

                    <form:hidden path="start" value="2011-12-03"/>

                    <div class="row d-flex align-middle">
                        <div class="col-xs-3 d-flex align-middle"><p>Course name:</p></div>
                        <div class="col-xs-9 d-flex align-middle">
                            <form:input path="name" placeholder="Course name" type="text" class="form-control"/>
                        </div>
                        <div class="col-xs-12"><form:errors path="name"/></div>
                    </div>

                    <div class="row d-flex align-middle">
                        <div class="col-xs-3 d-flex align-middle"><p>Is the course finished?</p></div>
                        <div class="col-xs-9 d-flex align-middle">
                            <form:checkbox path="finished"/>
                        </div>
                        <div class="col-xs-12"><form:errors path="finished"/></div>
                    </div>
                    <input type="submit" class="submit-link" value="Search">


                </form:form>
            </div>
        </div>
    </c:if>


    <c:if test="${!empty courses}">
        <div class="container pt1_5" align="center">
            <div class="crm-form mt60 full-width">
                <form:form cssClass="buttonText" method="get" commandName="courseId" action="/courseDetails">
                    <h1>Select Course:</h1>

                    <table class="courses-result full-width">
                        <tr>
                            <th></th>
                            <th>Course Name</th>
                            <th>Course Start Date</th>
                            <th>Finished State</th>
                            <th>Course Price</th>
                            <th>Course Teacher</th>
                            <th>Course Schedule</th>
                        </tr>
                        <c:forEach items="${courses}" var="course">

                            <tr>
                                <td><input type="radio" name="courseId" id="course-${course.id}" checked="true" value=${course.id}>
                                </td>
                                <td>${course.name}</td>
                                <td>${course.start}</td>
                                <td>${course.finished}</td>
                                <td>${course.price}</td>
                                <td>${course.teacher.firstName}; ${course.teacher.lastName}; ${course.teacher.email}</td>
                                <td>${course.schedule}</td>
                                <td>
                                </td>
                            </tr>

                        </c:forEach>
                    </table>

                    <input type="submit" class="submit-link" value="Show details">
                </form:form>
            </div>
        </div>
    </c:if>


    <div class="container" align="center">
        <c:if test="${!empty msg}">
            <p class="error-text">${msg}</p>
        </c:if>
    </div>
    </c:if>
    <div class="container mb-20" align="center">
        <div class="links">
            <a href="javascript:history.back();">Back</a> |
            <a href="<c:url value="/"/>">Home</a>
        </div>
    </div>

</div>

<mt:footer> </mt:footer>