<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><spring:message code="member.register" /></title>
</head>
<body>
	<p><spring:message code="register.done" arguments="${registerRequest.name}" /></p>
	<!-- 
	<p><spring:message code="register.done" arguments="${registserRequest.name},${registerRequest.email}"/></p>
	arguments에 두개의 인자를 넣어주었을 때, label.properties에서 몇번 째 인자를 사용할 것인지 정할 수 있다. 
	-->
	<p><a href="<c:url value='/main'/>">[<spring:message code="go.main" />]</a>
</body>
</html>