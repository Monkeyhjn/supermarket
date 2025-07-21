package top.grantdrew.servlet.user;

import top.grantdrew.pojo.User;
import top.grantdrew.service.user.UserService;
import top.grantdrew.service.user.UserServiceImpl;
import top.grantdrew.servlet.BaseServlet;
import top.grantdrew.util.Constant;
import top.grantdrew.util.FormatConvet;
import top.grantdrew.util.FormatException;
import top.grantdrew.util.JsonResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 用户登录控制器，使用 BaseServlet 支持 REST 风格访问
 * 支持 POST JSON 或表单提交方式访问：/user/login
 */
public class LoginServlet extends BaseServlet {
    public static FormatConvet CONV = new FormatConvet();
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        // 支持从 JSON 请求体或表单中获取参数
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        // 如果是 JSON 请求，尝试从 request attribute 中获取字段
        Object body = req.getAttribute("jsonBody");
        Map<?, ?> map;
        try {
            map = CONV.handleObject(body);  // 调用 handleObject 方法
        } catch (FormatException e) {
            // 处理 FormatException，例如记录日志或返回错误响应
            System.out.println("格式错误: " + e.getMessage());
            return;  // 或者根据你的需求处理
        }

// 这里是处理返回的 map
        if (map != null) {
            if (userCode == null) userCode = (String) map.get("userCode");
            if (userPassword == null) userPassword = (String) map.get("userPassword");
        }

        UserService userService = new UserServiceImpl();
        User user = userService.login(userCode, userPassword);

        JsonResponse<User> result;
        if (user != null) {
            req.getSession().setAttribute(Constant.USER_SESSION, user);
            result = JsonResponse.success(user);
        } else {
            result = JsonResponse.error(401, "用户名或密码错误");
        }

        writeJson(resp, result);
    }
}