package top.grantdrew.pojo;

import java.util.Map;

public abstract class BaseEntity {
    // 抽象映射方法
    public abstract void buildFromMap(Map<?, ?> map);
}