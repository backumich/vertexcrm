<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="с" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Add Certificate"> </mt:header>


<div class="container">
    <div class="crm-form mt60">
        <form:form cssClass="buttonText" method="post" commandName="certificateWithUserForm"
                   action="checkCertificateAndUser">
            <h1 class="fontSize180 silver">Add A New Certificate</h1>

            <form:hidden path="certificate.certificationId" value="1"/>
            <form:hidden path="certificate.userId" value="1"/>

            <div class="form-group">
                <form:label path="certificate.certificationDate" for="date">Certification Date</form:label>
                <form:input placeholder="yyyy-MM-dd" type="date" id="date" class="form-control"
                            path="certificate.certificationDate"/>
                <form:errors path="certificate.certificationDate"/>
            </div>
            <div class="form-group">
                <form:label path="certificate.courseName" for="course">Course Name</form:label>
                <form:input placeholder="Course" type="text" id="course" class="form-control"
                            path="certificate.courseName"/>
                <form:errors path="certificate.courseName"/>
            </div>
            <div class="form-group">
                <form:label path="certificate.language" for="lang">Language</form:label>
                <form:input placeholder="Language" type="text" id="lang" class="form-control"
                            path="certificate.language"/>
                <form:errors path="certificate.language"/>
            </div>
            <div class="form-group">
                <form:label path="user.email" for="email">User Email</form:label>
                <form:input placeholder="Email" type="text" id="email" class="form-control"
                            path="user.email"/>
                <form:errors path="user.email"/>
            </div>
            <div class="form-group">
                <form:label path="user.firstName" for="fname">User First Name</form:label>
                <form:input placeholder="First name" type="text" id="fname" class="form-control"
                            path="user.firstName"/>
                <form:errors path="user.firstName"/>
            </div>
            <div class="form-group">
                <form:label path="user.lastName" for="lname">User Last Name</form:label>
                <form:input placeholder="Last name" type="text" id="lname" class="form-control"
                            path="user.lastName"/>
                <form:errors path="user.lastName"/>
            </div>

            <input type="submit" class="submit-link" value="Add User">
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