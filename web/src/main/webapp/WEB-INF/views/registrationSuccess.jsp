<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Registration"> </mt:header>
<div class="container">
    <div class="success-box alert alert-success mt-30">
        <h1>Congratulation ${userFormRegistration.firstName} ${userFormRegistration.lastName}, registration success!</h1>
        <h3>An email has been sent to activate your account.</h3>
    </div>
    <div class="links">
        <a href="javascript:history.back();">Back</a> |
        <a href="<c:url value="/"/>">Home</a>
    </div>
</div>


<mt:footer> </mt:footer>