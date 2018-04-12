<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Image Error"> </mt:header>

<div class="container pt1_5">
    <div class="error-box alert alert-danger mt-30">
        <h1>${errorMessage}</h1>
    </div>
</div>

<div class="href">
    <a href="<c:url value="/logIn"/>">Back</a> |
    <a href="<c:url value="/"/>">Home</a>
</div>

<mt:footer> </mt:footer>