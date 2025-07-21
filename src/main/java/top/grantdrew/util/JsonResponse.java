package top.grantdrew.util;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JsonResponse<T> {
    private int code;
    private String message;
    private Optional<T> data;
    private Map<String, Object> extra = new HashMap<>(); // 用于额外附加数据

    public JsonResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = Optional.ofNullable(data);
    }

    public static <T> JsonResponse<T> success(T data) {
        return new JsonResponse<>(200, "success", data);
    }

    public static <T> JsonResponse<T> error(int code, String message) {
        return new JsonResponse<>(code, message, null);
    }

    // 添加 put 方法支持链式调用
    public JsonResponse<T> put(String key, Object value) {
        extra.put(key, value);
        return this;
    }

    public String toJsonString() {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("code", code);
        responseMap.put("message", message);
        responseMap.put("data", data.orElse(null));
        responseMap.put("extra", extra); // 输出额外字段
        return JSONObject.toJSONString(responseMap);
    }

    // Getters & setters
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Optional<T> getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = Optional.ofNullable(data);
    }
}