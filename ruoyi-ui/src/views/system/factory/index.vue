<template>
  <div class="app-container">
    <el-form :model="queryFarams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="工厂名称" prop="name">
        <el-input
          v-model="queryFarams.name"
          placeholder="请输入工厂"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryFarams.status" placeholder="状态" clearable>
            <el-option label="正常" value="1"></el-option>
            <el-option label="停用" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:dept:add']"
        >新增</el-button>
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-download"
            size="mini"
            @click="exportFactory"
            v-hasPermi="['system:dept:export']"
          >导出</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-view"
            size="mini"
            @click="baobiao"
            v-hasPermi="['system:dept:export']"
          >报表</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-upload
            class="upload-demo"
            drag
            :action=field101Action
            multiple>
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          </el-upload>
        </el-col>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!--分页-->
    <div>
    <el-table
      :data="tableData"
      v-if="refreshTable"
      v-loading="loading"
      row-key="id"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column prop="id" label="工厂编号" width="200"></el-table-column>
      <el-table-column prop="name" label="工厂名称" width="200"></el-table-column>
      <el-table-column prop="address" label="地址" width="200"></el-table-column>
      <el-table-column  label="状态" width="100">
        <template slot-scope="scope">
          <span>{{scope.row.status == 1 ? '正常': '停用'}}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="200">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="legalPerson" label="法人" width="200"></el-table-column>
      <el-table-column prop="personNumber" label="工程人数" width="100"></el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:dept:edit']"
          >修改</el-button>
          <el-button
            v-if="scope.row.id != 0"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:dept:remove']"
          >删除</el-button>
          <el-button
            v-if="scope.row.id != 0"
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleDeleteLook(scope.row)"
            v-hasPermi="['system:dept:remove']"
          >添加农作物</el-button>
          <el-button
            v-if="scope.row.id != 0"
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="trackLook(scope.row)"
            v-hasPermi="['system:dept:remove']"
          >跟踪记录</el-button>
        </template>
      </el-table-column>
    </el-table>
      <el-pagination
        @current-change="handleCurrentChange1"
        :page-size="queryFarams.pageSize"
        :current-page="queryFarams.pageNum"
        layout="prev, pager, next"
        align="center"
        :total="totals">
      </el-pagination>
    </div>
    <!--添加或修改部门对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="地址" prop="address">
              <el-cascader
                v-model="value"
                :options="options"
                @change="changeCity"></el-cascader>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="创建时间" prop="createTime">
          <el-date-picker
            v-model="form.createTime"
            style="width: 240px"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetime"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="工厂名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入工厂名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="法人" prop="legalPerson">
              <el-input v-model="form.legalPerson" placeholder="请输入法人" maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="工程人数" prop="personNumber">
              <el-input v-model="form.personNumber" placeholder="请输入工程人数" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" >
              <el-radio-group v-model="form.status">
                <el-radio v-model="form.status" :label="1">正常</el-radio>
                <el-radio v-model="form.status" :label="2">停用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <div>
      <!-- 工厂加工跟踪记录表-->
      <el-dialog :title="title" :visible.sync="one" width="1000px" append-to-body>
        <div>
          <el-table
            :data="tableDataTwo"
            v-if="refreshTable"
            v-loading="loading"
            row-key="id"
            :default-expand-all="isExpandAll"
            :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
          >
            <el-table-column prop="id" label="记录编号" width="100"></el-table-column>
            <el-table-column prop="processingPlantName" label="加工工厂名称" width="100"></el-table-column>
            <el-table-column prop="recordingPersonnelName" label="记录人员名称" width="100"></el-table-column>
            <el-table-column prop="operatorName" label="操作人员名称" width="100"></el-table-column>
            <el-table-column prop="processTime" label="加工时间" width="100"></el-table-column>
            <el-table-column prop="processContext" label="加工内容" width="100"></el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  v-if="scope.row.id != 0"
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDeleteRecords(scope.row)"
                  v-hasPermi="['system:dept:remove']"
                >删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <el-pagination
          @current-change="handleCurrentChange"
          :page-size="queryForm.pageSize"
          :current-page="queryForm.pageNum"
          layout="prev, pager, next"
          align="center"
          :total="total">
        </el-pagination>
      </el-dialog>
    </div>
    <!--添加或修改记录对话框 -->
    <el-dialog :title="title" :visible.sync="two" width="600px" append-to-body>
      <el-form ref="formRecords" :model="formRecords" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="内容" prop="processContext">
              <el-input v-model="formRecords.processContext" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="产品名称" prop="agriculturalProductName">
              <el-input v-model="formRecords.agriculturalProductName" placeholder="请输入产品名称" maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="工厂名称" prop="processingPlantName">
              <el-input v-model="formRecords.processingPlantName" placeholder="请输入工厂名称" maxlength="50" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="操作人员" prop="operatorName">
              <el-input v-model="formRecords.operatorName" placeholder="请输入操作人员" maxlength="50" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormRecords">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!--添加农作物-->
    <div>
     <el-dialog :title="title" :visible.sync="Three" width="600px" append-to-body>
       <el-form ref="formRecords" :model="form" :rules="rules" label-width="80px">
         <el-row>
           <el-col :span="12">
             <el-form-item label="农作物">
               <el-checkbox-group v-model="actorArr">
                 <el-checkbox :key="a.productId" v-for="a in actorList" :label="a.productId">{{a.productName}}</el-checkbox>
               </el-checkbox-group>
             </el-form-item>
           </el-col>
         </el-row>
         <el-row>
           <el-col :span="12">
             <el-form-item label="操作人">
               <el-select v-model="operatorId">
                 <el-option v-for="item in dataList" :key="item.id" :value="item.id" :label="item.name"/>
               </el-select>
             </el-form-item>
           </el-col>
         </el-row>
       </el-form>
       <div slot="footer" class="dialog-footer">
         <el-button type="primary" @click="submitCheckBox(form)">加工</el-button>
         <el-button @click="cancelThree">取 消</el-button>
       </div>
     </el-dialog>
    </div>
    <div>
      <el-dialog :title="title" :visible.sync="Six" width="1500px" modal-append-to-body>
        <!--报表-->
        <div>
          <template>
            <div id="bar-chart" style="width: 1400px; height: 300px"></div>
          </template>
        </div>
      </el-dialog>
    </div>


  </div>
