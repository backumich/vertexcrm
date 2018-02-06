<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Certificate Details"> </mt:header>

<div class="container">
    <div class="crm-form mt60 full-width">
        <c:if test="${empty users}">
            <form:form cssClass="buttonText" method="post" commandName="userDataForSearch"
                       action="searchUser">
                <h1>Search User</h1>
                <input placeholder="User name" type="text" class="form-control" name="userDataForSearch"/>
                <input class="submit-link" type="submit" value="Search"/>
            </form:form>
        </c:if>

        <c:if test="${!empty users}">
            <form:form method="post" commandName="userIdForCertificate"
                       action="selectUser">
                <h1>Select User</h1>
                <table class="courses-result full-width">
                    <tr>
                        <th></th>
                        <th>User Id</th>
                        <th>User E-mail</th>
                        <th>User first name</th>
                        <th>User last name</th>
                    </tr>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td><input type="radio" name="userIdForCertificate" checked="true" value=${user.userId}>
                            </td>
                            <td>${user.userId}</td>
                            <td>${user.email}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <br>
                <input type="submit" value="Select" class="submit-link"/>
            </form:form>
        </c:if>
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