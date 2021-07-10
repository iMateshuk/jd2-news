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

		<h1 id="user_action">Registration form</h1>

		<form action="Controller" method="post">

			<input type="hidden" name="command" value="user_registration" />

			Enter Name:
			<br />
			<input type="text" name="name" value="" />
			<br />

			Enter login<em>*</em>:
			<br />
			<input type="text" name="login" value="" />
			<br />
			
			Enter password<em>*</em>:
			<br />
			<input type="password" name="password" value="" />
			<br /> 
			
			Enter age<em>*</em>:
			<br />
			<input type="text" name="age" value="" />
			<br />
			
			Enter e-mail:
			<br />
			<input type="text" name="email" value="" />
			<br />
			<em> * - required </em> <br />
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

			<button type="submit" name="action" value="registration">Submit</button>
			<br /> <br />
		</form>

		<form action="Controller" method="post">

			<input type="hidden" name="command" value="user_tools" />

			<button type="submit" name="action" value="userAction">User
				tools</button>

		</form>
		
		<br /><br />

		<form action="Controller" method="post">

			<button type="submit" name="command" value="index">Main Page</button>

		</form>
	</div>

</body>
</html>