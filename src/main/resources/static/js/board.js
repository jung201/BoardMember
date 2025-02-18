document.addEventListener("DOMContentLoaded", function() {
    loadBoardList(1); // 페이지가 로드되면 게시글 목록을 불러오는 함수 실행, 첫 페이지 데이터 불러오기
});

// 게시글 목록을 서버에서 가져와서 HTML 테이블에 추가
function loadBoardList(page, category='') {
    fetch(`/api/board/list?page=${page}&category=${category}`)
        .then(response => response.json())
        .then(data => {
            console.log("불러온 데이터:", data);
            const { boardList, totalPages } = data;
            const tbody = document.getElementById("boardList");
            tbody.innerHTML = ""; // 기존 데이터 초기화

            if (!boardList || boardList.length === 0) {
                tbody.innerHTML = "<tr><td colspan='6'>게시글이 없습니다.</td></tr>";
                return;
            }

            boardList.forEach(board => {
                const row = document.createElement("tr"); // 새로운 행 생성
                row.innerHTML = `
                    <td>${board.bNo || '-'}</td>
                    <td>${board.bCategory || '-'}</td>
                    <td>${board.bTitle || '-'}</td>
                    <td>${board.bCreatedId || '-'}</td>
                    <td>${formatDate(board.bCreatedDate)}</td>
                    <td>${board.bViews || '0'}</td>
                `;
                tbody.appendChild(row); // tbody에 행 추가
            });

            // 페이지네이션 업데이트
            updatePagination(page, totalPages, category);
        })
        .catch(error => console.error("게시글 불러오기 오류 : ", error))
}

// 페이지네이션
function updatePagination(currentPage, totalPages, category) {
    const paginationContainer = document.getElementById("pagination");  // 페이지 네이션 버튼 추가
    paginationContainer.innerHTML = ""; // 기존 페이지네이션 버튼 제거 ( 초기화 )

//    if (totalPages <= 1) return; // 페이지가 1개 이하라면 페이지네이션 숨김

    // 이전 버튼
    let paginationHTML = `
        <button onclick="loadBoardList(${currentPage - 1}, '${category}')" ${currentPage === 1 ? "disabled" : ""}>이전</button>
    `;

    for (let i = 1; i <= totalPages; i++) {
        paginationHTML += `<button onclick="loadBoardList(${i}, '${category}')" class="${i === currentPage ? "active" : ""}">${i}</button>`;
    }

    // 다음 버튼
    paginationHTML += `
        <button onclick="loadBoardList(${currentPage + 1}, '${category}')" ${currentPage === totalPages ? "disabled" : ""}>다음</button>
    `;

    paginationContainer.innerHTML = paginationHTML;
}

// 날짜 포멧
function formatDate(dateString) {
    if (!dateString) return '날짜없음'; // 값이 없으면 기본값 반환
    const date = new Date(dateString);
    return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
}

// 검색 기능
function searchBoard() {
    let searchType = document.getElementById("searchType").value; // 검색 타입
    let keyword = document.getElementById("searchKeyword").value.trim(); // 검색 키워드
    let currentPage = 1; // 기본 페이지 1로 설정

    if (keyword === "") {
        alert("🚨 검색어를 입력해주세요 !");
        return;
    }

    // 기존 목록을 검색된 데이터로 업데이트
    fetch(`/api/board/search?searchType=${searchType}&keyword=${keyword}&page=${currentPage}`)
        .then(response => response.json())
        .then(data => {
            console.log("검색결과 : ", data);

        updateBoardList(data.boardList);
        updatePagination(currentPage, data.totalPages, searchType, keyword);
        })
        .catch(error => console.error("검색 오류 : ", error));
}

// 게시판 목록 업데이트 함수 (검색 포함)
function updateBoardList(boardList) {
    let tbody = document.getElementById("boardList");
    tbody.innerHTML = "";

    if (!boardList || boardList.length === 0) {
        tbody.innerHTML = "<tr><td colspan='6'>검색 결과가 없어요!</td></tr>";
        return;
    }

    boardList.forEach(board => {
        let row = `<tr>
            <td>${board.bNo || '-'}</td>
            <td>${board.bCategory || '-'}</td>
            <td>${board.bTitle  || '-'}</td>
            <td>${board.bCreatedId  || '-'}</td>
            <td>${formatDate(board.bCreatedDate)}</td>
            <td>${board.bViews || '0'}</td>
        </tr>`;
        tbody.innerHTML += row;
    });
}

// 엔터 키 실행
document.getElementById("searchKeyword").addEventListener("keypress", function(event) {
    if (event.key === "Enter") {
        event.preventDefault();  // 기본 엔터 동작(폼 제출 등) 방지
        searchBoard();  // 검색 실행
    }
});















