<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	import="by.http.news.bean.User" import="by.http.news.bean.News" 
	import="java.util.List" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="CSS/styles.css" rel="stylesheet" type="text/css">

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />

<fmt:message bundle="${loc}" key="local.locbutton.name.user_tools" var="user_tools" />
<fmt:message bundle="${loc}" key="local.locbutton.name.logged_out" var="logged_out" />
<fmt:message bundle="${loc}" key="local.locbutton.name.news_tools" var="news_tools" />
<fmt:message bundle="${loc}" key="local.locbuttonmain.name.clean" var="clean_btn" />
<fmt:message bundle="${loc}" key="local.loctextmain.name.news" var="news_text" />
<fmt:message bundle="${loc}" key="local.loctextmain.name.title" var="title_text" />
<fmt:message bundle="${loc}" key="local.loctextmain.name.info" var="info_text" />
<fmt:message bundle="${loc}" key="local.loctextmain.name.emty" var="empty_text" />

</head>
<body>

<%-- <c:set var="url" value="main" scope="session"  /> --%>

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

	<br /><br />

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
	
				<button class="news" type="submit" name="command" value="news_tools">${news_tools}</button>
	
			</c:if>
		
		</form><br/>
		
		<c:if test="${searchNews != null && not empty newses}">
		<div class="clean">
			<form action="Controller" method="post">
					
				<input type="hidden" name="command" value="main" />
				<button class="user" type="submit" name="clean" value="clean">${clean_btn}</button>
				<br/>

			</form>
		</div>
		</c:if>

		</div>

	</div>

	<div id="body">
	
		<c:if test="${newses == null}">

			<p class="info"> ${info_text} <strong>${news_text}</strong> !!! </p>
	
		</c:if>
		
		<c:if test="${empty newses}">

			<p class="info"> ${empty_text} <strong>${news_text}</strong> !!!</p>
	
		</c:if>
	
		<c:if test="${newses != null}">
		
			<c:forEach var="news" items="${newses}">

				<i>${title_text}:</i> <strong> <c:out value="${news.getTitle()}" /> </strong>
				<button type="button" class="collapsible"> <c:out value="${news.getBrief()}" /> ... </button>
				<div class="content"> <pre class="pre"><c:out value="${news.getBody()}" /></pre> </div>
			
			</c:forEach>
	
		</c:if>

	</div>
	
	<script type="text/javascript" src="JS/main.js"></script>
	
	<div class="clear"></div>

</body>
</html>