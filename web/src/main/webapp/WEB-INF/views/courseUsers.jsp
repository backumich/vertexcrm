<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Course Users"> </mt:header>

<div class="container course-users">
    <div class="crm-form mt60 full-width">
        <h1>Assigned users</h1>

        <table class="courses-result full-width">
            <tr>
                <td>Email</td>
                <td>First Name</td>
                <td>Last Name</td>
                <td>Phone</td>
                <td>Remove from course</td>
            </tr>

            <c:forEach items="${assignedUsers}" var="assignedUser">
                <sf:form action="confirmUserRemovalFromCourse" method="post" commandName="dtoCourseUser">
                    <input type="hidden" name="courseId" value="${dtoCourseUser.courseId}">
                    <input type="hidden" name="userId" value="${assignedUser.userId}">
                    <tr>
                        <td>${assignedUser.email}</td>
                        <td>${assignedUser.firstName}</td>
                        <td>${assignedUser.lastName}</td>
                        <td>${assignedUser.phone}</td>
                        <td><input type="submit" value="Remove" class="black"/></td>
                    </tr>
                </sf:form>
            </c:forEach>
        </table>

        <h2 class="mt60">Search for a user to assign (by name or email):</h2>

        <sf:form action="searchForUsersToAssign" method="get" commandName="dtoCourseUser">
            <input type="hidden" name="courseId" value="${dtoCourseUser.courseId}">
            <table class="courses-result full-width course-users-table">
                <tr>
                    <td>
                        <p>Enter first name, or last name, or email:</p>
                    </td>
                    <td>
                        <input type="text" name="searchParam" value="${dtoCourseUser.searchParam}" id="searchParam"
                               maxlength="255" placeholder="First name, or last name, or email" class="form-control"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <p>Select type of search:</p>
                    </td>
                    <td>
                        <label class="form-control">
                            <sf:select path="searchType" items="${selection}"/>
                        </label>
                    </td>
                </tr>
            </table>
            <input type="submit" value="Search" class="submit-link">
        </sf:form>

        <c:if test="${!empty search}">
            <sf:form action="clearSearchResults" method="get" class="mbt-20" commandName="dtoCourseUser">
                <input type="hidden" name="courseId" value="${dtoCourseUser.courseId}">
                <input type="submit" value="Clear search results" class="plane-link"></td>
            </sf:form>
        </c:if>

        <c:if test="${!empty freeUsers and !empty search}">
            <hr>
            <h2>Search results:</h2>

            <table class="courses-result full-width">
                <tr>
                    <th>Email</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Phone</th>
                    <th>Assign to course</th>
                </tr>
                <c:forEach items="${freeUsers}" var="freeUser">
                    <sf:form action="assignUser" method="post" commandName="dtoCourseUser">
                        <input type="hidden" name="searchType" value="${dtoCourseUser.searchType}">
                        <input type="hidden" name="searchParam" value="${dtoCourseUser.searchParam}">
                        <input type="hidden" name="courseId" value="${dtoCourseUser.courseId}">
                        <input type="hidden" name="userId" value="${freeUser.userId}">
                        <input type="hidden" name="email" value="${freeUser.email}">
                        <input type="hidden" name="firstName" value="${freeUser.firstName}">
                        <input type="hidden" name="lastName" value="${freeUser.lastName}">
                        <input type="hidden" name="phone" value="${freeUser.phone}">
                        <tr>
                            <td>${freeUser.email}</td>
                            <td>${freeUser.firstName}</td>
                            <td>${freeUser.lastName}</td>
                            <td>${freeUser.phone}</td>
                            <td><input type="submit" value="Assign" class="submit-link"/></td>
                        </tr>
                    </sf:form>
                </c:forEach>
            </table>
        </c:if>

        <c:if test="${empty freeUsers and !empty search}">
            <hr>
            <h2 class="mt60">Search results:</h2>
            <p>No users found</p>
        </c:if>

        <sf:form action="courseDetails" method="get">
            <input type="hidden" name="courseId" value="${dtoCourseUser.courseId}"/>
            <input type="submit" value="Back to course details" class="submit-link outline mbt-20"/>
        </sf:form>

    </div>
</div>
<div class="container mb-20" align="center">
    <div class="links">
        <a href="javascript:history.back();">Back</a> |
        <a href="<c:url value="/"/>">Home</a>
    </div>
</div>


<mt:footer> </mt:footer>