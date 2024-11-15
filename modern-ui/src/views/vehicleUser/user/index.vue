<template>
   <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="账号" prop="mobile" label-width="100px">
          <el-input
            v-model="queryParams.mobile"
            placeholder="请输入账号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="姓名" prop="realName" label-width="100px">
          <el-input
            v-model="queryParams.realName"
            placeholder="请输入姓名"
            clearable
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
            v-hasPermi="['vehicle:vehicleUser:add']"
          >新增账户</el-button>
        </el-col>
        <el-col :span="1.5" >
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['vehicle:vehicleUser:import']"
            >导入</el-button>
          </el-col>
        <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['vehicle:vehicleUser:export']"
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
            v-hasPermi="['vehicle:vehicleUser:remove']"
          >批量删除</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table ref="refTable" v-loading="loading" :data="listVehicleUser"
       @selection-change="handleSelectionChange" >
       <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="车辆ID" align="center" v-if="false" prop="id"/>
      <el-table-column label="用户ID" align="center" v-if="false" prop="userId"/>
      <el-table-column label="车辆类型ID" align="center" v-if="false" prop="tspVehicleModelId"/>
      <el-table-column label="车辆型号ID" align="center" v-if="false" prop="tspVehicleStdModelId"/>
      <el-table-column label="序号" type="index" align="center">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1}}</span>
          </template>
        </el-table-column>
       <el-table-column label="账号" align="center" prop="mobile"></el-table-column>
       <el-table-column label="姓名" align="center" prop="realName"></el-table-column>
       <el-table-column label="身份证号" align="center" prop="idCard"></el-table-column>
       <el-table-column label="状态" align="center" prop="mobileStatus">
       <template slot-scope="scope">
          <dict-tag :options="dict.type.mobile_status" :value="scope.row.bindStatus"/>
        </template>
      </el-table-column>
        <el-table-column label="注册时间" align="center" prop="createTime" width="180">
           <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template> 
        </el-table-column>
        <el-table-column label="车辆数量" prop="stdModeCount" align="center"></el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['vehicle:vehicleUserDetail:view']"
          >详情</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['vehicle:vehicleUser:edit']"
            >编辑</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['vehicle:vehicleUser:remove']"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="listVehicleUser"
      />

    <!-- 添加或修改账户对话框-->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="用户ID" prop="userId" v-if="false" :disabled="true"/>
        <el-form-item label="车主姓名" prop="realName" :required="true">
          <el-input
            v-model="form.realName"
            placeholder="请输入车主姓名"
            style="width: 70%"
            clearable
          />
        </el-form-item>
        <el-form-item label="手机号(账号)" prop="mobile" label-width="120px" :required="true">
          <el-input
            v-model="form.mobile"
            placeholder="请输入手机号(账号)"
            style="width: 70%"
            clearable
          />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard" :required="true">
          <el-input
            v-model="form.idCard"
            placeholder="请输入身份证号"
            style="width: 70%"
            clearable
          />
        </el-form-item>
        <el-form-item label="出生日期" prop="bithDate">
        <el-date-picker v-model="form.bithDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
          :style="{width: '70%'}" placeholder="选择日期时间" clearable></el-date-picker>
      </el-form-item>
      <el-form-item label="车主性别" prop="sex" label-width="120px" >
            <el-radio-group v-model="form.sex" ref="radioGroup">
              <el-radio :label="1">男</el-radio>
              <el-radio :label="0">女</el-radio>
            </el-radio-group>
      </el-form-item>
      <el-form-item label="注册地址" >
	     <el-cascader
		   size="large"
		   :options="options"
		   placeholder="请选择"
		   expand-trigger="hover"
		   ref="cascaderAddr"
		   v-model="form.selectedOptions"
           style="width: 70%"
           clerable
		   @change="handleChange">
	     </el-cascader>
     </el-form-item>
     <el-form-item label="详细地址" prop="address" >
          <el-input
            v-model="form.address"
            placeholder="请输入详细地址"
            style="width: 70%"
            clearable
          />
        </el-form-item>
        <el-form-item label="用户标签" prop="label" :required="true">          
            <el-select v-model="form.label" style="width: 70%" placeholder="请选择" clearable>
            <el-option
              v-for="dict in dict.type.label_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.label"
            />
          </el-select>
          </el-form-item> 
          <el-form-item label="请上传手持身份证正面照片"  prop="cardFrontImg">
        <el-upload
            :action="'/tsp/equipmentType/export'"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :on-success="imgSuccess"
            :on-error="imgError"
            :on-remove="imgRemove"
          >
          <i class="el-icon-plus"></i>
        </el-upload>
        </el-form-item>
        <el-form-item label="请上传手持身份证反面照片" prop="cardBackImg">
        <el-upload
            :action="'/tsp/equipmentType/export'"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :on-success="imgSuccess"
            :on-error="imgError"
            :on-remove="imgRemove"
          >
          <i class="el-icon-plus"></i>
        </el-upload>
        </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
      <!-- 导入弹窗 -->
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
import { regionData } from 'element-china-area-data';
import { getToken } from "@/utils/auth";


