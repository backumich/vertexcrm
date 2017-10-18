<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Course Details"> </mt:header>


<div align="center" class="page mh100 course-details">
    <div class="container pt1_5" align="center">
        <div class="crm-form mt60 full-width">
            <form:form commandName="course" action="updateCourse" cssClass="buttonText" method="post">
                <h1>Course Details</h1>
                <div class="container-fluid">
                    <div class="row d-flex align-middle">
                        <div class="col-xs-3 d-flex align-middle"><p>Course id:</p></div>
                        <div class="col-xs-9 d-flex align-middle">
                            <form:label path="id">${course.id}</form:label>
                            <form:hidden path="id" value="${course.id}"></form:hidden>
                        </div>
                    </div>
                    <div class="row d-flex align-middle">
                        <div class="col-xs-3 d-flex align-middle"><p>Course name:</p></div>
                        <div class="col-xs-9 d-flex align-middle">
                            <form:input id="name" class="form-control" type="text" value="${course.name}" path="name"/>
                        </div>
                        <form:errors path="name"/>
                    </div>
                    <div class="row d-flex align-middle">
                        <div class="col-xs-3 d-flex align-middle"><p>Start date:</p></div>
                        <div class="col-xs-9 d-flex align-middle">
                            <form:input id="start" class="form-control" type="text" value="${course.start}"
                                        path="start"/>
                        </div>
                        <form:errors path="start"/>

                    </div>
                    <div class="row d-flex align-middle">
                        <div class="col-xs-3 d-flex align-middle"><p>Price:</p></div>
                        <div class="col-xs-9 d-flex align-middle">
                            <form:label path="price">${course.price}</form:label>
                            <form:hidden path="price" value="${course.price}"></form:hidden>
                        </div>
                    </div>
                    <div class="row d-flex align-middle">
                        <div class="col-xs-3 d-flex align-middle"><p>Teacher name:</p></div>
                        <div class="col-xs-9 d-flex align-middle">
                            <form:select path="teacher.userId" class="form-control">
                                <form:option value="${teacher.userId}" label="--- Select ---"/>
                                <form:options items="${teachers}"/>
                            </form:select>
                        </div>
                    </div>
                    <div class="row d-flex align-middle">
                        <div class="col-xs-3 d-flex align-middle"><p>Schedule:</p></div>
                        <div class="col-xs-9 d-flex align-middle">
                            <form:input id="schedule" class="form-control" type="text" value="${course.schedule}"
                                                                         path="schedule"/>
                        </div>
                        <form:errors path="schedule"/>
                    </div>
                    <div class="row d-flex align-middle">
                        <div class="col-xs-3 d-flex align-middle"><p>Notes:</p></div>
                        <div class="col-xs-9 d-flex align-middle">
                            <form:textarea id="notes" class="form-control" rows="3"
                                                                         path="notes" value="${course.notes}"/>
                        </div>
                        <form:errors path="notes"/>
                    </div>
                    <input type="submit" class="submit-link" value="Update">
                </div>
            </form:form>

            <h2 class="mt60">Manage users of this course:</h2>
            <form:form action="showCourseAndUsers" method="get" commandName="course">
                <input type="hidden" name="id" value="${course.id}"/>
                <input type="submit" class="submit-link" value="Show users">
            </form:form>
        </div>

    </div>
    <div class="container" align="center">
        <c:if test="${!empty msg}">
            <p class="error-text">${msg}</p>
        </c:if>
    </div>

    <div class="container mb-20" align="center">
        <div class="links">
            <a href="javascript:history.back();">Back</a> |
            <a href="<c:url value="/"/>">Home</a>
        </div>
    </div>
</div>

<mt:footer> </mt:footer>