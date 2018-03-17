<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Registration"> </mt:header>

<div class="container">
    <div class="crm-form">
        <h1>Registration</h1>

        <form:form action="registration" method="post" commandName="userFormRegistration">
            <div class="form-group">
                <label for="crm-email">Email</label>
                <form:input class="form-control" id="crm-email" placeholder="Email" path="email"/>
                <form:errors path="email"/>
            </div>
            <div class="form-group">
                <label for="crm-password">Password</label>
                <form:password class="form-control" id="crm-password" placeholder="Password" path="password"/>
                <form:errors path="password"/>
            </div>
            <div class="form-group">
                <label for="crm-varify-password">Repeat password</label>
                <form:password class="form-control" id="crm-varify-password" placeholder="Confirm Password" path="verifyPassword"/>
                <form:errors path="verifyPassword"/>
            </div>
            <div class="form-group">
                <label for="crm-first-name">First name</label>
                <form:input class="form-control" id="crm-first-name" placeholder="First name" path="firstName"/>
                <form:errors path="firstName"/>
            </div>
            <div class="form-group">
                <label for="crm-last-name">Last name</label>
                <form:input class="form-control" id="crm-last-name" placeholder="Last name" path="lastName"/>
                <form:errors path="lastName"/>
            </div>
            <div class="form-group">
                <label for="crm-phone">Phone</label>
                <input class="form-control" id="phone" placeholder="Phone"
                            value="${userFormRegistration.phone}"/>
                <form:errors path="phone"/>
                <form:hidden class="buttonText" id="phoneHidden" path="phone"/>
            </div>

            <div class="g-recaptcha" data-sitekey="6LfuoCkUAAAAAJpyVDEXxqh-YgwfcV-V0C285XBM"></div>

            <div class="form-group">
                <c:if test="${captcha == false}">
                    <br><s:message code="reCaptcha.error"/><br>
                </c:if>
            </div>
            <input type="submit" class="submit-link" value="Register">

        </form:form>

        <form:errors path="userFormRegistration"/>

        <div class="links">
            <a href="javascript:history.back();">Back</a> |
            <a href="<c:url value="/"/>">Home</a>
        </div>
    </div>
</div>
</div>
<footer class="footer">
    <div class="container">
        <div class="right">
        </div>

        <div class="left">
            <div class="row">
                <div class="col-md-5">
                    <div class="copyright"><a href="https://vertex-academy.com" class="logo"></a>©
                        2015, Все права защищены
                    </div>
                </div>
                <div class="col-md-7">
                    <div class="cont"><span>г. Киев, ул. Мечникова, 16, оф. 4-1</span>|<span>(050) 205 77 99, (098) 205 77 99</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>

<script type="text/javascript" src="../../javascript/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../../javascript/bootstrap.min.js"></script>

<script type="text/javascript" src="../../javascript/main.js"></script>

<script src="https://www.google.com/recaptcha/api.js" async defer></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/12.1.8/js/intlTelInput.js"></script>
<script src="../../javascript/phoneNumberFormat.js"></script>
<script type="text/javascript">
    let link = document.createElement('link');
    link.setAttribute('href', 'https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/12.1.8/css/intlTelInput.css');
    link.setAttribute('rel', 'stylesheet');
    document.head.appendChild(link);
</script>
</body>
</html>