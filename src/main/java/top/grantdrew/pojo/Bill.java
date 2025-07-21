package top.grantdrew.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import top.grantdrew.util.FormatConvet;
import top.grantdrew.util.FormatException;

public class Bill extends BaseEntity{
    private Integer id;   //id
    private String billCode; //账单编码
    private String productName; //商品名称
    private String productDesc; //商品描述
    private String productUnit; //商品单位
    private BigDecimal productCount; //商品数量
    private BigDecimal totalPrice; //总金额
    private Integer isPayment; //是否支付
    private Integer providerId; //供应商ID
    private Integer createdBy; //创建者
    private Date creationDate; //创建时间
    private Integer modifyBy; //更新者
    private Date modifyDate;//更新时间

    private String providerName;//供应商名称

    @Override
    public void buildFromMap(Map<?, ?> map) {
        FormatConvet convet = new FormatConvet();
        this.id = map.containsKey("id") ? (Integer) map.get("id") : null;
        this.billCode = (String) map.get("billCode");
        this.productName = (String) map.get("productName");
        this.productDesc = (String) map.get("productDesc");
        this.productUnit = (String) map.get("productUnit");

        // 处理 BigDecimal 类型的字段，确保输入不会导致异常
        this.productCount = map.containsKey("productCount")
                ? new BigDecimal(String.valueOf(map.get("productCount")))
                : null;
        this.totalPrice = map.containsKey("totalPrice")
                ? new BigDecimal(String.valueOf(map.get("totalPrice")))
                : null;

        this.isPayment = map.containsKey("isPayment")
                ? (Integer) map.get("isPayment")
                : 0;
        this.providerId = map.containsKey("providerId")
                ? (Integer) map.get("providerId")
                : 0;
        this.createdBy = map.containsKey("createdBy")
                ? (Integer) map.get("createdBy")
                : null;

        // 处理 Date 类型的字段，确保格式正确
        if (map.containsKey("creationDate") && map.get("creationDate") != null) {
            try {
                this.creationDate = convet.convertToDate((String) map.get("creationDate"));
            } catch (FormatException e) {
                // 处理异常
                this.creationDate = null; // 或者设置为默认值
            }
        }
        this.modifyBy = map.containsKey("modifyBy")
                ? (Integer) map.get("modifyBy")
                : null;
        if (map.containsKey("modifyDate") && map.get("modifyDate") != null) {
            try {
                this.modifyDate = convet.convertToDate((String) map.get("modifyDate"));
            } catch (FormatException e) {
                // 处理异常
                this.modifyDate = null; // 或者设置为默认值
            }
        }

        this.providerName = (String) map.get("providerName");
    }
    public String getProviderName() {
        return providerName;
    }
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getBillCode() {
        return billCode;
    }
    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductDesc() {
        return productDesc;
    }
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
    public String getProductUnit() {
        return productUnit;
    }
    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }
    public BigDecimal getProductCount() {
        return productCount;
    }
    public void setProductCount(BigDecimal productCount) {
        this.productCount = productCount;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    public Integer getIsPayment() {
        return isPayment;
    }
    public void setIsPayment(Integer isPayment) {
        this.isPayment = isPayment;
    }

    public Integer getProviderId() {
        return providerId;
    }
    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
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
