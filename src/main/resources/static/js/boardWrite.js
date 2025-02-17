function submitPost() {
    let postData = {
        bCategory: document.getElementById("bCategory").value,
        bTitle: document.getElementById("bTitle").value,
        bContent: document.getElementById("bContent").value,
        bCreatedId: document.getElementById("bCreatedId").value
    };

    console.log("📌 전송할 데이터:", postData); // ✅ 콘솔에서 닉네임 값 확인!

    if (!postData.bCreatedId) {
        alert("🚨 로그인 상태를 확인해주세요! 작성자가 없습니다.");
        return;
    }

    fetch("/api/board/write", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(postData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert("🎉 글이 성공적으로 등록되었습니다!");
            window.location.href = "/board";
        } else {
            alert("🚨 등록에 실패했습니다.");
        }
    })
    .catch(error => console.error("등록 오류:", error));
}
