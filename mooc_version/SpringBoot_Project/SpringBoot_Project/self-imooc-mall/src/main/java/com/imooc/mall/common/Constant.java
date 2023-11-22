package com.imooc.mall.common;

import com.google.common.collect.Sets;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 描述：      常量值定义
 */
@Component //为了能使Spring注入该类下的资源，添加该注解
public class Constant {
    //指定键值，作为存放在HttpSession中的Map数据类型的键key,可以通过该键值获取对应的数据信息
    public static final String IMOOC_MALL_USER = "imooc_mall_user";
    //盐值用于数据加密
    public static final String SALT = "jksfpsdferor";

    //存放上传图片的文件目录，将其通过application.properties进行配置
    public static String FILE_UPLOAD_DIR;

    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir){
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    public interface ProductListOrderBy{
        //使用Sets构建出两个字符串，为了之后调用方便
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price desc", "price acs");
    }

    //商品上下架情况
    public interface SellStatus {
        int NOT_SALE = 0;//商品下架状态
        int SALE = 1;//商品上架状态
    }

    //购物车勾选情况
    public interface Cart {
        int NOT_SELECTED = 0;//购物车未选中
        int SELECTED = 1;//购物车已选中
    }

    public enum OrderStatusEnum {
        CANCELED(0, "用户已取消"),
        NOT_PAID(10, "未付款"),
        PAID(20, "已付款"),
        DELIVERED(30, "已发货"),
        FINISHED(40, "交易完成");

        private String value;
        private int code;

        OrderStatusEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public static OrderStatusEnum codeOf(int code) {
            for (OrderStatusEnum orderStatusEnum : values()) {
                if (orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new ImoocMallException(ImoocMallExceptionEnum.NO_ENUM);
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
