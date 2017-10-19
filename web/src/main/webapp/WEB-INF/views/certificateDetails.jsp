<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Certificate Details"> </mt:header>


<div align="center" class="page mh100 certificate-details">
    <div class="container pt1_5">
       <div class="crm-form mt60 full-width">
           <h1>Certificate Details</h1>
           <c:if test="${certificate == null}">
               <div class="row">
                   <div class="col-xs-3">
                       <p class="">Enter certificate UID:</p>
                   </div>
                   <div class="col-xs-9">
                       <sf:form method="get" action="getCertificate">
                           <input placeholder="xxxx-xxxx-xxxx-xxxx" id="show-certificate" type="text"
                                  name="certificateUid" class="form-control" maxlength="19"/>
                           <input type="submit" class="submit-link" value="Send">
                       </sf:form>
                   </div>
               </div>
               <c:if test="${errorMessage != null}">
                   <div class="error-box mt60 small">
                       <p class="error-text ac">${errorMessage}</p>
                   </div>
               </c:if>

           </c:if>

            <c:if test="${errorMessage == null && certificate != null}">
                <table class="courses-result full-width">
                    <tr>
                        <td>Certificate UID:</td>
                        <td>${certificate.certificateUid}</td>
                    </tr>
                    <tr>
                        <td>Certificate Holder First Name:</td>
                        <td>
                            <span class="error-text">
                                <c:if test="${user.firstName == null}">No holder assigned</c:if>
                            </span>
                            <c:if test="${user.firstName != null}">${user.firstName}</c:if></td>
                    </tr>
                    <tr>
                        <td>Certificate Holder Last Name:</td>
                        <td>
                            <span class="error-text">
                                    <c:if test="${user.lastName == null}">No holder assigned</c:if>
                            </span>
                            <c:if test="${user.lastName != null}">${user.lastName}</c:if></td>
                    </tr>
                    <tr>
                        <td>Certification Date:</td>
                        <td>${certificate.certificationDate}</td>
                    </tr>
                    <tr>
                        <td>Course Name:</td>
                        <td>${certificate.courseName}</td>
                    </tr>
                    <tr>
                        <td>Language:</td>
                        <td>${certificate.language}</td>
                    </tr>
                    <tr>
                        <td>Certificate Link:</td>
                        <td>
                            <a href="<c:url value="/getCertificate/${certificate.certificateUid}"/>">
                                localhost:8080/getCertificate/${certificate.certificateUid}</a></td>
                    </tr>
                </table>
                <br>

                <sf:form method="get" action="/showImage" commandName="user">
                    <input type="hidden" name="userId" value="${user.userId}"/>
                    <input type="hidden" name="pageToDisplay" value="image"/>
                    <input type="hidden" name="imageType" value="photo"/>
                    <input class="submit-link" type="submit" value="Show Certificate Holder Photo">
                </sf:form>
                <br><br>

                <sec:authorize access="isAuthenticated()">
                    <sec:authentication property="principal.username" var="authenticated"/>
                </sec:authorize>

                <c:if test="${(user.email).equals(authenticated)}">
                    <sf:form method="post" action="/generatePdf" commandName="dto">
                        <input type="hidden" name="certificateUid" value="${certificate.certificateUid}"/>
                        <input type="hidden" name="email" value="${user.email}"/>
                        <input type="hidden" name="firstName" value="${user.firstName}"/>
                        <input type="hidden" name="lastName" value="${user.lastName}"/>
                        <input type="hidden" name="courseName" value="${certificate.courseName}"/>
                        <input type="hidden" name="certificationDate" value="${(certificate.certificationDate).toString()}"/>
                        <input class="black" type="submit" value="Generate certificate PDF"/>
                    </sf:form>
                </c:if>
            </c:if>

       </div>
    </div>

    <div class="container mb-20" align="center">
        <div class="links">
            <a href="javascript:history.back();">Back</a> |
            <a href="<c:url value="/"/>">Home</a>
        </div>
    </div>
</div>


<mt:footer> </mt:footer>