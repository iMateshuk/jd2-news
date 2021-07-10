<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	import="by.http.news.bean.User" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="CSS/newsPageStyle.css" rel="stylesheet" type="text/css">

</head>
<body>

	<div id='wrapper'>

		<div id="answer">

			<c:if test="${param.action != null}">

				<c:out value="${param.action.toUpperCase()}"></c:out>
				<br />
			</c:if>

			<c:if test="${param.message != null}">

				<font color="red">Failed</font>
				<br />
				<br />

				<c:out value="${param.message}"></c:out>
				<br />
				<br />
			</c:if>

			<c:if test="${param.message == null}">

				<font color="#ff8000">Success</font>
				<br />
				<br />
			</c:if>



			<c:if
				test="${sessionScope.user != null && sessionScope.user.getRole() == 'admin'}">

				<form action="Controller" method="post">
					<input type="hidden" name="command" value="user_tools" />
					<button class="user" type="submit" name="action" value="userAction">User tools</button>
				</form>
				<br />

			</c:if>

			<%-- <c:if test="${sessionScope.user != null && sessionScope.user.getRole() != 'user'}"> --%>

				<form action="Controller" method="post">
					<input type="hidden" name="command" value="user_tools"/>
					<button class="user" type="submit" name="action" value="userAction">User tools</button>
				</form>
				<br/>
				
				<form action="Controller" method="post">
					<input type="hidden" name="command" value="news_tools" />
					<button class="user" type="submit" name="action" value="newsAction">News tools</button>
				</form>
				<br />

			<%-- </c:if> --%>

		</div>

		<form action="Controller" method="post">

			<button type="submit" name="command" value="main">Main Page</button>

		</form>

	</div>


</body>
</html>