package top.grantdrew.servlet.user;

import top.grantdrew.pojo.Role;
import top.grantdrew.pojo.User;
import top.grantdrew.service.role.RoleService;
import top.grantdrew.service.role.RoleServiceImpl;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.SQLException;

public class UserServlet extends BaseServlet {
    private final UserService userService = new UserServiceImpl();
    private final RoleService roleService = new RoleServiceImpl();
    private final FormatConvet conv = new FormatConvet();

    // 分页查询用户列表
    public void query(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String queryUserName = req.getParameter("queryUserName");
        String queryUserRoleStr = req.getParameter("queryUserRole");
        String pageIndexStr = req.getParameter("pageIndex");

        Object body = req.getAttribute("jsonBody");
        try {
            Map<?, ?> json = conv.handleObject(body);
            if (queryUserName == null) queryUserName = (String) json.get("queryUserName");
            if (queryUserRoleStr == null) queryUserRoleStr = String.valueOf(json.get("queryUserRole"));
            if (pageIndexStr == null) pageIndexStr = String.valueOf(json.get("pageIndex"));
        } catch (FormatException ignored) {}

        int queryUserRole = 0;
        int pageIndex = 1;
        try { queryUserRole = Integer.parseInt(queryUserRoleStr); } catch (Exception ignored) {}
        try { pageIndex = Integer.parseInt(pageIndexStr); } catch (Exception ignored) {}

        int pageSize = 5;
        int totalCount = userService.getUserCount(queryUserName, queryUserRole);
        int totalPageCount = (int) Math.ceil((double) totalCount / pageSize);
        pageIndex = Math.max(1, Math.min(pageIndex, totalPageCount));

        List<User> userList = userService.getUserList(queryUserName, queryUserRole, pageIndex, pageSize);
        List<Role> roleList = roleService.getRoleList();

        Map<String, Object> data = new HashMap<>();
        data.put("userList", userList);
        data.put("roleList", roleList);
        data.put("totalCount", totalCount);
        data.put("currentPage", pageIndex);
        data.put("totalPageCount", totalPageCount);

        writeJson(resp, JsonResponse.success(data));
    }

    // 添加用户
    public void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<?, ?> json;
        try {
            json = conv.handleObject(req.getAttribute("jsonBody"));
        } catch (FormatException e) {
            writeJson(resp, JsonResponse.error(400, "参数格式错误"));
            return;
        }

        User user = new User();
        user.buildFromMap(json);

        boolean success = userService.addUser(user);
        if (success) {
            writeJson(resp, JsonResponse.success("用户添加成功"));
        } else {
            writeJson(resp, JsonResponse.error(500, "用户添加失败"));
        }
    }

    // 删除用户
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uidStr = req.getParameter("uid");
        try {
            Map<?, ?> json = conv.handleObject(req.getAttribute("jsonBody"));
            if (uidStr == null) uidStr = String.valueOf(json.get("uid"));
        } catch (FormatException ignored) {}

        int uid;
        try {
            uid = Integer.parseInt(uidStr);
        } catch (NumberFormatException e) {
            writeJson(resp, JsonResponse.error(400, "用户ID格式错误"));
            return;
        }

        boolean deleted = userService.deleUser(uid);
        if (deleted) {
            writeJson(resp, JsonResponse.success("用户删除成功"));
        } else {
            writeJson(resp, JsonResponse.error(404, "用户不存在或删除失败"));
        }
    }

    // 根据 ID 获取用户信息
    public void getById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uid = req.getParameter("uid");
        try {
            Map<?, ?> json = conv.handleObject(req.getAttribute("jsonBody"));
            if (uid == null) uid = String.valueOf(json.get("uid"));
        } catch (FormatException ignored) {}

        try {
            User user = userService.getUserById(uid); // 可能会抛出 SQLException

            if (user != null) {
                writeJson(resp, JsonResponse.success(user)); // 假设返回用户信息
            } else {
                writeJson(resp, JsonResponse.error(404, "用户不存在"));
            }
        } catch (SQLException e) {
            writeJson(resp, JsonResponse.error(500, "数据库错误: " + e.getMessage()));
            e.printStackTrace(); // 将异常信息记录到日志中
        }
    }

    // 修改用户
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<?, ?> json;
        try {
            json = conv.handleObject(req.getAttribute("jsonBody"));
        } catch (FormatException e) {
            writeJson(resp, JsonResponse.error(400, "参数格式错误"));
            return;
        }

        User user = new User();
        user.buildFromMap(json);

        boolean success = userService.modifyUser(user);
        if (success) {
            writeJson(resp, JsonResponse.success("修改成功"));
        } else {
            writeJson(resp, JsonResponse.error(500, "修改失败"));
        }
    }

    // 校验 userCode 是否存在
    public void checkUserCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userCode = req.getParameter("userCode");
        if (userCode == null && req.getAttribute("jsonBody") != null) {
            try {
                Map<?, ?> json = conv.handleObject(req.getAttribute("jsonBody"));
                userCode = (String) json.get("userCode");
            } catch (FormatException ignored) {}
        }

        boolean exists = userService.getUser(userCode);
        Map<String, Object> result = new HashMap<>();
        result.put("userCode", userCode);
        result.put("exists", exists);
        writeJson(resp, JsonResponse.success(result));
    }

    // 修改密码
    public void updatePassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<?, ?> json;
        try {
            json = conv.handleObject(req.getAttribute("jsonBody"));
        } catch (FormatException e) {
            writeJson(resp, JsonResponse.error(400, "参数错误"));
            return;
        }

        String newPassword = (String) json.get("newPassword");
        User sessionUser = (User) req.getSession().getAttribute(Constant.USER_SESSION);
        boolean result = userService.updatePwd(sessionUser.getId(), newPassword);

        if (result) {
            req.getSession().removeAttribute(Constant.USER_SESSION);
            writeJson(resp, JsonResponse.success("密码修改成功，请重新登录"));
        } else {
            writeJson(resp, JsonResponse.error(500, "密码修改失败"));
        }
    }

    // 验证旧密码（前端 AJAX 用）不需要和数据库做匹配，只和会话做匹配
    public void validateOldPassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<?, ?> json;
        try {
            json = conv.handleObject(req.getAttribute("jsonBody"));
        } catch (FormatException e) {
            writeJson(resp, JsonResponse.error(400, "参数错误"));
            return;
        }

        String oldPassword = (String) json.get("oldPassword");
        User sessionUser = (User) req.getSession().getAttribute(Constant.USER_SESSION);

        if (sessionUser == null || oldPassword == null) {
            writeJson(resp, JsonResponse.error(401, "未登录或密码为空"));
        } else if (!sessionUser.getUserPassword().equals(oldPassword)) {
            writeJson(resp, JsonResponse.error(403, "旧密码不正确"));
        } else {
            writeJson(resp, JsonResponse.success("旧密码正确"));
        }
    }
}
