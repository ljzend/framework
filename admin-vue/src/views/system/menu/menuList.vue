<template>
  <el-main>
    <!-- 新增按钮 -->
    <el-form
      ref="searchForm"
      label-width="80px"
      :inline="true"
      size="small"
    >
      <el-form-item>
        <el-input v-model="searchModel.label" placeholder="请输入菜单名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
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
      style="margin-top: 10px"
      :height="tableHeight"
      :data="menuList"
      row-key="id"
      :tree-props="{ children: 'children' }"
      :border="true"
      stripe
    >
      <el-table-column prop="label" label="菜单名称" min-width="100px"></el-table-column>
      <el-table-column prop="type" label="菜单类型" align="center" min-width="50px">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type == '0'" size="normal">目录</el-tag>
          <el-tag type="success" v-else-if="scope.row.type == '1'" size="normal">
            菜单
          </el-tag>
          <el-tag type="warning" v-else-if="scope.row.type == '2'" size="normal">
            按钮
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="icon" label="图标" align="center" min-width="50px">
        <template slot-scope="scope">
          <i :class="scope.row.icon" v-if="scope.row.icon.includes('el-icon')">
          </i>
          <svg-icon v-else :icon-class="scope.row.icon"></svg-icon>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="路由名称" min-width="50px" align="center"></el-table-column>
      <el-table-column prop="path" label="路由地址" min-width="100px" align="center"></el-table-column>
      <el-table-column prop="url" label="组件路径" min-width="100px" align="center"></el-table-column>
      <el-table-column align="center" label="操作" min-width="100px">
        <template slot-scope="scope">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="small"
            @click="editMenu(scope.row)"
          >编辑
          </el-button
          >
          <el-button
            type="danger"
            size="small"
            icon="el-icon-delete"
            @click="deleteMenu(scope.row)"
          >删除
          </el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增或编辑弹框 -->
    <system-dialog
      :title="menuDialog.title"
      :width="menuDialog.width"
      :height="menuDialog.height"
      :visible="menuDialog.visible"
      @onClose="onClose"
      @onConfirm="onConfirm"
    >
      <div slot="content">
        <el-form
          :model="menu"
          ref="menuForm"
          :rules="rules"
          label-width="80px"
          :inline="true"
          size="small"
        >
          <el-row>
            <el-col :span="24">
              <el-form-item prop="type" label="菜单类型">
                <el-radio-group v-model="menu.type">
                  <el-radio :label="0">目录</el-radio>
                  <el-radio :label="1">菜单</el-radio>
                  <el-radio :label="2">按钮</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item prop="parentName" size="small" label="所属菜单">
            <el-input
              @click.native="selectParentMenu"
              v-model="menu.parentName"
              :readonly="true"
            ></el-input>
          </el-form-item>
          <el-form-item prop="label" size="small" label="菜单名称">
            <el-input v-model="menu.label"></el-input>
          </el-form-item>
          <el-form-item size="small" label="菜单图标">
            <my-icon @selecticon="setIcon" ref="child"></my-icon>
          </el-form-item>
          <el-form-item
            prop="name"
            v-if="menu.type === 1"
            size="small"
            label="路由名称"
          >
            <el-input v-model="menu.name"></el-input>
          </el-form-item>
          <el-form-item
            prop="path"
            v-if="menu.type !== 2"
            size="small"
            label="路由地址"
          >
            <el-input v-model="menu.path"></el-input>
          </el-form-item>
          <el-form-item
            prop="url"
            v-if="menu.type === 1"
            size="small"
            label="组件路径"
          >
            <el-input v-model="menu.url"></el-input>
          </el-form-item>
          <el-form-item prop="code" size="small" label="权限字段">
            <el-input v-model="menu.code"></el-input>
          </el-form-item>
          <el-form-item size="small" label="菜单序号">
            <el-input v-model="menu.orderNum"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </system-dialog>

    <!-- 选择所属菜单弹框 -->
    <system-dialog
      :title="parentDialog.title"
      :width="parentDialog.width"
      :height="parentDialog.height"
      :visible="parentDialog.visible"
      @onClose="onParentClose"
      @onConfirm="onParentConfirm"
    >
      <div slot="content">
        <el-tree
          style="font-size: 14px"
          ref="parentTree"
          :data="parentMenuList"
          node-key="id"
          :props="defaultProps"
          empty-text="暂无数据"
          :show-checkbox="false"
          :highlight-current="true"
          :expand-on-click-node="false"
          @node-click="handleNodeClick"
        >
          <div class="customer-tree-node" slot-scope="{ node, data }">
            <!-- 长度为0说明没有下级 -->
            <span v-if="data.children.length === 0">
              <i class="el-icon-document" style="color: #8c8c8c; font-size: 18px"/>
            </span>
            <span v-else @click.stop="openBtn(data)">
              <svg-icon v-if="data.open" icon-class="add-s"/>
              <svg-icon v-else icon-class="sub-s"/>
            </span>
            <span style="margin-left: 3px">{{ node.label }}</span>
          </div>
        </el-tree>
      </div>
    </system-dialog>
  </el-main>
