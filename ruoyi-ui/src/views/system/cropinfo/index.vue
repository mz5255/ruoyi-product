<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="编号  " prop="cropNum">
        <el-input
          v-model="queryParams.cropNum"
          placeholder="请输入编号  "
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="种植基地id" prop="baseId">
        <el-input
          v-model="queryParams.baseId"
          placeholder="请输入种植基地id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="农作物名称" prop="cropName">
        <el-input
          v-model="queryParams.cropName"
          placeholder="请输入农作物名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="登记时间" prop="registrationTime">
        <el-date-picker clearable
          v-model="queryParams.registrationTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择登记时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="周期" prop="cycle">
        <el-input
          v-model="queryParams.cycle"
          placeholder="请输入周期"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负责人" prop="baseCharge">
        <el-input
          v-model="queryParams.baseCharge"
          placeholder="请输入负责人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负责人联系方式" prop="basePhone">
        <el-input
          v-model="queryParams.basePhone"
          placeholder="请输入负责人联系方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="种植方式id" prop="planmethodId">
        <el-input
          v-model="queryParams.planmethodId"
          placeholder="请输入种植方式id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="灌溉方式id" prop="irrigationId">
        <el-input
          v-model="queryParams.irrigationId"
          placeholder="请输入灌溉方式id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="肥料类型id" prop="fertId">
        <el-input
          v-model="queryParams.fertId"
          placeholder="请输入肥料类型id"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:info:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="编号  " align="center" prop="cropNum" />
      <el-table-column label="种植基地id" align="center" prop="baseId" />
      <el-table-column label="农作物名称" align="center" prop="cropName" />
      <el-table-column label="登记时间" align="center" prop="registrationTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.registrationTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="周期" align="center" prop="cycle" />
      <el-table-column label="负责人" align="center" prop="baseCharge" />
      <el-table-column label="负责人联系方式" align="center" prop="basePhone" />
      <el-table-column label="种植方式id" align="center" prop="planmethodId" />
      <el-table-column label="灌溉方式id" align="center" prop="irrigationId" />
      <el-table-column label="肥料类型id" align="center" prop="fertId" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作人" align="center" prop="operator" />
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

    <!-- 添加或修改农作物信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="编号  " prop="cropNum">
          <el-input v-model="form.cropNum" placeholder="请输入编号  " />
        </el-form-item>
        <el-form-item label="种植基地id" prop="baseId">
          <el-input v-model="form.baseId" placeholder="请输入种植基地id" />
        </el-form-item>
        <el-form-item label="农作物名称" prop="cropName">
          <el-input v-model="form.cropName" placeholder="请输入农作物名称" />
        </el-form-item>
        <el-form-item label="登记时间" prop="registrationTime">
          <el-date-picker clearable
            v-model="form.registrationTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择登记时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="周期" prop="cycle">
          <el-input v-model="form.cycle" placeholder="请输入周期" />
        </el-form-item>
        <el-form-item label="负责人" prop="baseCharge">
          <el-input v-model="form.baseCharge" placeholder="请输入负责人" />
        </el-form-item>
        <el-form-item label="负责人联系方式" prop="basePhone">
          <el-input v-model="form.basePhone" placeholder="请输入负责人联系方式" />
        </el-form-item>
        <el-form-item label="种植方式id" prop="planmethodId">
          <el-input v-model="form.planmethodId" placeholder="请输入种植方式id" />
        </el-form-item>
        <el-form-item label="灌溉方式id" prop="irrigationId">
          <el-input v-model="form.irrigationId" placeholder="请输入灌溉方式id" />
        </el-form-item>
        <el-form-item label="肥料类型id" prop="fertId">
          <el-input v-model="form.fertId" placeholder="请输入肥料类型id" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="操作人" prop="operator">
          <el-input v-model="form.operator" placeholder="请输入操作人" />
        </el-form-item>
        <el-form-item label="逻辑删除" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入逻辑删除" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listInfo, getInfo, delInfo, addInfo, updateInfo } from "@/api/system/cropinfo";

export default {
  name: "Info",
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
      // 农作物信息表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        cropNum: null,
        baseId: null,
        cropName: null,
        registrationTime: null,
        cycle: null,
        baseCharge: null,
        basePhone: null,
        planmethodId: null,
        irrigationId: null,
        fertId: null,
        operator: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询农作物信息列表 */
    getList() {
      this.loading = true;
      listInfo(this.queryParams).then(response => {
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
        id: null,
        cropNum: null,
        baseId: null,
        cropName: null,
        registrationTime: null,
        cycle: null,
        baseCharge: null,
        basePhone: null,
        planmethodId: null,
        irrigationId: null,
        fertId: null,
        remark: null,
        operator: null,
        delFlag: null,
        createTime: null
      };
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加农作物信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改农作物信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除农作物信息编号为"' + ids + '"的数据项？').then(function() {
        return delInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/info/export', {
        ...this.queryParams
      }, `info_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
