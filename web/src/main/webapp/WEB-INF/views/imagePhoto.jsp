<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Certificate Details"> </mt:header>


<div align="center" class="page mh100 up-padding">

    <c:if test="${photo != null}">
        <img src="data:image/jpeg;base64,${photo}" width="auto" height="400" alt="no photo">
    </c:if>

    <sf:form method="post" action="/uploadImage" enctype="multipart/form-data" commandName="user">
        <sf:hidden path="userId" value="${user.userId}"/>
        <sf:hidden path="firstName" value="${user.firstName}"/>
        <sf:hidden path="lastName" value="${user.lastName}"/>
        <sf:hidden path="email" value="${user.email}"/>
        <sf:hidden path="discount" value="${user.discount}"/>
        <input type="hidden" name="imageType" value="photo"/>
        <table>
            <tr>
                <td class="silver"><input type="file" name="image" accept="image/jpeg, image/png"/></td>
            </tr>
            <tr>
                <td><input class="black" type="submit" value="Upload New Photo"></td>
            </tr>
        </table>
    </sf:form>



    <div class="container mb-20" align="center">
        <div class="links">
            <a href="javascript:history.back();">Back</a> |
            <a href="<c:url value="/"/>">Home</a>
        </div>
    </div>

</div>


<mt:footer> </mt:footer>