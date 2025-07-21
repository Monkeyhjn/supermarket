document.addEventListener("DOMContentLoaded", function () {
    const logoutLinks = [document.getElementById("logoutLink"), document.getElementById("logoutMenu")];

    logoutLinks.forEach(function (btn) {
        if (btn) {
            btn.addEventListener("click", function () {
                fetch(`${document.getElementById("path").value}/jsp/logout.do`, {
                    method: "GET"
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.code === 200) {
                            window.location.href = `${document.getElementById("path").value}/login.jsp`;
                        } else {
                            alert("退出失败：" + data.message);
                        }
                    })
                    .catch(err => {
                        alert("网络错误：" + err);
                    });
            });
        }
    });
});