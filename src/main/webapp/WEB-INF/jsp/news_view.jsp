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
<fmt:message bundle="${loc}" key="local.loctextmain.name.title" var="title_text" />

<fmt:message bundle="${loc}" key="local.locbuttonnewsview.name.sgn" var="sgn_button" />

</head>
<body>

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

			<form action="Controller" method="post">

				<c:if test="${news != null}">
				
					<h1>
						<i>${title_text}:</i> 
						<strong> <c:out value="${news.getTitle()}" /> </strong>
					</h1>
				
					<pre class="pre"><c:out value="${news.getBody()}" /></pre>

					<button type="submit" name="command" value="news_author_sgn">${sgn_button}</button>
				</c:if>

				<br />
				<br />

			</form>

			<form action="Controller" method="post">

				<button type="submit" name="command" value="main">${main_button}</button>

			</form>

		</div>

</body>
</html>