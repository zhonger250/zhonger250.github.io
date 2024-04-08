package com.example.entity;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zhonger250
 * @Date: 2024/4/8 14:03
 * @Description: 子菜单信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Meta {

    private String title;
    private String icon;
}
