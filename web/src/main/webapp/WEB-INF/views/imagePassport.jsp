<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Passport Scan"> </mt:header>

<c:if test="${passportScan != null}">
    <img src="data:image/jpeg;base64,${passportScan}" width="auto" height="600" alt="no passport scan">
</c:if>

<br><br>

<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal.username" var="username"/>
    <sec:authentication property="principal.authorities" var="auth"/>
    <c:if test="${username.equals(user.email) || auth.iterator().next().toString().equals('ROLE_ADMIN')}">
        <sf:form method="post" action="/uploadImage" enctype="multipart/form-data" commandName="user">
            <sf:hidden path="userId" value="${user.userId}"/>
            <sf:hidden path="firstName" value="${user.firstName}"/>
            <sf:hidden path="lastName" value="${user.lastName}"/>
            <sf:hidden path="email" value="${user.email}"/>
            <sf:hidden path="discount" value="${user.discount}"/>
            <input type="hidden" name="imageType" value="passportScan"/>
            <br>
            <input type="file" name="image" accept="image/*"/>
            <input class="submit-link" type="submit" value="Upload New Passport Scan">
        </sf:form>
    </c:if>
</sec:authorize>

<div class="href">
    <sec:authorize access="isAuthenticated()">
        <a href="<c:url value="/logIn"/>">Back</a> |
    </sec:authorize>
    <sec:authorize access="isAnonymous()">
        <a href="javascript:history.back();">Back</a> |
    </sec:authorize>
    <a href="<c:url value="/"/>">Home</a>
</div>

<mt:footer> </mt:footer>