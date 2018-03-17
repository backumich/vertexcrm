<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="с" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="User Details"> </mt:header>

<div class="container">

    <c:if test="${listCertificatesIsEmpty==true}">
        <h1>You do not have certificates!!!</h1>
    </c:if>
    <c:if test="${!empty certificates}">
        <table class="active" width="600">
            <tr>
                <th>Certification Id</th>
                <th>Certificate UID</th>
                <th>Certification Date</th>
                <th>Course Name</th>
                <th>Details</th>
            </tr>
            <tr></tr>
            <tr></tr>

            <c:forEach items="${certificates}" var="certificate">
                <tr>
                    <td>${certificate.certificationId}</td>
                    <td>${certificate.certificateUid}</td>
                    <td>${certificate.certificationDate}</td>
                    <td>${certificate.courseName}</td>
                    <td>
                        <a href="<c:url value="/getCertificate/${certificate.certificateUid}"/>">Details</a>
                    </td>
                </tr>
            </c:forEach>

        </table>
    </c:if>

    <br>

    <div class="hrefText">
        <a href="javascript:history.back();">Back</a> |
        <a href="<c:url value="/" />">Home</a>
    </div>
</div>


<mt:footer> </mt:footer>