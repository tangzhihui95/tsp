<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="关键字" prop="search" label-width="100px">
          <el-input
            v-model="queryParams.search"
            placeholder="VIN、车牌号、SN、SIM"
            clearable
            @keyup.enter.native="handleQuery"
            clerable
          />
        </el-form-item>
        <el-form-item label="关联账号" prop="relationNo" label-width="120px">
          <el-select v-model="queryParams.relationNo" placeholder="请选择关联账号" clearable>
            <el-option
              v-for="dict in dict.type.vehicle_relation_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="车辆状态" prop="vehicleStatus" label-width="120px">
          <el-select v-model="queryParams.vehicleStatus" placeholder="请选择车辆状态" clearable>
            <el-option
              v-for="dict2 in dict.type.vehicle_vehicle_status"
              :key="dict2.value"
              :label="dict2.label"
              :value="dict2.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="设备绑定状态" prop="bindStatus" label-width="120px">
          <el-select v-model="queryParams.bindStatus" placeholder="请选择绑定状态" clearable>
            <el-option
              v-for="dict3 in dict.type.vehicle_binding_status"
              :key="dict3.value"
              :label="dict3.label"
              :value="dict3.value"
            />
          </el-select>
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
        <el-form-item label="认证状态" prop="isCertified" label-width="120px">
          <el-select v-model="queryParams.isCertified" placeholder="请选择认证状态" clearable>
            <el-option
              v-for="dict4 in dict.type.vehicle_certified_status"
              :key="dict4.value"
              :label="dict4.label"
              :value="dict4.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="推送状态" prop="pushStatus" label-width="120px">
          <el-select v-model="queryParams.pushStatus" placeholder="请选择推送状态" clearable>
            <el-option
              v-for="dict5 in dict.type.vehicle_push_status"
              :key="dict5.value"
              :label="dict5.label"
              :value="dict5.value"
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
          >新增车辆</el-button>
        </el-col>
        <el-col :span="1.5" >
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['system:equipmentType:import']"
            >导入出厂信息</el-button>
          </el-col>
          <el-col :span="1.5" >
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport2"
              v-hasPermi="['system:equipmentType:import']"
            >导入销售信息</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['equipment:type:export']"
            >导出车辆信息</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport2"
              v-hasPermi="['equipment:type:export']"
            >导出出厂信息</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport3"
              v-hasPermi="['equipment:type:export']"
            >导出销售信息</el-button>
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
          >批量删除</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
  
      <el-table ref="refTable" v-loading="loading" :data="listVehicle"
       @selection-change="handleSelectionChange" >
       <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="车辆ID" align="center" v-if="false" prop="id"/>
      <el-table-column label="车辆型号ID" align="center" v-if="false" prop="vehicleModelId"/>
      <el-table-column label="序号" type="index" align="center">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1}}</span>
          </template>
        </el-table-column>
       <el-table-column label="VIN" align="center" prop="vin"></el-table-column>
       <el-table-column label="车牌号" align="center" prop="carNumber"></el-table-column>
       <el-table-column label="SIM" align="center" prop="sim"></el-table-column>
       <el-table-column label="车型" align="center" prop="typeModel"></el-table-column>
       <el-table-column label="设备绑定状态" align="center" prop="bindStatus">
       <template slot-scope="scope">
          <dict-tag :options="dict.type.vehicle_binding_status" :value="scope.row.bindStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="关联账号" align="center" prop="relationNo">
       <template slot-scope="scope">
          <dict-tag :options="dict.type.vehicle_relation_no" :value="scope.row.relationNo"/>
        </template>
      </el-table-column>
      <el-table-column label="认证状态" align="center" prop="isCertified">
       <template slot-scope="scope">
          <dict-tag :options="dict.type.vehicle_certified_status" :value="scope.row.isCertified"/>
        </template>
      </el-table-column>
       <el-table-column label="车辆状态" align="center" prop="vehicleStatus">
       <template slot-scope="scope">
          <dict-tag :options="dict.type.vehicle_vehicle_status" :value="scope.row.vehicleStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="推送状态" align="center" prop="pushStatus">
       <template slot-scope="scope">
          <dict-tag :options="dict.type.vehicle_push_status" :value="scope.row.pushStatus"/>
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
              icon="el-icon-Connection"
              @click="handleRecord(scope.row)"
              v-hasPermi="['system:deviceType:detail']"
            >绑定记录</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:deviceType:remove']"
            >删除</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-DArrowRight"
              @click="handlePush(scope.row)"
              v-hasPermi="['system:deviceType:remove']"
            >推送</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-Refresh"
              @click="handlePush(scope.row)"
              v-hasPermi="['system:deviceType:remove']"
            >更换车卡信息</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-Checked"
              @click="handlePush(scope.row)"
              v-hasPermi="['system:deviceType:remove']"
            >实名查询</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="listVehicle"
      />

      <!-- 添加或修改设备类型对话框 -->
      <!-- <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
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
      </el-dialog> -->
      
      <!-- 设备报废弹窗 -->
      <!-- <el-dialog :title="title" :visible.sync="scrapOpen" width="300px" append-to-body>        
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
      </el-dialog> -->
      
      <!-- 导入设备弹窗 -->
    <!-- <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
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
    </el-dialog> -->
    </div>
  </template>

  <script>


