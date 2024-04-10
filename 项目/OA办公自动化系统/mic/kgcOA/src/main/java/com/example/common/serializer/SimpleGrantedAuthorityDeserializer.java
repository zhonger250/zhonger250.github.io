package com.example.common.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;

/**
 * @Author: zhonger250
 * @Date: 2024/4/8 16:48
 * @Description: 权限对象的反序列化器<br><br>
 * SimpleGrantedAuthority: springSecurity提供的权限对象
 *
 */
public class SimpleGrantedAuthorityDeserializer extends StdDeserializer<SimpleGrantedAuthority> {
    /**
     * 构造方法: 指定要反序列化的类型
     */
    public SimpleGrantedAuthorityDeserializer() {
        super(SimpleGrantedAuthority.class);
    }

    @Override
    public SimpleGrantedAuthority deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        // 将SimpleGrantedAuthority对象的json格式的字符串转为SimpleGrantedAuthority对象
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

        return new SimpleGrantedAuthority(jsonNode.get("authority").textValue());
    }
}