</template>

<script>
import {
  listDept,
  getDept,
  delDept,
  addDept,
  updateDept,
  listDeptTwo,
  AddRecords,
  updateDeptRecords,
  delDeptRecords,
  city,
  cityList,
  getCityIds,
  ActorList,
  dataLists,
  exportFactory,
  fileS3Upload,
  report
} from "@/api/system/factory";
import * as XLSX from 'xlsx/xlsx.mjs'
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import log from "@/views/monitor/job/log";
import data from "@/views/system/dict/data";
import * as echarts from "echarts";
var that = this;
export default {
  props: [],
  name: "Dept",
  dicts: ['sys_normal_disable'],
  components: { Treeselect },
  data() {
    return {
      // 定义图表，各种参数
      msg: "柱状图",
      datay: [],
      datax: [],

      /**导入*/
      formData: {
        field101: null,
      },
      field101Action:"http://192.168.137.87:9204/processingPlant/importData",
      field101fileList: [],
      recordId:'',
      /**下拉框*/
      dataList:[],
      operatorId:'',
      /**复选框*/
      actorArr:[],
      actorList:[],
      parentId:0,
      value:[],
      options: [],
      /**遮罩层*/
      loading: true,
      tableData:[],
      tableDataTwo:[],
      /**显示搜索条件*/
      showSearch: true,
      /**表格树数据*/
      // deptList: [],
      /**部门树选项*/
      deptOptions: [],
      /**弹出层标题*/
      title: "",
      /**是否显示弹出层*/
      open: false,
      one: false,
      two: false,
      Three:false,
      Five:false,
      Six:false,
      /**是否展开，默认全部展开*/
      isExpandAll: true,
      /**重新渲染表格状态*/
      refreshTable: true,
      /**查询参数*/
      queryParams: {
        name: undefined,
        status: undefined
      },
      /**分页*/
      total:0,
      totals:0,
      queryFarams:{
        name:"",
        status:null,
        pageNum:1,
        pageSize:6,
      },
      queryForm: {
        pageNum:1,
        pageSize:6
      },
      /**表单参数*/
      form: {
        status:0,
        productIds:[],
        processingPlantId:'',
        processingPlantName:'',
        operatorId:'',

      },
      formRecords:{},
      /**表单校验*/
      rules: {
        personNumber:[
          { required: true, message: "人数不能为空", trigger: "blur" }
        ],
        legalPerson:[
          { required: true, message: "法人不能为空", trigger: "blur" }
        ],
        createTime:[
          { required: true, message: "时间不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "工厂名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  computed: {},

  created() {
    this.getList();
    this.changeCityList();
  },
  watch: {},
  mounted: function () {

  },
  methods: {
    getdataList(id){
      dataLists(id).then(response=>{
        this.dataList=response.data;
      })
    },
    getActorList(id){
      ActorList(id).then(response=>{
        this.actorList=response.data;
      })
    },
    /** 分页 */
      handleCurrentChange(res){
      this.queryForm.pageNum=res;
      this.getListTwo(this.recordId);
    },

    handleCurrentChange1(res){
      this.queryFarams.pageNum=res;
      this.getList();
    },
    /** 查询部门列表 */
    getList() {
      this.loading = true;
      listDept(this.queryFarams,this.name).then(response => {
        this.tableData=response.data.list
        this.totals=response.data.total
        this.loading = false;
      });
    },
    getListTwo(id) {
      this.loading = true;
      this.queryForm.processingPlantId = id
      listDeptTwo(this.queryForm).then(response => {
        this.tableDataTwo=response.data.list
        this.total=response.data.total
        this.loading = false;
      });
    },
    /** 转换部门数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.name,
        children: node.children
      };
    },



    /**取消工厂按钮*/
    cancel() {
      this.open = false;
      this.reset();
    },
    /**取消记录按钮*/
    cancelThree() {
      this.Three = false;
      this.reset();
    },
    /**表单重置*/
    reset() {
      this.form = {
        id:undefined,
        version:undefined,
        name:undefined,
        address:undefined,
        creareTime:undefined,
        legalPerson:undefined,
        personNumber:undefined,
        status: "0"
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增工厂按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工厂";
      listDept(this.form).then(response => {
        if (response.data===200){
          this.$message.success("添加成功")
        }
      });
    },
    baobiao(){
      this.Six = true;
      this.title = "查看报表";
      this.reportArr();
    },
    /**查看农作物*/
    handleDeleteLook(row) {
      this.getActorList(row.id);
      this.getdataList(row.id);
      this.reset();
      this.form.processingPlantId = row.id;
      this.form.processingPlantName = row.name
      this.Three = true;
      this.title = "查看";
    },
    /**查看跟踪记录*/
    trackLook(row) {
      this.reset();
      this.recordId = row.id;
      this.one = true;
      this.title = "查看跟踪记录";
      this.getListTwo(row.id);
    },
    /**导入工厂*/
    handleImport(){

    },
    /**导出工厂*/
    exportFactory(){
      exportFactory(this.queryFarams).then(res => {
        const content = res
        const blob = new Blob([content], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' })
        const fileName =  '加工工厂.xlsx' // 导出文件名称自定义
        if ('download' in document.createElement('a')) {
          // 非IE下载
          const elink = document.createElement('a')
          elink.download = fileName
          elink.style.display = 'none'
          elink.href = URL.createObjectURL(blob)
          document.body.appendChild(elink)
          elink.click()
          URL.revokeObjectURL(elink.href) // 释放URL 对象
          document.body.removeChild(elink)
        } else {
          // IE10+下载
          navigator.msSaveBlob(blob, fileName)
        }
      });
    },
    /**下拉框*/
    changeCityList(){
      cityList().then(response =>{
        this.options=response.data;
      })
    },
    /**城市三家联动下拉框*/
    changeCity(){
      city(this.parentId).then(response =>{
        this.options=response.data;
      })
    },
    /** 修改修改工厂按钮操作 */
    handleUpdate(row) {
      this.reset();
      getCityIds(row.id).then(response => {
        this.value = response;
      });
      getDept(row.id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改工厂"
      });
    },
    /** 工厂提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            this.form.baseProvinceId = this.value[0];
            this.form.baseCityId = this.value[1];
            this.form.baseAreaId = this.value[2];
            updateDept(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.baseProvinceId = this.value[0];
            this.form.baseCityId = this.value[1];
            this.form.baseAreaId = this.value[2];
            addDept(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 记录提交按钮*/
    submitFormRecords: function() {
      this.$refs["formRecords"].validate(valid => {
        if (valid) {
          if (this.formRecords.id != undefined) {
            updateDeptRecords(this.formRecords).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.two = false;
              this.getListTwo();
            });
          } else {
            AddRecords(this.formRecords).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.two = false;
              this.getListTwo();
            });
          }
        }
      });
    },
    /** 删除工厂按钮操作 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除名称为"' + row.name + '"的数据项？').then(function() {
        return delDept(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /**删除记录按钮操作 */
    handleDeleteRecords(row) {
      this.$modal.confirm('是否确认删除编号为"' + row.id + '"的数据项？').then(function() {
        return delDeptRecords(row.id);
      }).then(() => {
        this.getListTwo()
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /**农作物添加*/
    submitCheckBox(vv){
    this.form.productIds = this.actorArr;
    this.form.operatorId = this.operatorId;
      AddRecords(this.form).then(response => {
        this.$modal.msgSuccess("新增成功");
        this.two = false;
        this.getList();
      });
    },
    handleClose(done) {
      this.dialogVisible = false;
    },

    beforeUpload(file) {
      const isType = file.type === 'application/vnd.ms-excel'
      const isTypeComputer = file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      const fileType = isType || isTypeComputer
      if (!fileType) {
        this.$message.error('上传文件只能是xls/xlsx格式！')
      }
      const fileLimit = file.size / 1024 / 1024 < 10;
      if (!fileLimit) {
        this.$message.error('上传文件大小不超过10M！');
      }
      return fileType && fileLimit
    },
    /**柱状图*/
    reportArr(){
      report().then(res=>{
          this.lwData = res.data
        console.log(res.data)
          for(let i=0;i<this.lwData.length; i++) {
            this.datay[i] = this.lwData[i].peopleNumber
          }
          for(let i=0;i<this.lwData.length; i++) {
            this.datax[i] = this.lwData[i].addressName
          }
        console.log(this.datay)
        console.log(this.datax)
        this.initChart();

      })
    },
    initChart() {
      const chartDom = document.getElementById('bar-chart'); // 获取DOM元素作为容器
      const myChart = echarts.init(chartDom); // 初始化echarts对象
      // 定义柱状图配置项
      const option = {
        title: { text: '柱状图' },
        xAxis: { data: this.datax },
        yAxis: {},
        series: [{ type: 'bar', data: this.datay }],
      };

      // 将配置项设置到echarts对象上
      myChart.setOption(option);
    },


  },
};
</script>
<style>
.el-upload__tip {
  line-height: 1.2;
}

</style>
