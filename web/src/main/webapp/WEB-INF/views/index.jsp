<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>

<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Home"> </mt:header>

<div class="container">

    <main class="content crm-form mt60">
        <div id="registration-form">
            <div class="reg-form">
                <h1>Welcome to Vertex CRM</h1>
            </div>

            <div class="links">
                <sec:authorize access="isAnonymous()">
                    <a href="<c:url value="/registration"/>">Register</a> |
                    <a href="<c:url value="/logIn"/>">Log in</a><br><br>
                    <a href="<c:url value="/certificateDetails"/>">Certificate details by ID</a>
                </sec:authorize>

                <sec:authorize access="hasRole('USER')">
                    <a href="<c:url value="/logIn"/>">User page</a> |
                    <a href="<c:url value="/logOut"/>">Log out</a><br><br>
                    <a href="<c:url value="/certificateDetails"/>">Certificate details by ID</a>
                </sec:authorize>

                <sec:authorize access="hasRole('TEACHER')">
                    <a href="<c:url value="/logIn"/>">User page</a> |
                    <a href="<c:url value="/logOut"/>">Log out</a><br><br>
                    <a href="<c:url value="/certificateDetails"/>">Certificate details by ID</a><br><br>
                    <a href="<c:url value="/viewCourses/teacher"/>">View courses that I teach</a>
                </sec:authorize>

                <sec:authorize access="hasRole('ADMIN')">
                    <a href="<c:url value="/admin"/>">Admin page</a> |
                    <a href="<c:url value="/logOut"/>">Log out</a><br><br>
                    <a href="<c:url value="/certificateDetails"/>">Certificate details by ID</a><br><br>
                    <a href="<c:url value="/viewAllUsers"/>">View all users</a> |
                    <a href="<c:url value="/viewCourses/all"/>">View all courses</a>
                </sec:authorize>
            </div>
        </div>
    </main>
</div>

<mt:footer> </mt:footer>