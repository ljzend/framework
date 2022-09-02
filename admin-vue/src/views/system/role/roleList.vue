<template>
  <el-main>
    <!-- 查询条件 -->
    <el-form
      :model="searchModel"
      ref="searchForm"
      label-width="80px"
      :inline="true"
      size="small"
    >
      <el-form-item>
        <el-input v-model.trim="searchModel.roleName" placeholder="请输入角色名称"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="search(pageNo, pageSize)">查询</el-button>
        <el-button icon="el-icon-refresh-right" @click="resetValue">重置</el-button>
        <el-button
          type="success"
          icon="el-icon-plus"
          @click="openAddWindow"
        >新增</el-button>
      </el-form-item>
    </el-form>
    <!-- 数据表格 -->
    <el-table
      :data="roleList"
      :height="tableHeight"
      border
      stripe
      style="width: 100%; margin-bottom: 10px"
    >
      <el-table-column
        prop="id"
        label="角色编号"
        width="100"
        align="center"
      ></el-table-column>
      <el-table-column prop="roleName" label="角色名称" align="center"/>
      <el-table-column prop="remark" label="角色备注" align="center" />
      <el-table-column label="操作" align="center" width="290">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-edit"
            type="primary"
            size="small"
            @click="handleEdit(scope.row)"
          >编辑
          </el-button
          >
          <el-button
            icon="el-icon-delete"
            type="danger"
            size="small"
            @click="handleDelete(scope.row)"
          >删除
          </el-button
          >
          <el-button
            icon="el-icon-setting"
            type="primary"
            size="small"
            @click="assignRole(scope.row)"
          >分配权限
          </el-button
          >
        </template>
      </el-table-column>
    </el-table><!-- 分页工具栏 -->
    <Pagination
      :total="total"
      :page="pageNo"
      :limit="pageSize"
      :pageSizes="[5,10,15,20,25,30,35,40,45,50]"
      @handleSizeChange="handleSizeChange"
      @handleCurrentChange="handleCurrentChange"
    ></Pagination>

    <!-- 添加和修改角色窗口 -->
    <system-dialog
      :title="roleDialog.title"
      :visible="roleDialog.visible"
      :width="roleDialog.width"
      :height="roleDialog.height"
      @onClose="onClose"
      @onConfirm="onConfirm"
    >
      <div slot="content">
        <el-form
          :model="role" ref="roleForm"
          :rules="rules"
          label-width="80px"
          :inline="false"
          size="small"
        >
          <el-form-item label="角色编码" prop="roleCode">
            <el-input v-model.trim="role.roleCode"></el-input>
          </el-form-item>
          <el-form-item label="角色名称" prop="roleName">
            <el-input v-model.trim="role.roleName"></el-input>
          </el-form-item>
          <el-form-item label="角色描述">
            <el-input type="textarea" v-model.trim="role.remark" :rows="5"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </system-dialog>

    <!-- 分配权限树窗口 -->
    <system-dialog
      :title="assignDialog.title"
      :visible="assignDialog.visible"
      :width="assignDialog.width"
      :height="assignDialog.height"
      @onClose="onAssignClose"
      @onConfirm="onAssignConfirm"
    >
      <div slot="content">
        <el-tree
          ref="assignTree"
          :data="assignTreeData"
          node-key="id"
          :props="defaultProps"
          empty-text="暂无数据"
          :show-checkbox="true"
          :highlight-current="true"
        ></el-tree>
      </div>
    </system-dialog>
  </el-main>
</template>

<script>
//导入role.js脚本
import roleApi from '@/api/role'
//导入对话框组件
import SystemDialog from '@/components/system/SystemDialog.vue'
//导入末级节点脚本
import leafUtils from '@/utils/leaf'
import Pagination from '@/components/Pagination/index'

