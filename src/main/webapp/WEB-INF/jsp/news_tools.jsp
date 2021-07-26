<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	 import="by.http.news.bean.News" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="CSS/userPageStyle.css" rel="stylesheet" type="text/css">

</head>
<body>

<div id='wrapper'>

		<h1>News tools</h1>
		
		<form action="Controller" method="post">
		
		<input type="hidden" name="command" value="news_force" />
		
		<c:if test="${user != null && user.getRole() != 'user'}">
		
			<button type="submit" name="action" value="news_add">Add News</button>

		<br /><br />
		
			<button type="submit" name="action" value="news_update">Update News</button>

		<br /><br />
		
			<button type="submit" name="action" value="news_delete">Delete News</button>

		<br /><br />
		
		</c:if>
		
		<%-- <c:if test="${user != null && user.getRole() == 'user'}"> --%>
		
			<button type="submit" name="action" value="news_choose">Choose News</button>

		<br /><br />
		
		<%-- </c:if> --%>
		
		</form>
		
		<form action="Controller" method="post">
		
			<button type="submit" name="command" value="index">Main Page</button>

		</form>

	</div>

</body>
</html>