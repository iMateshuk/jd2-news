<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="CSS/newsPageStyle.css" rel="stylesheet" type="text/css">
</head>
<body>

	<div id='wrapper'>

		<h1>NEWS ${fn:replace(action, 'news_', '')}</h1>
		
		<form action="Controller" method="post">
		
			<c:if test="${action != 'news_choose'}">
				Enter Title<em>*</em>:
			</c:if>
			<c:if test="${action == 'news_choose'}">
				Enter Title:
			</c:if>
			<br />
			<input type="text" name="title" value="" />
			<br />
			
		<c:if test="${!(action == 'news_delete' || action == 'news_choose')}">
	
			Enter brief description<em>*</em>:
			<br />
			<input type="text" name="brief" value="" />
			<br />
			
			Enter body<em>*</em>:
			<br />
			<textarea name="body" rows="20" cols="60" maxlength="1000"></textarea>
			<!-- <input type="text" name="body" value="" /> -->
			<br />
			
		</c:if>
		
		<c:if test="${action != 'news_delete'}">
		
			<c:if test="${action != 'news_choose'}">
				Choose user role<em>*</em>:
			</c:if>
			<c:if test="${action == 'news_choose'}">
				<font color="#ff8000">or/and</font><br/>
				Choose style:
			</c:if>
			<br />
			<input type="radio" name="style" value="adult" />Adult
			<input type="radio" name="style" value="politic" />Politic
			<input type="radio" name="style" value="economic" />Economic
			<input type="radio" name="style" value="social" />Social
			<br />
			<br />
		</c:if>
		
		<c:if test="${action != 'news_choose'}">
			<em> * - required </em>
		</c:if>
		<br />
		
		<button type="submit" name="command" value="${action}">Send</button>
		
		</form>
		
		<br />
		
		<form action="Controller" method="post">
		
			<button type="submit" name="command" value="news_tools">News tools</button>

		</form>
		
		<br /><br />
		
		<form action="Controller" method="post">
		
			<button type="submit" name="command" value="index">Main Page</button>

		</form>

	</div>

</body>
</html>