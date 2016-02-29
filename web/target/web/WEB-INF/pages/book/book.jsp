<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="locale" />
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Book"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="book" value="active z-depth-2"/>
    <jsp:param name="author" value=""/>
    <jsp:param name="genre" value=""/>
    <jsp:param name="order" value=""/>
</jsp:include>
<div class="row col s8 offset-s4">
    <form method="post" action="/books/search">
        <div class="input-field col s5">
            <input id="value" name="value" type="text" class="validate">
            <label for="value"><fmt:message key="SEARCHVALUE"/></label>
        </div>
        <div class="input-field col s2">
            <select name="type">
                <option value="name"><fmt:message key="NAME"/></option>
                <option value="genre"><fmt:message key="GENRE"/></option>
                <option value="author"><fmt:message key="AUTHOR"/></option>
            </select>
            <label><fmt:message key="SEARCHTYPE"/></label>
        </div>
        <div class="col s1">
            <button class="btn waves-effect waves-light" type="submit" name="action" style="margin-top:20px;"><fmt:message key="SEARCH"/>
            </button>
        </div>
    </form>
</div>
<p></p>
<table class="bordered centered z-depth-2 col s6 offset-s4">
    <thead>
    <tr>
        <th data-field="id"><fmt:message key="NUMBER"/></th>
        <th data-field="name"><fmt:message key="NAME"/></th>
        <th data-field="year"><fmt:message key="YEAR"/></th>
        <th data-field="count"><fmt:message key="COUNT"/></th>
        <th data-field="genre"><fmt:message key="GENRE"/></th>
        <th data-field="author"><fmt:message key="AUTHOR"/></th>
        <c:if test="${sessionScope.role eq 'librarian'}">
            <th data-field="change"></th>
            <th data-field="delete"></th>
        </c:if>
        <c:if test="${sessionScope.role eq 'consumer'}">
            <th data-field="assign"></th>
        </c:if>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="book" items="${books}">
        <tr>
            <td><c:out value="${book.id}"/>
            </td>
            <td><c:out value="${book.name}"/>
            </td>
            <td><c:out value="${book.year}"/>
            </td>
            <td><c:out value="${book.count}"/>
            </td>
            <td><c:out value="${mapGenres[book.genreId].name}"/>
            </td>
            <td><c:out value="${mapAuthor[book.authorId].alias}"/>
            </td>
            <c:if test="${sessionScope.role eq 'librarian'}">
                <td>
                    <a href="/books/<c:out value="${book.id}"/>/edit" class="secondary-content">
                        <i class="material-icons">create</i>
                    </a>
                </td>
                <td>
                    <form action="/books/delete" method="post">
                        <input type="hidden" name="number" value="<c:out value="${book.id}"/>">
                        <a class="secondary-content" style="margin-right:20px;" onclick="parentNode.submit();">
                            <i class="material-icons">clear</i>
                        </a>
                    </form>
                </td>
            </c:if>
            <c:if test="${sessionScope.role eq 'consumer'}">
                <td>
                    <form action="/books/assign" method="post">
                        <input type="hidden" name="number" value="<c:out value="${book.id}"/>">
                        <a class="secondary-content" onclick="parentNode.submit();">
                            <i class="material-icons">bookmark</i>
                        </a>
                    </form>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>

</table>
<c:if test="${sessionScope.role eq 'librarian'}">
    <div class="row col s6 offset-s4 right-align">
        <a href="/books/add" class="right btn-floating btn-large waves-effect waves-light blue lighten-2 ">
            <i class="material-icons">add</i></a>
    </div>
</c:if>
<c:if test="${requestScope.pageCount ne 1}">
<div class="row col s6 offset-s4 center-align">
    <ul class="pagination text-white">
            <c:forEach begin="1" end="${requestScope.pageCount}" varStatus="loop">
                <li class="waves-effect light-blue"><a href="/books?page=<c:out value="${loop.index}"/>"><c:out value="${loop.index}"/></a></li>
            </c:forEach>
    </ul>
</div>
</c:if>
<jsp:include page="../footer.jsp"/>
