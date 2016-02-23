<%--
  Created by IntelliJ IDEA.
  User: infinity
  Date: 23.02.16
  Time: 20:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../header.jsp">
    <jsp:param name="title" value="Genre"/>
</jsp:include>
<jsp:include page="../leftMenu.jsp">
    <jsp:param name="book" value=""/>
    <jsp:param name="author" value=""/>
    <jsp:param name="genre" value="active z-depth-2"/>
    <jsp:param name="order" value=""/>
</jsp:include>
<div class="row col s6 z-depth-2  offset-s4" style="margin-top:50px;">
    <form class="col s12" action="/genres/add" method="post">
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
        <div class="row right-align">
            <button class="btn waves-effect waves-light" type="submit" name="action">Submit
                <i class="material-icons right">send</i>
            </button>
        </div>
    </form>
</div>
<jsp:include page="../footer.jsp"/>