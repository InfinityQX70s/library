<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="locale" />
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Author"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="book" value=""/>
    <jsp:param name="author" value="active z-depth-2"/>
    <jsp:param name="genre" value=""/>
    <jsp:param name="order" value=""/>
</jsp:include>
<div class="row col s6 z-depth-2  offset-s4" style="margin-top:50px;">
    <form class="col s12" action="/authors/change" method="post">
        <div class="row" style="margin-top:20px;">
            <div class="input-field col s12">
                <input type="hidden" name="number" value="<c:out value="${author.id}"/>">
                <input disabled id="number" name="number" type="text" value="<c:out value="${author.id}"/>" class="validate">
                <label for="number"><fmt:message key="NUMBER"/></label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input id="alias" name="alias" type="text" class="validate" value="<c:out value="${author.alias}"/>">
                <label for="alias"><fmt:message key="ALIAS"/></label>
            </div>
        </div>
        <div class="row right-align">
            <button class="btn waves-effect waves-light" type="submit" name="action"><fmt:message key="SUBMIT"/>
                <i class="material-icons right">send</i>
            </button>
        </div>
    </form>
</div>
<jsp:include page="../footer.jsp"/>
