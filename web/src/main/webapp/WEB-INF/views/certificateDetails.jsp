<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
<!DOCTYPE html>
<!-- saved from url=(0048)https://vertex-academy.com/lecturer-bakumov.html -->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Vertex Crm</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="<c:url value='/css' />" rel="stylesheet" type="text/css">
    <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet"/>
    <link href="<c:url value='/css/bootstrap-theme.min.css' />" rel="stylesheet"/>
    <link href="<c:url value='/css/slick.css' />" rel="stylesheet"/>
    <link href="<c:url value='/css/main.css' />" rel="stylesheet"/>
    <link href="<c:url value='/css/additional.css' />" rel="stylesheet"/>
    <link rel="icon" href="https://vertex-academy.com/favicon.ico" type="image/x-icon">
    <link rel="shortcut icon" href="https://vertex-academy.com/favicon.ico" type="image/x-icon">
    <link rel="apple-touch-icon" href="https://vertex-academy.com/apple-touch-icon.png">
    <script type="text/javascript" async="" src="javascript/watch.js"></script>
    <script async="" src="javascript/analytics.js"></script>
    <%--suppress CommaExpressionJS --%>
    <script>
        (function (i, s, o, g, r, a, m) {
            i['GoogleAnalyticsObject'] = r;
            i[r] = i[r] || function () {
                    (i[r].q = i[r].q || []).push(arguments)
                }, i[r].l = 1 * new Date();
            a = s.createElement(o),
                m = s.getElementsByTagName(o)[0];
            a.async = 1;
            a.src = g;
            m.parentNode.insertBefore(a, m)
        })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

        ga('create', 'UA-62731553-2', 'auto');
        ga('send', 'pageview');

    </script>
    <style id="style-1-cropbar-clipper">/* Copyright 2014 Evernote Corporation. All rights reserved. */

    .en-markup-crop-options div div:first-of-type {
        margin-left: 0 !important;
    }


    </style>
</head>
<body class="inside footer-under">
<!-- Yandex.Metrika counter -->
<script type="text/javascript">
    (function (d, w, c) {
        (w[c] = w[c] || []).push(function () {
            try {
                w.yaCounter37563830 = new Ya.Metrika({
                    id: 37563830,
                    clickmap: true,
                    trackLinks: true,
                    accurateTrackBounce: true,
                    webvisor: true
                });
            } catch (e) {
            }
        });

        var n = d.getElementsByTagName("script")[0],
            s = d.createElement("script"),
            f = function () {
                n.parentNode.insertBefore(s, n);
            };
        s.type = "text/javascript";
        s.async = true;
        s.src = "https://mc.yandex.ru/metrika/watch.js";

        //noinspection JSValidateTypes
        if (w.opera == "[object Opera]") {
            d.addEventListener("DOMContentLoaded", f, false);
        } else {
            f();
        }
    })(document, window, "yandex_metrika_callbacks");
</script>
<noscript>&lt;div&gt;&lt;img src="https://mc.yandex.ru/watch/37563830" style="position:absolute; left:-9999px;" alt="" /&gt;&lt;/div&gt;</noscript>
<!-- /Yandex.Metrika counter -->
<div class="nav navbar navbar-fixed-top navbar-hide">
    <div class="container"><a href="https://vertex-academy.com/index.html" class="logo pull-left"></a>
        <button data-toggle="collapse" data-target=".navbar-collapse" class="navbar-toggle"><span
                class="glyphicon glyphicon-align-justify"></span></button>
        <div class="collapse navbar-collapse navbar-responsive-collapse">
            <ul class="navbar-nav">
                <li><a href="https://vertex-academy.com/index.html#courses">Курсы</a></li>
                <li><a href="https://vertex-academy.com/index.html#about">о нас</a></li>
                <li><a href="https://vertex-academy.com/index.html#faq">FAQ</a></li>
                <li><a href="https://vertex-academy.com/index.html#feedback">Отзывы</a></li>
                <li><a href="https://vertex-academy.com/index.html#lecturers">Преподаватели</a></li>
                <li><a href="https://vertex-academy.com/tutorials/index.php" class="link">Блог</a></li>
                <li><a href="https://vertex-academy.com/index.html#contacts">Контакты</a></li>
                <li><a href="https://vertex-academy.com/tutorials/en/login/">Войти </a></li>
            </ul>
        </div>
    </div>
