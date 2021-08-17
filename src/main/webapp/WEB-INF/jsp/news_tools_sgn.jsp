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

<fmt:message bundle="${loc}" key="local.locbutton.name.news_tools" var="nt_btn" />
<fmt:message bundle="${loc}" key="local.locbutton.name.main" var="main_btn" />

<fmt:message bundle="${loc}" key="local.loctext.name.login" var="login_text" />

<fmt:message bundle="${loc}" key="local.loctextnewstoolssgn.name.name" var="name_text" />
<fmt:message bundle="${loc}" key="local.loctext.name.login" var="login_text" />
<fmt:message bundle="${loc}" key="local.loctextnewstoolssgn.name.news_tools_sgn" var="header_txt" />
<fmt:message bundle="${loc}" key="local.loctextnewstoolssgn.name.info" var="info_text" />
<fmt:message bundle="${loc}" key="local.locbuttonnewstoolssgn.name.del" var="del_btn" />
<fmt:message bundle="${loc}" key="local.locbuttonnewstoolssgn.name.sgn" var="sgn_btn" />

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

			<h1>${header_txt}</h1>

			<form action="Controller" method="post">
			
				<c:if test="${userSgn == null || empty userSgn}">
				
					<p class="info"> ${info_text} </p>
				</c:if>

				<c:if test="${userSgn != null && not empty userSgn}">

					<c:forEach var="userData" items="${userSgn}">
					
						<c:out value="${name_text} ${userData.getName()} :@ ${userData.getLogin()}" />
						<br />
					
					</c:forEach>
					<br />
					
					${del_btn}
					<br /> <br />
					${login_text} :	<input type="text" name="login" value="" />
					<br /> <br />
					<button type="submit" name="command" value="news_tools_sgn_del">${del_btn}</button>
					<br /> <br />
					
					<button type="submit" name="command" value="news_tools_sgn_view">${sgn_btn}</button>
					<br /> <br />
				</c:if>

			</form>

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