<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Remove user from course"> </mt:header>
<div class="crm-form mt60">
    <h1 class="ac">Confirm removal?</h1>
    <div class="row">
        <div class="col-xs-3 col-xs-offset-3 ac">
            <sf:form action="removeUserFromCourse" method="post" commandName="dtoCourseUser">
                    <input type="hidden" name="courseId" value="${dtoCourseUser.courseId}">
                    <input type="hidden" name="userId" value="${dtoCourseUser.userId}">
                    <input type="submit" value="Yes" class="submit-link">
            </sf:form>
        </div>
        <div class="col-xs-3 ac">
            <sf:form action="showCourseAndUsers" method="get" commandName="dtoCourseUser">
                <input type="hidden" name="id" value="${dtoCourseUser.courseId}">
                <input type="submit" value="No" class="submit-link">
            </sf:form>
        </div>
    </div>
</div>
<div class="container mb-20" align="center">
    <div class="links">
        <a href="javascript:history.back();">Back</a> |
        <a href="<c:url value="/"/>">Home</a>
    </div>
</div>

<mt:footer> </mt:footer>