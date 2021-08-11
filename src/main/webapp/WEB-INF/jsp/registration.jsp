<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="CSS/userPageStyle.css" rel="stylesheet" type="text/css">

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="resources.localization.local" var="loc" />

<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.send" var="send_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.user_tools" var="user_tools" />
<fmt:message bundle="${loc}" key="local.locbutton.name.main" var="main_button" />

<fmt:message bundle="${loc}" key="local.loctext.name.name" var="name_text" />
<fmt:message bundle="${loc}" key="local.loctext.name.login" var="login_text" />
<fmt:message bundle="${loc}" key="local.loctext.name.password" var="password_text" />
<fmt:message bundle="${loc}" key="local.loctext.name.email" var="email_text" />
<fmt:message bundle="${loc}" key="local.loctext.name.required" var="required_txt" />

<fmt:message bundle="${loc}" key="local.loctext.name.role" var="role_text" />
<fmt:message bundle="${loc}" key="local.loctext.name.admin" var="admin_text" />
<fmt:message bundle="${loc}" key="local.loctext.name.editor" var="editor_text" />
<fmt:message bundle="${loc}" key="local.loctext.name.user" var="user_text" />

<fmt:message bundle="${loc}" key="local.loctextregistration.name.header" var="header_txt" />
<fmt:message bundle="${loc}" key="local.loctextregistration.name.age" var="age_txt" />

</head>
<body>

<%-- <c:set var="url" value="registration" scope="session"  /> --%>

	<div class="locale">

		<div class="locale">

			<div class="en">

				<form action="Controller?command=change_local" method="post">
					<input type="hidden" name="local" value="en"/>
					<input class="local" type="submit" value="${en_button}"/>
				</form>
			</div>

			<div class="ru">

				<form action="Controller?command=change_local" method="post">
					<input type="hidden" name="local" value="ru"/>
					<input class="local" type="submit" value="${ru_button}"/>
				</form>
			</div>
		</div>
	</div>
		
		<br /> <br />

		<div id='wrapper'>

			<h1 id="user_action">${header_txt}</h1>

			<form action="Controller" method="post">

				<input type="hidden" name="command" value="user_registration" />

				${name_text}: <br /> <input type="text" name="name" value="" /> <br />

				${login_text}<em>*</em>: <br /> <input type="text" name="login"	value="" /> <br />
				
				${password_text}<em>*</em>: <br /> <input type="password" name="password" value="" /> <br />
				
				${age_txt}<em>*</em>: <br /> <input type="text" name="age" value="" /> <br /> 
				
				${email_text}: <br /> <input type="text" name="email" value="" /> <br />
				
				<em> * - ${required_txt} </em> <br /> <br />

				<c:if test="${user != null && user.getRole() == 'admin'}">
			
				${role_text}:
				<br />
					<input type="radio" name="role" value="admin" />${admin_text}
				<input type="radio" name="role" value="editor" />${editor_text}
				<input type="radio" name="role" value="user" />${user_text}
				<br />
					<br />

				</c:if>

				<button type="submit" name="action" value="registration">${send_button}</button>
				<br /> <br />
			</form>

			<form action="Controller" method="post">

				<input type="hidden" name="command" value="user_tools" />

				<button type="submit" name="action" value="userAction">${user_tools}</button>

			</form>

			<br />
			<br />

			<form action="Controller" method="post">

				<button type="submit" name="command" value="index">${main_button}</button>

			</form>
		</div>
</body>
</html>