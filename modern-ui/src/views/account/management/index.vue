<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="账号" prop="mobile">
        <el-input
          v-model="queryParams.mobile"
          placeholder="请输入账号"
          clearable
          style="width: 240px"
          @keyup.enter.native="handlueQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="realName">
        <el-input
          v-model="queryParams.realName"
          placeholder="请输入姓名"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">清空条件</el-button>
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
          v-hasPermi="['system:user:add']"
        >新增账户</el-button>
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
          v-hasPermi="['system:user:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="batchDelete"
          v-hasPermi="['system:user:remove']"
        >批量删除</el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

      <el-table ref="refTable" v-loading="loading" :data="listData"
       @selection-change="handleSelectionChange" >
      <el-table-column type="selection" width="55" align="center" />
     
      <el-table-column label="序号" type="index"   align="center">
          <template slot-scope="scope">
            <span>{{  (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1}}</span>
          </template>
      </el-table-column>
      <el-table-column label="账号" align="center" prop="mobile" :show-overflow-tooltip="true" />
      <el-table-column label="姓名" align="center" prop="realName" :show-overflow-tooltip="true" />
      <el-table-column label="身份证号" align="center" prop="idCard" :show-overflow-tooltip="true" />
     <el-table-column  label="注册状态">
        <template slot-scope="scope">
          <div>{{scope.row.realNameAudit|filter_realNameAudit}}</div>
        </template>
      </el-table-column>
      
        
      <el-table-column label="注册时间" align="center" prop="regTime" width="180">
           <template slot-scope="scope">
            <span>{{ parseTime(scope.row.regTime) }}</span>
          </template> 
        </el-table-column>
      <el-table-column label="车辆数量" align="center" prop="vehicleCount" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:user:edit']"
          >编辑</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['system:user:detail']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:user:remove']"
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

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form"   :rules="rules" label-width="80px">
        <el-form-item label="手机号（账号）" align="center" prop="mobile" :required="true">
          <el-input v-model="form.mobile" placeholder="请输入账号" :disabled="!this.isEditMode"/>
        </el-form-item>
        <el-form-item label="姓名" prop="realName" :required="true">
          <el-input v-model="form.realName" placeholder="请输入姓名" :disabled="!this.isEditMode"/>
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard" :required="true">
          <el-input v-model="form.idCard" placeholder="请输入身份证号" :disabled="!this.isEditMode"/>
        </el-form-item>

        <el-form-item label="出生日期" prop="birthday" >
         <el-date-picker v-model="form.birthday" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
          :style="{width: '100%'}"  placeholder="选择日期时间" :disabled="!this.isEditMode" clearable></el-date-picker>
        </el-form-item>

        <el-form-item label="车主性别" prop="sex"  >
            <el-radio-group v-model="form.sex" ref="radioGroup" :disabled="!this.isEditMode">
              <el-radio :label="1">男</el-radio>
              <el-radio :label="0">女</el-radio>
            </el-radio-group>
          </el-form-item>
        
    <el-form-item label="注册地址" ref="radioGroup" :required="true">
	     <el-cascader
		   size="large"
		   :options="options"
		   placeholder="请选择"
		   expand-trigger="hover"
		   ref="cascaderAddr"
		   v-model="form.selectedOptions"
       :disabled="!this.isEditMode"
       clerable
		   @change="handleChange">
	     </el-cascader>
    </el-form-item>
         
    <el-form-item label="详细地址" prop="address" >
       <el-input v-model="form.address" placeholder="请输入详细地址" :disabled="!this.isEditMode"/>
    </el-form-item>

    </el-form-item>
        <el-form-item label="用户标签" prop="labels" multiple :required="true" >          
          <el-select v-model="form.labels" placeholder="请选择" :disabled="!this.isEditMode"  clearable>
          <el-option
            v-for="dict1 in dict.type.tsp_user_label"
            :key="dict1.value"
            :label="dict1.label"
            :value="Number(dict1.value)"
          />
        </el-select>
        
    </el-form-item>
    
    

    <el-form-item label="请上传手持身份证正面照片" prop="userCardFrontImg" >
     <div class="component-upload-image"  >
          <el-upload
           
            :action="uploadImgUrl"
            list-type="picture-card"
            :on-success="handleUploadSuccess3"
            :before-upload="handleBeforeUpload"
            :limit="limit"
            :on-error="handleUploadError"
            :on-exceed="handleExceed"
            name="file"
            :on-remove="handleRemove"
            :show-file-list="true"
            :headers="headers"
            :file-list="fileList1"
            :on-preview="handlePictureCardPreview"
            :class="{hide: this.fileList1.length >= this.limit}"
        >
        
            <i class="el-icon-plus"></i>
        </el-upload>

        <!-- 上传提示 -->
      <div class="el-upload__tip" slot="tip" v-if="isShowTip">
        请上传
        <template v-if="fileSize"> 大小不超过 <b style="color: #f56c6c"> {{ fileSize }}MB </b></template>
        <template v-if="fileType"> 格式为 <b style="color: #f56c6c"> {{ fileType.join("/") }} </b></template>
        的文件
      </div>
 
       <el-dialog
        :visible.sync="dialogVisible"
        title="预览"
        width="800px"
        append-to-body
      >
       <img
        :src="dialogImageUrl"
        style="display: block; max-width: 100%; margin: 0 auto"
        />
      </el-dialog>
     </div>
    </el-form-item>

    <el-form-item label="请上传手持身份证反面照片" prop="userCardBackImg" :disabled="!this.isEditMode">
      <div class="component-upload-image">
          <el-upload
            :action="uploadImgUrl"
            list-type="picture-card"
            :on-success="handleUploadSuccess3"
            :before-upload="handleBeforeUpload"
            :limit="limit"
            :on-error="handleUploadError"
            :on-exceed="handleExceed"
            name="file"
            :on-remove="handleRemove"
            :show-file-list="true"
            :headers="headers"
            :file-list="fileList3"
            :on-preview="handlePictureCardPreview"
            :class="{hide: this.fileList3.length >= this.limit}"
        >
            <i class="el-icon-plus"></i>
        </el-upload>

        <!-- 上传提示 -->
       <div class="el-upload__tip" slot="tip" v-if="isShowTip">
        请上传
        <template v-if="fileSize"> 大小不超过 <b style="color: #f56c6c"> {{ fileSize }}MB </b></template>
        <template v-if="fileType"> 格式为 <b style="color: #f56c6c"> {{ fileType.join("/") }} </b></template>
        的文件
       </div>

       <el-dialog
        :visible.sync="dialogVisible"
        title="预览"
        width="800px"
        append-to-body
       >
        <img
        :src="dialogImageUrl"
        style="display: block; max-width: 100%; margin: 0 auto"
        />
       </el-dialog>
      </div>
        </el-form-item>
    
       

    </el-form>
       <div slot="footer" class="dialog-footer">
        <el-button v-if="this.isEditMode" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
       
      <!-- 导入设备弹窗 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>


<script>
import { listData,addaccount,updateaccount,delaccount,batchdelaccount  } from "@/api/account/management/data";
import { getToken } from "@/utils/auth";
import { regionData } from 'element-china-area-data';

export default {
  name: " listData",
  dicts: ['sys_normal_disable','sys_realname_status','tsp_user_label'],
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
      // 表格数据
      listData: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      //是否可编辑模式
      isEditMode: true,
      // 查询参数
      queryParams: {
          pageNum: 1,
          pageSize: 10,
          search: undefined,
          realName: undefined, 
          mobile: undefined,
          sex:undefined,
          
        },
      // 表单参数
      form: { 
            label: []
      },
      //
  
      //导入弹窗参数
      upload: {
        title: "",
        open: false,
        isUploading: false,
        updateSupport: 0,
        url: "",
        headers: {Authorization: "Bearer " + getToken() },
      },
       //下载图片路径
      dialogImageUrl: "",
	    dialogVisible: false,
	    hideUpload: false,
	    baseUrl: 'http://10.110.1.241:8088',
      
	    uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器地址
   
	    headers: {
	          Authorization: "Bearer " + getToken(),
	        },
      //身份证正面图片    
	    fileList1: [],
      //身份证反面图片
      fileList3: [],
      //省市区
      provinces: regionData,
      cities: [],
      area: [],
      value:'',
      //上牌省市区下拉框
      options:regionData,
      selectedOptions: [],
      //
   
      // 表单校验
      rules: {
        mobile: [
          { required: true, message: "账号不能为空", trigger: "blur" },
        ],
        realName: [
          { required: true, message: "姓名不能为空", trigger: "blur" },
        ],
        idCard: [
          { required: true, message: "身份证号不能为空", trigger: "blur" },
        ],
       province: [
          { required: true, message: "注册地址不能为空", trigger: "blur" },
        ],
        lable: [
          { required: true, message: "标签不能为空", trigger: "blur" },
        ],
        
        
      },
    };
  },
  created() {
    this.getList();
  },

  computed: {  
      // 过滤重复值
      uniqueItems() {
      return this.items.filter((item, index, self) => {
        return index === self.findIndex((t) => (
          t.id === item.id && t.realName === item.realName
        ));
      });
    }
  },

    methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      listData(this.queryParams).then(response => {
        this.listData = response.data.list;
        this.total = response.data.total;
      });
      this.loading = false;
    },
    
    // 上牌信息省市区
    handleChange(value) {
        this.form.awardProvince = value[0];
        this.form.awardCity = value[1];
        this.form.awardArea = value[2];
			  console.log(this.selectedOptions)
        console.log(value)		
      },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        mobile: undefined,
        realName: undefined,
        idCard: undefined,
        dictId: undefined,
        status: "0",
        label: [],
        userCardBackImg: undefined,
        userCardFrontImg: undefined,
        address: undefined,
        birthday: undefined,
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
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length!=1
        this.multiple = !selection.length
        //console.log(this.ids)
      },
    //
    computed: {
                // 转换数据为后端需要的格式
                transformedData() {
                    return this.form.label.map(label => Number(label));
                }
            },
    /** 新增账户按钮操作 */
      handleAdd() {
       
        this.reset();
        this.open = true;
        this.isEditMode = true;
        this.title = "新增账户";
      },

    /** 修改按钮操作 */
      handleUpdate(row) {
        this.form = row;  
        this.open = true;
        console.log(this.form.id);
        this.isEditMode = true;
        this.title = "编辑";
      },

    //详情按钮操作
      handleDetail(row) {
        this.form = row;
        this.open = true;
       
        this.isEditMode = false;
        this.title = "详情";

      },

    /** 提交按钮 */
      submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.tspUserId != undefined) {
              updateaccount(this.form).then(response => {
                this.$modal.msgSuccess("修改设备信息成功");
                this.open = false;
                this.getList();
              });
            } 
            
            else {
              addaccount(this.form).then(response => {
                this.$modal.msgSuccess("新增账户成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
        
      },

    /** 删除按钮操作 */
    handleDelete(row) {

      const tspUserId=row.id;

      if(tspUserId!= undefined) {

        this.$modal.confirm('是否确认删除数据？').then(() => {
          return delaccount(tspUserId);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
       }
     },
    
    /** 批量删除车辆信息按钮操作 */
     batchDelete() {       
      const tspUserIds = this.ids;
      if(tspUserIds.length == 0) {
        this.$message.warning("请选择需要删除的车辆信息");
        return;
      } 
      else{
        this.$modal.confirm('是否确认删除选中的数据项？').then(() => {
          return batchdelaccount(tspUserIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
     }
    },

    /** 导入出厂信息按钮操作 */
    handleImport() {  
        this.upload.title = "导入";
        this.upload.url =process.env.VUE_APP_BASE_API+ "/tsp/user/import";
        this.upload.open = true;
      },
    /** 导出按钮操作 */
    handleExport() {
      this.download('tsp/user/exportUser', {
        ...this.queryParams
      }, `type_${new Date().getTime()}.xlsx`)
    },
    /** 刷新缓存按钮操作 */
    handleRefreshCache() {
      refreshCache().then(() => {
        this.$modal.msgSuccess("刷新成功");
         
      });
    },
   
  },

    //全局过滤器
    filters: {
      filter_realNameAudit(value) {
       if (value) return '已注册'
       return value = '未注册'
      },
    
    // 身份证正面图片上传成功回调
    handleUploadSuccess1(res) {
        this.fileList1.push({url:res.url});
        
        console.log(this.fileList1);
   
        this.$emit("input", this.listToString(this.fileList1));
        this.loading.close();
      },

    // 身份证反面图片上传成功回调
    handleUploadSuccess3(res) {
        this.fileList3.push({url:res.url});
        
        console.log(this.fileList3);
   
        this.$emit("input", this.listToString(this.fileList3));
        this.loading.close();
      },

    // 删除图片
     handleRemove(file, fileList) {
       if(fileList == this.fileList1){
        const findex = this.fileList1.map(f => f.url).indexOf(file.url);
          if (findex > -1) {
              this.fileList1.splice(findex, 1);
              
              this.$emit("input", this.listToString(this.fileList1));

              

            }
        }
      
       else if(fileList == this.fileList3){
        const findex = this.fileList3.map(f => f.url).indexOf(file.url);
          if (findex > -1) {
              this.fileList3.splice(findex, 1);
              
              
              this.$emit("input", this.listToString(this.fileList3));

              
            }
        }
     },

   // 图片上传失败
    handleUploadError() {
        this.$message({
        type: "error",
        message: "上传失败",
        });
        this.loading.close();
        //this.getList();
    },

   // 附件上传图片预览事件，这个就是将路径直接放进一个弹窗显示出来就可以了
      handlePictureCardPreview(file) {
              this.dialogImageUrl = file.url;
              this.dialogVisible = true;
        },

   // 处理图片路径
      setImgUrl(imgArr) {
              let arr = [];
          if (imgArr.length>0) {
            for (let i = 0; i < imgArr.length; i++) {
                const element = imgArr[i];
                arr.push(element.response.data.url);

   //这个地方根据后台返回的数据进行取值，可以先打印一下
            }
          return arr.join();
           } else {
            return ''
          } 
      },


  }
}
</script>