export default {
  name: "vehicleModel",
  dicts: {
    type: {
      vehicle_binding_status: [],
      vehicle_relation_no: [],
      vehicle_certified_status: [],
      vehicle_vehicle_status: [],
      vehicle_push_status: [],
    }
  },
  data() {
    return {
      // 遮罩层
      loading: true,
     // 选中数组
     vehicleIds: [],
     // 非单个禁用
     single: true,
     // 非多个禁用
     multiple: true,
     // 显示搜索条件
     showSearch: true,
     //查询二级型号下拉框
     typeModelValue: [],
      option: [],
     //列表数据
     listVehicle: [],
     //总条数
      total: 0,
    //查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        search: undefined,
        typeModelValue: [],
        vehicleTypeId: undefined,
        vehicleModelId: undefined,
        bindStatus: undefined,
        relationNo: undefined,
        isCertified: undefined,
        vehicleStatus: undefined,
        pushStatus: undefined,
      },
      //新增弹窗
      open: false,
      //报废弹窗
      scrapOpen: false,
      //是否可编辑
      isEditMode: true,
      //弹窗标题
      title: "",
      //弹窗表单
      form: {},
      //报废表单
      scrapForm: {},
    };
  },
  created() {
    this.setTreeData();
    this.getList();
  },
  methods: {
    //初始化列表数据
    getList() {
      this.loading = true;
      this.listVehicle = [
        {
          id: 1,
          vehicleModelId: "11",
          vin: "HJDN3320240619333",
          carNumber:"沪A123456",
          sim: "13323215529",
          typeModel:"客车/客车型号1",
          bindStatus: "已绑定",
          relationNo: "13323215529",
          isCertified: "未认证",
          vehicleStatus: "已创建",
          pushStatus: "未推送",
          createTime: "2021-06-19 10:00:00",
        },
        {
          id: 2,
          vehicleModelId: "12",
          vin: "HJDN3320240619334",
          carNumber:"沪A123457",
          sim: "13323215530",
          typeModel:"客车/客车型号2",
          bindStatus: "已绑定",
          relationNo: "13323215530",
          isCertified: "未认证",
          vehicleStatus: "已创建",
          pushStatus: "未推送",
          createTime: "2021-06-19 10:00:00",
        },
        {
          id: 3,
          vehicleModelId: "11",
          vin: "HJDN3320240619335",
          carNumber:"沪A123458",
          sim: "13323215531",
          typeModel:"客车/客车型号1",
          bindStatus: "已绑定",
          relationNo: "13323215531",
          isCertified: "未认证",
          vehicleStatus: "已创建",
          pushStatus: "未推送",
          createTime: "2021-06-19 10:00:00",
        },
        {
          id: 4,
          vehicleModelId: "12",
          vin: "HJDN3320240619336",
          carNumber:"沪A123459",
          sim: "13323215532",
          typeModel:"客车/客车型号2",
          bindStatus: "已绑定",
          relationNo: "13323215532",
          isCertified: "未认证",
          vehicleStatus: "已创建",
          pushStatus: "未推送",
          createTime: "2021-06-19 10:00:00",
        },
      ];
      this.loading = false;
    },
    //设置树数据
    setTreeData() {
      this.typeModelValue = []
      this.option = [
        {
          label: "客车",
          value: "1",
          children: [
            {
              label: "客车型号1",
              value: "11",
            },
            {
              label: "客车型号2",
              value: "12",
            },
          ],
        },
        {
          label: "货车",
          value: "2",
          children: [
            {
              label: "货车型号1",
              value: "21",
            },
            {
              label: "货车型号2",
              value: "22",
            },
          ],  
        },
      ];
    },
    //获取树值
     handleChange(value) {
      console.log(value)
      //console.log(value.length)
      if(value.length>1)
     {
      this.queryParams.vehicleModelId=value[1]
      this.form.vehicleModelId=value[1]
      console.log(this.queryParams.vehicleModelId)
      console.log(this.form.vehicleModelId)
     }
     else
     {  
      this.queryParams.vehicleTypeId=value[0]
      this.form.vehicleTypeId=value[0]
      console.log(this.queryParams.vehicleTypeId)
      console.log(this.form.vehicleTypeId)
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
        this.queryParams.typeModelValue ='-1';
        this.queryParams.vehicleModelId = undefined;
        this.queryParams.vehicleTypeId = undefined;
        this.handleQuery();
      },  
    /** 新增车辆信息按钮操作 */
     handleAdd() {
        // this.reset();
        // this.open = true;
        // this.isEditMode = true;
        this.title = "新增车辆";
        //this.$router.push("/system/user-auth/role/");
        this.$router.push("/vehicle/vehicle-add/index/"+this.title);
      }, 
    /** 导入出厂信息按钮操作 */
     handleImport() {
      this.upload.title = "导入出厂信息";
      this.upload.open = true;
    },
    /** 导入销售信息按钮操作 */
     handleImport2() {
      this.upload.title = "导入销售信息";
      this.upload.open = true;
    },
    /** 导出车辆信息按钮操作 */
     handleExport() {
      this.download('/tsp/equipment/export', {
          ...this.queryParams
        }, `deviceModel_${new Date().getTime()}.xlsx`)

      },
    /** 导出出厂信息按钮操作 */
     handleExport2() {
        this.download('/tsp/equipment/export', {
          ...this.queryParams
        }, `deviceModel_${new Date().getTime()}.xlsx`)

      },
    /** 导出销售信息按钮操作 */
    handleExport3() {
      this.download('/tsp/equipment/export', {
          ...this.queryParams
        }, `deviceModel_${new Date().getTime()}.xlsx`)

      },
    //车辆报废按钮操作
      handleScrap() {
        if (this.vehicleIds.length == 0) {
          this.$message.warning("请选择需要报废的车辆信息");
          return;
        } 
        else{
          this.scrapOpen = true;
          this.title = "车辆报废";
        }

      },
    // 多选框选中数据
      handleSelectionChange(selection) {
        this.vehicleIds = selection.map(item => item.id)
        this.single = selection.length!=1
        this.multiple = !selection.length
        console.log(this.vehicleIds)
      },
    
  },
}
</script>