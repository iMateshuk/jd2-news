<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="CSS/styles.css" rel="stylesheet" type="text/css">

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="resources.localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" 	var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" 	var="en_button" />

<fmt:message bundle="${loc}" key="local.locbutton.name.user_tools" 	var="user_tools" />
<fmt:message bundle="${loc}" key="local.locbutton.name.logged_out" 	var="logged_out" />
<fmt:message bundle="${loc}" key="local.locbutton.name.news_tools" 	var="news_tools" />
<fmt:message bundle="${loc}" key="local.locbuttonmain.name.clean" 	var="clean_btn" />
<fmt:message bundle="${loc}" key="local.loctextmain.name.news" 	var="news_text" />
<fmt:message bundle="${loc}" key="local.loctextmain.name.title" var="title_text" />
<fmt:message bundle="${loc}" key="local.loctextmain.name.info" var="info_text" />
<fmt:message bundle="${loc}" key="local.loctextmain.name.empty" var="empty_text" />
<fmt:message bundle="${loc}" key="local.loctextmain.name.message" var="message_txt" />
<fmt:message bundle="${loc}" key="local.loctextmain.name.gotonews" var="gotonews_text" />
<fmt:message bundle="${loc}" key="local.loctextmain.name.page" var="page_txt" />
<fmt:message bundle="${loc}" key="local.loctextmain.name.prev" var="prev_txt" />
<fmt:message bundle="${loc}" key="local.loctextmain.name.next" var="next_txt" />

</head>
<body>

	<%-- <c:set var="url" value="main" scope="session"  /> --%>

	<div class="locale">

		<div class="locale">

			<div class="en">

				<form action="Controller?command=change_local" method="post">
					<input type="hidden" name="local" value="en" /> <input
						class="local" type="submit" value="${en_button}" />
				</form>
			</div>

			<div class="ru">

				<form action="Controller?command=change_local" method="post">
					<input type="hidden" name="local" value="ru" /> <input
						class="local" type="submit" value="${ru_button}" />
				</form>
			</div>
		</div>
	</div>

	<br />
	<br />

	<div class="header">

		<div class="headerLeft">

			<h1>${news_text}</h1>

		</div>

		<div class="headerRight">

			<form action="Controller" method="post">

				<c:if test="${user != null}">

					<button class="user" type="submit" name="command" value="loggedout">${logged_out}</button>

				</c:if>

				<button class="user" type="submit" name="command" value="user_tools">${user_tools}</button>

				<c:if test="${user != null }">

					<button class="news" type="submit" name="command"
						value="news_tools">${news_tools}</button>

				</c:if>

			</form>
			<br />

			<c:if test="${cmdSave != null && not empty newses}">
				<div class="clean">
					<form action="Controller" method="post">

						<input type="hidden" name="command" value="main" />
						<button class="user" type="submit" name="clean" value="clean">${clean_btn}</button>
						<br />

					</form>
				</div>
			</c:if>

		</div>

	</div>

	<div id="body">

		<c:if test="${newses == null || empty newses}">

			<p class="info">
				${info_text} <strong>${news_text}</strong> !!!
			</p>

		</c:if>

		<c:if test="${newses != null && not empty newses}">

			<c:forEach var="news" items="${newses}">

				<i>${title_text}:</i>
				<strong> <c:out value="${news.getTitle()}" /> </strong>
				<button type="button" class="collapsible">
					<c:out value="${news.getBrief()}" />
					...
				</button>

				<c:if test="${user != null || not empty user}">
					<div class="content">
						<pre class="pre">
							<c:out value="${news.getBody()}" />
						</pre>
						<a href="Controller?command=news_view&title=${news.getTitle()}">
							<c:out value="${gotonews_text} : ${news.getTitle()}" />
						</a>
					</div>
					<br />
				</c:if>

				<c:if test="${user == null || empty user}">
					<div class="content">
						<c:out value="${message_txt}" />
					</div>
					<br />
				</c:if>

			</c:forEach>

			<!-- pagination -->
			
			<div id="pagination">

			<c:if test="${page != null || not empty page}">
				<br />
				<strong> <c:out value="${page_txt} : ${page}" /> </strong>
				<br />
					<c:if test="${maxPages > 1}">
						<!-- Previous page -->
					<%-- 	<c:if test="${page > 1}">
							<td><a href="Controller?command=${cmdSave}&page=${page - 1}">${prev_txt}</a></td>
						</c:if> --%>
						<!--Table page-->
						<c:set var="tdCellDef" value="5" />
						<c:if test="${maxPages > tdCellDef}">
							<c:set var="tdCell" value="${tdCellDef}"/>
						</c:if>
						<c:if test="${maxPages < tdCellDef}">
							<c:set var="tdCell" value="${maxPages}"/>
						</c:if>
						<c:choose>
							<c:when test="${page - tdCell < 0}">
								<c:set var="tdBegin" value="1" />
								<c:set var="tdEnd" value="${tdCell}" />
							</c:when>
							<c:when test="${page + tdCell >= maxPages}">
								<c:set var="tdBegin" value="${maxPages - tdCell + 1}" />
								<c:set var="tdEnd" value="${maxPages}" />
							</c:when>
							<c:otherwise>
								<c:set var="tdBegin" value="${page - tdCell}" />
								<c:set var="tdEnd" value="${tdBegin + tdCell - 1}" />
							</c:otherwise>
						</c:choose>
						<table border="0" cellpadding="${tdCell}" cellspacing="${tdCell}">
							<tr>
								<c:forEach begin="${tdBegin}" end="${tdEnd}" var="i">
									<c:choose>
										<c:when test="${page == i}">
											<td>${i}</td>
										</c:when>
										<c:otherwise>
											<td><a href="Controller?command=${cmdSave}&page=${i}">${i}</a></td>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</tr>
						</table>
						<!-- Next page -->
						<%-- <c:if test="${page < maxPages}">
							<td><a href="Controller?command=${cmdSave}&page=${page + 1}">${next_txt}</a></td>
						</c:if> --%>

					</c:if>

				</c:if>
			
			</div>

		</c:if>

	</div>

	<script type="text/javascript" src="JS/main.js"></script>

	<div class="clear"></div>

</body>
</html>