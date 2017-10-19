<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Certificate Details"> </mt:header>


<div align="center" class="page mh100">
    <div class="container pt1_5">
        <c:if test="${photo != null}">
            <img src="data:image/jpeg;base64,${photo}" width="auto" height="400" alt="no photo" class="mtb20">
        </c:if>

        <div class="container mb-20" align="center">
            <div class="links">
                <a href="javascript:history.back();">Back</a> |
                <a href="<c:url value="/"/>">Home</a>
            </div>
        </div>
    </div>
</div>


<mt:footer> </mt:footer>