</div>


<div align="center" class="page gray-page mh100 up-padding">

    <span class="fontSize180 silver">Certificate Details</span><br><br><br>

    <c:if test="${certificate == null}">
        <span class="fontSize125 bold">Enter certificate UID:</span><br><br>

        <sf:form cssClass="black" method="get" action="getCertificate">
            <input placeholder="xxxx-xxxx-xxxx-xxxx" type="text" name="certificateUid" size="20" maxlength="19"/>
            <input type="submit" value="Send">
        </sf:form>
    </c:if>
    <br><br>

    <c:if test="${errorMessage != null}">
        <h3><span class="fontSize140 red">${errorMessage}</span></h3>
    </c:if>

    <c:if test="${errorMessage == null && certificate != null}">
        <table class="table fontSize140">
            <tr>
                <td>Certificate UID:</td>
                <td>${certificate.certificateUid}</td>
            </tr>
            <tr>
                <td>Certificate Holder First Name:</td>
                <td><span class="red">
                            <c:if test="${user.firstName == null}">No holder assigned</c:if></span>
                    <c:if test="${user.firstName != null}">${user.firstName}</c:if></td>
            </tr>
            <tr>
                <td>Certificate Holder Last Name:</td>
                <td><span class="red">
                            <c:if test="${user.lastName == null}">No holder assigned</c:if></span>
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
                <td class="fontSize70">
                    <a href="<c:url value="/getCertificate/${certificate.certificateUid}"/>">
                            ${pageContext.request.contextPath}/getCertificate/${certificate.certificateUid}</a></td>
            </tr>
        </table>
        <br>

        <sf:form method="post" action="/showImagePhoto" commandName="user">
            <input type="hidden" name="email" value="${user.email}"/>
            <input type="hidden" name="pageToDisplay" value="image"/>
            <input type="hidden" name="imageType" value="photo"/>
            <input class="black" type="submit" value="Show Certificate Holder Photo">
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

    <br>
    <div class="href">
        <a href="javascript:history.back();">Back</a> |
        <a href="<c:url value="/" />">Home</a>
    </div>
</div>


<div class="footer">
    <div class="container">
        <div class="right">
            <div class="social-block"><a href="https://www.facebook.com/vertex.academy" class="fb"></a><a
                    href="https://vk.com/vertex_academy" class="vk"></a><a href="https://twitter.com/vertex_academy"
                                                                           class="tw"></a><a
                    href="https://vertex-academy.com/lecturer-bakumov.html#" class="gp"></a><a
                    href="https://vertex-academy.com/lecturer-bakumov.html#" class="in"></a></div>
        </div>
        <div class="left">
            <div class="row">
                <div class="col-md-5">
                    <div class="copyright"><a href="https://vertex-academy.com/lecturer-bakumov.html#" class="logo"></a>©
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
</div>
<div id="myModal" class="modal modal-gray">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" data-dismiss="modal" aria-hidden="true" class="close"></button>
            </div>
            <div class="modal-body">
                <div class="send-mail">
                    <form id="sendmessage">
                        <div class="title">Записаться на курс</div>
                        <div class="desc">Оставьте Ваши контакты<br>и мы свяжемся с Вами в течение 24 часов</div>
                        <div class="row">
                            <div class="col-sm-6">
                                <input type="text" placeholder="Имя" name="fmail[name]" class="form-control"
                                       required="">
                            </div>
                            <div class="col-sm-6">
                                <input type="text" placeholder="Телефон" name="fmail[phone]" class="form-control"
                                       required="" pattern="[0-9]{6,}">
                            </div>
                        </div>
                        <textarea placeholder="Комментарий" name="fmail[text]" class="form-control"
                                  required=""></textarea>
                        <input type="submit" value="Записаться на курс" class="btn btn--v1 btn-block">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="javascript/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="javascript/bootstrap.min.js"></script>
<script src="./javascript/typed.js"></script>
<script src="javascript/slick.min.js"></script>
<script type="text/javascript" src="javascript/main.js"></script>

</body>
</html>