import request from '@/utils/request';
export default {
  // 根据角色名称分页查询数据
  getRolePage(searchObj){
    return new request({
      url:"/vue-admin/system/sysRole/page",
      method:"get",
      params: searchObj
    });
  },

  // 根据菜单ID获取菜单信息
  detail(id){
    return new request({
      url:`/vue-admin/system/sysRole/${id}`,
      method:"get"
    });
  },

  // 新增角色信息
  save(sysRole){
    return new request({
      url:"/vue-admin/system/sysRole/insert",
      method:"post",
      data:sysRole
    });
  },

  // 修改角色信息
  update(sysRole){
    return new request({
      url:"/vue-admin/system/sysRole/update",
      method:"put",
      data:sysRole
    });
  },

  // 根据ID删除角色信息
  del(id){
    return new request({
      url:`/vue-admin/system/sysRole/del/${id}`,
      method:"delete",
    });
  },

}
