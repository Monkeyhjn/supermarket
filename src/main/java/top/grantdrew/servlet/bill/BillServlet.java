package top.grantdrew.servlet.bill;

import top.grantdrew.pojo.Bill;
import top.grantdrew.service.bill.BillService;
import top.grantdrew.service.bill.BillServiceImpl;
import top.grantdrew.util.FormatConvet;
import top.grantdrew.util.JsonResponse;
import top.grantdrew.util.FormatException;
import top.grantdrew.servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BillServlet extends BaseServlet {

    private final BillService billService = new BillServiceImpl();
    private final FormatConvet formatConvet = new FormatConvet();

    // 条件分页查询账单
    protected void getBillList(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Map<?, ?> map = formatConvet.handleObject(req.getAttribute("jsonBody"));
            Bill bill = new Bill();
            bill.buildFromMap(map);
            List<Bill> billList = billService.getBillList(bill);
            writeJson(resp, JsonResponse.success(billList));
        } catch (Exception e) {
            writeJson(resp, JsonResponse.error(500, "获取账单列表失败").put("exception", e.getMessage()));
        }
    }

    // 添加账单
    protected void add(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Map<?, ?> map = formatConvet.handleObject(req.getAttribute("jsonBody"));
            Bill bill = new Bill();
            bill.buildFromMap(map);
            boolean success = billService.add(bill);
            if (success) {
                writeJson(resp, JsonResponse.success(null).put("msg", "添加成功"));
            } else {
                writeJson(resp, JsonResponse.error(400, "添加失败"));
            }
        } catch (FormatException e) {
            writeJson(resp, JsonResponse.error(400, "格式错误").put("exception", e.getMessage()));
        } catch (Exception e) {
            writeJson(resp, JsonResponse.error(500, "服务器错误").put("exception", e.getMessage()));
        }
    }

    // 根据 ID 获取账单
    protected void getById(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Map<?, ?> map = formatConvet.handleObject(req.getAttribute("jsonBody"));
            String id = map.get("id").toString();

            Bill bill = billService.getBillById(id);
            if (bill != null) {
                writeJson(resp, JsonResponse.success(bill));
            } else {
                writeJson(resp, JsonResponse.error(404, "账单未找到"));
            }
        } catch (Exception e) {
            writeJson(resp, JsonResponse.error(500, "查询账单失败").put("exception", e.getMessage()));
        }
    }

    // 修改账单
    protected void update(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Map<?, ?> map = formatConvet.handleObject(req.getAttribute("jsonBody"));
            Bill bill = new Bill();
            bill.buildFromMap(map);
            boolean success = billService.modify(bill);
            if (success) {
                writeJson(resp, JsonResponse.success(null).put("msg", "修改成功"));
            } else {
                writeJson(resp, JsonResponse.error(400, "修改失败"));
            }
        } catch (Exception e) {
            writeJson(resp, JsonResponse.error(500, "修改账单失败").put("exception", e.getMessage()));
        }
    }

    // 删除账单
    protected void delete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Map<?, ?> map = formatConvet.handleObject(req.getAttribute("jsonBody"));
            String id = map.get("id").toString();

            boolean success = billService.deleteBillById(id);
            if (success) {
                writeJson(resp, JsonResponse.success(null).put("msg", "删除成功"));
            } else {
                writeJson(resp, JsonResponse.error(400, "删除失败"));
            }
        } catch (Exception e) {
            writeJson(resp, JsonResponse.error(500, "删除账单失败").put("exception", e.getMessage()));
        }
    }
}
