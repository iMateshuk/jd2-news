<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="CSS/userPageStyle.css" rel="stylesheet" type="text/css">

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />

<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />

<fmt:message bundle="${loc}" key="local.locbutton.name.send" var="send_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.user_tools" var="user_tools" />
<fmt:message bundle="${loc}" key="local.locbutton.name.main" var="main_button" />

<fmt:message bundle="${loc}" key="local.loctext.name.login" var="login_text" />
<fmt:message bundle="${loc}" key="local.loctext.name.required" var="required_txt" />

<fmt:message bundle="${loc}" key="local.loctextusertoolsdelete.name.header" var="user_text" />

</head>
<body>

<%-- <c:set var="url" value="user_tools_delete" scope="session"  /> --%>

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

			<h1>${user_text}</h1>

			<form action="Controller" method="post">

				${login_text}<em>*</em>: 
				<br /> 
					<input type="text" name="login" value="" />
				<br />

				<em> * - ${required_txt} </em> <br /> <br />

				<button type="submit" name="command" value="user_delete">${send_button}</button>

			</form>

			<br />

			<form action="Controller" method="post">

				<button type="submit" name="command" value="user_tools">${user_tools}</button>

			</form>

			<br /> <br />

			<form action="Controller" method="post">

				<button type="submit" name="command" value="index">${main_button}</button>

			</form>

		</div>
</body>
</html>