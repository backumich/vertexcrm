<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="с" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Add certificate"> </mt:header>

<div class="container">
    <div class="crm-form mt60">
        <form:form cssClass="buttonText" method="post" commandName="certificate" action="checkCertificateWithUserId">
            <h1 class="fontSize180 silver">Add A New Certificate</h1>
            <form:hidden path="certificationId" value="1"/>
            <form:hidden path="userId" value="${userIdForCertificate}"/>
            <div class="form-group">
                <form:label path="certificationDate" for="date">Certification Date</form:label>
                <form:input placeholder="yyyy-MM-dd" type="date" id="date" class="form-control"
                            path="certificationDate"/>
                <form:errors path="certificationDate"/>
            </div>
            <div class="form-group">
                <form:label path="courseName" for="course">Course Name</form:label>
                <form:input placeholder="Course" type="text" id="course" class="form-control"
                            path="courseName"/>
                <form:errors path="courseName"/>
            </div>
            <div class="form-group">
                <form:label path="language" for="lang">Language</form:label>
                <form:input placeholder="Language" type="text" id="lang" class="form-control"
                            path="language"/>
                <form:errors path="language"/>
            </div>
            <input type="submit" class="submit-link" value="Add Certificate">
        </form:form>
    </div>
    <div class="container result-box" align="center">
        <c:if test="${!empty msg}">
            <h2 class="result-text">${msg}</h2>
        </c:if>
    </div>
</div>


<div class="container mb-20" align="center">
    <div class="links">
        <a href="javascript:history.back();">Back</a> |
        <a href="<c:url value="/"/>">Home</a>
    </div>
</div>

<mt:footer> </mt:footer>