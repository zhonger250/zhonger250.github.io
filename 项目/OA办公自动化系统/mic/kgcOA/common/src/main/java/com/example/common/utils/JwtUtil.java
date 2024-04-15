package com.example.common.utils;

import cn.hutool.core.util.StrUtil;
import com.example.common.constant.ResultConstant;
import com.example.common.exception.HttpException;
import io.jsonwebtoken.*;

import java.util.Date;

/**
 * @Author: zhonger250
 * @Date: 2024/4/3 17:13
 * @Description: Jwt工具类
 */

public class JwtUtil {
    private static final long tokenExpiration = 365L * 24 * 60 * 60 * 1000;
    private static final String tokenSignKey = "8r2ejkd8we";

    // 根据用户 id 和用户名称， 生成token的字符串
    public static String createToken(String userId, String userName) {
        return Jwts.builder()
                //JWT头 alg属性表示签名使用的算法,默认是HS256 type属性表示令牌的类型, 统一是JWT
                //JWT有效载荷:
                .setSubject("AUTH-USER") //主题
                .setExpiration(new Date(System.currentTimeMillis() +
                        tokenExpiration)) //过期时间
                .claim("userId", userId) //用户ID
                .claim("username", userName) //用户名字
                //JWT签名:
                .signWith(SignatureAlgorithm.HS512, tokenSignKey) //签名类 型 密钥
                .compressWith(CompressionCodecs.GZIP) //对载荷进行压缩
                .compact();
    }

    public static String getUserId(String token) {
        try {
            if (StrUtil.isEmpty(token)) return null;
            Jws<Claims> claimsJws =
                    Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return (String) claims.get("userId");
        } catch (Exception e) {
            throw new HttpException(ResultConstant.NO_LOGIN);
        }
    }

    public static String getUsername(String token) {
        try {
            if (StrUtil.isEmpty(token)) return "";
            Jws<Claims> claimsJws =
                    Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return (String) claims.get("username");
        } catch (Exception e) {
            throw new HttpException(ResultConstant.NO_LOGIN);
        }
    }

    public static void main(String[] args) {
        String token  = JwtUtil.createToken("1","admin");
//        String token = "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ";
        System.out.println(token);

        String userId = JwtUtil.getUserId(token);
        System.out.println(userId);
    }
}
