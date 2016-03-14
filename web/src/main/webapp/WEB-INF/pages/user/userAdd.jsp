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
<div class="row col s5 z-depth-2  offset-s4" style="margin-top:50px;">
    <form action="/users/add" method="post">
        <div class="row" style="margin-top: 30px">
            <div class="input-field col s12">
                <input id="email" name="email" type="email" class="validate">
                <label for="email"><fmt:message key="EMAIL"/></label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input id="password" type="password" name="password" class="validate">
                <label for="password"><fmt:message key="PASSWORD"/></label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input id="firstName" name="firstName" type="text" class="validate">
                <label for="firstName"><fmt:message key="FIRSTNAME"/></label>
            </div>
        </div>
        <div class="row" >
            <div class="input-field col s12">
                <input id="lastName" name="lastName" type="text" class="validate">
                <label for="lastName"><fmt:message key="LASTNAME"/></label>
            </div>
        </div>
        <div class="row center-align">
            <button class="btn waves-effect waves-light" type="submit" name="action"><fmt:message key="REGISTER"/>
                <i class="material-icons right">send</i>
            </button>
        </div>
    </form>
</div>
<jsp:include page="../footer.jsp"/>