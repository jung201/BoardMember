<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8"> <!--한글, 특수문자 깨짐방지-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> <!--반응형 웹디자인 설정-->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script> const contextPath = "${contextPath}"; </script>
    <script src="${contextPath}/js/login.js" defer></script> <!--JS 경로-->
    <link rel="stylesheet" href="${contextPath}/css/login.css"> <!--CSS 경로-->
    <title>Login</title>
</head>

<body>
<div class="loginContainer">
    <div class="loginBox">
        <form id="loginForm" action="/user/login" method="POST">
            <div class="loginTitle">로그인</div>
            <input type="text" id="uId" name="uId" placeholder="아이디를 입력하세요" required>
            <input type="password" id="uPwd" name="uPwd" placeholder="비밀번호를 입력하세요"autocomplete="current-password" required>
            <button class="loginBtn" type="submit">로그인</button>

            <!-- 로그인 페이지 이동 버튼 -->
            <div class="loginSignupBtn" onclick="location.href='${contextPath}/user/signup'">회원가입이 필요하신가요?</div>
        </form>
    </div>
</div>
</body>
</html>


