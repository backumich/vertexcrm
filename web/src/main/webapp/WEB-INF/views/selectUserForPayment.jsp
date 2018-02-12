<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Search Course"> </mt:header>

<div class="container">
    <div class="crm-form mt60 full-width">
        <c:if test="${empty users}">
            <form:form method="post" commandName="paymentForm" action="selectUserForPayment" name="payment"
                       id="payment">
                <h1>Enter amount :</h1>
                <form:hidden path="courseId" value='${courseIdForPayment}'/>
                <form:hidden path="userID" value='${userIdForPayment}'/>
                <form:input type="text" placeholder="0,0" path="payment.amount" name="amount" id="amount"/>
                <form:errors path="payment.amount"/>

                <input class="submit-link" type="submit" value="Pay">
            </form:form>
        </c:if>

        <c:if test="${!empty users}">
            <form:form method="post" commandName="paymentForm" action="selectUserForPayment" name="payment"
                       id="payment">
                <h1>Select user and enter amount :</h1>
                <table class="courses-result first-auto full-width mb-20">
                    <tr >
                        <th>Select user</th>
                        <th>User Id</th>
                        <th>User E-mail</th>
                        <th>User first name</th>
                        <th>User last name</th>
                    </tr>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td><form:radiobutton path="userID" checked="checked" value='${user.userId}'/></td>
                            <td>${user.userId}</td>
                            <td>${user.email}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                        </tr>
                    </c:forEach>
                </table>

                <form:hidden path="courseId" value='${courseIdForPayment}'/>
                <div class="form-group">
                        <form:label path="payment.amount">Enter amount:</form:label>
                        <form:input placeholder="0,0" class="form-control" path="payment.amount" name="amount" id="amount"/>
                        <form:errors path="payment.amount"/>
                </div>


                <input class="submit-link" type="submit" value="Pay"/>
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