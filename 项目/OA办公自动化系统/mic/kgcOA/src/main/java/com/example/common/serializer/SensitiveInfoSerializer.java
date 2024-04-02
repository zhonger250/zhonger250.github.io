package com.example.common.serializer;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.annotation.Desensitization;
import com.example.common.constant.CommonConstant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;

/**
 * @author 丁祥瑞
 * 自定义脱敏的序列化器
 */
public class SensitiveInfoSerializer extends JsonSerializer<String> implements ContextualSerializer {

    /**
     * 数据脱敏的类型
     */
    private CommonConstant.DesensitizationType desensitizationType;

    /**
     * 前多少位不需要脱敏
     */
    private int prefixLen;

    /**
     * 后多少位不要脱敏
     */
    private int suffixLen;


    /**
     * 数据进行脱敏
     */
    @Override
    public void serialize(String s, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // s 实际就是属性的值
        if (StrUtil.isNotEmpty(s)) {
            // 如果属性的值不为空, 对属性进行脱敏
            switch (desensitizationType) {
                case EMAIL:
                    // 如果属性的脱敏类型是邮箱,属性就按照邮箱脱敏规则进行脱敏
                    s = DesensitizedUtil.email(s);
                    break;
                case ADDRESS:
                    // 如果属性的脱敏类型是地址,属性就按照地址脱敏规则进行脱敏
                    s = DesensitizedUtil.address(s, 2);
                    break;
                case CHINESE_NAME:
                    // 如果属性的脱敏类型是中文名,属性就按照中文名脱敏规则进行脱敏
                    s = DesensitizedUtil.chineseName(s);
                    break;
                case CUSTOMIZE_RULE:
                    // 如果属性的脱敏类型是自定义规则,属性就按照自定义规则进行脱敏
                    s = StrUtil.hide(s, prefixLen, suffixLen);
                    break;
                case ID_CARD:
                    // 如果属性的脱敏类型是身份证,属性就按照身份证脱敏规则进行脱敏
                    s = DesensitizedUtil.idCardNum(s,4, 4);
                    break;
                case USER_ID:
                    // 如果属性的脱敏类型是用户ID,属性就按照用户ID脱敏规则进行脱敏
                    s = DesensitizedUtil.idCardNum(s, prefixLen, suffixLen);
                    break;
                case PASSWORD:
                    // 如果属性的脱敏类型是密码,属性就按照密码脱敏规则进行脱敏
                    s = DesensitizedUtil.password(s);
                    break;
                case BANK_CARD:
                    //  如果属性的脱敏类型是银行卡,属性就按照银行卡脱敏规则进行脱敏
                    s = DesensitizedUtil.bankCard(s);
                    break;
                case CAR_LICENSE:
                    //  如果属性的脱敏类型是车牌号,属性就按照车牌号脱敏规则进行脱敏
                    s = DesensitizedUtil.carLicense(s);
                    break;
                case FIXED_PHONE:
                    // 如果属性的脱敏类型是固定电话,属性就按照固定电话脱敏规则进行脱敏
                    s = DesensitizedUtil.fixedPhone(s);
                    break;
                case MOBILE_PHONE:
                    // 如果属性的脱敏类型是手机号,属性就按照手机号脱敏规则进行脱敏
                    s = DesensitizedUtil.mobilePhone(s);
                    break;
                default:
                    // 原样展示
                    break;
            }
            // 序列化属性
            gen.writeString(s);
        }

    }

    /**
     * 获得一些上下文的信息
     * 数据脱敏最终要实现的目标: 如果一个实体类中的属性上使用了我们自定义的脱敏属性的注解以后,
     * 再控制器中返回该实体类的对象的JSON格式数据时(序列化), 会将属性进行脱敏.
     * <p>
     * 可以从上下文信息中, 获得需要序列化的对象的属性,
     * 判断此属性是否使用了我们自定义的脱敏的注解.
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        // 获得属性上的脱敏的注解信息
        Desensitization annotation = beanProperty.getAnnotation(Desensitization.class);
        if (annotation != null) {
            // 属性上是有注解的
            this.desensitizationType = annotation.type();
            this.prefixLen = annotation.prefixLen();
            this.suffixLen = annotation.suffixLen();
            // 如果有脱敏注解， 需要使用当前序列化器对属性进行脱敏， 当前的序列化器是谁? this
            return this;
        }
        // 如果属性上没有脱敏注解,返回null.(null的意思是没有序列化, 也就是意味这个属性不脱敏)
        return null;
    }
}
