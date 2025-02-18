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
    <script src="${contextPath}/js/signup.js" defer></script> <!--JS 경로-->
    <link rel="stylesheet" href="${contextPath}/css/signup.css"> <!--CSS 경로-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
          integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/> <!--폰트어썸 경로-->
    <title>Login</title>
</head>

<body>
<div class="signupContainer">
    <form id="signupForm">
        <h1>Signup</h1>
        <div class="input-box">
            <input type="text" id="uId" name="uId" placeholder="UserName" autocomplete="current-id" required>
            <i class="fa-solid fa-user"></i>
        </div>
        <div class="input-box">
            <input type="password" id="uPwd" name="uPwd" placeholder="Password" autocomplete="current-password" required>
            <i class="fa-duotone fa-solid fa-lock"></i>
        </div>
        <div class="input-box">
            <input type="email" id="uEmail" name="uEmail" placeholder="Email"  required>
            <i class="fa-regular fa-envelope"></i>
        </div>
        <div class="input-box">
            <input type="text" id="uNickname" name="uNickname" placeholder="NickName" required>
            <i class="fa-solid fa-user-pen"></i>
        </div>

        <button class="signupBtn" type="submit">Signup</button>

        <!-- 회원가입 페이지 이동 버튼 -->
        <div class="signupLoginBtn" onclick="location.href='${contextPath}/user/login'">Do you already have an account?</div>
    </form>
</div>
</body>
</html>