</template>

<script>
//导入menu.js脚本代码
import menuApi from '@/api/menu'
//导入对话框组件
import SystemDialog from '@/components/system/SystemDialog.vue'
import MyIcon from '@/components/system/MyIcon'

export default {
  name: 'menuList',
  components: { SystemDialog, MyIcon },
  data() {
    return {
      searchModel: {
        label: '' // 菜单名称
      }, // 查询条件
      menuList: [], //菜单列表
      tableHeight: 0,//表格高度
      //新增或编辑弹框属性
      menuDialog: {
        title: '',
        width: 630,
        height: 270,
        visible: false
      },
      //菜单属性
      menu: {
        id: '',
        type: '',
        parentId: '',
        parentName: '',
        label: '',
        icon: '',
        name: '',
        path: '',
        url: '',
        code: '',
        orderNum: ''
      },
      rules: {
        type: [{ required: true, trigger: 'change', message: '请选择菜单类型' }],
        parentName: [{ required: true, trigger: 'change', message: '请选择所属菜单' }],
        label: [{ required: true, trigger: 'blur', message: '请输入菜单名称' }],
        name: [{ required: true, trigger: 'blur', message: '请输入路由名称' }],
        path: [{ required: true, trigger: 'blur', message: '请输入路由路径' }],
        url: [{ required: true, trigger: 'blur', message: '请输入组件路径' }],
        code: [{ required: true, trigger: 'blur', message: '请输入权限字段' }]
      },
      //上级菜单弹框属性
      parentDialog: {
        title: '选择所属菜单',
        width: 280,
        height: 450,
        visible: false
      },
      //树属性定义
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      parentMenuList: [] //所属菜单列表
    }
  },
  methods: {
    /**
     * 查询菜单列表
     */
    async search() {
      //发送查询请求
      let res = await menuApi.getMenuList(this.searchModel)
      //判断是否成功
      if (res.success) {
        //赋值
        this.menuList = res.data
      }
    },
    resetValue(){
      this.searchModel.label = ''
      this.search()
    },
    /**
     * 打开添加窗口
     */
    openAddWindow() {
      if(this.hasPermission('sys:menu:add')){
        this.$resetForm('menuForm', this.menu) //清空表单数据
        this.menuDialog.title = '新增菜单' //设置窗口标题
        this.menuDialog.visible = true //显示窗口
        this.$nextTick(() => {
          this.$refs['child'].userChooseIcon = ''//清空菜单图标
        })
      }else{
        this.$message.warning("没有操作权限,请联系管理员")
      }

    },
    /**
     * 添加和修改菜单窗口关闭事件
     */
    onClose() {
      this.menuDialog.visible = false //关闭窗口
    },
    /**
     * 添加和修改菜单窗口确认事件
     */
    onConfirm() {
      //表单验证
      this.$refs.menuForm.validate(async(valid) => {
        if (valid) {
          let res = null
          //判断菜单ID是否为空
          if (this.menu.id === '') {
            //发送添加请求
            res = await menuApi.addMenu(this.menu)
          } else {
            //发送修改请求
            res = await menuApi.updateMenu(this.menu);
          }
          //判断是否成功
          if (res.success) {
            this.$message.success(res.message)
            //刷新
            await this.search()
            //关闭窗口
            this.menuDialog.visible = false
          } else {
            this.$message.error(res.message)
          }
        }
      })
    },
    /**
     * 选择所属菜单
     */
    async selectParentMenu() {
      //显示窗口
      this.parentDialog.visible = true
      //发送查询请求
      let res = await menuApi.getParentMenuList()
      //判断是否成功
      if (res.success) {
        //赋值
        this.parentMenuList = res.data
      }
    },
    /**
     * 选择所属菜单取消事件
     */
    onParentClose() {
      this.parentDialog.visible = false //关闭窗口
    },
    /**
     * 选择所属菜单确认事件
     */
    onParentConfirm() {
      this.parentDialog.visible = false //关闭窗口
    },
    /**
     * 切换图标
     * @param data
     */
    openBtn(data) {
      data.open = !data.open
      this.$refs.parentTree.store.nodesMap[data.id].expanded = !data.open
    },
    /**
     * 所属菜单节点点击事件
     */
    handleNodeClick(data) {
      if(this.menu.id === data.id){
        this.$message.warning("选中项不可用，请选择其它项!");
        return;
      }
      //所属父级菜单ID
      this.menu.parentId = data.id
      //所属父级菜单名称
      this.menu.parentName = data.label
    },
    /**
     * 编辑菜单
     * @param row
     */
    editMenu(row) {
      if(this.hasPermission('sys:menu:edit')){
        //把当前要编辑的数据复制到数据域，给表单回显
        this.$objCopy(row, this.menu);
        //设置弹框属性
        this.menuDialog.title = "编辑菜单";
        this.menuDialog.visible = true;
        this.$nextTick(() => {
          this.$refs["child"].userChooseIcon = row.icon;//菜单图标回显
        })
      }else {
        this.$message.warning("没有操作权限,请联系管理员")
      }

    },
    /**
     * 删除菜单
     * @param row
     */
    async deleteMenu(row) {
      if(this.hasPermission('sys:menu:delete')) {
        //判断是否存在子菜单
        let result = await menuApi.checkPermission({ id: row.id })
        //判断是否可以删除
        if (!result.success) {
          //提示不能删除
          this.$message.warning(result.message);
        } else {
          //确认是否删除
          let confirm = await this.$myconfirm("确定要删除该数据吗?");
          if (confirm) {
            //发送删除请求
            let res = await menuApi.deleteById({ id: row.id });
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
     * 获取图标列表
     */
    //给icon绑定的点击事件
    setIcon(icon) {
      this.menu.icon = icon
    }
  },
//初始化时调用
  created() {
    //调用查询菜单列表的方法
    this.search()
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 180
    })
  }
}
</script>

<style scoped lang="scss">
.iconList {
  width: 400px;
  height: 300px;
  overflow-y: scroll;
  overflow-x: hidden;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;

  i {
    display: inline-block;
    width: 60px;
    height: 45px;
    color: #000000;
    font-size: 20px;
    border: 1px solid #e6e6e6;
    border-radius: 4px;
    cursor: pointer;
    text-align: center;
    line-height: 45px;
    margin: 5px;

    &:hover {
      color: orange;
      border-color: orange;
    }
  }
}

.chooseIcons {
  width: 175px;
  background-color: #FFFFFF;
  background-image: none;
  border-radius: 4px;
  border: 1px solid #DCDFE6;
  box-sizing: border-box;
  color: #606266;
  display: inline-block;
  font-size: inherit;
  height: 33px;
  line-height: 25px;
  outline: none;
  padding: 0 15px;
  transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
}
</style>
