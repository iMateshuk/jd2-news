<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="CSS/newsPageStyle.css" rel="stylesheet" type="text/css">

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />

<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />

<fmt:message bundle="${loc}" key="local.locbutton.name.main" var="main_button" />

<fmt:message bundle="${loc}" key="local.locbutton.name.send" var="send_btn" />
<fmt:message bundle="${loc}" key="local.locbutton.name.news_tools" var="nt_btn" />
<fmt:message bundle="${loc}" key="local.locbutton.name.main" var="main_btn" />

<fmt:message bundle="${loc}" key="local.loctextnewsforce.name.title" var="title_txt" />
<fmt:message bundle="${loc}" key="local.loctextnewsforce.name.style" var="style_txt" />
<fmt:message bundle="${loc}" key="local.loctextnewsforce.name.adult" var="adult_txt" />
<fmt:message bundle="${loc}" key="local.loctextnewsforce.name.politic" var="politic_txt" />
<fmt:message bundle="${loc}" key="local.loctextnewsforce.name.economic" var="economic_txt" />
<fmt:message bundle="${loc}" key="local.loctextnewsforce.name.social" var="social_txt" />
<fmt:message bundle="${loc}" key="local.loctextnewsforce.name.andor" var="andor_txt" />

<fmt:message bundle="${loc}" key="local.loctextnewstoolschoose.name.header" var="news_txt" />

</head>
<body>

	<div class="locale">

		<div class="locale">

			<div class="en">

				<form action="Controller" method="post">
					<input type="hidden" name="local" value="ru"/>
					<button class="local" type="submit" name="command" value="news_tools_choose"/>${ru_button}</button>
				</form>
			</div>

			<div class="ru">

				<form action="Controller" method="post">
					<input type="hidden" name="local" value="en" />
					<button class="local" type="submit" name="command" value="news_tools_choose" />${en_button}</button>
				</form>
			</div>
		</div>
		<br /> <br />

		<div id='wrapper'>

			<h1>${news_txt} </h1>

			<form action="Controller" method="post">

				${title_txt}:
				<br /> <input type="text" name="title" value="" /> <br />

				<font color="#ff8000">${andor_txt}</font>
				<br />
				${style_txt}:
				<br />
				<input type="radio" name="style" value="adult" />${adult_txt}
				<input type="radio" name="style" value="politic" />${politic_txt}
				<input type="radio" name="style" value="economic" />${economic_txt}
				<input type="radio" name="style" value="social" />${social_txt}
				<br />
				<br />
				
				<c:set var="para" value="valueHere" scope="session"  />
				
				<input type="hidden" name="clean" value="clean" />
				<button type="submit" name="command" value="news_choose">${send_btn}</button>

			</form>

			<br />

			<form action="Controller" method="post">

				<button type="submit" name="command" value="news_tools">${nt_btn}</button>

			</form>

			<br />
			<br />

			<form action="Controller" method="post">

				<button type="submit" name="command" value="main">${main_btn}</button>

			</form>

		</div>
</body>
</html>