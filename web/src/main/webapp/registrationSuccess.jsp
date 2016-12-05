<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link href="<c:url value="css/registration.css" />" rel="stylesheet">
    <%--<link rel="stylesheet" type="text/css" href="css/registration.css"/>--%>
    <%--<script src="js/index.js"></script>--%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>

<body>
<div align="center">
    <table border="0">
        <tr>
            <td colspan="2" align="center"><h2>Congratulation ${user.firstName} ${user.lastName}, registration
                success </h2></td>
        </tr>
    </table>

</div>

</body>
</html>