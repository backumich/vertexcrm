<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<!-- saved from url=(0048)https://vertex-academy.com/lecturer-bakumov.html -->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Vertex Crm</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="../../css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <link rel="stylesheet" href="../../css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="../../css/slick.css">
    <link rel="stylesheet" href="../../css/main.css">
    <link rel="icon" href="https://vertex-academy.com/favicon.ico" type="image/x-icon">
    <link rel="shortcut icon" href="https://vertex-academy.com/favicon.ico" type="image/x-icon">
    <link rel="apple-touch-icon" href="https://vertex-academy.com/apple-touch-icon.png">
    <script type="text/javascript" async="" src="../../javascript/watch.js"></script>
    <script async="" src="../../javascript/analytics.js"></script>
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
<div class="page gray-page mh100">
    <div class="container pt1_5" align="centr">
        <c:if test="${empty users}">
            <form:form method="post" commandName="paymentForm" action="selectUserForPayment" name="payment" id="payment">
                <table class="active" width="500">
                    <tr>
                        <td><form:hidden path="courseId" value='${courseIdForPayment}'/></td>
                    </tr>
                    <tr>
                        <td><form:hidden path="userID" value='${userIdForPayment}'/></td>
                    </tr>
                    <tr>
                        <td><form:label path="payment.amount">Enter amount:</form:label></td>
                        <td><form:input placeholder="0,0" path="payment.amount" name="amount" id="amount"
                                        onchange= "this.value = this.value.replace(/,/g, '.')"/></td>
                        <td><form:errors path="payment.amount"/></td>
                    </tr>
                </table>
                <br>
                <tr>
                    <td colspan="5" align="center"><input type="submit" value="Pay"/></td>
                </tr>
            </form:form>
        </c:if>
    </div>
    <div>
        <c:if test="${!empty users}">
            <form:form method="post" commandName="paymentForm" action="selectUserForPayment" name="payment" id="payment">
                <table class="active" width="500">
                    <table class="active" width="500">
                        <tr>
                            <th>Select user</th>
                            <th>User Id</th>
                            <th>User E-mail</th>
                            <th>User first name</th>
                            <th>User last name</th>
                        </tr>
                        <c:forEach items="${users}" var="user">
                            <tr>
                                <td><form:radiobutton path="userID" checked="checked" value='${user.userId}'/></td>
                                <td>${user.userId}</td>
                                <td>${user.email}</td>
                                <td>${user.firstName}</td>
                                <td>${user.lastName}</td>
                                <td>
                                </td>

                            </tr>
                        </c:forEach>
                    </table>

                    <tr>
                        <td><form:hidden path="courseId" value='${courseIdForPayment}'/></td>
                    </tr>
                    <tr>
                        <td><form:label path="payment.amount">Enter amount:</form:label></td>
                        <td><form:input placeholder="0,0" path="payment.amount" name="amount" id="amount"
                                        onchange= "this.value = this.value.replace(/,/g, '.')"  /></td>
                        <td><form:errors path="payment.amount"/></td>
                    </tr>
                </table>
                <br>
                <tr>
                    <td colspan="5" align="center"><input type="submit" value="Pay"/></td>
                </tr>
            </form:form>

        </c:if>
    </div>

    <br>
    <br>
    <br>
    <c:if test="${!empty msg}">
        <h3><span class="errorText250">${msg}</span></h3>
    </c:if>
    <br>
    <br>
    <br>

    <div class="hrefText">
        <a href="javascript:history.back();">Back</a> |
        <a href="<c:url value="/" />">Home</a>
    </div>
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
<script type="text/javascript" src="../../javascript/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="../../javascript/bootstrap.min.js"></script>
<script src="../../javascript/typed.js"></script>
<script src="../../javascript/slick.min.js"></script>
<script type="text/javascript" src="../../javascript/main.js"></script>

</body>
</html>