<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script> const contextPath = "${contextPath}"; </script>
    <script src="${contextPath}/js/boardWrite.js" defer></script>
    <link rel="stylesheet" href="${contextPath}/css/boardWrite.css">
    <title>글쓰기</title>
</head>
<body>
<div class="writeContainer">
    <form id="writeForm">
        <h2>게시글 작성</h2>
        <div class="cateId">
            <input type="text" id="bTitle" name="bTitle" placeholder="제목을 입력해 주세요" required>
            <select id="bCategory" name="bCategory">
                <option value="N">공지사항</option>
                <option value="F">자유이야기</option>
                <option value="Q">QnA</option>
            </select>
            <input type="hidden" id="bCreatedId" name="bCreatedId" value="${uNickname}">
        </div>

        <textarea id="bContent" name="bContent" rows="10" placeholder="내용을 입력해 주세요" required></textarea>

        <div class="writeBtn">
            <button type="button" onclick="submitPost()">등록</button>
            <button type="button" onclick="location.href='${contextPath}/board'">취소</button>
        </div>
    </form>
</div>
</body>
</html>
