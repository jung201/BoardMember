<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8"> <!--한글, 특수문자 깨짐방지-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> <!--반응형 웹디자인 설정-->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!--제이쿼리-->
    <script> const contextPath = "${contextPath}"; </script>
    <script src="${contextPath}/js/main.js" defer></script> <!--JS 경로-->
    <link rel="stylesheet" href="${contextPath}/css/main.css"> <!--CSS 경로-->
    <title>메인페이지</title>
</head>
<body>
    <h1>환영합니다 ! ${uNickname}님 !</h1>
    <a href="${contextPath}/board" class="btn">게시판 이동</a>
</body>

</html>