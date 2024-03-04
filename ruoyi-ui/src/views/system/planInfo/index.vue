<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="记录时间">
        <el-date-picker clearable
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>

      <el-form-item label="光照"  prop="illumination">
        <el-select v-model="queryParams.illumination" placeholder="请选择" clearable :style="{width: '100%'}">
          <el-option v-for="(item, index) in illuminations" :key="index" :label="item.value"
                     :value="item.value" :disabled="item.disabled"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="操作人" prop="operator">
        <el-input
          v-model="queryParams.operator"
          placeholder="请输入操作人"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['system:info:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:info:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:info:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['system:user:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:info:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="expand">
        <template v-slot="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="农作物编号">
              <span>{{ props.row.cropInfo.cropNum }}</span>
            </el-form-item>
            <el-form-item label="农作物名字">
              <span>{{ props.row.cropInfo.cropName }}</span>
            </el-form-item>
            <el-form-item label="登记时间">
              <span>{{ parseTime( props.row.cropInfo.registrationTime, '{y}-{m}-{d} {hh}:{mm}:{ss}') }}</span>
            </el-form-item>
            <el-form-item label="周期">
              <span>{{ props.row.cropInfo.cycle }}</span>
            </el-form-item>
            <el-form-item label="负责人">
              <span>{{ props.row.cropInfo.baseCharge }}</span>
            </el-form-item>
            <el-form-item label="负责人联系方式">
              <span>{{ props.row.cropInfo.basePhone }}</span>
            </el-form-item>
            <el-form-item label="种植方式">
              <span>{{ props.row.cropInfo.planmethodId }}</span>
            </el-form-item>
            <el-form-item label="灌溉方式">
              <span>{{ props.row.cropInfo.irrigationId }}</span>
            </el-form-item>
            <el-form-item label="肥料类型">
              <span>{{ props.row.cropInfo.fertId }}</span>
            </el-form-item>
            <el-form-item label="备注">
              <span>{{ props.row.cropInfo.remark }}</span>
            </el-form-item>
            <el-form-item label="操作人">
              <span>{{ props.row.cropInfo.operator }}</span>
            </el-form-item>
            <el-form-item label="创建时间">
              <span>{{ props.row.cropInfo.createTime }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="农作物记录ID" align="center" prop="planId"  v-if="columns[0].visible"/>
      <el-table-column label="农作物信息" align="center" prop="cropInfo.cropName"  v-if="columns[1].visible" >
        <template v-slot="scope">
          <span>{{scope.row.cropInfo.cropName}}</span>
        </template>
      </el-table-column>
      <el-table-column label="记录时间" align="center" prop="recordTime" width="180"  v-if="columns[2].visible">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.recordTime, '{y}-{m}-{d} {hh}:{mm}:{ss}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="温度" align="center" prop="temperature"   v-if="columns[3].visible"/>
      <el-table-column label="生长状况" align="center" prop="arowths"  v-if="columns[4].visible"/>
      <el-table-column label="湿度" align="center" prop="humidness"  v-if="columns[5].visible" />
      <el-table-column label="光照" align="center" prop="illumination"  v-if="columns[6].visible" />
      <el-table-column label="农作物图片" align="center" prop="pic"  v-if="columns[7].visible" >
        <template v-slot="scope">
          <el-image :src = scope.row.pic></el-image>
        </template>
      </el-table-column>
      <el-table-column label="备注信息" align="center" prop="remark"  v-if="columns[8].visible" />
      <el-table-column label="操作人" align="center" prop="operator"  v-if="columns[9].visible" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:info:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:info:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改农作物种植记录跟踪信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-row>
          <el-col :span="12">
            <el-form-item label="农作物信息" prop="cropInfoId">
              <el-select v-model="form.cropInfoId" placeholder="请选择" :clearable="true">
                <el-option
                  v-for="item in cropInfoForm"
                  :key="item.id"
                  :label="item.cropName"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="记录时间" prop="recordTime">
              <el-date-picker clearable
                              v-model="form.recordTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择记录时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="温度" prop="temperature">
              <el-input v-model="form.temperature" placeholder="请输入温度" >
                <template slot="append">°c</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生长状况" prop="arowths">
              <el-input v-model="form.arowths" placeholder="请输入生长状况" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>

          <el-col :span="12">
            <el-form-item label="湿度" prop="humidness">
              <el-input v-model="form.humidness" placeholder="请输入湿度" >
                <template slot="append">%PH</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作人" prop="operator">
              <el-select v-model="form.operator" placeholder="请选择操作人" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in userForm" :key="index" :label="item.userName"
                           :value="item.userName" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="备注信息" prop="remark">
              <el-input type="textarea" v-model="form.remark" placeholder="请输入备注信息" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
          </el-col>
        </el-row>

        <el-form-item label="光照"  prop="illumination">
          <el-radio-group v-model="form.illumination" size="medium">
            <el-radio v-for="(item, index) in illuminations" :key="index" :label="item.value"
                      :disabled="item.disabled">{{item.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="农作物图片"  prop="pic">
          <el-upload
            class="avatar-uploader"
            :action=uploadUrll
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

<!--文件导入-->
    <el-dialog title="文件导入" :visible.sync="openExcel" width="800px" append-to-body>
    <el-upload
      class="upload-demo"
      drag
      :action=saveExcels
      multiple>
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
    </el-upload>
    </el-dialog>


  </div>
</template>

<script>
import {
  listInfo,
  getInfo,
  delInfo,
  addInfo,
  updateInfo,
  getListUser,
  downExcel,
  getProductList
} from "@/api/system/planinfo";
import {getToken} from "@/utils/auth";

export default {

  name: "Info",
  data() {
    return {
      // 农作物信息表
      cropInfoForm:[],
      //打开导入excel
      openExcel: false,
      saveExcels:"http://localhost:8902/planInfo/saveExcel/",
      // 图片上传地址
      imageUrl :'',
      //图片上传地址
      uploadUrll :"http://localhost:8902/planInfo/uploadUrl/",
      illuminations: [{
        "label": "光照充足",
        "value": "光照充足"
      }, {
        "label": "光照一般",
        "value": "光照一般"
      }, {
        "label": "光照不足",
        "value": "光照不足"
      }],
      //选择操作人
      userForm: [
      ],

      // 日期范围
      dateRange: [],
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
      // 农作物种植记录跟踪信息表格数据
      infoList: [],
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
      // 列信息
      columns: [
        { key: 0, label: `农作物记录ID`, visible: true },
        { key: 1, label: `农作物信息ID`, visible: true },
        { key: 2, label: `记录时间`, visible: true },
        { key: 3, label: `温度`, visible: true },
        { key: 4, label: `生长状况`, visible: true },
        { key: 5, label: `湿度`, visible: true },
        { key: 6, label: `光照`, visible: true },
        { key: 7, label: `农作物图片`, visible: true },
        { key: 8, label: `备注信息（记录观察情况）`, visible: true },
        { key: 9, label: `操作人`, visible: true },
        { key: 10, label: `创建时间`, visible: true },
      ],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        cropInfoId: [
          { required: true, message: "农作物名字不能为空", trigger: "change" },
        ],
        recordTime:[
          { required: true, message: "记录时间不能为空", trigger: "change" },
        ],
        arowths:[
          { required: true, message: "生长情况不能为空", trigger: "blur" },
        ],
        humidness:[
          { required: true, message: "湿度不能为空", trigger: "blur" },
        ],
        temperature:[
          { required: true, message: "温度不能为空", trigger: "blur" },
        ],
        operator:[
          { required: true, message: "操作人不能为空", trigger: "change" },
        ],
        illumination:[
          { required: true, message: "光照情况不能为空", trigger: "change" },
        ],
        pic:[
          { required: true, message: "农作物图片不能为空", trigger: "change" },
        ],

      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 导入的地址
        url: "http://localhost:8902/planInfo/saveExcel"
      },

    };
  },
  created() {
    this.getList();
    this.getUserList();
  },
  methods: {
    //农作物信息
    productList(){
      getProductList().then(r=>{
        this.cropInfoForm = r.data;
      })
    },

    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },

    /** 导入按钮操作 */
    handleImport() {
      this.openExcel = true;
    },

    /**
     * 用户列表
     */
    getUserList(){
      getListUser().then(r=>{
        this.userForm = r.data;
      })
    },


    /** 查询农作物种植记录跟踪信息列表 */
    getList() {
      this.loading = true;
      listInfo(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.infoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        planId: null,
        cropInfoId: null,
        recordTime: null,
        temperature: null,
        arowths: null,
        humidness: null,
        illumination: null,
        pic: null,
        remark: null,
        operator: null,
        delFlag: null,
        createTime: null
      };
      this.imageUrl = null;
      this.resetForm("form");
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.planId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.productList();
      this.open = true;
      this.title = "添加农作物种植记录跟踪信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const planId = row.planId || this.ids
      getInfo(planId).then(response => {
        this.productList();
        this.form = response.data;
        this.imageUrl = this.form.pic;
        this.open = true;
        this.title = "修改农作物种植记录跟踪信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.planId != null) {
            updateInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const planIds = row.planId || this.ids;
      this.$modal.confirm('是否确认删除农作物种植记录跟踪信息编号为"' + planIds + '"的数据项？').then(function() {
        return delInfo(planIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      window.location="http://localhost:8902/planInfo/downExcel";
    },
    /**
     * 图片上传
     * @param res
     * @param file
     */
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      this.form.pic = res.data;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
  }
};
</script>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
