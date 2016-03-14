<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/locale.tld" prefix="t"%>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="locale" />
<body>
<div class="row">
    <div class="col s3">
        <ul id="nav-mobile" class="side-nav fixed" style="width: 240px;">
            <img src="/image/user.png" style="margin-top:20px; margin-left: 60px;">
            <p style="margin-left: 40px; margin-top:30px;color:#616161"><c:out value="${sessionScope.entity.email}"/></p>
            <div class="side-navbar">
                <ul style="margin-top:60px;">
                    <c:if test="${sessionScope.role eq 'librarian'}">
                        <li class="<c:out value="${param.book}"/>"><a href="/books"><fmt:message key="BOOK"/></a></li>
                        <li class="<c:out value="${param.author}"/>"><a href="/authors"><fmt:message key="AUTHOR"/></a></li>
                        <li class="<c:out value="${param.genre}"/>"><a href="/genres"><fmt:message key="GENRE"/></a></li>
                        <li class="<c:out value="${param.order}"/>"><a href="/orders"><fmt:message key="ORDER"/></a></li>
                        <li class="<c:out value="${param.user}"/>"><a href="/users"><fmt:message key="USER"/></a></li>
                        <li><a href="/logout"><fmt:message key="LOGOUT"/></a></li>
                    </c:if>
                    <c:if test="${sessionScope.role eq 'consumer'}">
                        <li class="<c:out value="${param.book}"/>"><a href="/books"><fmt:message key="BOOK"/></a></li>
                        <li class="<c:out value="${param.order}"/>"><a href="/orders"><fmt:message key="ORDER"/></a></li>
                        <li><a href="/logout"><fmt:message key="LOGOUT"/></a></li>
                    </c:if>
                </ul>
            </div>
            <form action="/localization" method="POST">
                <div class="input-field col s4 center" style="margin-left:15px;">
                    <select name="locale" onchange="this.form.submit()">
                        <t:locale locale="${sessionScope.locale}" en="English" ru="Русский"/>
                    </select>
                </div>
            </form>
        </ul>
    </div>