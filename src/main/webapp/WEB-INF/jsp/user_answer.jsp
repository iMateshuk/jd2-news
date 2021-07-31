<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	import="by.http.news.bean.User" pageEncoding="utf-8"%>
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

<fmt:message bundle="${loc}" key="local.locbutton.name.news_tools" var="news_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.user_tools" var="user_tools" />
<fmt:message bundle="${loc}" key="local.locbutton.name.main" var="main_button" />

<fmt:message bundle="${loc}" key="local.loctextuseranswer.name.login" var="login_txt" />
<fmt:message bundle="${loc}" key="local.loctextanswer.name.success" var="success_txt" />
<fmt:message bundle="${loc}" key="local.loctextanswer.name.failed" var="failed_txt" />

</head>
<body>

<c:set var="url" value="user_answer" scope="session"  />

<%-- 		<div class="locale">

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
	</div> --%>
		
		<br /> <br />

	<div id='wrapper'>

		<div id="answer">
		
		<c:if test="${param.action != null}">
		
			<c:out value="${param.action.toUpperCase()}"></c:out>
			<br/>
		</c:if>
		
		<c:if test="${sessionScope.user == null || sessionScope.user.getLogin().isEmpty()}">
		
			<c:if test="${param.action.toLowerCase() != 'loggedout'}">
			
				<font color="red">${failed_txt}</font>
				<br/> <br/>
			
			</c:if>
			
			<br/>
		</c:if>
		
		<c:if test="${param.message != null}">
		
			<c:out value="${param.message}"></c:out>
			<br/><br/>
		</c:if>
		
		<c:if test="${sessionScope.user != null && param.message == null }">
		
			<font color="#ff8000">${success_txt}</font>
			<br/><br/>
		
			${login_txt}: <c:out value="${sessionScope.user.getLogin()}"></c:out>
			<br/><br/>
		
		</c:if>
		
		<%-- <c:if test="${sessionScope.user == null || sessionScope.user.getRole() == 'admin'}"> --%>
		
			<form action="Controller" method="post">
			<input type="hidden" name="command" value="user_tools"/>
			<button class="user" type="submit" name="action" value="userAction">${user_tools}</button>
			</form>
			<br/>
		
		<%-- </c:if> --%>
		
		<c:if test="${sessionScope.user != null}">
		
			<form action="Controller" method="post">
			<input type="hidden" name="command" value="news_tools"/>
			<button class="user" type="submit" name="action" value="newsAction">${news_button}</button>
			</form>
			<br/>
		
		</c:if>

		</div>

		<form action="Controller" method="post">

			<button type="submit" name="command" value="main">${main_button}</button>

		</form>

	</div>

</body>
</html>