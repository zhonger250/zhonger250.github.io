<template>
  <div class="app-container">
    <div>
      {{ $route.query.roleName }}赋予权限
    </div>

    <el-tree ref="tree"
             :data="sysMenuList"
             show-checkbox
             default-expand-all
             node-key="id"
             :props="defaultProps"
    ></el-tree>

    <div>
      <el-button type="primary" icon="el-icon-check" size="mini" :loading="loading" @click="save()">
        保存
      </el-button>
      <el-button type="primary" icon="el-icon-refresh-right" size="mini" @click="back()">
        返回
      </el-button>
    </div>


  </div>
</template>
<script>
import api from '@/api/system/sysRole'

export default {
  data() {
    return {
      sysMenuList: [],// 所有菜单信息
      defaultProps: { // el-tree的配置
        children: "children",
        label: 'title'
      },
      loading: false, //为了防止点击按钮时,多次点击
    }
  },
  created() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      let roleId = this.$route.query.id;
      // 根据角色Id获取所有菜单以及角色对应的菜单的信息
      api.assignMenu(roleId).then(result => {
        // 获得所有菜单
        this.sysMenuList = result.data;
        // 找到选中的菜单
        let checkedIds = this.getCheckedMenuList(this.sysMenuList);
        // 选中的菜单
        this.$refs.tree.setCheckedKeys(checkedIds);
        console.log(checkedIds);
      });
    },
    // 获的所有菜单中, 角色选中的菜单Id, 将这些选中的菜单Id返回
    getCheckedMenuList(menus, array = []) {
      // 递归处理所有菜单信息, 使用reduce函数, reduce函数有两个参数.
      // 第一个参数是一个函数, 第二个参数是空的数组.
      // 函数中有两个参数,第一个参数(prev)是函数上一个处理完的结果, 第二个参数(item)是函数当前处理的元素.
      // reduce中的第一个参数的函数, 第一执行时, prev是空的数组, item是当前处理的菜单.
      // 如果函数是第一次执行时, prev是空数组.
      return menus.reduce((prev, item) => {
        // 当前处理的菜单被选中 同时 此菜单下没有子菜单
        if (item.checked && item.children.length === 0) {
          // 就在数组中保存当前菜单的Id
          prev.push(item.id);
        } else if (item.children.length !== 0) {
          // 当前处理的菜单有子菜单, 从这些子菜单中查找哪些菜单被选中, 获得选中的菜单的ID
          this.getCheckedMenuList(item.children, array);
        }
        return prev;
      }, array);

    },
    back() {
      this.$router.push('/system/sysRole');
    },
    save() {
      // 找到树形菜单中被选中的树形菜单
      // getCheckedNodes
      // 第一个参数是否只返回选中的节点
      // 第二个参数是父节点是半选状态时,返回父节点
      let checkedNodes = this.$refs.tree.getCheckedNodes(false, true);
      let checkedNodesId = checkedNodes.map(item => item.id);
      // 提交到后端的参数
      let roleMenu = {
        "roleId": this.$route.query.id,
        "menuIds": checkedNodesId,
      }
      // console.log(roleMenu);

      // 提交时, 防止按钮重复点击
      this.loading = true;
      // 保存
      api.doAssignMenu(roleMenu).then(() => {
        this.loading = false;
        this.$message.success("角色分配菜单成功!");
        this.back();
      });
    }
  }
}
</script>
