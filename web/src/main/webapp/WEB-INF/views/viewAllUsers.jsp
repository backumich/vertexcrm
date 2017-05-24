<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en" charset="UTF-8">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Vertex Crm</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="./css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="css/slick.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="icon" href="https://vertex-academy.com/favicon.ico" type="image/x-icon">
    <link rel="shortcut icon" href="https://vertex-academy.com/favicon.ico" type="image/x-icon">
    <link rel="apple-touch-icon" href="https://vertex-academy.com/apple-touch-icon.png">
    <script type="text/javascript" async="" src="javascript/watch.js"></script>
    <script async="" src="javascript/analytics.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <style id="style-1-cropbar-clipper">
        .en-markup-crop-options {
            top: 18px !important;
            left: 50% !important;
            margin-left: -100px !important;
            width: 200px !important;
            border: 2px rgba(255, 255, 255, .38) solid !important;
            border-radius: 4px !important;
        }

        .en-markup-crop-options div div:first-of-type {
            margin-left: 0px !important;
        }

        .colortext {
            background-color: #ffe; /* Цвет фона */
            color: black; /* Цвет текста */
        }

        .buttonText {
            color: black;
        }
    </style>
</head>
<body class="inside footer-under">
<script type="text/javascript">
    $(document).ready(function () {
        $('#perPage').change(function () {
            $('#submit').click();
        });

        $('a.page').click(function () {
            var id = $(this).attr('id');
            $('#nextPage').val(id);
            $('#submit').click();
        });
    });
</script>
<noscript>&lt;div&gt;&lt;img src="https://mc.yandex.ru/watch/37563830" style="position:absolute; left:-9999px;" alt="" /&gt;&lt;/div&gt;</noscript>
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
    <div class="container pt1_5">
        <div align="center">
            <c:if test="${dataNavigator.currentNumberPage!=1}">
                <a id="1" class="page" style="cursor: pointer;"><<</a>
            </c:if>
            <c:if test="${dataNavigator.currentNumberPage>1}">
                <a id="${dataNavigator.currentNumberPage-1}" class="page" style="cursor: pointer;">Prev</a>
            </c:if>

            <c:forEach begin="1" end="${dataNavigator.quantityPages}" var="val">
                <c:if test="${val==dataNavigator.currentNumberPage}">
                    <a style="color: #ffffff" id="${val}" class="page" style="cursor: pointer;">${val}</a>
                </c:if>
                <c:if test="${val!=dataNavigator.currentNumberPage}">
                    <a id="${val}" class="page" style="cursor: pointer;">${val}</a>
                </c:if>
            </c:forEach>

            <c:if test="${dataNavigator.currentNumberPage<dataNavigator.lastPage}">
                <a id="${dataNavigator.currentNumberPage+1}" class="page" style="cursor: pointer;">Next</a>
            </c:if>
            <c:if test="${dataNavigator.lastPage!=dataNavigator.currentNumberPage}">
                <a id="${dataNavigator.lastPage}" class="page" style="cursor: pointer;">>></a>
            </c:if>
            <br/>
            <br/>
            <br/>
            <form:form action="viewAllUsers" method="post" commandName="dataNavigator">
                <form:select id="perPage" path="currentRowPerPage" items="${dataNavigator.countRowPerPage}"/>

                <input id="currentNamePage" type="hidden" name="currentNamePage"
                       value="${dataNavigator.currentNamePage}">
                <input id="currentNumberPage" type="hidden" name="currentNumberPage"
                       value="${dataNavigator.currentNumberPage}">
                <input id="nextPage" type="hidden" name="nextPage" value="${dataNavigator.nextPage}">
                <input id="lastPage" type="hidden" name="lastPage" value="${dataNavigator.lastPage}">
                <input id="currentRowPerPage" type="hidden" name="currentRowPerPage"
                       value="${dataNavigator.currentRowPerPage}">
                <input id="quantityPages" type="hidden" name="quantityPages" value="${dataNavigator.quantityPages}">
                <input id="dataSize" type="hidden" name="dataSize" value="${dataNavigator.dataSize}">

                <input value="Submit" id="submit" class="buttonText" type="submit" style="display:none;"/>
            </form:form>
            <br/>
            <br/>
            <br/>

            <table bordercolor="red" border="2">

                <tr>
                    <th width="60px">User ID</th>
                    <th width="150px">E-mail</th>
                    <th width="150px">Last name</th>
                    <th width="150px">First name</th>
                    <th width="150px">Phone</th>
                    <th width="100px"></th>
                </tr>
                <c:if test="${empty users}">
                    <tr>
                        <td>There are no users!</td>
                    </tr>
                </c:if>
                <c:forEach var="users" items="${users}">
                    <tr>
                        <td>${users.userId} </td>
                        <td>${users.email} </td>
                        <td>${users.lastName} </td>
                        <td>${users.firstName} </td>
                        <td>${users.phone} </td>
                        <td>
                            <c:set var="titleURL">
                                <c:url value="userDetails">
                                    <c:param name="userId" value="${users.userId}"/>
                                </c:url>
                            </c:set>
                            <a href="${titleURL}">Detail</a>
                        </td>
                    </tr>
                </c:forEach>

            </table>
            <br/>
            <br/>
            <br/>
            <div class="href">
                <a href="javascript:history.back();">Back</a> |
                <a href="<c:url value="/" />">Home</a>
            </div>
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
<script type="text/javascript" src="javascript/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="javascript/bootstrap.min.js"></script>
<script src="./javascript/typed.js"></script>
<script src="javascript/slick.min.js"></script>
<script type="text/javascript" src="javascript/main.js"></script>

</body>
</html>