<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="CSS/userPageStyle.css" rel="stylesheet" type="text/css">

</head>
<body>

	<div id='wrapper'>

		<h1 id="user_action">Authorization form</h1>

		<font color=green> 
		
			<c:if test="${param.message != null}">
				
				<c:out value="${param.message}" />
				<br /><br />
			</c:if>
		
		</font>
 		
		<form action="Controller" method="post">

			<input type="hidden" name="command" value="user_authorization" />

				Enter login<em>*</em>:
				<br /> 
				<input type="text" name="login" value="" />
				<br /> 
				Enter password<em>*</em>:
				<br /> 
				<input type="password" name="password" value="" />
				<br />
				<em> * - required </em> <br />

			<button type="submit" name="action" value="authorization">Send</button>

			<br /> <br />
		</form>

		<form action="Controller" method="post">

			<input type="hidden" name="command" value="user_tools" />

			<button type="submit" name="action" value="userAction">User tools</button>

		</form>

	</div>

</body>
</html>