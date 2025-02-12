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

    // ✅ 이메일 중복 확인 함수
    async function checkEmailDuplicate(email) {
        try {
            const response = await fetch(`/api/user/check-email?email=${email}`);
            const data = await response.json();

            if (data.exists) {
                alert("🚨 이미 사용 중인 이메일입니다! 다른 이메일을 입력해주세요.");
                return false;
            }
            return true;
        } catch (error) {
            console.error("🚨 이메일 중복 확인 실패:", error);
            return false;
        }
    }

    // ✅ 회원가입 버튼 클릭 이벤트
    document.querySelector(".signupBtn").addEventListener("click", async function (event) {
        event.preventDefault(); // 기본 폼 제출 방지
        submitSignup(); // 회원가입 실행
    });

    // ✅ 엔터 키 입력 시 회원가입 버튼 클릭
    document.addEventListener("keydown", function (event) {
        if (event.key === "Enter") {
            event.preventDefault(); // 기본 엔터 키 폼 제출 방지
            document.querySelector(".signupBtn").click(); // 버튼 클릭 이벤트 실행
        }
    });

    // ✅ 회원가입 실행 함수
    async function submitSignup() {
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

        // ✅ 이메일 중복 확인
        let isEmailValid = await checkEmailDuplicate(uEmail);
        if (!isEmailValid) {
            return;
        }

        // ✅ 유효성 검사를 모두 통과한 경우, fetch 실행
        let signupData = { uId, uPwd, uEmail, uNickname };
        console.log("✅ 회원가입 데이터 확인:", signupData);

        // ✅ 회원가입 요청
        fetch("/api/user/signup", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(signupData),
        })
            .then(response => response.json())
            .then(data => {
                console.log("✅ 회원가입 응답 데이터:", data);
                alert(`✅ 회원가입 성공! ${data.uNickname}님!`);
                window.location.href = "/user/login";
            })
            .catch(error => console.error("🚨 회원가입 실패:", error));
    }
});