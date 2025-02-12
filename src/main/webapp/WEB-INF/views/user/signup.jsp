<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8"> <!--한글, 특수문자 깨짐방지-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> <!--반응형 웹디자인 설정-->
    <link rel="stylesheet" href="${contextPath}/css/signup.css"> <!--CSS 경로-->
    <script src="${contextPath}/js/signup.js" defer></script> <!--JS 경로-->
    <title>signup</title>
</head>

<body>

<div class="signupContainer">
    <div class="signupBox">
        <form id="signupForm">
            <div class="signupTitle">회원가입</div>
            <input type="text" id="uId" name="uId" placeholder="아이디를 입력하세요" autocomplete="current-id" required><br>
            <input type="password" id="uPwd" name="uPwd" placeholder="비밀번호를 입력하세요" autocomplete="current-password" required><br>
            <input type="email" id="uEmail" name="uEmail" placeholder="이메일을 입력하세요"required><br>
            <input type="text" id="uNickname" name="uNickname" placeholder="닉네임을 입력하세요"required><br>
            <button class="signupBtn" type="button">회원가입</button>

            <!-- 로그인 페이지 이동 버튼 -->
            <div class="signupLoginBtn" onclick="location.href='${contextPath}/user/login'">이미 아이디가 있으신가요?</div>
        </form>
    </div>
</div>
</body>
</html>
