<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Reset Password"> </mt:header>


<div class="container">
    <div class="crm-form">
        <h1>Reset Your Password</h1>

        <c:if test="${captcha == true}">
            <div class="red fontSize180">
                You missed captcha!
            </div>
            <br><br>
        </c:if>

        <c:if test="${emailInvalid == true}">
            <div class="red fontSize180">
                Invalid email!
            </div>
            <br><br>
        </c:if>

        <c:if test="${emailNotFound == true}">
            <div class="red fontSize180">
                This email is not registered or has not been activated yet. Make sure you enter the correct email!
            </div class="red fontSize180">
            <br><br>
        </c:if>

        <sf:form action="sendEmail" method="get" commandName="passwordResetDto">
            <table>
                <tr>
                    <td><span class="fontSize140 bold silver">
                            Enter your email address and we will send you the link to reset your password</span><br><br>
                    </td>
                </tr>
                <tr>
                    <td><input type="text" name="email" value="${email}"
                               placeholder="email@example.com" class="form-control"><br></td>
                </tr>
                <tr>
                    <td>
                        <div class="g-recaptcha" data-sitekey="6LfuoCkUAAAAAJpyVDEXxqh-YgwfcV-V0C285XBM"></div>
                        <br>
                    </td>
                </tr>
                <tr>
                    <td class="buttonPaddingTop"><input type="submit" value="Reset" class="black"></td>
                </tr>
            </table>
        </sf:form>

    </div>
    <div class="links">
        <a href="javascript:history.back();">Back</a> |
        <a href="<c:url value="/"/>">Home</a>
    </div>
</div>

<script src='https://www.google.com/recaptcha/api.js'></script>
<mt:footer> </mt:footer>
