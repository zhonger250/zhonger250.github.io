<template>
  <div class="app-container">
    <!--查询表单-->
    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="24">
            <el-form-item label="角色名称">
              <el-input style="width: 100%" v-model="searchObj.roleName" placeholder="角色名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="display:flex">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="fetchData()">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini">重置</el-button>
          <el-button type="success" icon="el-icon-plus" size="mini" @click="add">添 加</el-button>
        </el-row>
      </el-form>
    </div>
    <!-- 表格 -->
    <el-table :data="list" stripe border style="width: 100%;margin-top: 10px;">
      <el-table-column type="selection"/>
      <el-table-column prop="id" label="序号" align="center"/>
      <el-table-column prop="roleName" label="角色名称" align="center"/>
      <el-table-column prop="description" label="角色描述" align="center"/>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" title="修改" @click="edit(scope.row.id)"/>
          <el-button type="danger" icon="el-icon-delete" size="mini" title="删除" @click="del(scope.row.id)"/>
          <el-button type="warning" icon="el-icon-delete" size="mini" title="分配管理" @click="assignMenu(scope.row)"/>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <el-pagination :current-page="searchObj.current"
                   :total="total"
                   :page-size="5"
                   style="padding: 30px 0; text-align: center;"
                   layout="total, prev, pager, next, jumper"
                   @current-change="fetchData"/>

    <!-- 对话框 -->
    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%">
      <el-form ref="dataForm" :model="sysRole" label-width="150px" size="small" style="padding-right: 40px;">
        <el-form-item label="角色名称">
          <el-input v-model="sysRole.roleName"/>
        </el-form-item>
        <el-form-item label="角色描述">
          <el-input v-model="sysRole.description"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible=false" size="small" icon="el-icon-refresh-right">取 消</el-button>
        <el-button type="primary" icon="el-icon-check" @click="saveOrUpdate()" size="small">确 定</el-button>
      </span>
    </el-dialog>
  </div>

</template>

<script>
import api from '@/api/system/sysRole'

export default {
  data() {
    return {
      searchObj: {
        current: "1",
        roleName: ""
      },
      list: [],
      total: 0,
      dialogVisible: false,
      sysRole: {
        id: "",
        roleName: "",
        description: "",
        version: 0
      }
    }
  },
  created() {
    this.fetchData(1);
  },
  methods: {
    assignMenu(row) {
      console.log(row.id);
      this.$router.push('/system/assignMenu?id=' + row.id + "&roleName=" + row.roleName)
      console.log(row.roleName);
    },
    fetchData(page = 1) {
      // 得到当前页数
      this.searchObj.current = page;
      //
      api.getRolePage(this.searchObj).then(res => {
        this.list = res.data.records;
        this.total = res.data.total;
        this.searchObj.current = res.data.current;
      });
    },

    add() {
      // 点击添加, 显示对话框
      this.dialogVisible = true;
      this.sysRole = {};
    },

    edit(id) {
      // 显示对话框
      this.dialogVisible = true;
      // 点击编辑, 传入当前用户的ID
      api.detail(id).then(res => {
        console.log(res);
        // 根据id得到用户信息, 将用户信息保存到对话框中
        this.sysRole.id = res.data.id;
        this.sysRole.roleName = res.data.roleName;
        this.sysRole.description = res.data.description;
        this.sysRole.version = res.data.version;
      });
    },
    del(id) {
      this.$confirm("是否删除此数据?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        api.del(id);
      }).then(res => {
        this.$message.success("删除角色成功!");
        this.fetchData(1);
      });
    },
    saveOrUpdate() {
      if (!this.sysRole.id) {
        api.save(this.sysRole).then(res => {
          this.$message.success("新增角色成功!");
          this.fetchData(1);
        });
      } else {
        api.update(this.sysRole).then(res => {
          this.$message.success("更新角色成功!");
          this.fetchData(1);
        });
      }
      this.dialogVisible = false;
    }
  },
}
</script>

<style></style>
