<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Order"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="book" value=""/>
    <jsp:param name="author" value=""/>
    <jsp:param name="genre" value=""/>
    <jsp:param name="order" value="active z-depth-2"/>
</jsp:include>
<p></p>
<table style="margin-top:50px;" class="bordered centered z-depth-2 col s6 offset-s4">
    <thead>
    <tr>
        <th data-field="name">Book</th>
        <th data-field="user">User</th>
        <th data-field="status">Status</th>
        <th data-field="change"></th>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="order" items="${bookOrders}">
        <tr>
            <td><c:out value="${mapBook[order.bookId].name}"/>
            </td>
            <td><c:out value="${mapUser[order.userId].email}"/>
            </td>
            <td><c:out value="${mapStatus[order.statusId].name}"/>
            </td>
            <c:if test="${sessionScope.role eq 'librarian'}">
                <td>
                    <a href="/orders/<c:out value="${order.id}"/>/edit" class="secondary-content"
                       style="margin-right:20px;">
                        <i class="material-icons">create</i>
                    </a>
                </td>
            </c:if>
            <c:if test="${sessionScope.role eq 'consumer'}">
                <c:if test="${mapStatus[order.statusId].name eq 'open'}">
                    <td>
                        <form action="/orders/delete" method="post">
                            <input type="hidden" name="number" value="<c:out value="${order.id}"/>">
                            <a class="secondary-content" style="margin-right:20px;" onclick="parentNode.submit();">
                                <i class="material-icons">clear</i>
                            </a>
                        </form>
                    </td>
                </c:if>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
<jsp:include page="../footer.jsp"/>