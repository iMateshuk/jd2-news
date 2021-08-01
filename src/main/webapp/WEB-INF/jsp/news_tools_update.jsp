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

<fmt:message bundle="${loc}" key="local.loctext.name.required" var="req_txt" />
<fmt:message bundle="${loc}" key="local.locbutton.name.send" var="send_btn" />
<fmt:message bundle="${loc}" key="local.locbutton.name.news_tools" var="nt_btn" />
<fmt:message bundle="${loc}" key="local.locbutton.name.main" var="main_btn" />

<fmt:message bundle="${loc}" key="local.loctextnewsforce.name.title" var="title_txt" />
<fmt:message bundle="${loc}" key="local.loctextnewsforce.name.brief" var="brief_txt" />
<fmt:message bundle="${loc}" key="local.loctextnewsforce.name.body" var="body_txt" />
<fmt:message bundle="${loc}" key="local.loctextnewsforce.name.style" var="style_txt" />
<fmt:message bundle="${loc}" key="local.loctextnewsforce.name.adult" var="adult_txt" />
<fmt:message bundle="${loc}" key="local.loctextnewsforce.name.politic" var="politic_txt" />
<fmt:message bundle="${loc}" key="local.loctextnewsforce.name.economic" var="economic_txt" />
<fmt:message bundle="${loc}" key="local.loctextnewsforce.name.social" var="social_txt" />

<fmt:message bundle="${loc}" key="local.loctextnewstoolsupdate.name.header" var="news_txt" />

</head>
<body>

<%-- <c:set var="url" value="news_tools_update" scope="session"  /> --%>

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

			<h1>${news_txt} ${fn:replace(action, 'news_', '')}</h1>

			<form action="Controller" method="post">

				${title_txt}<em>*</em>:
				<br />
					<input type="text" name="title" value="" />
				<br />

				${brief_txt}<em>*</em>:
				<br />
					<input type="text" name="brief" value="" />
				<br />
			
				${body_txt}<em>*</em>:
				<br />
					<textarea name="body" rows="20" cols="60" maxlength="1000"></textarea>
				<br />

				${style_txt}:
				<br />
					<input type="radio" name="style" value="adult" />${adult_txt}
					<input type="radio" name="style" value="politic" />${politic_txt}
					<input type="radio" name="style" value="economic" />${economic_txt}
					<input type="radio" name="style" value="social" />${social_txt}
				<br />	
				<br />
			
				<em> * - ${req_txt} </em>
				<br />

				<button type="submit" name="command" value="news_update">${send_btn}</button>

			</form>

			<br />

			<form action="Controller" method="post">

				<button type="submit" name="command" value="news_tools">${nt_btn}</button>

			</form>

			<br />
			<br />

			<form action="Controller" method="post">

				<button type="submit" name="command" value="index">${main_btn}</button>

			</form>

		</div>
</body>
</html>