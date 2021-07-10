<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	import="by.http.news.bean.User" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="CSS/userPageStyle.css" rel="stylesheet" type="text/css">

</head>
<body>
	<div id='wrapper'>

		<h1>User tools</h1>

		<c:if test="${user == null || user.getRole() == 'admin'}">

			<form action="Controller" method="post">

				<input type="hidden" name="command" value="registration" />
				<button type="submit" name="action" value="registration">Registration</button>

			</form>

			<br />
		</c:if>

		<c:if test="${user == null}">

			<form action="Controller" method="post">

				<button type="submit" name="command" value="authorization">Authorization</button>
				
			</form>
			<br />
		</c:if>

		<form action="Controller" method="post">
		
		<input type="hidden" name="command" value="user_force" />

			<c:if test="${user != null}">
			
				<button type="submit" name="action" value="user_update">User Update</button>
				<br /> <br />
				
				<button type="submit" name="action" value="user_password">User Password Change</button>
				<br /><br />
			</c:if>

			<c:if test="${user != null && user.getRole() == 'admin'}">

				<button type="submit" name="action" value="user_delete">User Delete</button>
				<br /> 		 
			</c:if>
		</form>
		<br />

		<form action="Controller" method="post">

			<button type="submit" name="command" value="index">Main page</button>

		</form>

	</div>

</body>
</html>