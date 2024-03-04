<template>
  <div class="app-container">


      <right-toolbar :showSearch.sync="showSearch" @queryTable="getApplyForVoList" ></right-toolbar>
    <el-table v-loading="loading" :data="voList">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="申请单号" align="center" prop="num" />
      <el-table-column label="申请人" align="center" prop="name" />
      <el-table-column label="申请时间" align="center" prop="created" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.created }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请原因" align="center" prop="reason" />
      <el-table-column label="审核状态" align="center" prop="status" >
        <template v-slot="scope">
          <el-tag v-if="scope.row.status==0">同意</el-tag>
          <el-tag v-if="scope.row.status==1">拒绝</el-tag>
          <el-tag v-if="scope.row.status==2">未审核</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="当前任务ID" align="center" prop="taskId" />
      <el-table-column label="当前步骤" align="center" prop="taskName" />
      <el-table-column label="当前操作人" align="center" prop="assignee" />
      <el-table-column label="当前流程ID" align="center" prop="instanceId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">

          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="openComplete(scope.row)"
          >审批</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="genzong(scope.row)"
          >流程跟踪</el-button>

        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getApplyForVoList"
    />

<!--    审批弹框-->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      >
      <el-form :model="completeForm">
        <el-form-item label="审批意见" :label-width="formLabelWidth">
          <el-input v-model="completeForm.recordText" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>

    <el-button @click="dialogVisible = false"
               size="mini"
               type="text"
               icon="el-icon-edit"
    >取 消</el-button>
      <el-button
        size="mini"
        type="text"
        icon="el-icon-edit"
        @click="completeActiviti()"
      >同意</el-button>
      <el-button
        size="mini"
        type="text"
        icon="el-icon-edit"
        @click="completeActivitiOne()"
      >拒绝</el-button>

    </el-dialog>

  </div>
</template>

<script>
import {listVo, getVo, delVo, addVo, updateVo, getVoList, complete} from "@/api/system/applyForVo";
import qs from 'qs';
export default {
  name: "Vo",
  data() {
    return {
      completeForm:{},
      formLabelWidth :"120px",
      //审批弹框的状态
      dialogVisible: false,
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
      voList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        cropInfoId: null,
        recordTime: null,
        temperature: null,
        arowths: null,
        humidness: null,
        illumination: null,
        pic: null,
        operator: null,
      },

    };
  },
  created() {
    this.getApplyForVoList();
  },
  methods: {

    /**
     * 查询当前流程
     */
    genzong(row){
      console.log(row.instanceId)
      //注意：一定要把流程实例id传过去
      location.href="http://localhost:8089/activiti/image?processInstanceId="+row.instanceId;
    },

    /**
     * 打开审批弹框
     */
    openComplete(vv){
      this.dialogVisible =true;
      this.completeForm = vv;
    },
    /**
     * 同意审批流程定义
     */
    completeActiviti(){
     this.completeForm.flag = 1;
      complete(this.completeForm).then(r=>{
        this.getApplyForVoList();
        this.dialogVisible = false;
      })
    },
    /**
     * 拒绝审批流程定义
     */
    completeActivitiOne(){
      this.completeForm.flag  = 2;
      complete(this.completeForm).then(r=>{
        this.getApplyForVoList();
        this.dialogVisible = false;
      })
    },


    getApplyForVoList(){
      let username = sessionStorage.getItem("username");
      getVoList(username).then(r=>{
        this.voList = r.data;
        this.total = r.total;
        this.loading = false;
      })
    }

  }

};
</script>
