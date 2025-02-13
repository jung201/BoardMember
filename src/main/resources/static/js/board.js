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

            if (boardList.length === 0) {
                tbody.innerHTML = "<tr><td colspan='6'>게시글이 없습니다.</td></tr>";
                return;
            }

            boardList.forEach(board => {
                const categoryMap = {
                    "N": "공지사항",
                    "F": "자유이야기",
                    "Q": "QnA"
                };

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
            updatePagination(page, totalPages, category);
        })
        .catch(error => console.error("게시글 불러오기 오류 : ", error))
}

// 페이지네이션
function updatePagination(currentPage, totalPages, category) {
    const paginationContainer = document.getElementById("pagination");  // 페이지 네이션 버튼 추가
    paginationContainer.innerHTML = ""; // 기존 페이지네이션 버튼 제거 ( 초기화 )

    // 이전 버튼 ( 첫번째 페이지에서는 비활성화 )
    let paginationHTML = `
        <button onclick="loadBoardList(${currentPage - 1}, '${category}')" ${currentPage === 1 ? "disabled" : ""}>이전</button>
    `;

    /*
    *   4. 페이지 숫자 버튼 생성
    *   i는 1부터 totalpages까지 반복하면서 페이지 버튼을 만듦
    */
    for (let i = 1; i <= totalPages; i++) { //
        paginationHTML += `<button onclick="loadBoardList(${i}, '${category}')" class="${i === currentPage ? "active" : ""}">${i}</button>`;
    }

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













