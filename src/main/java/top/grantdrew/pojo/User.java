package top.grantdrew.pojo;

import java.util.Date;
import java.util.Map;
import top.grantdrew.util.FormatConvet;
import top.grantdrew.util.FormatException;

public class User extends BaseEntity{
    private Integer id;
    private String userCode;    //用户编码
    private String userName;
    private String userPassword;
    private Integer gender;
    private Date birthday;
    private String phone;
    private String address;
    private Integer userRole;
    private Integer createdBy;  //创建者
    private Date creationDate;
    private Integer modifyBy;   //更新者
    private Date modifyDate;    //更新时间

    private Integer age;    //年龄
    private String userRoleName;    //用户角色名称


    @Override
    public void buildFromMap(Map<?, ?> map) {
        FormatConvet convert = new FormatConvet();
        this.id = map.containsKey("id") ? (Integer) map.get("id") : null;
        this.userCode = (String) map.get("userCode");
        this.userName = (String) map.get("userName");
        this.userPassword = (String) map.get("userPassword");

        // 处理性别字段
        this.gender = map.containsKey("gender") ? (Integer) map.get("gender") : null;

        if (map.containsKey("birthday") && map.get("birthday") != null) {
            try {
                this.birthday = convert.convertToDate((String) map.get("birthday"));
            } catch (FormatException e) {
                // 处理异常
                this.birthday = null; // 或者设置为默认值
            }
        }

        this.phone = (String) map.get("phone");
        this.address = (String) map.get("address");

        // 处理用户角色字段
        this.userRole = map.containsKey("userRole") ? (Integer) map.get("userRole") : null;

        this.createdBy = map.containsKey("createdBy") ? (Integer) map.get("createdBy") : null;

        // 处理创建时间
        if (map.containsKey("creationDate") && map.get("creationDate") != null) {
            try {
                this.creationDate = convert.convertToDate((String) map.get("creationDate"));
            } catch (FormatException e) {
                // 处理异常
                this.creationDate = null; // 或者设置为默认值
            }
        }

        this.modifyBy = map.containsKey("modifyBy") ? (Integer) map.get("modifyBy") : null;

        // 处理更新时间
        if (map.containsKey("modifyDate") && map.get("modifyDate") != null) {
            try {
                this.modifyDate = convert.convertToDate((String) map.get("modifyDate"));
            } catch (FormatException e) {
                // 处理异常
                this.modifyDate = null; // 或者设置为默认值
            }
        }

        // 处理年龄字段
        this.age = map.containsKey("age") ? (Integer) map.get("age") : null;

        // 处理用户角色名称字段
        this.userRoleName = (String) map.get("userRoleName");
    }
    public Integer getAge() {
        Date date = new Date();
        Integer age = date.getYear()-birthday.getYear();
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
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