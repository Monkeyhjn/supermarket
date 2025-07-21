package top.grantdrew.servlet.provider;

import top.grantdrew.pojo.Provider;
import top.grantdrew.service.provider.ProviderService;
import top.grantdrew.service.provider.ProviderServiceImpl;
import top.grantdrew.util.FormatConvet;
import top.grantdrew.util.JsonResponse;
import top.grantdrew.util.FormatException;
import top.grantdrew.servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@WebServlet("/provider/*")
public class ProviderServlet extends BaseServlet {

    private final ProviderService providerService = new ProviderServiceImpl();
    private final FormatConvet formatConvet = new FormatConvet();

    // 查询供应商列表（支持模糊查询）
    protected void getProviderList(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Map<?, ?> map = formatConvet.handleObject(req.getAttribute("jsonBody"));
            String proName = (map.get("proName")!=null)?(String) map.get("proName"):"";
            String proCode = (map.get("proCode")!=null)?(String) map.get("proCode"):"";
            List<Provider> list = providerService.getProviderList(proName, proCode);
            writeJson(resp, JsonResponse.success(list));
        } catch (Exception e) {
            writeJson(resp, JsonResponse.error(500, "查询供应商列表失败").put("exception", e.getMessage()));
        }
    }

    // 添加供应商
    protected void add(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Map<?, ?> map = formatConvet.handleObject(req.getAttribute("jsonBody"));
            Provider provider = new Provider();
            provider.buildFromMap(map);
            provider.setCreationDate(new Date());

            boolean result = providerService.add(provider);
            if (result) {
                writeJson(resp, JsonResponse.success(null).put("msg", "添加成功"));
            } else {
                writeJson(resp, JsonResponse.error(400, "添加失败"));
            }
        } catch (FormatException e) {
            writeJson(resp, JsonResponse.error(400, "格式错误").put("exception", e.getMessage()));
        } catch (Exception e) {
            writeJson(resp, JsonResponse.error(500, "添加供应商失败").put("exception", e.getMessage()));
        }
    }

    // 根据 ID 获取供应商信息
    protected void getById(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Map<?, ?> map = formatConvet.handleObject(req.getAttribute("jsonBody"));
            String id = map.get("id").toString();

            Provider provider = providerService.getProviderById(id);
            if (provider != null) {
                writeJson(resp, JsonResponse.success(provider));
            } else {
                writeJson(resp, JsonResponse.error(404, "供应商未找到"));
            }
        } catch (Exception e) {
            writeJson(resp, JsonResponse.error(500, "查询失败").put("exception", e.getMessage()));
        }
    }

    // 修改供应商
    protected void update(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Map<?, ?> map = formatConvet.handleObject(req.getAttribute("jsonBody"));
            Provider provider = new Provider();
            provider.buildFromMap(map);
            provider.setModifyDate(new Date());

            boolean result = providerService.modify(provider);
            if (result) {
                writeJson(resp, JsonResponse.success(null).put("msg", "修改成功"));
            } else {
                writeJson(resp, JsonResponse.error(400, "修改失败"));
            }
        } catch (Exception e) {
            writeJson(resp, JsonResponse.error(500, "修改失败").put("exception", e.getMessage()));
        }
    }

    // 删除供应商
    protected void delete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Map<?, ?> map = formatConvet.handleObject(req.getAttribute("jsonBody"));
            String id = map.get("id").toString();

            int flag = providerService.deleteProviderById(id);
            JsonResponse<Object> response = JsonResponse.success(null);
            switch (flag) {
                case 0:
                    response.put("delResult", "true");
                    break;
                case -1:
                    response.put("delResult", "false");
                    break;
                default:
                    response.put("delResult", String.valueOf(flag)); // 有订单，不能删
                    break;
            }
            writeJson(resp, response);
        } catch (Exception e) {
            writeJson(resp, JsonResponse.error(500, "删除失败").put("exception", e.getMessage()));
        }
    }
}
