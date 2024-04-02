package com.example.dto;

import com.example.entity.SysMenu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024/4/2 10:07
 * @Description:
 */
@Data
public class DistributionMenuDTO {

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 角色所拥有菜单集合
     */
    private List<Integer> menuIds = new ArrayList<>();
}
