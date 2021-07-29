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

<fmt:message bundle="${loc}" key="local.locbutton.name.main" var="main_button" />
<fmt:message bundle="${loc}" key="local.loctextunknowncommand.name.header" var="header_txt" />

</head>
<body>

	<h2>${header_txt}!</h2>
	
	<div id="body">

	<form action="Controller" method="post">

		<button class="wrong_cmd" type="submit" name="command" value="index">${main_button}</button>

	</form>
	
	</div>

</body>
</html>