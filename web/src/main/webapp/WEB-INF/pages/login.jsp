<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="locale" />
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>

    <!--Import Google Icon Font-->
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="/css/materialize.css" media="screen,projection"/>
</head>

<body>
<br>
<br>
<br>
<br>
<div class="row">
    <div class="col col s4 offset-l4">
        <form action="/login" method="post">
            <div class="card white">
                <div class="col s12">
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
                </div>
                <div class="card-action right-align">
                    <a href="#" onclick="document.forms[0].submit();"><fmt:message key="LOGIN"/></a>
                    <a href="/registration"><fmt:message key="REGISTRATION"/></a>
                </div>
            </div>
        </form>
    </div>
</div>
<!--Import jQuery before materialize.js-->
<script type="text/javascript" src="/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="/js/materialize.min.js"></script>
</body>
</html>
