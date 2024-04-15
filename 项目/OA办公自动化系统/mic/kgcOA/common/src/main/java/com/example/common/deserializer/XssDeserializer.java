package com.example.common.deserializer;

import cn.hutool.http.HtmlUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author 丁祥瑞
 * 如果传递的参数是一个对象(控制器中使用@ResquestBody接收参数)
 * 对这些参数进行XSS过滤
 */
@Slf4j
public class XssDeserializer extends JsonDeserializer<String> {

    /**
     * 创建当前类的对象(使用此对象替换掉默认的String类型的反序列化器)
     */

    public static final XssDeserializer xss = new XssDeserializer();

    /**
     * 对String类型的参数进行反序列化, 过滤有害部分
     *
     * @param p    Parsed used for reading JSON content
     * @param ctxt Context that can be used to access information about
     *             this deserialization activity.
     * @return
     * @throws IOException
     * @throws JsonProcessingException
     */
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String source = p.getText().trim();
        log.debug("XssDeserializer deserialize: {}", source);
        return HtmlUtil.filter(source);
    }
}
