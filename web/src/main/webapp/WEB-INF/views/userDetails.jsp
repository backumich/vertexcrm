<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="User Details"> </mt:header>

<div class="container">
    <div class="crm-form mt60 full-width course-users">
        <%--user - то что отсылается в контроллер--%>
        <form:form action="saveUserData" method="post" commandName="user" enctype="multipart/form-data">
            <input type="hidden" name="user" value="user"/>
            <h1>User Details</h1>
            <table class="courses-result full-width">
                <c:if test="${!empty msg}">
                    <tr>
                        <td colspan="2">
                            <div align="center">
                                <c:if test="${!empty msg}">
                                    <p class="error-text">${msg}</p>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                </c:if>

                <tr>
                    <td>user Id</td>
                    <td><form:label path="userId">${user.userId}</form:label>
                        <form:hidden path="userId"></form:hidden></td>
                </tr>
                <tr>
                    <td>E-mail</td>
                    <td><form:input id="email" class="form-control" type="email" value="${user.email}"
                                    path="email"/>
                    <td><form:errors path="email" cssClass="red"/></td>
                </tr>
                <tr>
                    <td>First name</td>
                    <td><form:input id="firstName" class="form-control" type="text"
                                    value="${user.firstName}" path="firstName"/></td>
                    <td><form:errors path="firstName"/></td>
                </tr>
                <tr>
                    <td>Last name</td>
                    <td><form:input id="lastName" class="form-control" type="text" value="${user.lastName}"
                                    path="lastName"/></td>
                    <td><form:errors path="lastName"/></td>
                </tr>
                <tr>
                    <td>Passport scan</td>
                    <td class="block">
                        <img src="data:image/jpeg;base64,${user.passportScanAsString}" width="800" height="auto"
                             alt="No scan passport">
                        <input type="file" name="imagePassportScan" accept="image/*"/>
                    </td>

                    <form:hidden path="passportScan"/>
                    <td><form:errors path="passportScan" cssClass="red"/></td>
                </tr>
                <tr>
                    <td>Photo</td>
                    <td class="block">
                        <img src="data:image/jpeg;base64,${user.photoAsString}" width="400" height="auto"
                             alt="No photo">
                        <input type="file" name="imagePhoto" accept="image/*"/>
                    </td>
                    <form:hidden path="photo"/>
                    <td><form:errors path="photo" cssClass="red"/></td>
                </tr>
                <tr>
                    <td>Discount</td>
                    <td>
                        <form:input id="discount" class="form-control" type="number"
                                    value="${user.discount}" path="discount"/>
                        <form:errors path="discount"/>
                    </td>
                </tr>
                <tr>
                    <td>Phone</td>
                    <td><input id="crm-phone" class="form-control" type="text" value="${user.phone}"/>
                    <td><form:hidden class="buttonText" id="phoneHidden" path="phone"/></td>
                    <td><form:errors path="phone"/></td>
                </tr>
                <tr>
                    <td>User roles</td>
                    <td>
                        <c:if test="${empty user.role}">
                            Error! The current user does not have roles in the system!
                        </c:if>
                        <c:if test="${!empty user.role}">
                            <form:select path="role" items="${allRoles}"/>
                        </c:if>
                    </td>
                </tr>
            </table>


            <h2 class="mt60">User certificates</h2>

            <table class="courses-result full-width">
                <tr>
                    <th>№</th>
                    <th>Certification date</th>
                    <th>Course name</th>
                    <th>Language</th>
                </tr>
                <c:forEach items="${certificates}" var="certificates" varStatus="status">
                    <tr>
                        <td>${status.count}</td>

                            <%--<td><form:input name="certificates[${status.index}].certificationDate"--%>
                            <%--value="${certificates.certificationDate}" class="buttonText"--%>
                            <%--path="certificationDate"/>--%>
                            <%--</td>--%>

                        <td>${certificates.certificationDate}</td>

                        <td>${certificates.courseName}</td>
                        <td>${certificates.language}</td>
                    </tr>
                </c:forEach>
            </table>

            <input id="save" class="submit-link" type="submit" value="Save"/>

        </form:form>

        <div class="container mb-20" align="center">
            <div class="links">
                <a href="javascript:history.back();">Back</a> |
                <a href="<c:url value="/"/>">Home</a>
            </div>
        </div>
    </div>
</div>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/12.1.8/js/intlTelInput.js"></script>
<script src="../../javascript/phoneNumberFormat.js"></script>
<script type="text/javascript">
    let link = document.createElement('link');
    link.setAttribute('href', 'https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/12.1.8/css/intlTelInput.css');
    link.setAttribute('rel', 'stylesheet');
    document.head.appendChild(link);
</script>

<mt:footer> </mt:footer>
