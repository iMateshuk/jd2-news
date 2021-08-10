<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
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
<fmt:setBundle basename="localization.local" var="loc" />

<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.send" var="send_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.user_tools" var="user_tools" />

<fmt:message bundle="${loc}" key="local.loctext.name.login" var="login_text" />
<fmt:message bundle="${loc}" key="local.loctext.name.password" var="password_text" />
<fmt:message bundle="${loc}" key="local.loctext.name.required" var="required_txt" />

<fmt:message bundle="${loc}" key="local.loctextauthorization.name.header" var="header_text" />

</head>
<body>

<%-- <c:set var="url" value="authorization" scope="session"  /> --%>

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
		<br />
		<br />

		<div id='wrapper'>

			<h1 id="user_action">${header_text}</h1>

			<font color=green> <c:if test="${param.message != null}">

					<c:out value="${param.message} : ${param.newuser}" />
					<br />
					<br />
				</c:if>

			</font>

			<form action="Controller" method="post">

				<input type="hidden" name="command" value="user_authorization" />

				${login_text}<em>*</em>: <br /> <input type="text" name="login"
					value="" /> <br /> ${password_text}<em>*</em>: <br /> <input
					type="password" name="password" value="" /> <br /> <em> * -
					${required_txt} </em> <br />

				<button type="submit" name="action" value="authorization">${send_button}</button>

				<br /> <br />
			</form>

			<form action="Controller" method="post">

				<input type="hidden" name="command" value="user_tools" />

				<button type="submit" name="action" value="userAction">${user_tools}</button>

			</form>

		</div>
</body>
</html>