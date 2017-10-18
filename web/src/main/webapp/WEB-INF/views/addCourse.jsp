<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Home"> </mt:header>

<div class="page mh100">
    <div class="container pt1_5" align="center">

        <div class="crm-form mt60">
            <form:form action="addCourse" method="post" commandName="course">
                <h1 class="fontSize180 silver">Add A New Course</h1>
                <div class="form-group">
                    <label for="course-name">Course name</label>
                    <form:input class="form-control" id="course-name" placeholder="Course name" path="name"/>
                    <form:errors path="name"/>
                </div>
                <div class="form-group">
                    <label for="start">Start Date</label>
                    <form:input class="form-control" id="start" placeholder="YYYY-MM-DD" path="start"/>
                    <form:errors path="start"/>
                </div>
                <div class="form-group">
                    <label class="form-check-label">
                        Finished? <form:checkbox class="form-check-input" placeholder="finished" path="finished"/>
                    </label>
                    <form:errors path="finished"/>
                </div>
                <div class="form-group">
                    <label for="price">Price</label>
                    <form:input class="form-control" id="price" placeholder="Course price" path="price"/>
                    <form:errors path="price"/>
                </div>
                <div class="form-group">
                    <label for="teacher">Teacher</label>
                    <form:select path="teacher.userId" class="form-control">
                        <form:options items="${teachers}"/>
                    </form:select>
                </div>
                <div class="form-group">
                    <label for="schedule">Schedule:</label>
                    <form:input class="form-control" id="schedule" placeholder="Schedule" path="schedule"/>
                    <form:errors path="schedule"/>
                </div>
                <div class="form-group">
                    <label for="notes">Notes:</label>
                    <form:textarea class="form-control" rows="3" placeholder="Notes" path="notes"/>
                    <form:errors path="notes"/>
                </div>
                <input type="submit" class="submit-link" value="Add Course">
            </form:form>
        </div>


    </div>
</div>

<mt:footer> </mt:footer>