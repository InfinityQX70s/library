<%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 23.02.16
  Time: 20:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Genre"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="book" value=""/>
    <jsp:param name="author" value=""/>
    <jsp:param name="genre" value="active z-depth-2"/>
    <jsp:param name="order" value=""/>
</jsp:include>
<p></p>
<table style="margin-top:30px;" class="bordered centered z-depth-2 col s6 offset-s4">
    <thead>
    <tr>
        <th data-field="id">Number</th>
        <th data-field="name">Name</th>
        <th data-field="change"></th>
        <th data-field="delete"></th>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="genre" items="${requestScope.genres}">
        <tr>
            <td><c:out value="${genre.id}"/>
            </td>
            <td><c:out value="${genre.name}"/>
            </td>
            <td>
                <a href="/genres/<c:out value="${genre.id}"/>/edit" class="secondary-content">
                    <i class="material-icons">create</i>
                </a>
            </td>
            <td>
                <form action="/genres/delete" method="post">
                    <input type="hidden" name="number" value="<c:out value="${genre.id}"/>">
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
    <a href="/genres/add" class="right btn-floating btn-large waves-effect waves-light blue lighten-2 ">
        <i class="material-icons">add</i></a>
</div>
<c:if test="${requestScope.pageCount ne 1}">
<div class="row col s6 offset-s4 center-align">
    <ul class="pagination text-white">
            <c:forEach begin="1" end="${requestScope.pageCount}" varStatus="loop">
                <li class="waves-effect light-blue"><a href="/genres?page=<c:out value="${loop.index}"/>"><c:out value="${loop.index}"/></a></li>
            </c:forEach>
    </ul>
</div>
</c:if>
<jsp:include page="../footer.jsp"/>
