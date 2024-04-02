package com.example.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024/3/26 16:14
 * @Description:
 */
@Data
public class DistributionRoleDTO {
    /**
     * 用户ID
     */
    private Integer userId;


    /**
     * 用户所拥有的所有角色集合
     */
    private List<Integer> roleIds = new ArrayList<>();
}
