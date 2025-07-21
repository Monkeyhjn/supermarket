package top.grantdrew.pojo;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Date;
import top.grantdrew.util.FormatConvet;
import top.grantdrew.util.FormatException;

public class Provider extends BaseEntity{
    private Integer id;   //id
    private String proCode; //供应商编码
    private String proName; //供应商名称
    private String proDesc; //供应商描述
    private String proContact; //供应商联系人
    private String proPhone; //供应商电话
    private String proAddress; //供应商地址
    private String proFax; //供应商传真
    private Integer createdBy; //创建者
    private Date creationDate; //创建时间
    private Integer modifyBy; //更新者
    private Date modifyDate;//更新时间

    @Override
    public void buildFromMap(Map<?, ?> map) {
        FormatConvet convet = new FormatConvet();
        this.id = map.containsKey("id") ? (Integer) map.get("id") : null;
        this.proCode = (String) map.get("proCode");
        this.proName = (String) map.get("proName");
        this.proDesc = (String) map.get("proDesc");
        this.proContact = (String) map.get("proContact");
        this.proPhone = (String) map.get("proPhone");
        this.proAddress = (String) map.get("proAddress");
        this.proFax = (String) map.get("proFax");

        this.createdBy = map.containsKey("createdBy") ? (Integer) map.get("createdBy") : null;

        // 确保将日期字符串转换为 java.sql.Date
        if (map.containsKey("creationDate") && map.get("creationDate") != null) {
            try {
                this.creationDate = convet.convertToDate((String) map.get("creationDate"));
            } catch (FormatException e) {
                // 处理异常
                this.creationDate = null; // 或者设置为默认值
            }
        }

        this.modifyBy = map.containsKey("modifyBy") ? (Integer) map.get("modifyBy") : null;

        if (map.containsKey("modifyDate") && map.get("modifyDate") != null) {
            try {
                this.modifyDate = convet.convertToDate((String) map.get("modifyDate"));
            } catch (FormatException e) {
                // 处理异常
                this.modifyDate = null; // 或者设置为默认值
            }
        }
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getProCode() {
        return proCode;
    }
    public void setProCode(String proCode) {
        this.proCode = proCode;
    }
    public String getProName() {
        return proName;
    }
    public void setProName(String proName) {
        this.proName = proName;
    }
    public String getProDesc() {
        return proDesc;
    }
    public void setProDesc(String proDesc) {
        this.proDesc = proDesc;
    }
    public String getProContact() {
        return proContact;
    }
    public void setProContact(String proContact) {
        this.proContact = proContact;
    }
    public String getProPhone() {
        return proPhone;
    }
    public void setProPhone(String proPhone) {
        this.proPhone = proPhone;
    }
    public String getProAddress() {
        return proAddress;
    }
    public void setProAddress(String proAddress) {
        this.proAddress = proAddress;
    }
    public String getProFax() {
        return proFax;
    }
    public void setProFax(String proFax) {
        this.proFax = proFax;
    }
    public Integer getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public Integer getModifyBy() {
        return modifyBy;
    }
    public void setModifyBy(Integer modifyBy) {
        this.modifyBy = modifyBy;
    }
    public Date getModifyDate() {
        return modifyDate;
    }
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
