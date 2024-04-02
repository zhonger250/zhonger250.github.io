<template>
  <div class="app-container">
    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="5">
            <el-form-item label="姓名">
              <el-input style="width: 95%" v-model="searchObj.realName" placeholder="姓名"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="岗位">
              <el-input style="width: 95%" v-model="searchObj.job" placeholder="岗位"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="部门">
              <el-select v-model="searchObj.deptId" placeholder="请选择" clearable @clear="restDept">
                <el-option
                  v-for="item in depts"
                  :key="item.id"
                  :label="item.deptName"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="display:flex">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="fetchData()">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="clear()">重置</el-button>
          <el-button type="success" icon="el-icon-plus" size="mini" @click="add">添 加</el-button>
        </el-row>
      </el-form>
    </div>
    <!-- 列表 -->
    <el-table :data="list" stripe border style="width: 100%;margin-top: 10px;">
      <el-table-column prop="realName" label="姓名"/>
      <el-table-column prop="deptName" label="部门"/>
      <el-table-column prop="roleNames" label="所属角色"/>
      <el-table-column label="状态" prop="status">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status === 1" @change="switchStatus(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit(scope.row.id)"
                     title="修改用户信息"/>
          <el-button type="primary" icon="el-icon-share" size="mini" title="分配角色"
                     @click="showAssignRole(scope.row.id)"/>
          <el-button type="danger" icon="el-icon-delete" size="mini"
                     @click="removeDataById(scope.row.id)" title="删除"/>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      :current-page="searchObj.current"
      :total="total"
      :page-size="5"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="fetchData"
    />

    <!-- 对话框 -->
    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%">
      <el-form ref="dataForm" :model="sysUser" width="100px" size="small" style="padding-right: 40px;">
        <el-form-item label="账号" prop="account">
          <el-input v-model="sysUser.account"/>
        </el-form-item>
        <el-form-item label="姓名" prop="realName">
          <el-input v-model="sysUser.realName"/>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="sysUser.address"/>
        </el-form-item>
        <el-form-item label="部门" prop="deptId">
          <el-select v-model="sysUser.deptId" placeholder="请选择" clearable @clear="restDept">
            <el-option
              v-for="item in depts"
              :key="item.id"
              :label="item.deptName"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input v-model="sysUser.phone"/>
        </el-form-item>
        <el-form-item label="工作" prop="job">
          <el-input v-model="sysUser.job"/>
        </el-form-item>
        <el-form-item label="入职时间" prop="hiredate">
          <el-date-picker v-model="sysUser.hireDate" type="date" placeholder="选择日期" value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small" icon="el-icon-refresh-right">取 消</el-button>
        <el-button type="primary" icon="el-icon-check" size="small" @click="saveOrUpdate">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 对话框 -->
    <el-dialog title="分配角色" :visible.sync="dialogRoleVisible">
      <el-form label-width="80px">
        <el-form-item label="姓名">
          <el-input disabled :value="sysUser.realName"></el-input>
        </el-form-item>
        <el-form-item label="角色列表">
          <div style="margin: 15px 0;"></div>
          <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll"
                       @change="handleCheckAllChange">全选
          </el-checkbox>
          <el-checkbox-group v-model="userRoleIds" @change="handleCheckedRoleChange">
            <el-checkbox v-for="role in allRoles"
                         :key="role.id" :label="role.id">
              {{ role.roleName }}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="assignRole" size="small">保存</el-button>
        <el-button @click="dialogRoleVisible = false" size="small">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import api from '@/api/system/sysUser'

export default {
  data() {
    return {
      searchObj: { //查询条件
        realName: '', //姓名
        deptID: '', //部门名
        job: '', //岗位
        current: "1" //当前页
      },
      list: [], //当前页数据
      total: 0, //总页数
      dialogVisible: false, //弹框是否可见

      sysUser: { // 新增/修改用户的信息
        id: '',
        version: '',
        account: 'king',
        realName: '小马哥',
        address: '深圳',
        birthday: '1980-01-01',
        phone: '13333333333',
        deptId: '1',
        job: 'Boss',
        hireDate: "2024-01-01"
      },
      depts: [], //所有部门信息
      dialogRoleVisible: false, // 分配角色的对话框是否可见
      userRoleIds: [], // 用户的角色ID
      allRoles: [], //所有角色列表
      checkAll: false,
      isIndeterminate: true,
    }
  },
  created() {
    this.getDepts(); // 获得所有部门信息
    this.fetchData(); // 分页查询当前页的用户信息
  },

  methods: {
    fetchData(current = 1) {
      this.searchObj.current = current;
      api.getUserPage(this.searchObj).then(res => {
        this.list = res.data.records;
        this.total = res.data.total;
        this.searchObj.current = res.data.current;
      });
    },
    getDepts() {
      api.getDepts().then(res => {
        this.depts = res.data;
      });
    },
    switchStatus(row) {
      api.switchStatus(row.id).then(res => {
        this.$message.success("更新用户状态成功");
        this.fetchData(this.searchObj.current);
      });
    },
    edit(id) {
      this.dialogVisible = true;
      api.getUserInfo(id).then(res => {
        this.sysUser.id = res.data.id;
        this.sysUser.name = res.data.name;
        this.sysUser.realName = res.data.realName;
        this.sysUser.address = res.data.address;
        this.sysUser.birthday = res.data.birthday;
        this.sysUser.deptId = res.data.deptId;
        this.sysUser.hiredate = res.data.hiredate;
        this.sysUser.job = res.data.job;
        this.sysUser.phone = res.data.phone;
        this.sysUser.version = res.data.version;
        this.allRoles = res.data.allRoles;
        this.userRoleIds = res.data.userRoleIds;
      });
    },
    removeDataById(id) {
      this.$confirm('是否确认删除', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return api.deleteById(id);
      }).then(() => {
        this.$message.success("删除用户成功");
        this.fetchData(1);
      });
    },
    add() { //新增用户信息
      this.dialogVisible = true;
      this.sysUser = {};
    },
    restDept() { //重置下拉框中的内容
      this.searchObj.deptID = "";
    },
    saveOrUpdate() { // 新增/更新用户信息
      if (!this.sysUser.id) {
        this.save(this.sysUser);
      } else {
        this.update(this.sysUser);
      }
    },
    update(sysUser) { //修改用户信息
      api.updateUser(sysUser).then(res => {
        this.$message.success("更新成功");
        this.fetchData(this.searchObj.current);
        this.dialogVisible = false;
      });
    },
    save(sysUser) { //保存用户信息
      api.save(sysUser).then(res => {
        this.$message.success("新增用户成功");
        this.fetchData(this.searchObj.current);
        this.dialogVisible = false;
      });
    },
    showAssignRole(id) { //展示用户和分配角色的页面
      this.dialogRoleVisible = true;
      api.getUserRoleIds(id).then(res => {
        this.sysUser = res.data.sysUser;
        this.userRoleIds = res.data.userRoleIds;
        this.allRoles = res.data.allRoles;
      });
    }, assignRole() { //保存角色信息
      let tempId = this.sysUser.id;
      let tempUserRoleIds = this.userRoleIds;
      api.distributionRole(tempId, tempUserRoleIds).then(res => {
        this.$message.success("分配角色成功");
        this.dialogRoleVisible = false;
        this.fetchData(this.searchObj.current);
      });
    },
    handleCheckAllChange(val) {
      this.userRoleIds = val ? this.allRoles.map(item => item.id) : []
      this.isIndeterminate = false;
    },
    handleCheckedRoleChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.allRoles.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.a
    },
    clear() {
      this.searchObj = { //查询条件
        realName: '', //姓名
        deptID: '0', //部门名
        job: '', //岗位
      };
    },

  }




  // methods: {
  //   fetchData(current = 1) { //获得当前页的用户信息
  //     this.searchObj.current = current;
  //     api.getUserPage(this.searchObj).then(res => {
  //       this.list = res.data.records;
  //       this.total = res.data.total;
  //       this.searchObj.current = res.data.current;
  //     });
  //   },
  //   getDepts() { //查询所有部门信息
  //     api.getDepts().then(res => {
  //       this.depts = res.data;
  //     });
  //   },
  //   switchStatus(row) { //切换用户状态
  //     api.switchStatus(row.id).then(res => {
  //       this.$message.success("更改用户状态成功");
  //       this.fetchData(this.searchObj.current);
  //     });
  //   },
  //   edit(id){ //编辑用户信息
  //     this.dialogVisible=true;
  //     api.getUserInfo(id).then(res=>{
  //       this.sysUser.id=res.data.id;
  //       this.sysUser.name=res.data.name;
  //       this.sysUser.realName=res.data.realName;
  //       this.sysUser.address=res.data.address;
  //       this.sysUser.birthday=res.data.birthday;
  //       this.sysUser.deptId=res.data.deptId;
  //       this.sysUser.hiredate=res.data.hiredate;
  //       this.sysUser.job=res.data.job;
  //       this.sysUser.phone=res.data.phone;
  //       this.sysUser.version=res.data.version;
  //
  //       this.userRoleIds = res.data.userRoleIds;
  //       this.allRoles = res.data.allRoles;
  //     });
  //   },
  //
  //   removeDataById(id) { //删除用户信息
  //     this.$confirm('是否确认删除', '提示', {
  //       confirmButtonText: '确认',
  //       cancelButtonText: '取消',
  //       type: 'warning'
  //     }).then(() => {
  //       return api.deleteById(id);
  //     }).then(() => {
  //       this.$message.success("删除用户成功");
  //       this.fetchData(1);
  //     });
  //   },
  //   add() { //新增用户信息
  //     this.dialogVisible = true;
  //     this.sysUser = {};
  //   },
  //   restDept() { //重置下拉框中的内容
  //     this.searchObj.deptID = "";
  //   },
  //   saveOrUpdate() { // 新增/更新用户信息
  //     if (!this.sysUser.id) {
  //       this.save(this.sysUser);
  //     } else {
  //       this.update(this.sysUser);
  //     }
  //   },
  //   update(sysUser) { //修改用户信息
  //     api.updateUser(sysUser).then(res => {
  //       this.$message.success("更新成功");
  //       this.fetchData(this.searchObj.current);
  //       this.dialogVisible = false;
  //     });
  //   },
  //   save(sysUser) { //保存用户信息
  //     api.save(sysUser).then(res => {
  //       this.$message.success("新增用户成功");
  //       this.fetchData(this.searchObj.current);
  //       this.dialogVisible = false;
  //     });
  //   },
  //   showAssignRole(id) { //展示用户和分配角色的页面
  //     this.dialogRoleVisible = true;
  //     api.getUserRoleIds(id).then(res => {
  //       this.sysUser = res.data.sysUser;
  //       this.userRoleIds = res.data.userRoleIds;
  //       this.allRoles = res.data.allRoles;
  //     });
  //   },
  //
  //
  //   assignRole() { //保存角色信息
  //     let tempId = this.sysUser.id;
  //     let tempUserRoleIds = this.userRoleIds;
  //     api.distributionRole(tempId, tempUserRoleIds).then(res => {
  //       this.$message.success("分配角色成功");
  //       this.dialogRoleVisible = false;
  //       this.fetchData(this.searchObj.current);
  //     });
  //   },
  //   handleCheckAllChange(val) {
  //     this.userRoleIds = val ? this.allRoles.map(item => item.id) : [];
  //     this.isIndeterminate = false;
  //   },
  //   handleCheckedRoleChange(value) {
  //     let checkedCount = value.length;
  //     this.checkAll = checkedCount === this.allRoles.length;
  //     this.isIndeterminate = checkedCount > 0 && checkedCount < this.allRoles.length;
  //   },
  //   clear(){
  //     this.searchObj= { //查询条件
  //       realName: '', //姓名
  //         deptID: '0', //部门名
  //         job: '', //岗位
  //     };
  //   }
  // }
}
</script>
