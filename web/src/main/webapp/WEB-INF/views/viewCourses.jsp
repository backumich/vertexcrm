<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="All Courses"> </mt:header>

<div class="container">

    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <sec:authentication property="principal.username" var="admin"/>
    </sec:authorize>

    <div class="crm-form mt60 full-width">
        <c:if test="${admin != null}">

            <a href="<c:url value="/addCourse"/>">Add course</a>

        </c:if>
        <c:if test="${dataNavigator.currentNumberPage!=1}">
            <a id="1" class="page">&lt;&lt;</a>
        </c:if>
        <c:if test="${dataNavigator.currentNumberPage>1}">
            <a id="${dataNavigator.currentNumberPage-1}" class="page">Prev</a>
        </c:if>

        <c:forEach begin="1" end="${dataNavigator.totalPages}" var="val">
            <c:if test="${val==dataNavigator.currentNumberPage}">
                <a id="${val}" class="page">${val}</a>
            </c:if>
            <c:if test="${val!=dataNavigator.currentNumberPage}">
                <a id="${val}" class="page">${val}</a>
            </c:if>
        </c:forEach>

        <c:if test="${dataNavigator.currentNumberPage<dataNavigator.lastPage}">
            <a id="${dataNavigator.currentNumberPage+1}" class="page" >Next</a>
        </c:if>
        <c:if test="${dataNavigator.lastPage!=dataNavigator.currentNumberPage}">
            <a id="${dataNavigator.lastPage}" class="page" >&gt;&gt;</a>
        </c:if>

        <form:form action="viewAllUsers" method="get" commandName="dataNavigator">
            <form:select id="perPage" class="buttonText" path="rowPerPage"
                         items="${dataNavigator.countRowPerPage}"/>

            <input id="currentNamePage" type="hidden" name="currentNamePage"
                   value="${dataNavigator.currentNamePage}">
            <input id="currentNumberPage" type="hidden" name="currentNumberPage"
                   value="${dataNavigator.currentNumberPage}">
            <input id="nextPage" type="hidden" name="nextPage" value="${dataNavigator.nextPage}">
            <input id="lastPage" type="hidden" name="lastPage" value="${dataNavigator.lastPage}">
            <input id="rowPerPage" type="hidden" name="rowPerPage"
                   value="${dataNavigator.rowPerPage}">
            <input id="totalPages" type="hidden" name="totalPages" value="${dataNavigator.totalPages}">
            <input id="dataSize" type="hidden" name="dataSize" value="${dataNavigator.dataSize}">

            <input value="Submit" id="submit" class="buttonText" type="submit" style="display:none;"/>
        </form:form>

        <table class="courses-result full-width">
            <tr>
                <th width="60px">Course ID</th>
                <th width="150px">Course name</th>
                <th width="150px">Start course</th>
                <th width="100px">Finished</th>
                <th width="100px">Price</th>
                <th width="300px">Teacher name</th>
                <th width="150px">Schedule</th>
                <th width="300px">Notes</th>
                <th width="100px"></th>
            </tr>
            <c:if test="${empty courses}">
                <tr>
                    <td>There are no courses!</td>
                </tr>
            </c:if>
            <c:forEach var="courses" items="${courses}">
                <tr>
                    <td>${courses.id} </td>
                    <td>${courses.name} </td>
                    <td>${courses.start} </td>
                    <c:if test="${courses.finished}">
                        <td class = "padding-left-15">&#10003;</td>
                    </c:if>
                    <c:if test="${!courses.finished}">
                        <td> </td>
                    </c:if>
                    <td>${courses.price} </td>
                    <td>${courses.teacher.firstName} ${courses.teacher.lastName} '${courses.teacher.email}'</td>
                    <td>${courses.schedule} </td>
                    <td>${courses.notes} </td>
                    <td>
                        <c:set var="titleURL">
                            <c:url value="/courseDetails">
                                <c:param name="courseId" value="${courses.id}"/>
                            </c:url>
                        </c:set>
                        <a href="${titleURL}">Detail</a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <div class="container mb-20" align="center">
            <div class="links">
                <a href="javascript:history.back();">Back</a> |
                <a href="<c:url value="/"/>">Home</a>
            </div>
        </div>
    </div>
</div>
<mt:footer> </mt:footer>