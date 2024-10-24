<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="关键字" prop="search" label-width="100px">
          <el-input
            v-model="queryParams.search"
            placeholder="车型/型号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="车辆类型ID" prop="vehicleTypeId" v-if="false" label-width="100px">
          <el-input
            v-model="queryParams.vehicleTypeId"
            placeholder="车辆类型ID"
            clearable
          />
        </el-form-item>
        <el-form-item label="车辆型号ID" prop="vehicleModelId" v-if="false" label-width="100px">
          <el-input
            v-model="queryParams.vehicleModelId"
            placeholder="车辆型号ID"
            clearable
          />
        </el-form-item>
        <el-form-item label="二级车型" prop="typeModelValue" label-width="120px">
      <el-cascader
        v-model="queryParams.typeModelValue"
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
      <el-table-column label="二级型号ID" align="center" v-if="false" prop="tspEquipmentModelId"/> 
          <el-table-column prop="modelName" label="二级型号" align="center"/>
          <el-table-column label="公告型号" prop="modelNotice" width="180" align="center"></el-table-column>
          <el-table-column label="公告批次" prop="batchNumber" width="180" align="center"></el-table-column>
        <el-table-column label="关联车辆" prop="extraModel" align="center"></el-table-column>
        <el-table-column label="能源类型" prop="energyType" align="center"></el-table-column>
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
      <el-table-column label="一级车型ID" align="center" v-if="false" prop="tspEquipmentTypeId"/>
      <el-table-column label="ID" align="center" v-if="false" prop="id"/>
      <el-table-column label="序号" type="index" align="center">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1}}</span>
          </template>
        </el-table-column>
        <el-table-column label="一级车型" align="center" prop="vehicleType"></el-table-column>
        <el-table-column label="关联车辆" align="center" prop="extraType" />
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
        <el-form-item label="车辆类型ID" prop="vehicleTypeId" v-if="false" :disabled="true"/>
            <el-form-item label="车型名称" prop="name" :required="true">
              <el-input
            v-model="form.name"
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
        <el-form-item label="车辆类型ID" prop="vehicleTypeId" v-if="false" :disabled="true"/>
        <el-form-item label="车辆型号ID" prop="vehicleModelId" v-if="false" :disabled="true"/>
        <div class="itemInline">
        <el-form-item label="车型" prop="typeName" :required="true" >
          <el-input
            v-model="form2.typeName"
            placeholder="请输入车型"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item>
        <el-form-item label="型号名称" prop="modelName" :required="true">
          <el-input
            v-model="form2.modelName"
            placeholder="请输入型号名称"
            style="width:100%"
            :disabled="!this.isEditMode"
            clearable
          />
        </el-form-item>
          <el-form-item label="选择能源类型" prop="energyType" :required="true">          
            <el-select v-model="form2.energyType" placeholder="请选择能源类型" :disabled="!this.isEditMode" clearable>
            <el-option
              v-for="dict1 in dict.type.energy_type"
              :key="dict1.value"
              :label="dict1.label"
              :value="dict1.label"
            />
          </el-select>
          </el-form-item>
        </div>
        <div class="itemInline">
          <el-form-item label="公告型号" prop="modelNotice" :required="true">
          <el-input
            v-model="form2.modelNotice"
            placeholder="请输入公告型号"
            style="width: 80%"
            :disabled="!this.isEditMode"
            clearable
          />
        </el-form-item>
        <el-form-item label="公告批次" prop="batchNumber" :required="true">
          <el-input
            v-model="form2.batchNumber"
            placeholder="请输入公告批次"
            style="width: 100%"
            :disabled="!this.isEditMode"
            clearable
          />
        </el-form-item>
        </div>
        <h4 class="form-header h4" content-position="left">整车参数</h4>
        <div class="itemInline">    
          <el-form-item label="气缸数(个)" prop="cylinderNum">
            <el-input
            v-model="form2.cylinderNum"
            placeholder="请输入气缸数"
            style="width: 100%"
            :disabled="!this.isEditMode"
            clearable
            />
           </el-form-item>
           <el-form-item label="环保标准" prop="environmentalStandard" :required="true">          
            <el-select v-model="form2.environmentalStandard" placeholder="请选择环保标准" :disabled="!this.isEditMode" clearable>
            <el-option
              v-for="dict2 in dict.type.environmental_type"
              :key="dict2.value"
              :label="dict2.label"
              :value="dict2.label"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="整车质保" prop="cylinderNum">
            <el-input
            v-model="form2.cylinderNum"
            placeholder="请输入整车质保"
            style="width: 100%"
            :disabled="!this.isEditMode"
            clearable
            />
           </el-form-item>   
        </div>
        <div class="itemInline">
           <el-form-item label="发动机型号" prop="engineModel">
            <el-input
            v-model="form2.engineModel"
            placeholder="请输入发动机型号"
            style="width: 80%"
            :disabled="!this.isEditMode"
            clearable
            />
           </el-form-item> 
           <el-form-item label="车身尺寸(长*宽*高)M" prop="carSize" :required="true">          
            <el-input
            v-model="form2.carSize"
            placeholder="请输入车身尺寸"
            style="width: 100%"
            :disabled="!this.isEditMode"
            clearable
            />
           </el-form-item>           
        </div>
        <el-form-item label="车型图片" prop="modelImageUrl">
        <el-upload
            :action="'/tsp/equipmentType/export'"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :on-success="imgSuccess"
            :on-error="imgError"
            :on-remove="imgRemove"
            :disabled="!this.isEditMode"
          >
          <i class="el-icon-plus"></i>
        </el-upload>
        </el-form-item>
        <h4 class="form-header h4" content-position="left">性能参数</h4>
        <div class="itemInline">    
          <el-form-item label="发动机排量(mL)" prop="engineDisplacement">
            <el-input
            v-model="form2.engineDisplacement"
            placeholder="请输入发动机排量"
            style="width: 100%"
            :disabled="!this.isEditMode"
            clearable
            />
          </el-form-item>
          <el-form-item label="综合油耗(L/100km)" prop="combustionConsumption">
            <el-input
            v-model="form2.combustionConsumption"
            placeholder="请输入综合油耗"
            style="width: 100%"
            :disabled="!this.isEditMode"
            clearable
            />
          </el-form-item>  
        </div>
        <div class="itemInline">    
          <el-form-item label="最大功率kw" prop="power">
            <el-input
            v-model="form2.power"
            placeholder="请输入最大功率"
            style="width: 100%"
            :disabled="!this.isEditMode"
            clearable
            />
          </el-form-item>
          <el-form-item label="最大扭矩(N*m)" prop="torque">
            <el-input
            v-model="form2.torque"
            placeholder="请输入最大扭矩"
            style="width: 100%"
            :disabled="!this.isEditMode"
            clearable
            />
          </el-form-item>
        </div>
        <div class="itemInline">  
            <el-form-item label="变速箱" prop="gearbox" :required="true">          
            <el-select v-model="form2.gearbox" placeholder="请选择变速箱" :disabled="!this.isEditMode" clearable>
            <el-option
              v-for="dict3 in dict.type.gearbox_type"
              :key="dict3.value"
              :label="dict3.label"
              :value="dict3.label"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="驱动方式" prop="driveMode">
            <el-input
            v-model="form2.driveMode"
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

