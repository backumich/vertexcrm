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
    <link href="<c:url value='/css/sva.css' />" rel="stylesheet"/>
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

    <div class="fontSize180">Assigned users:</div>
    <br>
    <table class="tableSpacing">
        <tr class="fontSize125 tableHeader">
            <td class="labelWidth200">Email</td>
            <td class="labelWidth150">First Name</td>
            <td class="labelWidth150">Last Name</td>
            <td class="labelWidth150">Phone</td>
            <td class="labelWidth150">Remove from course</td>
        </tr>
        <tr></tr>
        <tr></tr>
        <c:forEach items="${assignedUsers}" var="assignedUser">
            <sf:form action="confirmUserRemovalFromCourse" method="post" commandName="dto">
                <input type="hidden" name="courseId" value="${dto.courseId}">
                <input type="hidden" name="userId" value="${assignedUser.userId}">
                <tr>
                    <td>${assignedUser.email}</td>
                    <td>${assignedUser.firstName}</td>
                    <td>${assignedUser.lastName}</td>
                    <td>${assignedUser.phone}</td>
                    <td><input type="submit" value="Remove" class="black"/></td>
                </tr>
            </sf:form>
        </c:forEach>
    </table>
    <br><br><br>

    <div class="fontSize180">Search for a user to assign (by name or email):</div>
    <br>
    <sf:form action="searchForUsersToAssign" method="get" commandName="dto">
        <input type="hidden" name="courseId" value="${dto.courseId}">
        <table>
            <tr>
                <td><label for="searchParam" class="fontSize125 labelWidth300">
                    Enter first name, or last name, or email:</label></td>
                <td><input type="text" name="searchParam" id="searchParam" maxlength="255"
                           placeholder="First name, or last name, or email" class="labelWidth200 black"/></td>
            </tr>
            <tr>
                <td><span class="fontSize125">Select type of search:</span></td>
                <td>
                    <label class="black">
                        <select name="searchType">
                            <option selected value="first_name">By first name</option>
                            <option value="last_name">By last name</option>
                            <option value="email">By email</option>
                        </select>
                    </label>
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="buttonPaddingTop fontSize125"><input type="submit" value="Search" class="black"></td>
            </tr>
        </table>
    </sf:form>

    <c:if test="${!empty search}">
        <br>
        <sf:form action="clearSearchResults" method="get" commandName="dto">
            <input type="hidden" name="courseId" value="${dto.courseId}">
            <table>
                <tr>
                    <td class="buttonPaddingTop fontSize125">
                        <input type="submit" value="Clear search results" class="black"></td>
                </tr>
            </table>
        </sf:form>
    </c:if>
    <br><br>

    <c:if test="${!empty freeUsers and !empty search}">
        <div class="fontSize180">Search results:</div>
        <br>
        <table class="tableSpacing">
            <tr class="fontSize125 tableHeader">
                <td class="labelWidth200">Email</td>
                <td class="labelWidth150">First Name</td>
                <td class="labelWidth150">Last Name</td>
                <td class="labelWidth150">Phone</td>
                <td class="labelWidth150">Assign to course</td>
            </tr>
            <tr></tr>
            <tr></tr>
            <c:forEach items="${freeUsers}" var="freeUser">
                <sf:form action="assignUser" method="post" commandName="dto">
                    <input type="hidden" name="searchType" value="${dto.searchType}">
                    <input type="hidden" name="searchParam" value="${dto.searchParam}">
                    <input type="hidden" name="courseId" value="${dto.courseId}">
                    <input type="hidden" name="userId" value="${freeUser.userId}">
                    <input type="hidden" name="email" value="${freeUser.email}">
                    <input type="hidden" name="firstName" value="${freeUser.firstName}">
                    <input type="hidden" name="lastName" value="${freeUser.lastName}">
                    <input type="hidden" name="phone" value="${freeUser.phone}">
                    <tr>
                        <td>${freeUser.email}</td>
                        <td>${freeUser.firstName}</td>
                        <td>${freeUser.lastName}</td>
                        <td>${freeUser.phone}</td>
                        <td><input type="submit" value="Assign" class="black"/></td>
                    </tr>
                </sf:form>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${empty freeUsers and !empty search}">
        <div class="fontSize180">Search results:</div>
        <br>
        <span class="fontSize140">No users found</span>
    </c:if><br><br><br>

    <sf:form action="courseDetails" method="post">
        <input type="hidden" name="courseId" value="${dto.courseId}"/>
        <input type="submit" value="Back to course details" class="black fontSize125"/>
    </sf:form>

    <br><br><br>
    <div class="href">
        <a href="javascript:history.back();">Back</a> |
        <a href="<c:url value="/" />">Home</a>
    </div>
    <br><br>
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