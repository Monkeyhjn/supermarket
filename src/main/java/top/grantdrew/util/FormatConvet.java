package top.grantdrew.util;
import java.util.Map;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FormatConvet {
    private static final String DATE_FORMAT = "yyyy-MM-dd"; // 定义日期格式常量
    public Map<?, ?> handleObject(Object obj) throws FormatException {
        if (obj instanceof Map) {
            return (Map<?, ?>) obj;
        } else {
            throw new FormatException("非json包被接受，位置: " + obj.getClass().getName());
        }
    }
    public Date convertToDate(String dateString) throws FormatException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new FormatException("日期格式不正确: " + dateString);
        }
    }
}
