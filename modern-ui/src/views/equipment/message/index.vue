<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="设备" prop="search" label-width="100px">
          <el-input
            v-model="queryParams.search"
            placeholder="SN、SIM"
            clearable
            @keyup.enter.native="handleQuery"
            clerable
          />
        </el-form-item>
        <el-form-item label="设备类型-型号" prop="typeModelValue" label-width="120px">
      <el-cascader
        v-model="queryParams.typeModelValue"
        style="width:200px"
        size="small"
        :options="option"
        @change="handleChange"
        clerable
      /> 
       </el-form-item>
    <el-form-item label="设备类型ID" prop="tspEquipmentTypeId" v-if="false" label-width="100px">
        <el-input
          v-model="queryParams.tspEquipmentTypeId"
          placeholder="设备类型ID"
          clearable
        />
      </el-form-item>
      <el-form-item label="设备型号ID" prop="tspEquipmentModelId" v-if="false" label-width="100px">
        <el-input
          v-model="queryParams.tspEquipmentModelId"
          placeholder="设备型号ID"
          clearable
        />
      </el-form-item>
        <el-form-item label="车辆绑定状态" prop="bindStatus" label-width="120px">
          <el-select v-model="queryParams.bindStatus" placeholder="请选择绑定状态" clearable>
            <el-option
              v-for="dict2 in dict.type.vehicle_binding_status"
              :key="dict2.value"
              :label="dict2.label"
              :value="dict2.value"
            />
          </el-select>
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
          >新增设备</el-button>
        </el-col>
        <el-col :span="1.5" >
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['system:equipmentType:import']"
            >导入</el-button>
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
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-CircleCloseFille"
              size="mini"
              @click="handleScrap"
              v-hasPermi="['system:Model:scrap']"
            >报废</el-button>
          </el-col>
         <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="batchDelete()"
            v-hasPermi="['system:Model:remove']"
          >批量删除设备</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
  
      <el-table ref="refTable" v-loading="loading" :data="listdeviceModel"
       @selection-change="handleSelectionChange" >
       <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="设备IDs" align="center" v-if="false" prop="id"/>
      <el-table-column label="设备型号ID" align="center" v-if="false" prop="tspEquipmentModelId"/>
      <el-table-column label="序号" type="index" align="center">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1}}</span>
          </template>
        </el-table-column>
       <el-table-column label="设备SN" align="center" prop="sn"></el-table-column>
       <el-table-column label="SIM" align="center" prop="sim"></el-table-column>
       <el-table-column label="车辆绑定状态" align="center" prop="bindStatus">
       <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.bindStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="ICCID" align="center" prop="iccId"></el-table-column>
      <el-table-column label="IMSI" align="center" prop="imsi"></el-table-column>
      <el-table-column label="版本号" align="center" prop="version"></el-table-column>
        <el-table-column label="设备类型-型号" align="center" prop="typeModel"></el-table-column>
        <el-table-column  label="是否为终端">
        <template slot-scope="scope">
          <div>{{scope.row.isTerminal|capitalize}}</div>
        </template>
      </el-table-column>
        <el-table-column label="供应商代码" align="center" prop="supplierCode" />
        <el-table-column label="批次流水号" align="center" prop="serialNumber"></el-table-column>
        <el-table-column  label="在线状态">
        <template slot-scope="scope">
          <div>{{scope.row.isOnline|filter_isOnline}}</div>
        </template>
      </el-table-column>
      <el-table-column  label="注册状态">
        <template slot-scope="scope">
          <div>{{scope.row.isRegister|filter_isRegister}}</div>
        </template>
      </el-table-column>
      <el-table-column  label="报废状态">
        <template slot-scope="scope">
          <div>{{scope.row.isScrap|filter_isScrap}}</div>
        </template>
      </el-table-column>
      <el-table-column label="报废时间" align="center" prop="scrapTime" width="180">
           <template slot-scope="scope">
            <span>{{ parseTime(scope.row.scrapTime) }}</span>
          </template> 
        </el-table-column>
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
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['system:deviceModel:add']"
          >详情</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:deviceType:detail']"
            >编辑</el-button>
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
        @pagination="listdeviceModel"
      />

      <el-form-item label="设备类型ID" prop="tspEquipmentTypeId" v-if="false" label-width="100px">
        <el-input
          v-model="queryParams.tspEquipmentTypeId"
          placeholder="设备类型ID"
          clearable
        />
      </el-form-item>
      <el-form-item label="设备型号ID" prop="tspEquipmentModelId" v-if="false" label-width="100px">
        <el-input
          v-model="queryParams.tspEquipmentModelId"
          placeholder="设备型号ID"
          clearable
        />
      </el-form-item>
  
      <!-- 添加或修改设备类型对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="120px">
          <el-form-item label="设备分类ID" prop="tspEquipmentTypeId" v-if="false" :disabled="true"/>
        <el-form-item label="设备ID" prop="tspEquipmentId" v-if="false" :disabled="true"/>
        <el-form-item label="设备型号ID" prop="tspEquipmentModelId" v-if="false" :disabled="true"/>
    <el-form-item label="选择设备分类" :required="true" prop="typeModelValue">
    <el-cascader
      v-model="form.typeModelValue"
      style="width:70%"
      :options="option"
      @change="handleChange"
      :disabled="!this.isEditMode"
      clerable
    /> 
  </el-form-item>
      <el-form-item label="设备SN" prop="sn" :required="true">
            <el-input v-model="form.sn" placeholder="请输入设备SN" :disabled="!this.isEditMode" style="width: 70%" clearable/>
      </el-form-item>
      <el-form-item label="ICCID" prop="iccId" :required="true">
            <el-input v-model="form.iccId" placeholder="请输入ICCID" :disabled="!this.isEditMode" style="width: 70%" clearable/>
      </el-form-item>
      <el-form-item label="IMSI" prop="imsi" :required="true">
            <el-input v-model="form.imsi" placeholder="请输入IMSI" :disabled="!this.isEditMode" style="width: 70%" clearable/>
      </el-form-item>
      <el-form-item label="SIM" prop="sim" :required="true">
            <el-input v-model="form.sim" placeholder="请输入SIM" :disabled="!this.isEditMode" style="width: 70%" clearable/>
      </el-form-item>
      <el-form-item label="IMEI" prop="imei" :required="true">
            <el-input v-model="form.imei" placeholder="请输入IMEI" :disabled="!this.isEditMode" style="width: 70%" clearable/>
      </el-form-item>
      <el-form-item label="版本号" prop="version" :required="true">
            <el-input v-model="form.version" placeholder="请输入版本号" :disabled="!this.isEditMode" style="width: 70%" clearable/>
      </el-form-item>
      <el-form-item label="批次流水号" prop="serialNumber" :required="true" >
            <el-input v-model="form.serialNumber" placeholder="请输入批次流水号" :disabled="!this.isEditMode" style="width: 70%" clearable/>
      </el-form-item>
      <el-form-item label="供应商代码" prop="supplierCode" :required="true" >
            <el-input v-model="form.supplierCode" placeholder="请输入供应商代码" :disabled="!this.isEditMode" style="width: 70%" clearable/>
      </el-form-item>
          <el-form-item label="运营商" prop="operator" :required="true">          
            <el-select v-model="form.operator" placeholder="请选择运营商" :disabled="!this.isEditMode" style="width: 70%" clearable>
            <el-option
              v-for="dict2 in dict.type.operator"
              :key="Number(dict2.value)"
              :label="dict2.label"
              :value="Number(dict2.value)"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="是否为终端" prop="isTerminal" label-width="120px" >
            <el-radio-group v-model="form.isTerminal" ref="radioGroup" :disabled="!this.isEditMode">
              <el-radio :label="1">是</el-radio>
              <el-radio :label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="注册状态" prop="isRegister" label-width="120px" >
            <el-radio-group v-model="form.isRegister" ref="radioGroup" :disabled="!this.isEditMode">
              <el-radio :label="1">是</el-radio>
              <el-radio :label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button v-if="this.isEditMode" type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
      
      <!-- 设备报废弹窗 -->
      <el-dialog :title="title" :visible.sync="scrapOpen" width="300px" append-to-body>        
      <el-form ref="scrapForm" :model="scrapForm" :rules="rules" label-width="80px">
        <el-form-item label="登录密码" prop="password" :required="true">
          <el-input
          v-model="scrapForm.password"
          type="password"
          auto-complete="off"
          placeholder="请输入登录密码"
         />
        </el-form-item>
        <el-form-item label="设备IDs" prop="tspEquipmentIds" v-if="false" :disabled="true" ></el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm2">确 定</el-button>
          <el-button @click="cancel2">取 消</el-button>
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
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的设备信息数据
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
import { listdeviceModel, adddeviceModel,updatedeviceModel,
  deldeviceModel,scrapdeviceModel} from "@/api/equipment/model";
