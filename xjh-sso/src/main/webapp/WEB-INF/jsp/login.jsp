<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<body>
<h2>Welcome login!</h2>
<form action="/login/dologin" method="post">
用户名：<input type="text" name="username" >
密码：<input type="password" name="password" >
<input type="hidden" name="reqUrl" value="${reqUrl }">
<input type="submit" value="登陆" >
</form>
</body>
</html>
