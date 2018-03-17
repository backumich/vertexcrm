<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Passport Saved"> </mt:header>


<div align="center" class="page gray-page mh100 up-padding">
    <span class="fontSize200 silver">Enter New Password</span><br><br><br>

    <c:if test="${expired == true}">
        <div class="red fontSize180">
            The link to change your password has expired!
            If you still need to change your password,
            <a href="<c:url value="http://localhost:8080/resetPassword"/>">get a new link</a>
        </div>
        <br><br>
    </c:if>
    <c:if test="${empty expired}">

        <c:if test="${passwordInvalid == true}">
            <div class="red fontSize180">
                Invalid password format! Try again!
            </div>
            <br><br>
        </c:if>

        <c:if test="${passwordMismatch == true}">
            <div class="red fontSize180">
                Passwords do not match! Try again!
            </div>
            <br><br>
        </c:if>

        <c:if test="${captcha == true}">
            <div class="red fontSize180">
                You missed captcha!
            </div>
            <br><br>
        </c:if>

        <sf:form action="/passwordSaveNew" method="post" commandName="passwordDto">
            <sf:hidden path="email" value="${passwordDto.email}"/>
            <table>
                <tr>
                    <td><sf:label path="rawPassword">
                        <span class="fontSize140 bold silver">New password:</span></sf:label></td>
                    <td><sf:input path="rawPassword" placeholder="5-30 symbols long" type="password"
                                  class="width300" maxlength="30"/></td>
                </tr>
                <tr>
                    <td><sf:label path="repeatPassword">
                        <span class="fontSize140 bold silver">Repeat password:</span></sf:label></td>
                    <td><sf:input path="repeatPassword" placeholder="5-30 symbols long" type="password"
                                  class="width300" maxlength="30"/></td>
                </tr>
                <tr>
                    <td><br><br><br>
                        <div class="g-recaptcha" data-sitekey="6LfuoCkUAAAAAJpyVDEXxqh-YgwfcV-V0C285XBM"></div>
                        <br>
                    </td>
                </tr>
                <tr>
                    <td class="buttonPaddingTop"><input type="submit" value="Save" class="black"></td>
                </tr>
            </table>
        </sf:form>
    </c:if>

     <div class="links">
        <a href="javascript:history.back();">Back</a> |
        <a href="<c:url value="/"/>">Home</a>
    </div>
</div>


<script src='https://www.google.com/recaptcha/api.js'></script>
<mt:footer> </mt:footer>