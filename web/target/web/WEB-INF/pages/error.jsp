<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="locale" />
<html>
<head>
    <title><fmt:message key="ERROR"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/css/materialize.css" media="screen,projection"/>
    <script type="text/javascript" src="/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/js/materialize.min.js"></script>
</head>
<body>
<div class="row" style="margin-top:50px;">
    <div class="col s6 offset-s3">
        <div class="card-panel">
          <span class="black-text">
                <blockquote>
                    <h5><c:out value="${requestScope.error.message}"/></h5>
                </blockquote>
                  <c:forEach begin="0" end="4" var="stackTraceElem" items="${requestScope.error.stackTrace}">
                      <c:out value="${stackTraceElem}"/><br/>
                  </c:forEach>
          </span>
            <div class="row center" style="margin-top:30px;">
                <button class="btn waves-effect waves-light" onclick="window.history.back();">Back
                    <i class="material-icons left">arrow_back</i>
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
