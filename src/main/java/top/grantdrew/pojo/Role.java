package top.grantdrew.pojo;

import java.util.Date;
import java.util.Map;
import top.grantdrew.util.FormatConvet;
import top.grantdrew.util.FormatException;

public class Role extends BaseEntity{
    private Integer id;   //id
    private String roleCode; //角色编码
    private String roleName; //角色名称
    private Integer createdBy; //创建者
    private Date creationDate; //创建时间
    private Integer modifyBy; //更新者
    private Date modifyDate;//更新时间


    @Override
    public void buildFromMap(Map<?, ?> map) {
        FormatConvet  convet = new FormatConvet();
        this.id = map.containsKey("id") ? (Integer) map.get("id") : null;
        this.roleCode = (String) map.get("roleCode");
        this.roleName = (String) map.get("roleName");

        this.createdBy = map.containsKey("createdBy") ? (Integer) map.get("createdBy") : null;

        // 将日期字符串转换为 java.sql.Date
        if (map.containsKey("creationDate") && map.get("creationDate") != null) {
            try {
                this.creationDate = convet.convertToDate((String) map.get("creationDate"));
            } catch (FormatException e) {
                // 处理异常
                this.creationDate = null; // 或者设置为默认值
            }
        }

        this.modifyBy = map.containsKey("modifyBy") ? (Integer) map.get("modifyBy") : null;

        // 处理更新时间
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
    public String getRoleCode() {
        return roleCode;
    }
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
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