export default {
  name: 'roleList',
  components: { SystemDialog, Pagination },
  data() {
    return {
      //查询条件
      searchModel: {
        roleName: '',
        pageNo: 1,
        pageSize: 10,
        userId: this.$store.getters.userId //用户ID
      },
      roleList: [], //数据列表
      tableHeight: 0, //表格高度
      pageNo: 1, //当前页码
      pageSize: 10, //每页显示数量
      total: 0,//总数量
      rules: {
        roleCode: [{ required: true, trigger: 'blur', message: '请输入角色编码' }],
        roleName: [{ required: true, trigger: 'blur', message: '请输入角色名称' }]
      },
      //添加和修改角色窗口属性
      roleDialog: {
        title: '',
        visible: false,
        height: 230,
        width: 500
      },
      //角色对象
      role: {
        id: '',
        roleCode: '',
        roleName: '',
        remark: '',
        createUser: this.$store.getters.userId
      },
      //分配权限窗口属性
      assignDialog: {
        title: '',
        visible: false,
        height: 450,
        width: 300
      },
      roleId: '', //角色ID
      assignTreeData: [], //树节点数据
      //树节点属性
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    }
  },
  methods: {
    /**
     * 分配权限
     */
    async assignRole(row) {
      //设置角色ID
      this.roleId = row.id
      //构建参数
      let params = {
        roleId: row.id,
        userId: this.$store.getters.userId
      }
      //发送查询请求
      let res = await roleApi.getAssignTreeApi(params)
      //判断是否成功
      if (res.success) {
        //获取当前登录用户拥有的所有权限
        let permissionList = res.data.permissionList
        //获取当前被分配的角色已经拥有的权限信息
        let checkList = res.data.checkList
        //判断当前菜单是否是末级
        let { setLeaf } = leafUtils()
        //设置权限菜单列表
        let newPermissionList = setLeaf(permissionList)
        //设置树节点菜单数据
        this.assignTreeData = newPermissionList
        //将回调延迟到下次DOM更新循环之后执行,在修改数据之后立即使用它,然后等待DOM更新。
        this.$nextTick(() => {
          //获取树菜单的节点数据
          let nodes = this.$refs.assignTree.children
          //设置子节点
          this.setChild(nodes, checkList)
        })
      }
      //显示窗口
      this.assignDialog.visible = true
      //设置窗口标题
      this.assignDialog.title = `给【${row.roleName}】分配权限`
    },
    /**
     * 设置子节点
     */
    setChild(childNodes, checkList) {
      //判断是否存在子节点
      if (childNodes && childNodes.length > 0) {
        //循环所有权限
        for (let i = 0; i < childNodes.length; i++) {
          //根据 data 或者 key 拿到 Tree 组件中的 node
          let node = this.$refs.assignTree.getNode(childNodes[i])
          //判断是否已经拥有对应的角色数据
          if (checkList && checkList.length > 0) {
            //循环遍历已有的权限集合
            for (let j = 0; j < checkList.length; j++) {
              //找到已经存在的菜单权限节点
              if (checkList[j] == childNodes[i].id) {
                //如果节点是展开状态，则将树节点选中
                if (childNodes[i].open) {
                  this.$refs.assignTree.setChecked(node, true)
                  break
                }
              }
            }
          }
          //如果存在子节点，则递归选中
          if (childNodes[i].children) {
            this.setChild(childNodes[i].children, checkList)
          }
        }
      }
    }, /**
     * 分配权限窗口取消事件
     */
    onAssignClose() {
      this.assignDialog.visible = false
    },
    /**
     * 分配权限窗口确认事件
     */
    async onAssignConfirm() {
      //获取选中的节点key
      let ids = this.$refs.assignTree.getCheckedKeys()
      //获取选中节点的父节点id
      let pids = this.$refs.assignTree.getHalfCheckedKeys()
      //组装选中的节点ID数据
      let listId = ids.concat(pids)
      //组装参数
      let params = {
        roleId: this.roleId,
        list: listId
      }
      //发送请求
      let res = await roleApi.assignSaveApi(params)
      //判断是否成功
      if (res.success) {
        //关闭窗口
        this.assignDialog.visible = false
        //提示成功
        this.$message.success(res.message)
      } else {
        //提示失败
        this.$message.error(res.data)
      }
    },
    /**
     * 打开添加窗口
     */
    openAddWindow() {
      if(this.hasPermission('sys:role:add')){
        //清空表单数据
        this.$resetForm('roleForm', this.role)
        this.roleDialog.title = '新增角色'//设置窗口标题
        this.roleDialog.visible = true//显示窗口
      }else{
        this.$message.warning("没有操作权限,请联系管理员")
      }

    },
    /**
     * 窗口取消事件
     */
    onClose() {
      this.roleDialog.visible = false
    },
    /**
     * 窗口确认事件
     */
    onConfirm() {
      //表单验证
      this.$refs.roleForm.validate(async(valid) => {
        if (valid) {
          let res = null
          //判断角色ID是否为空
          if (this.role.id === '') {
            //新增
            //发送添加请求
            res = await roleApi.addRoleApi(this.role)
          } else {
            //发送修改请求
            res = await roleApi.updateRoleApi(this.role)
          }
          //判断是否成功
          if (res.success) {
            this.$message.success(res.message)
            //刷新
            this.search(this.pageNo, this.pageSize)
            //关闭窗口
            this.roleDialog.visible = false
          } else {
            this.$message.error(res.message)
          }
        }
      })
    },
    /**
     * 查询
     */
    async search(pageNo = 1, pageSize = 10) {
      this.searchModel.pageNo = pageNo//当前页码
      this.searchModel.pageSize = pageSize//每页显示数量
      //发送查询请求
      let res = await roleApi.getRoleListApi(this.searchModel)
      //执行成功
      if (res.success) {
        //角色列表
        this.roleList = res.data.records
        //总数量
        this.total = res.data.total
      }
    },
    /**
     * 打开编辑窗口
     */
    handleEdit(row) {
      if(this.hasPermission('sys:role:edit')){
        //数据回显
        this.$objCopy(row, this.role) //将当前编辑的数据复制到role对象中
        //设置窗口标题
        this.roleDialog.title = '编辑角色'
        //显示编辑角色窗口
        this.roleDialog.visible = true
      }else{
        this.$message.warning("没有操作权限,请联系管理员")
      }

    },
    /**
     * 打开删除窗口
     */
    async handleDelete(row) {
      if(this.hasPermission('sys:role:delete')) {
        // 判断该角色下是否有用户
        let res = await roleApi.checkRoleAPi({ id: row.id });
        // //判断是否可以删除
        if (!res.success) {
          //提示不能删除
          this.$message.warning(res.message)
        } else {
          //确认是否删除
          let confirm = await this.$myconfirm("确定要删除该数据吗?");
          if (confirm) {
            //发送删除请求
            let res = await roleApi.deleteById({ id: row.id });
            //判断是否成功
            if (res.success) {
              //成功提示
              this.$message.success(res.message);
              //刷新
              this.search();
            } else {
              //失败提示
              this.$message.error(res.message);
            }
          }
        }
      }else{
        this.$message.warning("没有操作权限,请联系管理员")
      }
    },
    /**
     * 重置查询条件
     */
    resetValue() {
      //清空查询条件
      this.searchModel.roleName = ''
      //重新查询
      this.search()
    },
    /**
     * 当每页数量发生变化时触发该事件
     */
    handleSizeChange(val) {
      const {page, limit} = val
      this.pageSize = limit
      this.search(page, limit)
    },
    /**
     * 当页码发生变化时触发该事件
     */
    handleCurrentChange(val) {
      const {page, limit} = val
      this.pageNo = page
      this.search(page, limit)
    }
  }
  ,
  created() {
    //调用查询角色列表的方法
    this.search()
  }
  ,
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 220
    })
  }
}
</script>

<style scoped>
.pagination-container{
  margin: 0;
  padding: 0 !important;
  text-align: center;
}
</style>
