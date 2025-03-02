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

<!-- 검색창 추가 -->
<div class="boardNavigator">
    <div class="search-container">
        <select id="searchType">
            <option value="all">전체</option>
            <option value="title">제목</option>
            <option value="writer">작성자</option>
        </select>
        <input type="text" id="searchKeyword" placeholder="키워드를 입력해 주세요">
        <button onclick="searchBoard()" id="search-btn">🔍</button>
    </div>

    <!-- 글 작성 버튼 -->
    <div class="write-container">
        <button onclick="location.href='${contextPath}/board/write'" id="write-btn">글 작성</button>
    </div>
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

<!-- 게시글 상세 보기 팝업 -->
<div id="boardDetailPopup" class="popup">
    <div class="popup-content">
        <span class="close-btn" onclick="closePopup()">&times;</span>

        <h2 id="popupTitle">게시글 상세 보기</h2>

        <!-- 4개 항목을 가로로 배치 -->
        <div class="popup-row">
            <div class="popup-item">
                <label>카테고리</label>
                <!-- 카테고리 (보기 모드) -->
                <div class="popup-info" id="popupCategory"></div>

                <!-- 카테고리 (수정 모드, 기본 숨김) -->
                <select id="popupCategorySelect" style="display: none;">
                    <option value="N">공지사항</option>
                    <option value="F">자유이야기</option>
                    <option value="Q">QnA</option>
                </select>
            </div>
            <div class="popup-item">
                <label>작성자</label>
                <div class="popup-info" id="popupWriter"></div>
            </div>
            <div class="popup-item">
                <label>작성일</label>
                <div class="popup-info" id="popupDate"></div>
            </div>
            <div class="popup-item">
                <label>조회수</label>
                <div class="popup-info" id="popupViews"></div>
            </div>
        </div>

        <!-- 내용 -->
        <textarea id="popupContent" readonly></textarea>

        <!-- 수정/삭제 버튼 (본인이 작성한 글일 경우만 보이도록 설정) -->
        <div id="editDeleteBtns" class="edit-delete-btns" style="display: none;">
            <button id="editBtn" onclick="editPost()">수정</button>
            <button id="deleteBtn" onclick="deletePost()">삭제</button>
            <button id="saveBtn" onclick="saveEdit()" style="display: none;">수정 완료</button>
        </div>

    </div>
</div>



<!-- 페이지네이션 버튼 -->
<div id="pagination"></div>

</body>
</html>


