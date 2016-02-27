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
                            <label for="email">Email</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="password" type="password" name="password" class="validate">
                            <label for="password">Password</label>
                        </div>
                    </div>
                </div>
                <div class="card-action center">
                    <a href="#" onclick="document.forms[0].submit();">log in</a>
                    <a href="/registration">registration</a>
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
