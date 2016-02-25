<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Book"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="book" value="active z-depth-2"/>
    <jsp:param name="author" value=""/>
    <jsp:param name="genre" value=""/>
    <jsp:param name="order" value=""/>
</jsp:include>
<div class="row col s6 z-depth-2  offset-s4" style="margin-top:50px;">
    <form class="col s12" action="/books/add" method="post">
        <div class="row" style="margin-top:20px;">
            <div class="input-field col s12">
                <input id="number" name="number" type="text" class="validate">
                <label for="number">Number</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input id="name" name="name" type="text" class="validate">
                <label for="name">Name</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s6">
                <input id="year" name="year" type="text" class="validate">
                <label for="year">Year</label>
            </div>
            <div class="input-field col s6">
                <input id="count" name="count" type="text" class="validate">
                <label for="count">Count</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s6">
                <select name="genre">
                    <c:forEach var="genres" items="${genre}">
                        <option value="<c:out value="${genres.name}"/>"><c:out value="${genres.name}"/></option>
                    </c:forEach>
                </select>
                <label>Genre</label>
            </div>
            <div class="input-field col s6">
                <select name="author">
                    <c:forEach var="authors" items="${author}">
                        <option value="<c:out value="${authors.alias}"/>"><c:out value="${authors.alias}"/></option>
                    </c:forEach>
                </select>
                <label>Author</label>
            </div>
        </div>
        <div class="row right-align">
            <button class="btn waves-effect waves-light" type="submit" name="action">Submit
                <i class="material-icons right">send</i>
            </button>
        </div>
    </form>
</div>
<jsp:include page="../footer.jsp"/>