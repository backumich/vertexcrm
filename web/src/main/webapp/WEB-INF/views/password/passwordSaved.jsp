<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>

<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Passport Saved"> </mt:header>
<div class="container">
	<div class="success-box alert alert-success mt-30">
	    <h1>The new password has been successfully saved!</h1>
	</div>
    <div class="links">
        <a href="javascript:history.back();">Back</a> |
        <a href="<c:url value="/"/>">Home</a>
    </div>
</div>

<mt:footer> </mt:footer>
