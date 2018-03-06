<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="User Details"> </mt:header>

<div class="container">
    <div class="crm-form mt60 full-width">

        <h1>User Profile</h1>

        <table class="courses-result full-width">
            <tr>
                <td width="40%">User ID:</td>
                <td>${String.format("%05d", user.userId)}</td>
            </tr>
            <tr>
                <td>First Name:</td>
                <td>${user.firstName}</td>
            </tr>
            <tr>
                <td>Last Name:</td>
                <td>${user.lastName}</td>
            </tr>
            <tr>
                <td>Email:</td>
                <td>${user.email}</td>
            </tr>
            <tr>
                <td>Discount:</td>
                <td>${user.discount}</td>
            </tr>
            <tr>
                <td>My certificates:</td>
                <td><a href="<c:url value="/getCertificateByUserId?userId=${user.userId}"/>">Go to certificates page</a>
                </td>
            </tr>
            <tr>
                <td>Photo:</td>
                <td>
                    <sf:form method="post" action="/showImagePhoto" commandName="user">
                        <input type="hidden" name="userId" value="${user.userId}"/>
                        <input type="hidden" name="firstName" value="${user.firstName}"/>
                        <input type="hidden" name="lastName" value="${user.lastName}"/>
                        <input type="hidden" name="email" value="${user.email}"/>
                        <input type="hidden" name="phone" value="${user.phone}"/>
                        <input type="hidden" name="discount" value="${user.discount}"/>
                        <input type="hidden" name="imageType" value="photo"/>
                        <input class="black" type="submit" value="Show Photo">
                    </sf:form><br>
                </td>
            </tr>
            <tr>
                <td>Passport scan:</td>
                <td>
                    <sf:form method="post" action="/showImagePassport" commandName="user">
                        <input type="hidden" name="userId" value="${user.userId}"/>
                        <input type="hidden" name="firstName" value="${user.firstName}"/>
                        <input type="hidden" name="lastName" value="${user.lastName}"/>
                        <input type="hidden" name="email" value="${user.email}"/>
                        <input type="hidden" name="phone" value="${user.phone}"/>
                        <input type="hidden" name="discount" value="${user.discount}"/>
                        <input type="hidden" name="imageType" value="passportScan"/>
                        <input class="black" type="submit" value="Show Passport Scan">
                    </sf:form><br>
                </td>
            </tr>
        </table>

        <div class="href">
            <a href="javascript:history.back();">Back</a> |
            <a href="<c:url value="/" />">Home</a>
        </div>

    </div></div>

<div class="container mb-20" align="center">
    <div class="links">
        <a href="javascript:history.back();">Back</a> |
        <a href="<c:url value="/"/>">Home</a>
    </div>
</div>

<mt:footer> </mt:footer>