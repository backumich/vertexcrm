<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Error"> </mt:header>

<div align="center" class="page mh100">
    <div class="container pt1_5">
        <div class="error-box alert alert-danger mt-30">
            <h1>Error: 403 - access is denied</h1><br><br>
            <div class="links">
                <a href="<c:url value="/" />">Home</a>
            </div>
        </div>
    </div>
</div>

<mt:footer> </mt:footer>