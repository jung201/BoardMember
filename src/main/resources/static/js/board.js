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
                    <td class="board-title">
                        <a href="#" onclick="openPopup(${board.bNo})">${board.bTitle || '-'}</a>
                    </td>
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
            <td class="board-title">
                <a href="#" onclick="openPopup(${board.bNo})">${board.bTitle || '-'}</a>
            </td>
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


// 게시글 상세보기 팝업 열기
function openPopup(bNo) {

     // 조회수 증가 요청
    fetch(`/api/board/increaseView?bNo=${bNo}`, {
        method: "PUT"
    })
    .then(() => {
        return fetch(`/api/board/detail?bNo=${bNo}`);
    })
    .then(response => response.json())
    .then(data => {
        const board = data.board;
        document.getElementById("popupTitle").innerText = board.bTitle;
        document.getElementById("popupTitle").setAttribute("data-bno", board.bNo);
        document.getElementById("popupCategory").innerText = board.bCategory;
        document.getElementById("popupWriter").innerText = board.bCreatedId;
        document.getElementById("popupDate").innerText = formatDate(board.bCreatedDate);
        document.getElementById("popupViews").innerText = board.bViews;
        document.getElementById("popupContent").value = board.bContent;

        // 본인 작성 글 여부 확인 후 버튼 표시
        if (data.isAuthor) {
            document.getElementById("editDeleteBtns").style.display = "flex";
        } else {
            document.getElementById("editDeleteBtns").style.display = "none";
        }
         document.getElementById("boardDetailPopup").style.display = "flex";
    })
    .catch(error => console.error("게시글 상세보기 오류: ", error));
}

// 팝업 닫기
function closePopup() {
    document.getElementById("boardDetailPopup").style.display = "none";
}

// 게시글 삭제 기능
function deletePost() {
    const bNo = document.getElementById("popupTitle").getAttribute("data-bno");

    if(!confirm("정말 삭제하시겠습니까 ? ")) {
        return; // 사용자가 취소하면 삭제 중단
    }

    fetch(`/api/board/delete?bNo=${bNo}`, {
        method: "DELETE",
    })
    .then(response => response.json())
    .then(result => {
        if(result.success) {
            alert("게시글이 삭제되었습니다 !");
            closePopup();
            loadBoardList(1);
        } else {
            alert("삭제에 실패했습니다 !");
        }
    })
    .catch(error => console.error("게시글 삭제 오류: ", error))
}


// 게시글 수정
function editPost() {
    enableEditMode();
}

let isEditing = false; // 수정 모드 상태

// 수정 모드 활성화 함수
function enableEditMode() {
    if (isEditing) {
        return;
    }

    isEditing = true;
    document.getElementById("popupContent").removeAttribute("readonly");
    document.getElementById("popupContent").style.border = "1px solid #ccc";
    document.getElementById("popupTitle").contentEditable = "true";
    document.getElementById("popupTitle").style.borderBottom = "1px solid #ccc";

    // 카테고리 선택 가능하도록 변경
    document.getElementById("popupCategory").style.display = "none";  // 기존 텍스트 숨김
    document.getElementById("popupCategorySelect").style.display = "block"; // select 보이기

    // 버튼 변경
    document.getElementById("editBtn").style.display = "none"; // "수정" 버튼 숨기기
    document.getElementById("deleteBtn").style.display = "none"; // "삭제" 버튼 숨기기
    document.getElementById("saveBtn").style.display = "inline-block"; // "수정 완료" 버튼 표시
}

// 수정 완료 후 서버로 데이터 전송
function saveEdit() {
    const bNo = document.getElementById("popupTitle").getAttribute("data-bno");
    const updatedTitle = document.getElementById("popupTitle").innerText.trim();
    const updatedContent = document.getElementById("popupContent").value.trim();
    const updatedCategory = document.getElementById("popupCategorySelect").value;

    if (!updatedTitle || !updatedContent) {
        alert("🚨 제목과 내용을 입력해주세요!");
        return;
    }

    fetch(`/api/board/update`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            bNo: bNo,
            bTitle: updatedTitle,
            bCategory: updatedCategory,
            bContent: updatedContent
        })
    })
    .then(response => response.json())
    .then(result => {
        if (result.success) {
            alert("게시글이 수정되었습니다!");

            // 수정 모드 초기화
            isEditing = false;
            document.getElementById("popupContent").setAttribute("readonly", true); // 다시 읽기 전용
            document.getElementById("popupContent").style.border = "none";
            document.getElementById("popupTitle").contentEditable = "false";
            document.getElementById("popupTitle").style.borderBottom = "none";
            
            // 카테고리 다시 텍스트 모드로 변경
            document.getElementById("popupCategory").innerText = document.getElementById("popupCategorySelect").selectedOptions[0].text;
            document.getElementById("popupCategory").style.display = "block";
            document.getElementById("popupCategorySelect").style.display = "none";

            // 버튼 원래대로 복구
            document.getElementById("editBtn").style.display = "inline-block";
            document.getElementById("deleteBtn").style.display = "inline-block";
            document.getElementById("saveBtn").style.display = "none";

            closePopup();
            loadBoardList(1); // 게시판 목록 새로고침
        } else {
            alert("수정에 실패했습니다.");
        }
    })
    .catch(error => console.error("게시글 수정 오류: ", error));
}











