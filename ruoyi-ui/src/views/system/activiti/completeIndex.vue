<template>
  <div>
    <el-table v-loading="loading" :data="rocordList" @selection-change="getList">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="审批单号" align="center" prop="id" />
      <el-table-column label="职位" align="center" prop="position" />
      <el-table-column label="审批人" align="center" prop="recordName" />
      <el-table-column label="审批时间" align="center" prop="created" width="180">
        <template slot-scope="scope">
          <span>{{scope.row.created}}</span>
        </template>
      </el-table-column>
      <el-table-column label="审批内容" align="center" prop="recordText" />
      <el-table-column label="审批结果" align="center" prop="status" >
        <template v-slot="scope">
          <el-tag v-if="scope.row.status ===1">同意</el-tag>
          <el-tag type="danger" v-if="scope.row.status ===2">拒绝</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="申请单号" align="center" prop="applyForNum" />
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
<!--        <template slot-scope="scope">-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['system:rocord:edit']"-->
<!--          >修改</el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['system:rocord:remove']"-->
<!--          >删除</el-button>-->
<!--        </template>-->
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import {getApplyRocordList} from "@/api/system/applyForVo";

export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 【请填写功能名称】表格数据
      rocordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        position: null,
        recordName: null,
        created: null,
        recordText: null,
        status: null,
        applyForNum: null,
        userId: null,
        username: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    }
  },
  methods: {
    getList(){
      let username = sessionStorage.getItem("username");
      this.queryParams.recordName = username;
      getApplyRocordList(this.queryParams).then(r=>{
        this.loading = false;
        if(r.code === 200){
          this.rocordList =r.data.list;
          this.total = r.data.total;
        }else{
          this.$message.error(r.msg)
        }
      })
    }

  },
  created() {
    this.getList();
  }
}
</script>

<style scoped>

</style>
