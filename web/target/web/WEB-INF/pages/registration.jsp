<!DOCTYPE html>
<html>
<head>
    <title>Registration</title>
    <!--Import Google Icon Font-->
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="/css/materialize.css" media="screen,projection"/>
</head>

<body>
<div class="row">
    <div class="row col s5 z-depth-2  offset-s4" style="margin-top:50px;">
        <form action="/registration" method="post">
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
            <div class="row">
                <div class="input-field col s12">
                    <input id="firstName" name="firstName" type="text" class="validate">
                    <label for="firstName">First Name</label>
                </div>
            </div>
            <div class="row" >
                <div class="input-field col s12">
                    <input id="lastName" name="lastName" type="text" class="validate">
                    <label for="lastName">Last Name</label>
                </div>
            </div>
            <div class="row center-align">
                <button class="btn waves-effect waves-light" type="submit" name="action">Register
                    <i class="material-icons right">send</i>
                </button>
            </div>
        </form>
    </div>
</div>
<!--Import jQuery before materialize.js-->
<script type="text/javascript" src="/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="/js/materialize.min.js"></script>
</body>
</html>