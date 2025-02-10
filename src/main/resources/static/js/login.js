document.addEventListener("DOMContentLoaded", function() {
    // 로그인 폼 가져오기
    const loginForm = document.querySelector("#loginForm");

    loginForm.addEventListener("submit", function(event) {
    event.preventDefault(); // 기본 제출 동작 방지

    // 입력한 아이디, 비밀번호 가져오기
    const uId = document.querySelector("#uId").value.trim();
    const uPwd = document.querySelector("#uPwd").value.trim();

    console.log("입력된 ID:", uId);
    console.log("입력된 PWD:", uPwd);
    console.log("보낼 데이터:", JSON.stringify({ uId, uPwd })); // 확인용 로그

    // 서버 로그인 요청 보내기

    fetch("/api/user/login", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({ "uId": uId, "uPwd": uPwd })
    })
    .then(response => response.json()) // json 응답 변환
    .then(data => {
        if(data.success) {
            alert("로그인 성공!");
            window.location.href = contextPath + "/main/main";
        } else {
            alert("아이디 또는 비밀번호가 일치하지 않습니다 !");
        }
    })
    .catch(error => {
            console.error("로그인 요청 오류", error);
            alert("서버 오류가 발생했습니다. 다시 시도해 주세요 !");
        });
    });
});