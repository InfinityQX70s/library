<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<body>
<div class="row">
    <div class="col s3">
        <ul id="nav-mobile" class="side-nav fixed" style="width: 240px;">
            <img src="/image/user.png" style="margin-top:20px; margin-left: 60px;">
            <p style="margin-left: 40px; margin-top:30px;color:#616161">mazumisha@gmail.com</p>
            <div class="side-navbar">
                <ul style="margin-top:60px;">
                    <c:if test="${sessionScope.role eq 'librarian'}">
                        <li class="<c:out value="${param.book}"/>"><a href="/books">Book</a></li>
                        <li class="<c:out value="${param.author}"/>"><a href="/authors">Author</a></li>
                        <li class="<c:out value="${param.genre}"/>"><a href="/genres">Genre</a></li>
                        <li class="<c:out value="${param.order}"/>"><a href="/orders">Order</a></li>
                        <li><a href="/logout">Logout</a></li>
                    </c:if>
                    <c:if test="${sessionScope.role eq 'consumer'}">
                        <li class="<c:out value="${param.book}"/>"><a href="/books">Book</a></li>
                        <li class="<c:out value="${param.order}"/>"><a href="/orders">Order</a></li>
                        <li><a href="/logout">Logout</a></li>
                    </c:if>
                </ul>
            </div>
        </ul>
    </div>