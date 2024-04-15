package com.example.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024/4/2 16:12
 * @Description:
 */
@Data
public class RoleMenuDTO {
    private Integer roleId;

    private List<Integer> menuIds = new ArrayList<>();
}
