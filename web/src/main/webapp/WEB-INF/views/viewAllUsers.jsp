<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mt" tagdir="/WEB-INF/tags" %>

<mt:header title="All Users"> </mt:header>

<div class="container">
    <div class="crm-form mt60 full-width">
        <div class="d-flex align-right">
            <form:form action="viewAllUsers" method="post" commandName="dataNavigator">
                <form:select id="perPage" class="buttonText" path="rowPerPage"
                             items="${dataNavigator.countRowPerPage}"/>

                <input id="currentNamePage" type="hidden" name="currentNamePage"
                       value="${dataNavigator.currentNamePage}">
                <input id="currentNumberPage" type="hidden" name="currentNumberPage"
                       value="${dataNavigator.currentNumberPage}">
                <input id="nextPage" type="hidden" name="nextPage" value="${dataNavigator.nextPage}">
                <input id="lastPage" type="hidden" name="lastPage" value="${dataNavigator.lastPage}">
                <input id="rowPerPage" type="hidden" name="rowPerPage"
                       value="${dataNavigator.rowPerPage}">
                <input id="totalPages" type="hidden" name="totalPages" value="${dataNavigator.totalPages}">
                <input id="dataSize" type="hidden" name="dataSize" value="${dataNavigator.dataSize}">

                <input value="Submit" id="submit" class="buttonText" type="submit" style="display:none;"/>
            </form:form>
        </div>
        <table class="courses-result full-width">

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

        <div class="pagination ac">
            <c:if test="${dataNavigator.currentNumberPage!=1}">
                <a id="1" class="page">&lt;&lt;</a>
            </c:if>
            <c:if test="${dataNavigator.currentNumberPage>1}">
                <a id="${dataNavigator.currentNumberPage-1}" class="page">Prev</a>
            </c:if>

            <c:forEach begin="1" end="${dataNavigator.totalPages}" var="val">
                <c:if test="${val==dataNavigator.currentNumberPage}">
                    <a id="${val}" class="page current">${val}</a>
                </c:if>
                <c:if test="${val!=dataNavigator.currentNumberPage}">
                    <a id="${val}" class="page">${val}</a>
                </c:if>
            </c:forEach>

            <c:if test="${dataNavigator.currentNumberPage<dataNavigator.lastPage}">
                <a id="${dataNavigator.lastPage+1}" class="page" >Next</a>
            </c:if>
            <c:if test="${dataNavigator.lastPage!=dataNavigator.currentNumberPage}">
                <a id="${dataNavigator.lastPage}" class="page">&gt;&gt;</a>
            </c:if>
        </div>
    </div>
</div>

<div class="container mb-20" align="center">
    <div class="links">
        <a href="javascript:history.back();">Back</a> |
        <a href="<c:url value="/"/>">Home</a>
    </div>
</div>


<mt:footer> </mt:footer>