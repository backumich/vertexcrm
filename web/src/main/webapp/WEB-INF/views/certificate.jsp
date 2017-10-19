<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="с" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Error"> </mt:header>
<div class="page gray-page mh100">
    <div class="container pt1_5">
        <c:if test="${empty certificate}">
            <h1>No certificate with going !!!</h1>
        </c:if>
        <c:if test="${!empty certificate}">
            <table class="active" width="700">
                <tr>
                    <th>Certification Id</th>
                    <th>User Id</th>
                    <th>Certification Date</th>
                    <th>Course Name</th>
                    <th>Language</th>
                    <th>Certificate Link</th>
                </tr>
                <tr>
                    <td>${certificate.certificationId}</td>
                    <td>${certificate.userId}</td>
                    <td>${certificate.certificationDate}</td>
                    <td>${certificate.courseName}</td>
                    <td>${certificate.language}</td>
                    <td>localhost:8080/processCertificateDetails/${certificate.certificationId}</td>
                </tr>
            </table>
        </c:if>
        <br>

        <div class="hrefText">
            <a href="javascript:history.back();">Back</a> |
            <a href="<c:url value="/" />">Home</a>
        </div>
    </div>
</div>

<mt:footer> </mt:footer>