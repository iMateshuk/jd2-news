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

<fmt:message bundle="${loc}" key="local.locbutton.name.main" var="main_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />

<fmt:message bundle="${loc}" key="local.locbutton.name.news_tools" var="header_txt" />
<fmt:message bundle="${loc}" key="local.loctextnewstools.name.add" var="add_txt" />
<fmt:message bundle="${loc}" key="local.loctextnewstools.name.update" var="update_txt" />
<fmt:message bundle="${loc}" key="local.loctextnewstools.name.delete" var="delete_txt" />
<fmt:message bundle="${loc}" key="local.loctextnewstools.name.choose" var="choose_txt" />
<fmt:message bundle="${loc}" key="local.loctextnewstools.name.sgn" var="sgn_txt" />

</head>
<body>

<%-- <c:set var="url" value="news_tools" scope="session"  /> --%>

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

			<h1>${header_txt}</h1>

			<form action="Controller" method="post">

				<c:if test="${user != null && user.getRole() == 'editor'}">

					<button type="submit" name="command" value="news_tools_add">${add_txt}</button>

					<br />
					<br />

					<button type="submit" name="command" value="news_tools_update">${update_txt}</button>

					<br />
					<br />
				</c:if>
					
				<c:if test="${user != null && user.getRole() == 'admin'}">
					<button type="submit" name="command" value="news_tools_delete">${delete_txt}</button>
					<br />
					<br />
				</c:if>

				<button type="submit" name="command" value="news_tools_choose">${choose_txt}</button>
				<br />
				<br />
				<button type="submit" name="command" value="news_tools_sgn">${sgn_txt}</button>

				<br />
				<br />

			</form>

			<form action="Controller" method="post">

				<button type="submit" name="command" value="index">${main_button}</button>

			</form>

		</div>
</body>
</html>