<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="编号 " prop="baseNum">
        <el-input
          v-model="queryParams.baseNum"
          placeholder="请输入编号 "
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="省id" prop="provinceId">
        <el-input
          v-model="queryParams.provinceId"
          placeholder="请输入省id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="市id" prop="cityId">
        <el-input
          v-model="queryParams.cityId"
          placeholder="请输入市id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="区id" prop="areaId">
        <el-input
          v-model="queryParams.areaId"
          placeholder="请输入区id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="基地名称" prop="baseName">
        <el-input
          v-model="queryParams.baseName"
          placeholder="请输入基地名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="合作单位id " prop="cooperatorId">
        <el-input
          v-model="queryParams.cooperatorId"
          placeholder="请输入合作单位id "
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负责人" prop="charge">
        <el-input
          v-model="queryParams.charge"
          placeholder="请输入负责人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系方式" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入联系方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="面积 " prop="area">
        <el-input
          v-model="queryParams.area"
          placeholder="请输入面积 "
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="适合种植的农作物" prop="cropName">
        <el-input
          v-model="queryParams.cropName"
          placeholder="请输入适合种植的农作物"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="排序" prop="level">
        <el-input
          v-model="queryParams.level"
          placeholder="请输入排序"
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
      <el-table-column label="序号" align="center" prop="baseId" />
      <el-table-column label="编号 " align="center" prop="baseNum" />
      <el-table-column label="省id" align="center" prop="provinceId" />
      <el-table-column label="市id" align="center" prop="cityId" />
      <el-table-column label="区id" align="center" prop="areaId" />
      <el-table-column label="基地名称" align="center" prop="baseName" />
      <el-table-column label="合作单位id " align="center" prop="cooperatorId" />
      <el-table-column label="负责人" align="center" prop="charge" />
      <el-table-column label="联系方式" align="center" prop="phone" />
      <el-table-column label="面积 " align="center" prop="area" />
      <el-table-column label="适合种植的农作物" align="center" prop="cropName" />
      <el-table-column label="基地状态" align="center" prop="status" />
      <el-table-column label="排序" align="center" prop="level" />
      <el-table-column label="操作人" align="center" prop="operator" />
      <el-table-column label="备用字段" align="center" prop="remark" />
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

    <!-- 添加或修改种植基地信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="编号 " prop="baseNum">
          <el-input v-model="form.baseNum" placeholder="请输入编号 " />
        </el-form-item>
        <el-form-item label="省id" prop="provinceId">
          <el-input v-model="form.provinceId" placeholder="请输入省id" />
        </el-form-item>
        <el-form-item label="市id" prop="cityId">
          <el-input v-model="form.cityId" placeholder="请输入市id" />
        </el-form-item>
        <el-form-item label="区id" prop="areaId">
          <el-input v-model="form.areaId" placeholder="请输入区id" />
        </el-form-item>
        <el-form-item label="基地名称" prop="baseName">
          <el-input v-model="form.baseName" placeholder="请输入基地名称" />
        </el-form-item>
        <el-form-item label="合作单位id " prop="cooperatorId">
          <el-input v-model="form.cooperatorId" placeholder="请输入合作单位id " />
        </el-form-item>
        <el-form-item label="负责人" prop="charge">
          <el-input v-model="form.charge" placeholder="请输入负责人" />
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="面积 " prop="area">
          <el-input v-model="form.area" placeholder="请输入面积 " />
        </el-form-item>
        <el-form-item label="适合种植的农作物" prop="cropName">
          <el-input v-model="form.cropName" placeholder="请输入适合种植的农作物" />
        </el-form-item>
        <el-form-item label="排序" prop="level">
          <el-input v-model="form.level" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="逻辑删除" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入逻辑删除" />
        </el-form-item>
        <el-form-item label="操作人" prop="operator">
          <el-input v-model="form.operator" placeholder="请输入操作人" />
        </el-form-item>
        <el-form-item label="备用字段" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备用字段" />
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
import { listInfo, getInfo, delInfo, addInfo, updateInfo } from "@/api/system/baseinfo";

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
      // 种植基地信息表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        baseNum: null,
        provinceId: null,
        cityId: null,
        areaId: null,
        baseName: null,
        cooperatorId: null,
        charge: null,
        phone: null,
        area: null,
        cropName: null,
        status: null,
        level: null,
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
    /** 查询种植基地信息列表 */
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
        baseId: null,
        baseNum: null,
        provinceId: null,
        cityId: null,
        areaId: null,
        baseName: null,
        cooperatorId: null,
        charge: null,
        phone: null,
        area: null,
        createTime: null,
        cropName: null,
        status: null,
        level: null,
        delFlag: null,
        operator: null,
        remark: null
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
      this.ids = selection.map(item => item.baseId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加种植基地信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const baseId = row.baseId || this.ids
      getInfo(baseId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改种植基地信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.baseId != null) {
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
      const baseIds = row.baseId || this.ids;
      this.$modal.confirm('是否确认删除种植基地信息编号为"' + baseIds + '"的数据项？').then(function() {
        return delInfo(baseIds);
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
