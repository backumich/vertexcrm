<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Log In"> </mt:header>

<div align="center" class="page mh100 pt1_5">
    <div class="crm-form">
        <h1>Log into the system</h1>

        <sf:form action="logIn" method="post">
            <div class="form-group">
                <label for="crm-email">Email address</label>
                <c:if test="${param.error == null}">
                    <input type="email" class="form-control" id="crm-email" name="username" placeholder="Enter email">
                </c:if>
                <c:if test="${param.error != null}">
                    <input type="email" class="form-control error-field" id="crm-email" name="username" placeholder="Enter email">
                </c:if>
            </div>
            <div class="form-group">
                <label for="crm-pass">Password</label>
                <c:if test="${param.error == null}">
                    <input type="password" class="form-control" id="crm-pass" name="password" placeholder="Password">
                </c:if>
                <c:if test="${param.error != null}">
                    <input type="password" class="form-control error-field" id="crm-pass" name="password" placeholder="Password">
                </c:if>
            </div>
            <c:if test="${param.error != null}">
                <p class="error-text">Invalid Email or Password</p>
            </c:if>
            <div class="form-check">
                <label class="form-check-label">
                    Remember me <input type="checkbox" class="form-check-input" name="remember-me">
                </label>
            </div>

            <input type="submit" class="submit-link" value="Log In">

        </sf:form>


        <div class="links">
            <a href="javascript:history.back();">Back</a> |
            <a href="<c:url value="/"/>">Home</a>
        </div>
    </div>
</div>


<mt:footer> </mt:footer>