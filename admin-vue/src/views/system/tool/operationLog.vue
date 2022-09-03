<template>
  <!-- 表格数据 -->
  <el-main>
    <!-- 查询条件 -->
    <el-form
      :model="searchModel" ref="searchForm"
      label-width="80px"
      :inline="true"
      size="small"
    >
      <el-form-item>
        <el-input v-model.trim="searchModel.operationName" placeholder="请输入操作名"/>
      </el-form-item>
      <el-form-item>
        <el-input v-model.trim="searchModel.userName" placeholder="请输入操作用户名"/>
      </el-form-item>
      <el-form-item>
        <el-button
          icon="el-icon-search"
          type="primary"
          @click="search(pageNo, pageSize)"
        >
          查询
        </el-button>
        <el-button
          icon="el-icon-delete"
          @click="resetValue()"
        >
          重置
        </el-button>
      </el-form-item>
    </el-form>
    <!-- 用户表格数据 -->
    <el-table
      :height="tableHeight"
      :data="logList"
      :default-sort="{prop: 'date', order: 'descending'}"
      border
      stripe
      style="width: 100%; margin-bottom: 10px"
    >
      <el-table-column prop="id" label="日志编号" align="center"/>
      <el-table-column prop="userName" label="操作用户" align="center"/>
      <el-table-column prop="operationName" label="操作名称" align="center"/>
      <el-table-column prop="requestUri" label="请求路径" align="center"/>
      <el-table-column prop="methodType" label="请求方式" align="center"/>
      <el-table-column prop="code" label="响应状态码" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.code === 200" size="normal">成功</el-tag>
          <el-tag v-else type="warning" size="normal">失败</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" align="center" sortable show-overflow-tooltip/>
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-link type="success" @click="openWindow(scope.row)">
            <i class="el-icon-view el-icon--right"/> 查 看
          </el-link>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页工具栏 -->
    <Pagination
      :total="total"
      :page="pageNo"
      :limit="pageSize"
      :pageSizes="[5,10,15,20,25,30,35,40,45,50]"
      @handleSizeChange="handleSizeChange"
      @handleCurrentChange="handleCurrentChange"
    ></Pagination>

    <el-dialog title="操作日志详细" :visible.sync="logDialog.visible" width="700px" append-to-body>
      <el-form ref="logForm" :model="logForm" label-width="100px" size="mini">
        <el-row>
          <el-col :span="12">
            <el-form-item label="操作名称：">{{ logForm.operationName }}</el-form-item>
            <el-form-item
              label="登录信息："
            >{{ logForm.userName }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请求地址：">{{ logForm.requestUri }}</el-form-item>
            <el-form-item label="请求方式：">{{ logForm.methodType }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="操作方法：">{{ logForm.methodName }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="请求参数：">{{ logForm.requestParam }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="返回参数：">{{ logForm.responseParam }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作状态：">
              <div v-if="logForm.code === 200">正常</div>
              <div v-else>失败</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作时间：">{{ logForm.createTime }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="异常信息：" v-if="logForm.code !== 200">{{ logForm.exceptionMsg }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="logDialog.visible = false">关 闭</el-button>
      </div>
    </el-dialog>
  </el-main>
</template>

<script>
import Pagination from '@/components/Pagination/index'
import logApi from '@/api/log'
import SystemDialog from '@/components/system/SystemDialog'

export default {
  name: 'operationLog',
  components: { SystemDialog, Pagination },
  data() {
    return {
      searchModel: {
        operationName: '',
        userName: '',
        pageNo: 1,
        pageSize: 10
      },
      pageNo: 1,
      pageSize: 10,
      total: 0, //总数量
      tableHeight: 0, //表格高度
      logList: [],
      logDialog: {
        title: '',
        visible: false,
        height: 450,
        width: 300
      },
      logForm: {
        id: '',
        userName: '',
        operationName: '',
        requestUri: '',
        methodType: '',
        requestParam: '',
        methodName: '',
        code: '',
        responseParam: '',
        exceptionMsg: '',
        createTime: ''
      }
    }
  },

  methods: {
    /**
     * 查询日志列表
     */
    async search(pageNo = 1, pageSize = 10) {
      this.searchModel.pageNo = pageNo
      this.searchModel.pageSize = pageSize
      console.log('111')
      let res = await logApi.getLogList(this.searchModel)
      if (res.success) {
        console.log('222')
        this.logList = res.data.records
        //总数量
        this.total = res.data.total
      }
    },
    /**
     * 重置按钮
     */
    resetValue() {
      this.searchModel = {}
      this.search(this.pageNo, this.pageSize)
    },
    /**
     * 打开查看窗口
     */
    openWindow(row) {
      //数据回显
      this.$objCopy(row, this.logForm) //将当前编辑的数据复制到role对象中
      //设置窗口标题
      this.logDialog.title = '操作日志详细'
      //显示编辑角色窗口
      this.logDialog.visible = true
    },
    onClose() {
    },
    /**
     * 删除
     */
    handleDelete() {
    },
    /**
     * 每页大小改变
     */
    handleSizeChange(val) {
      const { page, limit } = val
      this.pageSize = limit
      this.search(page, limit)
    },
    /**
     * 当前页改变
     */
    handleCurrentChange(val) {
      const { page, limit } = val
      this.pageNo = page
      this.search(page, limit)
    }
  },

  created() {
    this.search()
  },

  mounted() {
    this.$nextTick(() => {
      //表格高度
      this.tableHeight = window.innerHeight - 220
    })
  }
}
</script>

<style scoped>
.pagination-container {
  margin: 0;
  padding: 0 !important;
  text-align: center;
}
</style>
