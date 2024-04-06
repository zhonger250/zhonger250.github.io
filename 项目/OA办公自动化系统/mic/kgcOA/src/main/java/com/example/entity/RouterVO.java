package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024/4/5 14:47
 * @Description: 系统左侧菜单的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouterVO {
    /**
     * 地址
     */
    private String path;
    /**
     * 组件
     */
    private String component;
    /**
     * 标题
     */
    private String title;
    /**
     * 图标
     */
    private String icon;
    /**
     * 子菜单是否一直展示
     */
    private boolean alwaysShow;
    /**
     * 子菜单
     */
    private List<RouterVO> children = new ArrayList<>();
    /**
     * 是否显示
     */
    private boolean hidden;
}
