document.addEventListener("DOMContentLoaded", function () {

   // 1️⃣ 아이디 검증 (12자 이내, 문자+숫자 조합 필수)
    function validateId(id) {
        const idRegex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]{1,12}$/;
        return idRegex.test(id);
    }

    // 2️⃣ 비밀번호 검증 (16자 이내, 대문자 + 특수문자 포함)
    function validatePassword(password) {
        const pwdRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{1,16}$/;
        return pwdRegex.test(password);
    }

    // 3️⃣ 이메일 검증 (@, .com 포함, 숫자만 불가)
    function validateEmail(email) {
        const emailRegex = /^(?=.*[a-zA-Z])(?=.*@)(?=.*\.com)[a-zA-Z0-9@.]+$/;
        return emailRegex.test(email);
    }

    // 4️⃣ 닉네임 검증 (숫자 & 특수문자 포함 불가)
    function validateNickname(nickname) {
        const nicknameRegex = /^[가-힣a-zA-Z0-9]+$/;
        return nicknameRegex.test(nickname);
    }

    // ✅ 이벤트 리스너 등록
    document.querySelector(".signupBtn").addEventListener("click", function (event) {
        event.preventDefault(); // 기본 폼 제출 방지

        let uId = document.getElementById("uId").value.trim();
        let uPwd = document.getElementById("uPwd").value.trim();
        let uEmail = document.getElementById("uEmail").value.trim();
        let uNickname = document.getElementById("uNickname").value.trim();

        // ✅ 유효성 검사 (fetch보다 먼저 실행!)
        if (!validateId(uId)) {
            alert("❌ 아이디는 12자 이내, 문자+숫자로 입력해야 해요! (숫자만 불가)");
            return;
        }
        if (!validatePassword(uPwd)) {
            alert("❌ 비밀번호는 16자 이내, 대문자 & 특수문자를 포함해야 해요!");
            return;
        }
        if (!validateEmail(uEmail)) {
            alert("❌ 올바른 이메일 형식을 입력해주세요! (@와 .com 포함 필수)");
            return;
        }
        if (!validateNickname(uNickname)) {
            alert("❌ 닉네임은 한글, 영문, 숫자만 가능해요! (특수문자 ❌)");
            return;
        }

        // ✅ 유효성 검사를 모두 통과한 경우, fetch 실행
        let signupData = { uId, uPwd, uEmail, uNickname };
        console.log("✅ 회원가입 데이터 확인:", signupData); // 전송할 데이터 콘솔 확인

        fetch("/api/user/signup", { // REST API URL
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(signupData)
        })
        .then(response => response.json()) // 서버 응답을 JSON으로 변환
        .then(data => { // JSON 데이터에서 uNickname 추출
            console.log("✅ 회원가입 응답 데이터:", data); // 응답 확인

            alert(`✅ 회원가입 성공! ${data.uNickname}님!`); // 닉네임을 alert으로 표시
            window.location.href = "/user/login"; // 로그인 페이지로 이동
        })
        .catch(error => console.error("🚨 회원가입 실패:", error));
    });
});