export default {
  name: "listVehicleType",
  dicts: {
    type: {
      energy_type: [
        { value: "电动", label: "电动" },
        { value: "燃油", label: "燃油" },
        { value: "混合动力", label: "混合动力" },
      ],
      environmental_type: [
        { value: "非标", label: "非标" },
        { value: "两者兼顾", label: "两者兼顾" },
      ],
      gearbox_type: [
        { value: "自动", label: "自动" },
        { value: "手动", label: "手动" },
        { value: "半自动", label: "半自动" },
      ],
    },
  },
  data() {
    return {
    //遮罩层
      loading: false,
    // 选中数组
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
    //查询参数
    queryParams: {
        search: "",
        vehicleTypeId: "",
        vehicleModelId: "",
        typeModelValue: [],
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
    form2: {},
    //表单校验
    rules:
    {
        name: [
          { required: true, message: "请输入车型名称", trigger: "blur" },
          { min: 1, max: 50, message: "长度在 1 到 50 个字符", trigger: "blur" },
        ],
    }, 
    //查询下拉框
    typeModelValue: [],
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
      this.listVehicleType=[
         {
          id: 1,
          vehicleType: "客车",
          extraType: "20",
          createTime: "2021-01-01 12:00:00",
          children: [
            {
              id: 11,
              vehicleModelId: 11,
              modelName: "客车型号1",
              modelNotice: "Note1",
              batchNumber: "21",
              extraModel: "41",
              energyType: "电动",
            },  
            {
              id: 12,
              vehicleModelId: 12,
              modelName: "客车型号2",
              modelNotice: "Note2",
              batchNumber: "88",
              extraModel: "55",
              energyType: "燃油",
            },  
          ]
         },

         ]
         this.loading = false;
    },
    // 获取查询下拉框数据
    setTreeData() {
      this.typeModelValue = []
      this.option = []
   },         
//查询车型下拉框获取值
handleChange(value) {
  
  console.log(value)
  if(value.length>1)
 {
  this.queryParams.vehicleTypeId=value[0]
  this.queryParams.vehicleModelId=value[1]
  console.log(this.queryParams.vehicleTypeId)
  console.log(this.queryParams.vehicleModelId)
 }
 else
 {  
  this.queryParams.vehicleTypeId=value[0]
  this.queryParams.vehicleModelId=undefined
  console.log(this.queryParams.vehicleTypeId)
  console.log(this.queryParams.vehicleModelId)
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
        this.queryParams.typeModelValue = "-1";
        this.queryParams.vehicleModelId = undefined;
        this.queryParams.vehicleTypeId = undefined;
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
          name: "",
          vehicleTypeId: "",
        };
        this.resetForm("form");
      },
//车辆二级型号表单重置
    reset2() {
        this.form2 = {
          typeName: "",          
          modelName: "",
          energyType: "",
          modelNotice: "",
          batchNumber: "",
          carSize: "",
          engineModel: "",
          engineDisplacement: "",
          combustionConsumption: "",
          power: "",
          torque: "",
          gearbox: "",
          driveMode: "",
          modelImageUrl: "",
        };        
        this.resetForm("form2");
      },
/** 车型提交按钮 */
    submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.vehicleTypeId != undefined) {
              updatedeviceType(this.form).then(response => {
                this.$modal.msgSuccess("修改车型成功");
                this.open = false;
                this.getList();
              });
            } else {
              adddeviceType(this.form).then(response => {
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
            if (this.form2.tspEquipmentModelId != undefined) {  
              updatedeviceModel(this.form2).then(response => {          
                this.$modal.msgSuccess("修改型号成功");
                this.open2 = false;
                this.getList();
              });
            } else {
              adddeviceModel(this.form2).then(response => {          
                this.$modal.msgSuccess("新增设备型号成功");
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
        this.form2.vehicleTypeId = row.id;
        this.form2.typeName = row.vehicleType;
        this.isEditMode=true;
        this.title = "添加型号";
      },
/** 修改车辆分类按钮操作 */
      handleUpdate(row) {
        this.form = row;
        this.form.vehicleTypeId = row.id;
        this.form.name = row.vehicleType;
        this.open = true;
        this.title = "编辑车型";
      },
/** 修改车辆型号按钮操作 */ 
      handleUpdateModel(row) {
        this.form2 = row;
        this.form2.vehicleModelId = row.id;
        this.open2 = true;
        this.isEditMode = true;
        this.title = "编辑型号";
      },
//二级型号详情按钮操作
      handleDetail(row) {
        this.isEditMode = false;
        this.form2 = row;
        this.form2.vehicleModelId = row.id;
        //this.form2.typeName = this.vehicleType;
        this.open2 = true;
        this.title = "二级型号详情";

      },
/** 删除单条车辆分类数据按钮操作 */
      handleDelete(row) {
        const vehicleTypeId = row.id ;
        this.$modal.confirm('是否确认设备名称为"' + row.name + '"的数据项？').then(function() {
          return deldeviceType(vehicleTypeId);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
/** 删除车辆型号按钮操作 */
      handleDeleteModel(row) {
        const vehicleModelId = row.id ;
        this.$modal.confirm('是否确认设备型号为"' + row.modelName + '"的数据项？').then(() => {
          return deldeviceModel(vehicleModelId);
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
          return batchdeldeviceType(typeIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
/** 导出按钮操作 */
    handleExport() {
        this.download('/tsp/equipmentType/export', {
          ...this.queryParams
        }, `deviceType_${new Date().getTime()}.xlsx`)
      },
/** 导入车辆分类按钮操作 */
    handleImportType() {
        this.upload.title = "车辆分类导入";
        this.upload.url =process.env.VUE_APP_BASE_API+ "/tsp/equipmentType/importEquipmentType";
        this.upload.open = true;
      },
/** 导入车辆型号按钮操作 */
    handleImportModel() {  
        this.upload.title = "车辆型号导入";
        this.upload.url =process.env.VUE_APP_BASE_API+ "/tsp/equipmentType/importEquipmentModel";
        this.upload.open = true;
      },
  /** 下载模板操作 */
    importTemplate() {
        if(this.upload.title=="车辆分类导入")
        {
          this.download('/tsp/equipmentType/importTypeTemplate', {
          }, `设备分类导入模板${new Date().getTime()}.xlsx`)
        }
        else if(this.upload.title=="车辆型号导入")
        {
          this.download('/tsp/equipmentType/importModelTemplate', {
          }, `设备型号导入模板${new Date().getTime()}.xlsx`)
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