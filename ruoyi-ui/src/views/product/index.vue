<template>
   <div>
     <el-form :inline="true" :model="productManagementFrom" class="demo-form-inline">
       <el-form-item label="农产品名称">
         <el-input v-model="productManagementFrom.cultivarName" placeholder="农产品名称"></el-input>
       </el-form-item>
<!--       <el-form-item label="产品类型">-->
<!--         <el-select  placeholder="请选择">-->
<!--           <el-option label="果蔬" value="0"></el-option>-->
<!--           <el-option label="农产品" value="1"></el-option>-->
<!--         </el-select>-->
<!--       </el-form-item>-->

       <el-form-item>
         <el-button type="primary" @click="onSubmit">查询</el-button>
       </el-form-item>
     </el-form>
     <el-row :gutter="10" class="mb8">
       <el-col :span="1.5">
         <el-button
           type="primary"
           plain
           icon="el-icon-plus"
           size="mini"
           @click="OpenDialong"
           v-hasPermi="['system:dept:add']"
         >新增</el-button>
       </el-col>

       <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
     </el-row>
     <el-table
       :data="productManagement"
       stripe
       style="width: 100%">
       <el-table-column
         prop="productNum"
         label="产品编号"
         width="180">
       </el-table-column>
       <el-table-column
         prop="cultivarName"
         label="产品名称"
         width="180">
       </el-table-column>
       <el-table-column
         prop="status"
         label="产品的状态"
         width="180">
           <template slot-scope="scope">
             <el-switch
               v-model="scope.row.status"
               active-color="#13ce66"
               inactive-color="#ff4949">
             </el-switch>
           </template>
       </el-table-column>
       <el-table-column
         prop="type"
         label="产品类型">
         <template slot-scope="scope">
           <el-tag type="success" v-if="scope.row.type==0">果蔬</el-tag>
           <el-tag type="danger" v-if="scope.row.type==1">农作物</el-tag>
         </template>

       </el-table-column>
       <el-table-column
         prop="introduce"
         label="产品介绍">
       </el-table-column>

       <el-table-column
         prop="photo"
         label="产品照片">
         <template slot-scope="scope">
           <el-image :src="scope.row.photo" style="height: 50px;width: 50px"></el-image>
         </template>
       </el-table-column>

       <el-table-column
         prop="sort"
         label="排序">
       </el-table-column>

       <el-table-column
         prop="createTime"
         label="生产时间"
          dataformatas="yyyy-MM-dd HH:mm:dd">
       </el-table-column>
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
             size="mini"
             type="text"
             icon="el-icon-plus"
             @click="OpenDialong(scope.row)"
             v-hasPermi="['system:dept:add']"
           >新增</el-button>
           <el-button
             v-if="scope.row.parentId != 0"
             size="mini"
             type="text"
             icon="el-icon-delete"
             @click="handleDelete(scope.row)"
             v-hasPermi="['system:dept:remove']"
           >删除</el-button>
         </template>
       </el-table-column>
     </el-table>

     <el-pagination
       @current-change="handleCurrentChange"
       :current-page.sync="productManagementFrom.pageNum"
       :page-size="productManagementFrom.pageSize"
       :total="productManagementFrom.total">
     </el-pagination>


     <el-dialog title="产 品 新 增" :visible.sync="dialogFormVisible">
       <el-form :model="saveProductManagementFrom">
         <el-form-item label="产品名称" :label-width="formLabelWidth">
           <el-input v-model="saveProductManagementFrom.cultivarName" autocomplete="off"></el-input>
         </el-form-item>
         <el-form-item label="产品的状态" :label-width="formLabelWidth">
           <el-radio v-model="saveProductManagementFrom.status" label="0">正 常</el-radio>
           <el-radio v-model="saveProductManagementFrom.status" label="1">下 架</el-radio>
         </el-form-item>
         <el-form-item label="产品类型" :label-width="formLabelWidth">
           <el-radio v-model="saveProductManagementFrom.type" label="0">果 蔬</el-radio>
           <el-radio v-model="saveProductManagementFrom.type" label="1">农 产 品</el-radio>
         </el-form-item>
         <el-form-item label="产品介绍" :label-width="formLabelWidth">
           <el-input type="textarea" v-model="saveProductManagementFrom.introduce" autocomplete="off"></el-input>
         </el-form-item>
         <el-form-item label="产品照片" :label-width="formLabelWidth">
           <el-upload
             class="avatar-uploader"
             action="https://jsonplaceholder.typicode.com/posts/"
             :show-file-list="false"
             :on-success="handleAvatarSuccess"
             :before-upload="beforeAvatarUpload">
             <img v-if="imageUrl" :src="updateProductManagementFrom.photo" class="avatar">
             <i v-else class="el-icon-plus avatar-uploader-icon"></i>
           </el-upload>
         </el-form-item>
         <el-form-item label="排序" :label-width="formLabelWidth">
           <el-input v-model="saveProductManagementFrom.sort" autocomplete="off"></el-input>
         </el-form-item>
       </el-form>
       <div slot="footer" class="dialog-footer">
         <el-button @click="dialogFormVisible = false">取 消</el-button>
         <el-button type="primary" @click="saveProduct">确 定</el-button>
       </div>
     </el-dialog>


     <el-dialog title="产 品 详 情" :visible.sync="dialogFormVisibleTwo">
       <el-form :model="updateProductManagementFrom">
         <el-form-item label="产品名称" :label-width="formLabelWidth">
           <el-input v-model="updateProductManagementFrom.cultivarName" autocomplete="off"></el-input>
         </el-form-item>
         <el-form-item label="产品的状态" :label-width="formLabelWidth">
           <el-radio v-model="updateProductManagementFrom.status" label="0">正 常</el-radio>
           <el-radio v-model="updateProductManagementFrom.status" label="1">下 架</el-radio>
         </el-form-item>
         <el-form-item label="产品类型" :label-width="formLabelWidth">
           <el-radio v-model="updateProductManagementFrom.type" label="0" disabled>果 蔬</el-radio>
           <el-radio v-model="updateProductManagementFrom.type" label="1" disabled>农 产 品</el-radio>
         </el-form-item>
         <el-form-item label="产品介绍" :label-width="formLabelWidth">
           <el-input type="textarea" v-model="updateProductManagementFrom.introduce" autocomplete="off"></el-input>
         </el-form-item>
         <el-form-item label="产品照片" :label-width="formLabelWidth">
           <el-upload
             class="avatar-uploader"
             action="https://jsonplaceholder.typicode.com/posts/"
             :show-file-list="false"
             :on-success="handleAvatarSuccess"
             :before-upload="beforeAvatarUpload">
             <img v-if="imageUrl" :src="updateProductManagementFrom.photo" class="avatar">
             <i v-else class="el-icon-plus avatar-uploader-icon"></i>
           </el-upload>
         </el-form-item>
         <el-form-item label="排序" :label-width="formLabelWidth">
           <el-input v-model="updateProductManagementFrom.sort" autocomplete="off"></el-input>
         </el-form-item>
       </el-form>
       <div slot="footer" class="dialog-footer">
         <el-button @click="dialogFormVisible = false">取 消</el-button>
         <el-button type="primary" @click="updateProduct">确 定</el-button>
       </div>
     </el-dialog>
   </div>
