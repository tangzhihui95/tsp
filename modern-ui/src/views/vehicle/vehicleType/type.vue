<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="关键字" prop="vehicleModelName" label-width="100px">
          <el-input
            v-model="queryParams.vehicleModelName"
            placeholder="车型/型号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="车辆类型ID" prop="tspVehicleModelId" v-if="false" label-width="100px">
          <el-input
            v-model="queryParams.tspVehicleModelId"
            placeholder="车辆类型ID"
            clearable
          />
        </el-form-item>
        <el-form-item label="车辆型号ID" prop="tspVehicleStdModelId" v-if="false" label-width="100px">
          <el-input
            v-model="queryParams.tspVehicleStdModelId"
            placeholder="车辆型号ID"
            clearable
          />
        </el-form-item>
        <el-form-item label="二级车型" prop="vehicleTypeModel" label-width="120px">
      <el-cascader
        v-model="queryParams.vehicleTypeModel"
        style="width:200px"
        size="small"
        :options="option"
        @change="handleChange"
         clerable
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
            v-hasPermi="['system:equipmentType:add']"
          >新增车型</el-button>
        </el-col>
        <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['equipment:type:export']"
            >导出</el-button>
          </el-col>
        <el-col :span="1.5" >
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImportType"
              v-hasPermi="['system:equipmentType:import']"
            >导入一级车型</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImportModel"
              v-hasPermi="['system:deviceModel:import']"
            >导入二级车型</el-button>
          </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="batchDelete"
            v-hasPermi="['system:deviceType:remove']"
          >批量删除</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
  
      <el-table ref="refTable" v-loading="loading" :data="listVehicleType"
       @selection-change="handleSelectionChange" 
      @expand-change="expandChange">
      <!-- 嵌套二级型号表 -->
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="expand">
    <template slot-scope="scope">
      <el-form label-position="left" inline class="demo-table-expand">
        <el-table :data="scope.row.children" style="width: 100%">
          <el-table-column label="序号" type="index" align="center">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1}}</span>
          </template>
        </el-table-column>
      <el-table-column label="二级型号ID" align="center" v-if="false" prop="id"/> 
          <el-table-column prop="stdModeName" label="二级型号" align="center"/>
          <el-table-column label="公告型号" prop="noticeModel" width="180" align="center"></el-table-column>
          <el-table-column label="公告批次" prop="noticeBatch" width="180" align="center"></el-table-column>
        <el-table-column label="关联车辆" prop="stdModeCount" align="center"></el-table-column>
        <el-table-column label="能源类型" align="center">
          <template slot-scope="scope">
          <div>{{scope.row.dataKey|filter_dataKey}}</div>
        </template>
        </el-table-column>
          <el-table-column label="操作"  align="center">
            <template slot-scope="scope">
              <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdateModel(scope.row)"
              v-hasPermi="['system:deviceModel:edit']"
            >编辑型号</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDeleteModel(scope.row)"
              v-hasPermi="['system:deviceModel:remove']"
            >删除型号</el-button>
            <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['system:deviceModel:add']"
          >详情</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
    </template>
  </el-table-column>
      <el-table-column label="一级车型ID" align="center" v-if="false" prop="id"/>
      <el-table-column label="序号" type="index" align="center">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1}}</span>
          </template>
        </el-table-column>
        <el-table-column label="一级车型" align="center" prop="vehicleModelName"></el-table-column>
        <el-table-column label="关联车辆" align="center" prop="vehicleCount" />
        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
           <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template> 
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:deviceType:edit']"
            >编辑</el-button>
            <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAddModel(scope.row)"
            v-hasPermi="['system:deviceModel:add']"
          >添加型号</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:deviceType:remove']"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
  
    <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="listVehicleType"
      /> 
    <!-- 添加或修改一级车型对话框-->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="车辆类型ID" prop="tspVehicleModelId" v-if="false" :disabled="true"/>
            <el-form-item label="车型名称" prop="vehicleModelName" :required="true">
              <el-input
            v-model="form.vehicleModelName"
            placeholder="请输入车型名称"
            style="width: 70%"
            clearable
          />
        </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    <!-- 添加或修改二级型号对话框 -->
      <el-dialog :title="title" :visible.sync="open2" width="1100px" append-to-body>
        <el-form ref="form2" :model="form2" :rules="rules" label-width="180px">
        <h4 class="form-header h4" content-position="left">基本信息</h4>
        <el-form-item label="车辆类型ID" prop="tspVehicleModelId" v-if="false" :disabled="true"/>
        <el-form-item label="车辆型号ID" prop="tspVehicleStdModelId" v-if="false" :disabled="true"/>
        <div class="itemInline">
        <el-form-item label="车型" prop="vehicleModelName" :required="true" >
          <el-input
            v-model="form2.vehicleModelName"
            placeholder="请输入车型"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item>
        <el-form-item label="型号名称" prop="stdModeName" :required="true">
          <el-input
            v-model="form2.stdModeName"
            placeholder="请输入型号名称"
            style="width:100%"
            :disabled="!this.isEditMode"
            clearable
          />
        </el-form-item>
          <el-form-item label="选择能源类型" prop="dataKey" :required="true">          
            <el-select v-model="form2.dataKey" placeholder="请选择能源类型" :disabled="!this.isEditMode" clearable>
            <el-option
              v-for="dict1 in dict.type.data_type"
              :key="Number(dict1.value)"
              :label="dict1.label"
              :value="Number(dict1.value)"
            />
          </el-select>
          </el-form-item>
        </div>
        <div class="itemInline">
          <el-form-item label="公告型号" prop="noticeModel" :required="true">
          <el-input
            v-model="form2.noticeModel"
            placeholder="请输入公告型号"
            style="width: 80%"
            :disabled="!this.isEditMode"
            clearable
          />
        </el-form-item>
        <el-form-item label="公告批次" prop="noticeBatch" :required="true">
          <el-input
            v-model="form2.noticeBatch"
            placeholder="请输入公告批次"
            style="width: 100%"
            :disabled="!this.isEditMode"
            clearable
          />
        </el-form-item>
        </div>
        <h4 class="form-header h4" content-position="left">整车参数</h4>
        <div class="itemInline">    
          <el-form-item label="气缸数(个)" prop="stdModelExtraAddVO.cylinderNumber">
            <el-input
            v-model="form2.stdModelExtraAddVO.cylinderNumber"
            placeholder="请输入气缸数"
            style="width: 100%"
            :disabled="!this.isEditMode"
            clearable
            />
           </el-form-item>
           <el-form-item label="环保标准" prop="stdModelExtraAddVO.environmentalProtection" :required="true">          
            <el-select v-model="form2.stdModelExtraAddVO.environmentalProtection" placeholder="请选择环保标准" :disabled="!this.isEditMode" clearable>
            <el-option
              v-for="dict2 in dict.type.environmental_protection"
              :key="dict2.value"
              :label="dict2.label"
              :value="dict2.label"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="整车质保" prop="stdModelExtraAddVO.vehicleWarranty">
            <el-input
            v-model="form2.stdModelExtraAddVO.vehicleWarranty"
            placeholder="请输入整车质保"
            style="width: 100%"
            :disabled="!this.isEditMode"
            clearable
            />
           </el-form-item>   
        </div>
        <div class="itemInline">
           <el-form-item label="发动机型号" prop="stdModelExtraAddVO.engineType">
            <el-input
            v-model="form2.stdModelExtraAddVO.engineType"
            placeholder="请输入发动机型号"
            style="width: 80%"
            :disabled="!this.isEditMode"
            clearable
            />
           </el-form-item> 
           <el-form-item label="车身尺寸(长*宽*高)M" prop="stdModelExtraAddVO.dimensions" :required="true">          
            <el-input
            v-model="form2.stdModelExtraAddVO.dimensions"
            placeholder="请输入车身尺寸"
            style="width: 100%"
            :disabled="!this.isEditMode"
            clearable
            />
           </el-form-item>           
        </div>
        <el-form-item label="车型图片" prop="stdModelExtraAddVO.extraImages">
          <div class="component-upload-image">
          <el-upload
            :action="uploadImgUrl"
            list-type="picture-card"
            :on-success="handleUploadSuccess"
            :before-upload="handleBeforeUpload"
            :limit="limit"
            :on-error="handleUploadError"
            :on-exceed="handleExceed"
            name="file"
            :on-remove="handleRemove"
            :show-file-list="true"
            :headers="headers"
            :file-list="fileList"
            :on-preview="handlePictureCardPreview"
            :class="{hide: this.fileList.length >= this.limit}"
            :disabled="!this.isEditMode"
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
        <h4 class="form-header h4" content-position="left">性能参数</h4>
        <div class="itemInline">    
          <el-form-item label="发动机排量(mL)" prop="stdModelExtraAddVO.displacement">
            <el-input
            v-model="form2.stdModelExtraAddVO.displacement"
            placeholder="请输入发动机排量"
            style="width: 100%"
            :disabled="!this.isEditMode"
            clearable
            />
          </el-form-item>
          <el-form-item label="综合油耗(L/100km)" prop="stdModelExtraAddVO.oilWear">
            <el-input
            v-model="form2.stdModelExtraAddVO.oilWear"
            placeholder="请输入综合油耗"
            style="width: 100%"
            :disabled="!this.isEditMode"
            clearable
            />
          </el-form-item>  
        </div>
        <div class="itemInline">    
          <el-form-item label="最大功率kw" prop="stdModelExtraAddVO.maximumPower">
            <el-input
            v-model="form2.stdModelExtraAddVO.maximumPower"
            placeholder="请输入最大功率"
            style="width: 100%"
            :disabled="!this.isEditMode"
            clearable
            />
          </el-form-item>
          <el-form-item label="最大扭矩(N*m)" prop="stdModelExtraAddVO.maximumTorque">
            <el-input
            v-model="form2.stdModelExtraAddVO.maximumTorque"
            placeholder="请输入最大扭矩"
            style="width: 100%"
            :disabled="!this.isEditMode"
            clearable
            />
          </el-form-item>
        </div>
        <div class="itemInline">  
            <el-form-item label="变速箱" prop="stdModelExtraAddVO.transmissionCase" :required="true">          
            <el-select v-model="form2.stdModelExtraAddVO.transmissionCase" placeholder="请选择变速箱" :disabled="!this.isEditMode" clearable>
            <el-option
              v-for="dict3 in dict.type.transmission_case"
              :key="dict3.value"
              :label="dict3.label"
              :value="dict3.label"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="驱动方式" prop="stdModelExtraAddVO.drivingMode">
            <el-input
            v-model="form2.stdModelExtraAddVO.drivingMode"
            placeholder="请输入驱动方式"
            style="width: 100%"
            :disabled="!this.isEditMode"
            clearable
            />
          </el-form-item> 
        </div>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button v-if="this.isEditMode" type="primary" @click="submitForm2">确 定</el-button>
          <el-button @click="cancel2">取 消</el-button>
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
import { getToken } from "@/utils/auth";
import { listVehicleType, addVehicleType,updateVehicleType,delVehicleType,batchDelVehicleType,
  vehicleTypeModel,addVehicleModel,updateVehicleModel,delVehicleModel,vehicleModelDetail} from "../../../api/vehicle/vehicleType";


