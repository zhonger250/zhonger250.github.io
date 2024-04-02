import request from "@/utils/request";

export default {
  // 根据用户真实姓名,部门ID分页查询数据
  getUserPage(searchObj) {
    return new request({
      url: '/vue-admin/system/sysUser/page',
      method: "get",
      params: searchObj
    });
  },

  /**
   * 获得所有部门信息
   */
  getDepts() {
    return new request({
      url: '/vue-admin/system/dept/list',
      method: "get"
    });
  },

  /**
   * 根据用户ID查询用户信息
   */
  getUserInfo(id) {
    return new request({
      url: `/vue-admin/system/sysUser/${id}`,
      method: "get",
    });
  },

  /**
   * 给用户分配角色信息
   */
  distributionRole(userId, roleIds) {
    return new request({
      url: `/vue-admin/system/sysUser/distributionRole`,
      method: "put",
      data: {
        "userId": userId,
        "roleIds": roleIds
      },
    });
  },

  /**
   * 保存用户信息
   */
  save(sysUser) {
    return new request({
      url: "/vue-admin/system/sysUser/insert",
      method: "post",
      data: sysUser,
    });
  },

  /**
   * 根据ID删除用户信息
   */
  deleteById(id) {
    return new request({
      url: `/vue-admin/system/sysUser/del/${id}`,
      method: "delete",
    });
  },

  /**
   * 修改用户状态
   */
  switchStatus(id) {
    return new request({
      url: `/vue-admin/system/sysUser/switchStatus/${id}`,
      method: "put"
    });
  },

  /**
   * 修改用户信息
   */
  updateUser(sysUser) {
    return new request({
      url: "/vue-admin/system/sysUser/update",
      method: "put",
      data: sysUser,
    });
  },

  /**
   * 查询用户的所有角色以及系统的所有角色信息
   */
  getUserRoleIds(userId) {
    return new request({
      url:`/vue-admin/system/sysUser/selectUserRoleIdsAndAllRole/${userId}`,
      method:"get",
    });
  },

}
