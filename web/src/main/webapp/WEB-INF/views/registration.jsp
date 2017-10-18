<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Registration"> </mt:header>

<div class="page mh100 pt1_5">
    <div class="container" align="center">
        <div class="crm-form">
            <h1>Registration</h1>

            <form:form action="registration" method="post" commandName="userFormRegistration">
                <div class="form-group">
                    <label for="crm-email">Email</label>
                    <form:input class="form-control" id="crm-email" placeholder="Email" path="email"/>
                    <form:errors path="email"/>
                 </div>
                <div class="form-group">
                    <label for="crm-password">Password</label>
                    <form:password class="form-control" id="crm-password" placeholder="Password" path="password"/>
                    <form:errors path="password"/>
                </div>
                <div class="form-group">
                    <label for="crm-varify-password">Repeat password</label>
                    <form:password class="form-control" id="crm-varify-password" placeholder="Confirm Password" path="verifyPassword"/>
                    <form:errors path="verifyPassword"/>
                </div>
                <div class="form-group">
                    <label for="crm-name">First name</label>
                    <form:input class="form-control" id="crm-name" placeholder="First name" path="firstName"/>
                    <form:errors path="firstName"/>
                </div>
                <div class="form-group">
                    <label for="crm-name">Last name</label>
                    <form:input class="form-control" id="crm-name" placeholder="Last name" path="lastName"/>
                    <form:errors path="lastName"/>
                </div>
                <div class="form-group">
                    <label for="crm-phone">Phone</label>
                    <form:input class="form-control" id="crm-phone" placeholder="Phone" path="phone"/>
                    <form:errors path="phone"/>
                </div>

                <div class="g-recaptcha" data-sitekey="6LfuoCkUAAAAAJpyVDEXxqh-YgwfcV-V0C285XBM"></div>

                <div class="form-group">
                    <c:if test="${captcha == false}">
                        <br><s:message code="reCaptcha.error"/><br>
                    </c:if>
                </div>
                <input type="submit" class="submit-link" value="Register">

            </form:form>

            <form:errors path="userFormRegistration"/>

            <div class="links">
                <a href="javascript:history.back();">Back</a> |
                <a href="<c:url value="/"/>">Home</a>
            </div>

        </div>
    </div>
</div>

<mt:footer> </mt:footer>