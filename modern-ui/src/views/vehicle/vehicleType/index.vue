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
      <!-- <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备类型ID" prop="equipmentTypeId" v-if="false" :disabled="true"/>
            <el-form-item label="设备类型" prop="name" :required="true">
              <el-input
            v-model="form.name"
            placeholder="请输入设备类型"
            style="width: 70%"
            clearable
          />
        </el-form-item>
          <el-form-item label="设备扩展信息" prop="extraType" label-width="120px":required="true">          
            <el-select v-model="form.extraType" placeholder="请输入设备扩展信息" clearable>
            <el-option
              v-for="dict2 in dict.type.equipment_type"
              :key="dict2.value"
              :label="dict2.label"
              :value="dict2.label"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="是否为终端设备" prop="isTerminal" label-width="120px">
            <el-radio-group v-model="form.isTerminal">
              <el-radio
              v-for="a in terminals"
              :label="a.value"
              :key="a.label"
              >{{a.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog> -->
      <!-- 添加或修改二级型号对话框 -->
      <!-- <el-dialog :title="title" :visible.sync="open2" width="500px" append-to-body>
        <el-form ref="form2" :model="form2" :rules="rules" label-width="80px">
        <el-form-item label="设备类型ID" prop="tspEquipmentTypeId" v-if="false" :disabled="true"/>
        <el-form-item label="设备型号ID" prop="tspEquipmentModelId" v-if="false" :disabled="true"/>
            <el-form-item label="设备型号" prop="modelName" :required="true">
              <el-input
            v-model="form2.modelName"
            placeholder="请输入设备型号"
            style="width: 70%"
            clearable
          />
        </el-form-item>
          <el-form-item label="供应商" prop="suppliers" :required="true">          
            <el-select v-model="form2.suppliers" placeholder="请选择供应商" clearable>
            <el-option
              v-for="dict3 in dict.type.supplier_type"
              :key="dict3.value"
              :label="dict3.label"
              :value="dict3.label"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="生产批次" prop="batchNumber" :required="true">
              <el-input
            v-model="form2.batchNumber"
            placeholder="请输入生产批次"
            style="width: 70%"
            clearable
          />
        </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm2">确 定</el-button>
          <el-button @click="cancel2">取 消</el-button>
        </div>
      </el-dialog>  -->
            <!-- 导入设备类型弹窗 -->
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
  import { listVehicleType, adddeviceType,updatedeviceType,deldeviceType,batchdeldeviceType,
    adddeviceModel,updatedeviceModel, deldeviceModel} from "@/api/vehicle/vehicleType";
  import { selectdeviceType } from "../../../api/vehicle/vehicleType";
  //import { getToken } from "@/utils/auth";
  
  export default {
    name: "listVehicleType",
    dicts: ['sys_normal_disable','equipment_type','supplier_type'],
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
        // 设备类型表格数据
        listVehicleType: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        open2: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          search: undefined,
          typeModelValue: undefined,
          vehicleModelId: undefined, 
          vehicleTypeId: undefined, 
        },
        //导入设备类型/型号参数
        upload: {
            title: "",
            open: false,
            isUploading: false,
            updateSupport: 0,
            url: "",
            headers: {
              Authorization: "Bearer " + getToken() 
            },
          },
        // 终端设备数据
        terminals: [
        { value: 1, label: "是" },
        { value: 0, label: "否" },
        ],
        // 表单参数
        form: {},
        form2: {},
        // 表单校验
        rules: {
          name: [
            { required: true, message: "设备类型不能为空", trigger: "blur" }
          ],
          extraType: [
            { required: true, message: "设备扩展信息不能为空", trigger: "blur" }
          ],
          modelName: [
            { required: true, message: "设备型号不能为空", trigger: "blur" }
          ],
          batchNumber: [
            { required: true, message: "生产批次不能为空", trigger: "blur" }
          ],
          suppliers: [
            { required: true, message: "供应商不能为空", trigger: "blur" }
          ],
      
        },
        //时间树
        typeModelValue: [],
        option: [],
      };
    },
    created() {
      this.getList();
      //this.setTreeData();
    },
    computed: {  
      // 过滤重复值
      uniqueItems() {
      return this.items.filter((item, index, self) => {
        return index === self.findIndex((t) => (
          t.id === item.id && t.name === item.name
        ));
      });
    }
  },
  
    methods: {
      /** 查询设备类型列表 */
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
        // listdeviceType(this.queryParams).then(response => {
        //   this.listdeviceType = response.data.list;
        //   this.total = response.data.total;
        //   this.loading = false;
        // });
        this.loading = false;
      },
       
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      cancel2() {
        this.open2 = false;
        this.resest2();
      },
      // 表单重置
      reset() {
        this.form = {
          id: undefined,
          equipmentTypeId: undefined,
          name: undefined,
          extraType: undefined,
          isTerminal: "0",
        };
        his.resetForm("form");
      },
      resest2() {
        this.form2={  
          tspEquipmentTypeId: undefined,  
          tspEquipmentModelId: undefined,  
          modelName: undefined,  
          suppliers: undefined,  
          batchNumber: undefined,  
        };
        this.resetForm("form2");
  
      },
      /** 搜索按钮操作 */
      // handleQuery() {
      //   this.queryParams.pageNum = 1;
      //   this.getList();
      // },
     /** 重置按钮操作 */
      // resetQuery() {
      //   this.resetForm("queryForm");
      //   this.queryParams.typeModelValue = "-1";
      //   this.queryParams.tspEquipmentModelId = undefined;
      //   this.queryParams.tspEquipmentTypeId = undefined;
      //   this.handleQuery();
      // },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length!=1
        this.multiple = !selection.length
      },
      //获取树值
      //  handleChange(value) {
  
      //   console.log(value)
      //   if(value.length>1)
      //  {
        //this.tspEquipmentTypeId=value[0]
        // this.queryParams.tspEquipmentModelId=value[2]
        //console.log(this.tspEquipmentTypeId)
      //   console.log(this.queryParams.tspEquipmentModelId)
      //  }
      //  else
      //  {  
      //   this.queryParams.tspEquipmentTypeId=value[0]
        //this.tspEquipmentModelId=undefined
        // console.log(this.queryParams.tspEquipmentTypeId)
        //console.log(this.tspEquipmentModelId)
      //  }
    
      // },
      //给下拉框赋值
  // setTreeData() {  
  //   this.typeModelValue = [];
  //   selectdeviceType(this.queryParams).then(response => {
  //         this.option = response.data;
  //       });
  // },
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
  
      /** 新增设备分类按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "新增分类";
      },
      /** 新增设备型号按钮操作 */
      handleAddModel(row) {
        this.resest2();
        this.open2 = true;
        this.form2.tspEquipmentTypeId = row.id;
        this.title = "添加型号";
      },
  
      /** 修改分类按钮操作 */
      handleUpdate(row) {
        this.form = row;
        this.form.equipmentTypeId = row.id;
        this.open = true;
        this.title = "编辑分类";
      },
      /** 修改设备型号按钮操作 */ 
      handleUpdateModel(row) {
        this.form2 = row;
        this.form2.tspEquipmentModelId = row.id;
        this.open2 = true;
        this.title = "编辑型号";
      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.equipmentTypeId != undefined) {
              updatedeviceType(this.form).then(response => {
                this.$modal.msgSuccess("修改设备类型成功");
                this.open = false;
                this.getList();
              });
            } else {
              adddeviceType(this.form).then(response => {
                this.$modal.msgSuccess("新增设备类型成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
        
      },
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
      /** 删除单条设备分类数据按钮操作 */
      handleDelete(row) {
        const typeId = row.id ;
        this.$modal.confirm('是否确认设备名称为"' + row.name + '"的数据项？').then(function() {
          return deldeviceType(typeId);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 删除设备型号按钮操作 */
      handleDeleteModel(row) {
        const modelId = row.id ;
        this.$modal.confirm('是否确认设备型号为"' + row.modelName + '"的数据项？').then(function() {
          return deldeviceModel(modelId);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
  /** 批量删除设备分类按钮操作 */
      batchDelete() {
        const typeIds = this.ids;
        if (typeIds.length == 0) {
          this.$message.warning("请选择需要删除的数据");
          return;
        }
        this.$modal.confirm("是否确认删除选中数据？").then(() => {
          return batchdeldeviceType(typeIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
       /** 导入设备分类按钮操作 */
       handleImportType() {
        this.upload.title = "设备类型导入";
        this.upload.url =process.env.VUE_APP_BASE_API+ "/tsp/equipmentType/importEquipmentType";
        this.upload.open = true;
      },
      handleImportModel() {  
        this.upload.title = "设备型号导入";
        this.upload.url =process.env.VUE_APP_BASE_API+ "/tsp/equipmentType/importEquipmentModel";
        this.upload.open = true;
      },
      /** 下载模板操作 */
      importTemplate() {
        if(this.upload.title=="设备类型导入")
        {
          this.download('/tsp/equipmentType/importTypeTemplate', {
          }, `设备分类导入模板${new Date().getTime()}.xlsx`)
        }
        else if(this.upload.title=="设备型号导入")
        {
          this.download('/tsp/equipmentType/importModelTemplate', {
          }, `设备型号导入模板${new Date().getTime()}.xlsx`)
        }
        
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
        this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
        this.getList();
      },
      // 提交上传文件
      submitFileForm() {
        this.$refs.upload.submit();
      },
      /** 导出按钮操作 */
      // handleExport() {
      //   this.download('/tsp/equipmentType/export', {
      //     ...this.queryParams
      //   }, `deviceType_${new Date().getTime()}.xlsx`)
      // }
  
    },
  
    filters: {
      capitalize(value) {
        if (value) return '是'
        return value = '否'
        }
   }
  };