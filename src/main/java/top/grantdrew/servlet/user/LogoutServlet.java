package top.grantdrew.servlet.user;

import top.grantdrew.servlet.BaseServlet;
import top.grantdrew.util.Constant;
import top.grantdrew.util.JsonResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends BaseServlet {

    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().removeAttribute(Constant.USER_SESSION);
        JsonResponse<Object> result = JsonResponse.success("退出成功");
        writeJson(resp, result);
    }
}