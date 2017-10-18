<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Home"> </mt:header>

<div class="page mh100">
    <div class="container pt1_5" align="center">

        <main class="content crm-form mt60">
            <div id="registration-form">
                <div class="reg-form">
                    <h1>Welcome to Vertex CRM</h1>
                </div>

                <sec:authorize access="hasRole('ADMIN')">
                    <sec:authentication property="principal.username" var="admin"/>
                </sec:authorize>
                <sec:authorize access="hasRole('USER')">
                    <sec:authentication property="principal.username" var="user"/>
                </sec:authorize>
                <sec:authorize access="hasRole('TEACHER')">
                    <sec:authentication property="principal.username" var="teacher"/>
                </sec:authorize>

                <div class="links">
                    <c:if test="${user == null && admin == null}">
                        <a href="<c:url value="/registration"/>">Register</a> |
                        <a href="<c:url value="/logIn"/>">Log in</a><br><br>
                        <a href="<c:url value="/certificateDetails"/>">Certificate details by ID</a>
                    </c:if>

                    <c:if test="${user != null}">
                        <a href="<c:url value="/logIn"/>">User page</a> |
                        <a href="<c:url value="/logOut"/>">Log out</a><br><br>
                        <a href="<c:url value="/certificateDetails"/>">Certificate details by ID</a>
                    </c:if>

                    <c:if test="${admin != null}">
                        <a href="<c:url value="/admin"/>">Admin page</a> |
                        <a href="<c:url value="/logOut"/>">Log out</a><br><br>
                        <a href="<c:url value="/certificateDetails"/>">Certificate details by ID</a><br><br>
                        <a href="<c:url value="/viewAllUsers"/>">View all users</a> |
                        <a href="<c:url value="/viewAllCourses"/>">View all courses</a>
                    </c:if>
                </div>
            </div>
        </main>
    </div>
</div>

<mt:footer> </mt:footer>