import { getToken } from "@/utils/auth";
import{selectdeviceType} from "@/api/equipment/deviceType";
  
export default {
    name: "listdeviceModel",
    dicts: ['sys_normal_disable', 'vehicle_binding_status', 'operator'],
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        tspEquipmentIds: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 设备信息表格数据
        listdeviceModel: [],
        // 弹出层标题
        title: "",
        //是否可编辑模式
        isEditMode: true,
        // 是否显示弹出层
        open: false,
        scrapOpen: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          search: undefined,
          typeModelValue: undefined, 
          bindStatus: undefined,
          tspEquipmentModelId: undefined,
          tspEquipmentTypeId: undefined,
        },
        // 表单参数
        form: {
        },
        //导入设备参数
        upload: {
          title: "",
          open: false,
          isUploading: false,
          updateSupport: 0,
          url:process.env.VUE_APP_BASE_API+ "/tsp/equipment/importEquipment",
          headers: {
            Authorization: "Bearer " + getToken() 
          },
        },
        // 设备报废参数
        scrapForm: {},
        // 表单校验
        rules: {
          password: [          
            { required: true, message: "请输入登录密码", trigger: "blur" },
          ],
          tspEquipmentTypeId: [
            { required: true, message: "设备类型ID不能为空", trigger: "blur" },
          ],
          tspEquipmentIds: [
            { required: true, message: "设备IDs不能为空", trigger: "blur" },
          ],  
          tspEquipmentModelId: [
            { required: true, message: "设备型号ID不能为空", trigger: "blur" },          
          ],
          tspEquipmentId: [
            { required: true, message: "设备ID不能为空", trigger: "blur" },
          ],
          sn: [          
            { required: true, message: "请输入设备SN", trigger: "blur" },
          ],
          iccId: [          
            { required: true, message: "请输入ICCID", trigger: "blur" },
          ],
          imsi: [          
            { required: true, message: "请输入IMSI", trigger: "blur" },
          ],
          sim: [          
            { required: true, message: "请输入SIM", trigger: "blur" },
          ],
          imei: [          
            { required: true, message: "请输入IMEI", trigger: "blur" },
          ],          
          version: [          
            { required: true, message: "请输入版本号", trigger: "blur" },
          ],
          serialNumber: [          
            { required: true, message: "请输入批次流水号", trigger: "blur" },
          ],
          supplierCode: [          
            { required: true, message: "请输入供应商代码", trigger: "blur" },
          ],
          operator: [          
            { required: true, message: "请选择运营商", trigger: "blur" },
          ],
         
        },
        //设备类型-型号树
        typeModelValue: [],
        option: [],
      };
    },
    created() {
      this.getList();
      this.setTreeData();
    },
        // 全局混入
  //       mounted() 
  //  {
  //   this.$refs.radioGroup.$children.forEach((item) => {
  //       item.$refs.radioGroup.removeAttribute("aria-hidden");
  //   });
  //  },
    computed: {  
      // 过滤重复值
      uniqueItems() {
      return this.items.filter((item, index, self) => {
        return index === self.findIndex((t) => (
          t.id === item.id && t.modelName === item.modelName
        ));
      });
    }
  },
  
    methods: {
      /** 查询设备信息列表 */
      getList() {
        this.loading = true;
        listdeviceModel(this.queryParams).then(response => {
          this.listdeviceModel = response.data.list;
          this.total = response.data.total;
          this.loading = false;
        });
      },
      //给设备类型-供应商-型号树赋值
      setTreeData() {
        this.typeModelValue = [];
        selectdeviceType(this.queryParams).then(response => {
        this.option = response.data;
        });
      },


      //获取树值
      handleChange(value) {
      console.log(value)
      if(value.length>1)
     {
      //this.tspEquipmentTypeId=value[0]
      this.queryParams.tspEquipmentModelId=value[2]
      this.form.tspEquipmentModelId=value[2]
      //console.log(this.tspEquipmentTypeId)
      console.log(this.queryParams.tspEquipmentModelId)
      console.log(this.form.tspEquipmentModelId)
     }
     else
     {  
      this.queryParams.tspEquipmentTypeId=value[0]
      this.form.tspEquipmentTypeId=value[0]
      //this.tspEquipmentModelId=undefined
      console.log(this.queryParams.tspEquipmentTypeId)
      console.log(this.form.tspEquipmentTypeId)
      //console.log(this.tspEquipmentModelId)
     }
    },
    
    // 取消按钮
      cancel() {
        this.open = false;
        this.title = "";
      },
      cancel2() {
        this.scrapOpen = false;
        this.reset2();
      },
      // 设备信息表单重置
      reset() {
        this.form = {
          tspEquipmentId: undefined,
          tspEquipmentModelId: undefined,
          sn: undefined,
          iccId: undefined,
          imsi: undefined,
          sim: undefined,
          imei: undefined,
          version: undefined,
          serialNumber: undefined,
          supplierCode: undefined,
          operator: undefined,
          isTerminal: undefined,    
          isRegister: undefined,
          typeModelValue: undefined,
        };
      },
      // 设备报废表单重置
      reset2() {
        this.scrapForm = {
          password: undefined,
          tspEquipmentIds: undefined,
        };
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
     /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.queryParams.typeModelValue ='-1';
        this.queryParams.tspEquipmentModelId = undefined;
        this.queryParams.tspEquipmentTypeId = undefined;
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.tspEquipmentIds = selection.map(item => item.id)
        this.single = selection.length!=1
        this.multiple = !selection.length
        //console.log(this.tspEquipmentIds)
      },
  
      /** 新增设备信息按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.isEditMode = true;
        this.title = "新增设备";
      },
      /** 修改设备信息按钮操作 */
      handleUpdate(row) {
        this.form = row;
        this.form.tspEquipmentId = row.id;
        this.form.typeModelValue = [row.tspEquipmentTypeId,row.tspEquipmentModelId,row.tspEquipmentModelId];
        this.open = true;
        console.log(this.form.id);
        this.isEditMode = true;
        this.title = "编辑设备";
      },
      //设备详情按钮操作
      handleDetail(row) {
        this.isEditMode = false;
        this.form = row;
        this.form.tspEquipmentId = row.id;
        this.form.typeModelValue = [row.tspEquipmentTypeId,row.tspEquipmentModelId,row.tspEquipmentModelId];
        this.open = true;
        this.title = "设备详情";

      },
      //设备报废按钮操作
      handleScrap() {
        if (this.tspEquipmentIds.length == 0) {
          this.$message.warning("请选择需要报废的设备信息");
          return;
        } 
        else{
          this.scrapOpen = true;
          this.title = "设备报废";
        }
        
      },
    
      /** 提交按钮 */
      submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.tspEquipmentId != undefined) {
              updatedeviceModel(this.form).then(response => {
                this.$modal.msgSuccess("修改设备信息成功");
                this.open = false;
                this.getList();
              });
            } else {
              adddeviceModel(this.form).then(response => {
                this.$modal.msgSuccess("新增设备成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
        
      },
      /** 设备报废提交按钮 */
      submitForm2: function() {
        this.$refs["scrapForm"].validate(valid => {        
          if (valid) {          
            this.scrapForm.tspEquipmentIds = this.tspEquipmentIds;     
      }
            this.$modal.confirm("是否确认报废选中设备？") .then(() => {            
              return  scrapdeviceModel(this.scrapForm);
            }).then(response => {            
              this.$modal.msgSuccess("设备报废成功");            
              this.scrapOpen = false;
              this.getList();
            });
          }
        )},
  /** 删除单条设备信息按钮操作 */
      handleDelete(row) {
        const ModelIds = row.id;
        if (ModelIds.length == 0) {
          this.$message.warning("请选择需要删除的设备信息");
          return;
        }
        this.$modal.confirm("是否确认删除选中设备信息？").then(() => {
          return deldeviceModel(ModelIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 批量删除设备信息按钮操作 */
      batchDelete() {
        const modelIds = this.tspEquipmentIds;
        if (modelIds.length == 0) {
          this.$message.warning("请选择需要删除的设备信息");
          return;
        }
        this.$modal.confirm("是否确认删除选中设备信息？").then(() => {
          return deldeviceModel(modelIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("批量删除成功");
        }).catch(() => {});
      },
      /** 导入按钮操作 */
      handleImport() {
      this.upload.title = "设备导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('/tsp/equipment/importTemplate', {
      }, `设备信息导入模板${new Date().getTime()}.xlsx`)
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
      handleExport() {
        this.download('/tsp/equipment/export', {
          ...this.queryParams
        }, `deviceModel_${new Date().getTime()}.xlsx`)

      },

    },
    //全局过滤器
    filters: {
      capitalize(value) {
        if (value) return '是'
        return value = '否'
        },
        filter_isOnline(value) {
          if (value) return '在线'
          return value = '未在线'
        },
        filter_isRegister(value) {
      if (value) return '是'
      return value = '否'
    },
    filter_isScrap(value) {
        if (value) return '是'
        return value = '否'
    }
   },


  }
  </script>