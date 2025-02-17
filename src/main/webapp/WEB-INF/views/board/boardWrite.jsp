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

<h2>게시글 작성</h2>

<form id="writeForm">
    <label>카테고리:
        <select id="bCategory" name="bCategory">
            <option value="N">공지사항</option>
            <option value="F">자유이야기</option>
            <option value="Q">QnA</option>
        </select>
    </label>

    <label>제목: <input type="text" id="bTitle" name="bTitle" required></label>

    <label>내용:
        <textarea id="bContent" name="bContent" rows="5" required></textarea>
    </label>

    <label>작성자: <input type="text" id="bCreatedId" name="bCreatedId" value="${uNickname}" readonly></label>

    <button type="button" onclick="submitPost()">등록</button>
</form>

</body>
</html>
