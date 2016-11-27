<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
    <form method="get" action="${pageContext.request.contextPath}/unregistered">
        <div class="block1">
            <h2>Enter certificate ID:</h2>
            <input type="text" name="certificationId">
            <input type="submit" value="Send">
        </div>
    </form>
</div>
<a href="javascript:history.back();"><h3>Back</h3></a>
<a href="/"><h3>Home</h3></a>
</body>
</html>
