<%@ page import="org.apache.jasper.tagplugins.jstl.core.If"%>
<%@ page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
	import="by.http.news.bean.User" import="by.http.news.bean.News" 
	import="java.util.List" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="CSS/styles.css" rel="stylesheet" type="text/css">

</head>
<body>


	<div class="header">

		<div class="headerLeft">

			<h1>NEWS</h1>

		</div>

		<div class="headerRight">
		
		<form action="Controller" method="post">
		
			<c:if test="${user != null}">
		
				<button class="user" type="submit" name="command" value="loggedout">Logged out</button>
		
			</c:if>
			
			<%-- <c:if test="${user == null || user.getRole() == 'admin'}"> --%>
	
				<button class="user" type="submit" name="command" value="user_tools">User tools</button>
	
			<%-- </c:if> --%>
			
			<c:if test="${user != null }"> <!-- && user.getRole() != 'user' -->
	
				<button class="news" type="submit" name="command" value="news_tools">News tools</button>
	
			</c:if>
		
		</form><br/>

		</div>

	</div>

	<div id="body">
	
		<c:if test="${newses == null}">
	
			<p class="info"> Soon there will be <strong>NEWS</strong> !!! </p>
	
		</c:if>
	
		<c:if test="${newses != null}">
		
			<c:forEach var="news" items="${newses}">
		
				<i>Title:</i> <strong> <c:out value="${news.getTitle()}" /> </strong>
				<button type="button" class="collapsible"> <c:out value="${news.getBrief()}" /> ... </button>
				<div class="content"> <p> <c:out value="${news.getBody()}" /> </p> </div>
			
			</c:forEach>
	
		</c:if>

	</div>
	
	<script type="text/javascript" src="JS/main.js"></script>
	
	<div class="clear"></div>

</body>
</html>