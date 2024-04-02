import request from "@/utils/request";

export default {
  // 返回所有菜单
  getMenus() {
    return new request({
      url: "/vue-admin/system/sysMenu/list",
      method: "get"
    });
  },
  // 更新菜单状态
  switchMenuStatus(id) {
    return new request({
      url: `/vue-admin/system/sysMenu/switchStatus/${id}`,
      method: "put",
    });
  },
  // 删除菜单
  del(id) {
    return new request({
      url: `/vue-admin/system/sysMenu/del/${id}`,
      method: "delete"
    });
  },
  // 新增菜单
  save(sysMenu) {
    return new request({
      url: "/vue-admin/system/sysMenu/insert",
      method: "post",
      data: {
        "parentId": sysMenu.parentId,
        "title": sysMenu.title,
        "path": sysMenu.path,
        "component": sysMenu.component,
        "icon": sysMenu.icon,
        "permission": sysMenu.permission
      }
    });
  },
  // 更新菜单
  update(sysMenu){
    return new request({
      url:"/vue-admin/system/sysMenu/update",
      method:"put",
      data:{
        "id":sysMenu.id,
        "parentId": sysMenu.parentId,
        "title": sysMenu.title,
        "path": sysMenu.path,
        "component": sysMenu.component,
        "sort":sysMenu.sort,
        "icon": sysMenu.icon,
        "permission": sysMenu.permission,
        "type":sysMenu.type,
        "version":sysMenu.version
      }
    });
  },
  // 根据角色ID获取角色信息
  detail(id){
    return new request({
      url:`/vue-admin/system/sysRole/${id}`,
      method:"get"
    });
  },

}