export default {
    name:"listVehicleUser",
    dicts: [
    
    ],
    data() {
      return {
        //显示搜索条件
        showSearch: true,
        //遮罩层    
        loading: false,
        //查询表单
        queryParams: {
          mobile: "",
          realName: "",
          pageNum: 1,
          pageSize: 10,
        },
        //注册地址下拉框
        options:regionData,
        selectedOptions: [],
        // 用户列表数据
        listVehicleUser: [],
        //总条数
        total: 0,
        //弹出层标题
        title: "",
        //弹出层表单
        form: {},
        // 是否显示弹出层
        open: false,
        //非多个禁用
        multiple: true,
        // 选中用户ID数组
        ids: [],
        // 非单个禁用
        single: true,
        mobileOption: [],
        // 表单校验规则
        rules: {},
        //导入弹窗参数
        upload: {
          title: "",
          open: false,
          isUploading: false,
          updateSupport: 0,
          url: "",
          headers: {Authorization: "Bearer " + getToken() },
        },
      };
    },
    created() {
        this.getList();
    },
    methods: {
    //获取用户列表
      getList() {
        this.loading = true;
        this.listVehicleUser = [
            {
              id: 1,
              mobile: "13811111111",
              realName: "张三",
              idCard: "123456789012345678",
              mobileStatus: 1,
              createTime: "2021-01-01 10:00:00",
              stdModeCount: 1,
            },
        ];
        this.loading = false;
    },
    // 账户表单取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
    //重置账户表单
      reset() {
        this.form = {
            userId: '',
            realName: '',
            mobile: '',
            idCard: '',
            bithDate: '',
            sex: '',
            address: '',
            label: '',
            cardFrontImg: '',
            cardBackImg: '',
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
    /** 新增账户按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "新增账户";
      }, 
    //新增提交按钮
      submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.userId != '') {
              updateVehicleType(this.form).then(response => {
                this.$modal.msgSuccess("修改账户成功");
                this.open = false;
                this.getList();
              });
            } else {
              addVehicleType(this.form).then(response => {
                this.$modal.msgSuccess("新增账户成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
        
      }, 
    //编辑账户按钮操作
      handleUpdate(row) {
        this.title = "编辑账户";
        this.open = true;
        this.form = row;
        this.form.userId = row.id;
    },
    //详情按钮操作
      handleDetail(row) {

        if(row.id!= undefined) {
        
        const userId=row.id;

        this.$router.push("/vehicleUser/user/detail/"+ userId);
        
      }
      },
    /** 导入按钮操作 */
    handleImport() {
        this.upload.title = "账户导入";
        this.upload.url =process.env.VUE_APP_BASE_API+ "/tsp/vehicle/model/importVehicleModel";
        this.upload.open = true;
      },
   /** 下载模板操作 */
    importTemplate() {
        if(this.upload.title=="账户导入")
        {
          this.download('/tsp/vehicle/model/importTemplateModel', {
          }, `账户导入模板${new Date().getTime()}.xlsx`)
        }
        
      },
    // 导入文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
        this.upload.isUploading = true;
      },
   // 导入文件上传成功处理
    handleFileSuccess(response, file, fileList) {
        this.upload.open = false;
        this.upload.isUploading = false;
        this.$refs.upload.clearFiles();
        this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
        this.getList();
      },
   // 提交上传导入文件
    submitFileForm() {
        this.$refs.upload.submit();
      }, 
    /** 导出按钮操作 */
      handleExport() {
        this.download('/tsp/equipmentType/export', {
          ...this.queryParams
        }, `deviceType_${new Date().getTime()}.xlsx`)
      },
    //删除按钮操作  
      handleDelete(row) {

        const vehicleUserIds=row.id;

        if(vehicleUserIds!= undefined) {

            this.$modal.confirm('是否确认删除VIN为"' + row.id + '"的数据项？').then(() => {
            return deleteVehicleMessage(vehicleUserIds);
      }).then(() => {
            this.getList();
            this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  },      
    //批量删除
      batchDelete() {
        const vehicleUserIds = this.ids;
        if (vehicleUserIds.length == 0) {
          this.$message.warning("请选择需要删除的数据");
          return;
        }
        this.$modal.confirm("是否确认删除选中的"+vehicleUserIds.length+"条数据？").then(() => {
          return batchDelVehicleType(vehicleUserIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },   
    // 多选框选中数据
      handleSelectionChange(selection) {
         this.ids = selection.map(item => item.id)
         this.single = selection.length!=1
         this.multiple = !selection.length
     },
    //注册地址下拉框
      handleChange(value) {

        console.log(this.selectedOptions)
        console.log(value)
    
     },
     // 图片上传成功
      imgSuccess(res, file, fileList) {
              this.fileList = fileList;
              //这里我是用一个fileList数组存取，当保存的时候进行图片路径处理
        
        },
    // 图片上传失败
      imgError(res) {
              this.$message({
              type: "error",
              message: "附件上传失败",
              });
        },
   // 删除图片
      imgRemove(file, fileList) {
              this.fileList = fileList;
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

 },

}
</script>