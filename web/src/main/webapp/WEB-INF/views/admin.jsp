<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="с" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Admin Page"> </mt:header>


<div class="page mh100">
    <div class="container pt1_5">
        <div class="crm-form full-width row">
            <h1 class="fontSize180 silver">Admin Page</h1>
            <div class="form-group t-row col-xs-6">
                <p class="col-sm-6">Add a certificate for an existing user</p>
                <div class="col-sm-6 d-flex align-right">
                    <form:form cssClass="buttonText" method="post" action="addCertificateWithUserId">
                        <input type="submit" class="submit-link outline" name="addCertificate" value="Add Certificate">
                    </form:form>
                </div>
            </div>
            <div class="form-group t-row col-xs-6">
                <p class="col-sm-6">Add a certificate for a new user</p>
                <div class="col-sm-6 d-flex align-right">
                    <form:form cssClass="buttonText" method="post" action="addCertificateAndCreateUser">
                        <input type="submit" class="submit-link outline" name="addCertificate" value="Add Certificate">
                    </form:form>
                </div>
            </div>
            <div class="form-group t-row col-xs-6">
                <p class="col-sm-6">Create a new payment</p>
                <div class="col-sm-6 d-flex align-right">
                    <form:form cssClass="buttonText" method="post" action="createPayment">
                        <input type="submit" class="submit-link outline" name="createPayment" value="Create a New Payment">
                    </form:form>
                </div>
            </div>
            <div class="form-group t-row col-xs-6">
                <p class="col-sm-6">Add a new course</p>
                <div class="col-sm-6 d-flex align-right">
                    <form:form cssClass="buttonText" method="get" action="/addCourse">
                        <input type="submit" class="submit-link outline" name="addCourse" value="Add Course">
                    </form:form>
                </div>
            </div>
            <div class="form-group t-row col-xs-6">
                <p class="col-sm-6">Course Info</p>
                <div class="col-sm-6 d-flex align-right">
                    <form:form cssClass="buttonText" method="post" action="/searchCourseJsp">
                        <input type="submit" class="submit-link outline" name="courseInfo" value="Course Info">
                    </form:form>
                </div>
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

</div>

<mt:footer> </mt:footer>