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
    <script src="${contextPath}/js/board.js" defer></script> <!--JS 경로-->
    <link rel="stylesheet" href="${contextPath}/css/board.css"> <!--CSS 경로-->
    <title>게시판</title>
</head>
<body>

<h2>게시판</h2>
<!-- 카테고리 필터 버튼 -->
<div class="category-filter">
    <button onclick="loadBoardList(1, '')">전체</button>
    <button onclick="loadBoardList(1, 'N')">공지사항</button>
    <button onclick="loadBoardList(1, 'F')">자유이야기</button>
    <button onclick="loadBoardList(1, 'Q')">QnA</button>
</div>

<!-- 글 작성 버튼 -->
<div class="btn-container">
    <a href="/board/write" class="btn">글 작성</a>
</div>

<!-- 게시판 테이블 -->
<table>
    <thead>
    <tr>
        <th>번호</th>
        <th>카테고리</th>
        <th>제목</th>
        <th>작성자</th>
        <th>작성일</th>
        <th>조회수</th>
    </tr>
    </thead>
    <tbody id="boardList">
    <!-- JavaScript에서 추가 -->
    </tbody>
</table>

<!-- 페이지네이션 버튼 -->
<div id="pagination"></div>

</body>
</html>


