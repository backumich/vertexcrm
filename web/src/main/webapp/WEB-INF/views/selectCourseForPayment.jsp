<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Search Course"> </mt:header>

<div class="container">
    <c:if test="${empty courses}">
        <h3><span class="error-text">Payment is not possible. All courses were paid.</span></h3>
    </c:if>
</div>
<div class="container">
    <div class="crm-form mt60 full-width">
        <c:if test="${!empty courses}">
            <form:form cssClass="buttonText" method="post" commandName="courseIdForPayment"
                       action="selectCourse">
                <h1>Select course :</h1>
                <table class="courses-result first-auto full-width">
                    <tr>
                        <th>Select course</th>
                        <th>Course name</th>
                        <th>Start</th>
                        <th>Finished</th>
                        <th>Price</th>
                        <th>Teacher name</th>
                        <th>Notes</th>

                    </tr>

                    <c:forEach items="${courses}" var="course">
                        <tr>
                            <td><input type="radio" name="courseIdForPayment" checked="checked" value=${course.id}>
                            </td>
                            <td>${course.name}</td>
                            <td>${course.start}</td>
                            <td><c:choose>
                                <c:when test="${course.finished}">
                                    Yes
                                </c:when>
                                <c:otherwise>
                                    No
                                </c:otherwise>
                            </c:choose></td>
                            <td>${course.price}</td>
                            <td>${course.teacher.firstName}; ${course.teacher.lastName}; ${course.teacher.email}</td>
                            <td>${course.notes}</td>
                        </tr>
                    </c:forEach>
                </table>
                <br>
                <input class="submit-link" type="submit" value="Select"/>
            </form:form>
        </c:if>
        <div class="container result-box" align="center">
            <c:if test="${!empty msg}">
                <h2 class="result-text">${msg}</h2>
            </c:if>
        </div>
        <div class="container mb-20" align="center">
            <div class="links">
                <a href="javascript:history.back();">Back</a> |
                <a href="<c:url value="/"/>">Home</a>
            </div>
        </div>
    </div>
</div>

<mt:footer> </mt:footer>