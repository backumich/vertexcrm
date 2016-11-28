<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vertex CRM: for unregistered</title>
    <style type="text/css">
        /*.block1 {*/
        /*background: #008abd none repeat scroll 0 0;*/
        /*border: medium none;*/
        /*color: #ffffff;*/
        /*font-family: "wf_SegoeUI";*/
        /*font-size: 14px;*/
        /*height: 32px;*/
        /*position: relative;*/
        /*vertical-align: middle;*/
        /*text-align: center;*/
        /*width: 200px;*/
        /*!*top: 100px;*!*/
        /*!*left: 400px;*!*/
        /*}*/

        body {
            font: 12px/18px Arial, sans-serif;
            width: 100%;
            height: 100%;
            background: none repeat scroll 0 0 #edf1f5;
        }
    </style>
</head>
<body>

<div>
    <form method="get" action="${pageContext.request.contextPath}/certificateDetails">
        <div class="block1">
            <h2>Enter certificate ID:</h2>
            <input type="text" name="certificationId">
            <input type="submit" value="Send">
        </div>
    </form>
</div>
<br>

<c:set var="certificateIsNull" value="${certificateIsNull}"/>
<h2>${certificateIsNull}</h2>
<c:set var="certificationId" value="${certificationId}"/>
<h2>${certificationId}</h2>
<c:set var="userId" value="${userId}"/>
<h2>${userId}</h2>
<c:set var="userFirstName" value="${userFirstName}"/>
<h2>${userFirstName}</h2>
<c:set var="userLastName" value="${userLastName}"/>
<h2>${userLastName}</h2>
<c:set var="certificationDate" value="${certificationDate}"/>
<h2>${certificationDate}</h2>
<c:set var="courseName" value="${courseName}"/>
<h2>${courseName}</h2>
<c:set var="language" value="${language}"/>
<h2>${language}</h2>

<a href="javascript:history.back();"><h3>Back</h3></a>
<a href="/"><h3>Home</h3></a>
</body>
</html>