</template>

<script>


import {delProduct, getProductManagement, saveProduct, updateProduct, updateStatusById} from "@/api/system/product";

export default {
  name: "index",
  data(){
    return{
      updateStatusById:{

      },
      photo:"",
      imageUrl:"",
      productManagement:[],
      productManagementFrom:{
        cultivarName:"",
        type:"",
        pageNum:1,
        pageSize:5,
        total:0,
      },
      value: true,
      dialogFormVisible : false,
      dialogFormVisibleTwo:false,
      formLabelWidth:"100px",
      saveProductManagementFrom:{
        imageUrl:""
      },
      previews: {},
      showSearch: true,
      updateProductManagementFrom:{},
    }
  },
  methods:{
    handleAvatarSuccess(file) {
      console.log(file)
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      console.log(file)
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 10;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 10MB!');
      }
      return isJPG && isLt2M;
    },
    updateProduct(){
      updateProduct(this.saveProductManagementFrom).then(()=>{
        this.getList()
      })
    },
    handleUpdate(row){
      console.log(row)
     this.dialogFormVisibleTwo=true;
     this.updateProductManagementFrom=row;

    },
    handleDelete(row){
      console.log(row)
      delProduct(row.productId).then(()=>{
        this.getList()
      })
    },
    saveProduct(){
      saveProduct(this.saveProductManagementFrom).then(()=>{
        this.getList()
        this.dialogFormVisible=false;
      })
    },
    OpenDialong(){
        this.dialogFormVisible=true;
    },
    onSubmit(){
      this.getList()
    },
    updateStatus(row){
      console.log(row)
      updateStatusById(row.id).then(()=>{
        this.getList()
      })

    },
    getList(){
      getProductManagement(this.productManagementFrom).then(resp=>{
        console.log(resp)
        this.photo=resp.data.list.photo
        this.productManagementFrom.pageNum=1;
        this.productManagementFrom.total=resp.data.total;
        this.productManagement=resp.data.list

      })
    },
    handleCurrentChange(val) {
      this.productManagementFrom.pageNum=val;
      this.getList()
    },

  },
  created() {
    this.getList()
  }
}
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
</style>
