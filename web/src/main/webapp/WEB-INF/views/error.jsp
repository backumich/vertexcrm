<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Error"> </mt:header>

<div class="container">
    <div class="error-box alert alert-danger mt-30">
        <c:if test="${empty errorMessage}">
            <h1>Something went wrong...</h1>
        </c:if>
        <c:if test="${!empty errorMessage}">
            <h1>Error: ${errorMessage}</h1>
        </c:if>
        <div class="links">
            <a href="<c:url value="/" />">Home</a>
        </div>
    </div>
</div>

<mt:footer> </mt:footer>