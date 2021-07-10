<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="CSS/userPageStyle.css" rel="stylesheet" type="text/css">
</head>
<body>

	<div id='wrapper'>

		<h1>User ${fn:replace(action, 'user_', '')}</h1>

		<form action="Controller" method="post">

			Enter login<em>*</em>:
			<br />
			<input type="text" name="login" value="" />
			<br />

			<c:if test="${action == 'user_password'}">
				Enter password<em>*</em>:
				<br />
				<input type="password" name="password" value="" />
				<br />
			</c:if>

			<c:if test="${action != 'user_delete' && action != 'user_password'}">
	
				Enter Name<em>*</em>:
				<br />
				<input type="text" name="name" value="" />
				<br />
			
				Enter e-mail<em>*</em>:
				<br />
				<input type="text" name="email" value="" />
				<br />

				<c:if test="${user != null && user.getRole() == 'admin'}">
			
					Choose user role (default - User):
					<br />
					<input type="radio" name="role" value="admin" />Admin
					<input type="radio" name="role" value="editor" />Editor
					<input type="radio" name="role" value="user" />User
					<br />
					<br />

				</c:if>

			</c:if>

			<em> * - required </em> <br />
			<br />

			<button type="submit" name="command" value="${action}">Send</button>

		</form>

		<br />

		<form action="Controller" method="post">

			<button type="submit" name="command" value="user_tools">User tools</button>

		</form>

		<br />
		<br />

		<form action="Controller" method="post">

			<button type="submit" name="command" value="index">Main Page</button>

		</form>

	</div>

</body>
</html>