window.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("actionForm");
    const userCodeInput = document.getElementById("userCode");
    const userPasswordInput = document.getElementById("userPassword");

    form.addEventListener("submit", function (event) {
        event.preventDefault(); // 阻止表单默认提交

        const userCode = userCodeInput.value.trim();
        const userPassword = userPasswordInput.value.trim();

        if (!userCode || !userPassword) {
            alert("请输入用户名和密码！");
            return;
        }

        const params = new URLSearchParams();
        params.append("userCode", userCode);
        params.append("userPassword", userPassword);

        fetch(form.action, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
            },
            body: params.toString()
        })
            .then(response => response.json())
            .then(data => {
                if (data.code === 200) {
                    // 登录成功，跳转主页
                    window.location.href = "jsp/frame.jsp";
                } else {
                    // 显示错误信息
                    const info = document.querySelector(".info");
                    info.innerText = data.message || "登录失败！";
                }
            })
            .catch(error => {
                console.error("登录请求失败：", error);
                alert("系统异常，请稍后重试！");
            });
    });
});