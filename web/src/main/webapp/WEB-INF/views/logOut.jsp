<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="Certificate Details"> </mt:header>


<div align="center" class="page mh100">
    <div class="container pt1_5" align="center">
        <div class="crm-form mt60">
            <h1 class="ac">Want to log out?</h1>

            <div class="row">
                <div class="col-xs-3 col-xs-offset-3 ac">
                    <sf:form action="/logOut" method="post">
                        <input type="submit" class="submit-link" value="Yes"/>
                    </sf:form>
                </div>
                <div class="col-xs-3 ac">
                    <sf:form action="/logOutRefuse" method="get">
                        <input type="submit" class="submit-link" value="No"/>
                    </sf:form>
                </div>
            </div>

        </div>
    </div>
    <div class="container mb-20" align="center">
        <div class="links">
            <a href="javascript:history.back();">Back</a> |
            <a href="<c:url value="/"/>">Home</a>
        </div>
    </div>
</div>


<mt:footer> </mt:footer>