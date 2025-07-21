package top.grantdrew.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import top.grantdrew.util.JsonResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 通用REST风格Servlet基类，支持 methodName 参数 或 RESTful 风格路径调用方法
 */
public abstract class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String methodName = null;

        // 处理 Content-Type 为 application/json 的 POST 请求
        String contentType = req.getHeader("Content-Type");
        if (contentType != null && contentType.startsWith("application/json")) {
            String postJSON = getPostJSON(req);
            if (postJSON != null && !postJSON.trim().isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> map = mapper.readValue(postJSON, Map.class);
                methodName = (String) map.get("methodName");
                req.setAttribute("jsonBody", map); // 保存 JSON 数据到 request 域
            }
        } else {
            methodName = req.getParameter("methodName");
        }

        // 如果未提供 methodName 参数，则尝试从 URI 中截取方法名（REST 风格）
        if (methodName == null || methodName.trim().isEmpty()) {
            String uri = req.getRequestURI();
            int lastSlash = uri.lastIndexOf('/');
            methodName = uri.substring(lastSlash + 1); // eg. /user/login → login
        }

        // 利用反射执行对应方法
        try {
            Method method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(this, req, resp);
        } catch (Exception e) {
            JsonResponse<Object> error = JsonResponse.error(404, "请求的方法不存在：" + methodName)
                    .put("exception", e.getMessage());
            writeJson(resp, error);
        }
    }

    /**
     * 从请求中读取 JSON 字符串
     */
    protected String getPostJSON(HttpServletRequest request) {
        try (BufferedReader reader = request.getReader()) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            return sb.toString();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 向客户端写入 JSON 响应
     */
    protected void writeJson(HttpServletResponse response, Object data) {
        try {
            response.setContentType("application/json;charset=UTF-8");
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // 支持时间类型序列化
            mapper.writeValue(response.getWriter(), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}