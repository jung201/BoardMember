document.getElementById("loginForm").addEventListener("submit", function(event){
    event.preventDefault(); // 기본 폼 제출 방지

    const uId = document.getElementById("uId").value;
    const uPwd = document.getElementById("uPwd").value;

    console.log("uId : " + uId);
    console.log("uPwd : " + uPwd);

    // REST API 요청 (fetch 사용)
    fetch("/api/user/login", {
        method: "POST",
        headers:{
            "Content-Type" : "application/x-www-form-urlencoded",
        },
        body: new URLSearchParams({ uId, uPwd }) // 데이터 변환
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("🚨로그인 실패 ! 아이디 또는 비밀번호를 확인해 주세요 !! ");
        }
        return response.json();
    })
    .then(data => {
        console.log("✅로그인 응답 데이터:", data); // 콘솔에서 데이터 확인

        if (data.uNickname) {
            alert(`${data.uNickname}님 환영합니다!`);
        }
        window.location.href = "/main";
    })
    .catch(error => {
        alert(error.message);
    });
});