export default {
  name: "listVehicleType",
  dicts:['transmission_case','environmental_protection','data_type'],
  props: {
	      value: [String, Object, Array],
	      // 图片数量限制
	      limit: {
	        type: Number,
	        default: 5,
	      },
	      // 大小限制(MB)
	      fileSize: {
	        type: Number,
	        default: 5,
	      },
	      // 文件类型, 例如['png', 'jpg', 'jpeg']
	      fileType: {
	        type: Array,
	        default: () => ["png", "jpg", "jpeg"],
	      },
	      // 是否显示提示
	      isShowTip: {
	        type: Boolean,
	        default: true
	      }
	  },
  data() {
    return {
    //遮罩层
      loading: false,
    // 选中车型ID数组
      ids: [],
    // 非单个禁用
     single: true,
    // 非多个禁用
     multiple: true,
    //显示搜索条件
    showSearch: true,
    //总条数
    total: 0,  
    //车辆类型数据
    listVehicleType: [],    
    // 弹出层标题
    title: "",
    //是否可编辑模式
    isEditMode: true,
    // 是否显示弹出层
    open: false,
    open2: false, 
    //下载图片路径
    dialogImageUrl: "",
	  dialogVisible: false,
	  hideUpload: false,
	  baseUrl: 'http://10.110.1.241:8088',
    //baseUrl: 'http://10.100.18.60:8088',
	  uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器地址
    //uploadImgUrl: process.env.VUE_APP_BASE_API + "/tsp/vehicle/model/upload/1",
	    headers: {
	          Authorization: "Bearer " + getToken(),
	        },
	  fileList: [],
    //查询参数
    queryParams: {
        vehicleModelName: "",
        tspVehicleModelId: "",
        tspVehicleStdModelId: "",
        vehicleTypeModel: [],
        pageNum: 1,
        pageSize: 10,
      },
    //导入弹窗参数
        upload: {
        title: "",
        open: false,
        isUploading: false,
        updateSupport: 0,
        url: "",
        headers: {Authorization: "Bearer " + getToken() },
        },
    // 表单参数
    form: {},
    form2: {
      stdModelExtraAddVO: {
        extraImages: [],
      },
      pageNum: 1,
      pageSize: 10,
    },
    //表单校验
    rules:
    {
      stdModeName: [
          { required: true, message: "型号名称不能为空", trigger: "blur" },
          { min: 1, max: 50, message: "长度在 1 到 50 个字符", trigger: "blur" },
        ],
      dataKey: [
          { required: true, message: "请选择能源类型", trigger: "blur" },
        ],
      noticeModel: [
          { required: true, message: "请输入公告型号", trigger: "blur" },
          { min: 1, max: 50, message: "长度在 1 到 50 个字符", trigger: "blur" },
        ], 
      noticeBatch: [
          { required: true, message: "请输入公告批次", trigger: "blur" },
          { min: 1, max: 50, message: "长度在 1 到 50 个字符", trigger: "blur" },
        ],
      'stdModelExtraAddVO.dimensions':[
          { required: true, message: "请输入车身尺寸", trigger: "blur" }
      ],
        
    }, 
    //查询下拉框
    vehicleTypeModel: [],
    option: [],
    };  
  },    
  created() {    
    this.getList(); 
    this.setTreeData();            
    
  },    
  methods: {
    // 获取列表数据
    getList() {
      
        this.loading = true;
        listVehicleType(this.queryParams).then(response => {
        this.listVehicleType = response.data.list;
        this.total = response.data.total;
        
      });
      this.loading = false;
      
    },
    // 获取查询下拉框数据
    setTreeData() {

      this.vehicleTypeModel = []
      vehicleTypeModel(this.queryParams).then(response => {
        this.option = response.data;
      });
   },         
   //查询车型下拉框获取值
  handleChange(value) {
  
      console.log(value)
      if(value.length>1)
    {
       this.queryParams.tspVehicleModelId=value[0]
       this.queryParams.tspVehicleStdModelId=value[1]
       console.log(this.queryParams.tspVehicleModelId)
       console.log(this.queryParams.tspVehicleStdModelId)
    }
    else
   {  
      this.queryParams.tspVehicleModelId=value[0]
      this.queryParams.tspVehicleStdModelId=undefined
      console.log(this.queryParams.tspVehicleModelId)
      console.log(this.queryParams.tspVehicleStdModelId)
   }

  },
   // 多选框选中数据
  handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!=1
      this.multiple = !selection.length
  },
   //只允许展示一个栏目
    expandChange (row, expandedRows) {
    let that = this
    if (expandedRows.length > 1) {
      that.expands = []
      if (row) {
        that.expands.push(row)
      }
      this.$refs.refTable.toggleRowExpansion(expandedRows[0])
    } else {
      that.expands = []
    }
  },    
  /** 搜索按钮操作 */
    handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
  /** 重置按钮操作 */
    resetQuery() {
        this.resetForm("queryForm");
        this.queryParams.vehicleTypeModel = "-1";
        this.queryParams.tspVehicleStdModelId = undefined;
        this.queryParams.tspVehicleModelId = undefined;
        this.handleQuery();
      },
  // 车型表单取消按钮
    cancel() {
        this.open = false;
        this.reset();
      },
  // 二级型号表单取消按钮
    cancel2() {
        this.open2 = false;
        this.reset2();
      },
  //车型表单重置
    reset() {
        this.form = {
          vehicleModelName: "",
          tspVehicleModelId: "",
        };
        this.resetForm("form");
      },
  //车辆二级型号表单重置
    reset2() {

        this.fileList = [];
        this.form2 = {
          vehicleModelName: "",          
          stdModeName: "",
          dataKey: "",
          noticeModel: "",
          noticeBatch: "",
          tspVehicleModelId: "",
          tspVehicleStdModelId:"",
          stdModelExtraAddVO:{
            cylinderNumber: "",
            environmentalProtection: "",
            vehicleWarranty: "",
            engineType: "",
            dimensions: "",
            displacement: "",
            extraImages: [],
            oilWear: "",
            maximumPower: "",
            maximumTorque: "",
            transmissionCase: "",
            drivingMode: "",
  
          }
        };        
        this.resetForm("form2");
      },
  /** 车型提交按钮 */
    submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.tspVehicleModelId != '') {
              updateVehicleType(this.form).then(response => {
                this.$modal.msgSuccess("修改车型成功");
                this.open = false;
                this.getList();
              });
            } else {
              addVehicleType(this.form).then(response => {
                this.$modal.msgSuccess("新增车型成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
        
      },
  //车辆二级型号表单提交按钮
    submitForm2: function() {
        this.$refs["form2"].validate(valid => {
          if (valid) {
            if (this.form2.tspVehicleStdModelId != '') {  
              updateVehicleModel(this.form2).then(response => {          
                this.$modal.msgSuccess("修改型号成功");
                this.open2 = false;
                this.getList();
              });
            } else {
              addVehicleModel(this.form2).then(response => {          
                this.$modal.msgSuccess("新增车辆型号成功");
                this.open2 = false;
                this.getList();
              });
            }
          }
        });
       },
  /** 新增车辆类型按钮操作 */
    handleAdd() {
        this.reset();
        this.open = true;
        this.title = "新增车型";
      },
  //新增车辆二级型号
    handleAddModel(row) {
        this.reset2();
        this.open2 = true;
        this.form2.tspVehicleModelId = row.id;
        this.form2.vehicleModelName = row.vehicleModelName;
        this.isEditMode=true;
        this.title = "添加型号";
      },
   /** 修改车辆分类按钮操作 */
      handleUpdate(row) {
        this.form = row;
        this.form.tspVehicleModelId = row.id;
        this.form.vehicleModelName = row.vehicleModelName;
        this.open = true;
        this.title = "编辑车型";
      },
   /** 修改车辆型号按钮操作 */ 
      handleUpdateModel(row) {
          this.fileList = [];
          vehicleModelDetail(row.id).then(response => {
          this.form2.tspVehicleModelId=row.tspVehicleModelId;
          //console.log(this.form2.tspVehicleModelId) 
          this.form2=response.data;
          this.form2.stdModelExtraAddVO=response.data.stdModelExtra
          this.form2.tspVehicleStdModelId =row.id;

          //console.log(this.form2.stdModelExtraAddVO.extraImages);
          for(let i in response.data.stdModelExtraAddVO.extraImages){

           this.fileList.push({url:this.baseUrl + response.data.stdModelExtraAddVO.extraImages[i]});
        }
          //console.log(this.form2.tspVehicleStdModelId) 
        });
        this.open2 = true;
        this.isEditMode = true;
        this.title = "编辑型号";
      },
    //二级型号详情按钮操作
      handleDetail(row) {
        this.isEditMode = false;
        this.fileList = [];
        vehicleModelDetail(row.id).then(response => {
          this.form2.tspVehicleStdModelId = row.id;
          this.form2=response.data;
          this.form2.stdModelExtraAddVO=response.data.stdModelExtra;
          //console.log(this.form2.stdModelExtraAddVO.extraImages);

          for(let i in response.data.stdModelExtraAddVO.extraImages){

          this.fileList.push({url:this.baseUrl + response.data.stdModelExtraAddVO.extraImages[i]});
        }
      });
 
        //console.log(this.fileList);
        this.open2 = true;
        this.title = "二级型号详情";

      },
   /** 删除单条车辆分类数据按钮操作 */
      handleDelete(row) {
        const tspVehicleModelId = row.id ;
        this.$modal.confirm('是否确认车型名称为"' + row.vehicleModelName + '"的数据项？').then(function() {
          return delVehicleType(tspVehicleModelId);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
   /** 删除车辆型号按钮操作 */
      handleDeleteModel(row) {
        const tspVehicleStdModelId = row.id ;
        this.$modal.confirm('是否确认车辆型号为"' + row.stdModeName + '"的数据项？').then(() => {
          return delVehicleModel(tspVehicleStdModelId);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
   /** 批量删除车辆分类按钮操作 */
    batchDelete() {
        const typeIds = this.ids;
        if (typeIds.length == 0) {
          this.$message.warning("请选择需要删除的数据");
          return;
        }
        this.$modal.confirm("是否确认删除选中的"+typeIds.length+"条数据？").then(() => {
          return batchDelVehicleType(typeIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
   /** 导出按钮操作 */
    handleExport() {
        this.download('/tsp/vehicle/model/export', {
          ...this.queryParams
        }, `vehicleType_${new Date().getTime()}.xlsx`)
      },
   /** 导入车辆分类按钮操作 */
    handleImportType() {
        this.upload.title = "车辆分类导入";
        this.upload.url =process.env.VUE_APP_BASE_API+ "/tsp/vehicle/model/importVehicleModel";
        this.upload.open = true;
      },
   /** 导入车辆型号按钮操作 */
    handleImportModel() {  
        this.upload.title = "车辆型号导入";
        this.upload.url =process.env.VUE_APP_BASE_API+ "/tsp/vehicle/model/importVehicleStdModel";
        this.upload.open = true;
      },
   /** 下载模板操作 */
    importTemplate() {
        if(this.upload.title=="车辆分类导入")
        {
          this.download('/tsp/vehicle/model/importTemplateModel', {
          }, `车辆分类导入模板${new Date().getTime()}.xlsx`)
        }
        else if(this.upload.title=="车辆型号导入")
        {
          this.download('/tsp/vehicle/model/importTemplateStdModel', {
          }, `车辆型号导入模板${new Date().getTime()}.xlsx`)
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
	  // 删除图片
    handleRemove(file, fileList) {

        const findex = this.fileList.map(f => f.url).indexOf(file.url);
        if (findex > -1) {
          this.fileList.splice(findex, 1);
          //console.log(this.fileList);
          //this.form2.stdModelExtraAddVO.extraImages.splice(findex, 1);
          this.$emit("input", this.listToString(this.fileList));

          //console.log(this.form2.stdModelExtraAddVO.extraImages);
        
        }

      },
      // 图片上传成功回调
      handleUploadSuccess(res) {
        this.fileList.push({url:res.url});
        //this.fileList.push({url:res.data})
          console.log(this.fileList);
        //console.log(this.form2.stdModelExtraAddVO.extraImages);
        this.$emit("input", this.listToString(this.fileList));
        this.loading.close();
        this.getList();
      },
      // 图片上传前loading加载
      handleBeforeUpload(file) {
        let isImg = false;
        if (this.fileType.length) {
          let fileExtension = "";
          if (file.name.lastIndexOf(".") > -1) {
            fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1);
          }
          isImg = this.fileType.some(type => {
            if (file.type.indexOf(type) > -1) return true;
            if (fileExtension && fileExtension.indexOf(type) > -1) return true;
            return false;
          });
        } else {
          isImg = file.type.indexOf("image") > -1;
        }

        if (!isImg) {
          this.$message.error(
            `文件格式不正确, 请上传${this.fileType.join("/")}图片格式文件!`
          );
          return false;
        }
        if (this.fileSize) {
          const isLt = file.size / 1024 / 1024 < this.fileSize;
          if (!isLt) {
            this.$message.error(`上传图片大小不能超过 ${this.fileSize} MB!`);
            return false;
          }
        }
        this.loading = this.$loading({
          lock: true,
          text: "上传中",
          background: "rgba(0, 0, 0, 0.7)",
        });
        this.loading.close();
      },
      // 图片文件个数超出
      handleExceed() {
        this.$message.error(`上传图片数量不能超过 ${this.limit} 个!`);
      },
      // 图片上传失败
      handleUploadError() {
        this.$message({
          type: "error",
          message: "上传失败",
        });
        this.loading.close();
        this.getList();
      },
      // 图片预览
      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },
      // 图片对象转成指定字符串分隔
      listToString(list){
         let strs = "";
         let i = 0;
         this.form2.stdModelExtraAddVO.extraImages = [];
         if (list.length == 0) {

           this.form2.stdModelExtraAddVO.extraImages = [];
           return '';
         }
         else{
          for (i = 0; i < list.length; i++) {
           strs  = "";
           strs += list[i].url.substring(24);
           //strs += list[i].url;
           this.form2.stdModelExtraAddVO.extraImages[i] = strs;
         }
        }

         console.log(this.form2.stdModelExtraAddVO.extraImages);
         //return strs != '' ? strs.substring(0, strs.length - 1) : '';
      },


  },
   filters: {

    //能源类型
    filter_dataKey(value) {

      if(value==1)
         
      {
        return "混合电动"
      }
      else if(value==2)
      
      {
        return value="纯电动"
      }
      else if(value==3)
      
      {
        return value="燃料电池电动"
      }
      else if(value==4)
      
      {
        return value="插电式混动"
      }
      else if(value==5)
      
      {
        return value="增程式混动"
      }
   }
  }
}

  </script>
  <style>
   .itemInline {    
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
  </style>