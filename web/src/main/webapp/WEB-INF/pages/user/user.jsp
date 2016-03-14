<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="locale" />
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="User"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="book" value=""/>
    <jsp:param name="author" value=""/>
    <jsp:param name="genre" value=""/>
    <jsp:param name="order" value=""/>
    <jsp:param name="user" value="active z-depth-2"/>
</jsp:include>
<p></p>
<table style="margin-top:30px;" class="bordered centered z-depth-2 col s6 offset-s4">
    <thead>
    <tr>
        <th data-field="id"><fmt:message key="NUMBER"/></th>
        <th data-field="firstName"><fmt:message key="FIRSTNAME"/></th>
        <th data-field="lastName"><fmt:message key="LASTNAME"/></th>
        <th data-field="email"><fmt:message key="EMAIL"/></th>
        <th data-field="delete"></th>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="user" items="${requestScope.users}">
        <tr>
            <td><c:out value="${user.id}"/>
            </td>
            <td><c:out value="${user.firstName}"/>
            </td>
            <td><c:out value="${user.lastName}"/>
            </td>
            <td><c:out value="${user.email}"/>
            </td>
            <td>
                <form action="/users/delete" method="post">
                    <input type="hidden" name="number" value="<c:out value="${user.id}"/>">
                    <a class="secondary-content" style="margin-right:20px;" onclick="parentNode.submit();">
                        <i class="material-icons">clear</i>
                    </a>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>

</table>
<div class="row col s6 offset-s4 right-align">
    <a href="/users/add" class="right btn-floating btn-large waves-effect waves-light blue lighten-2 ">
        <i class="material-icons">add</i></a>
</div>
<jsp:include page="../footer.jsp"/>