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
        <el-form-item label="关联账号" prop="mobile" label-width="120px">
          <el-select v-model="queryParams.mobile" placeholder="请选择关联账号" clearable>
            <el-option
              v-for="dict in dict.type.vehicle_relation_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="车辆状态" prop="state" label-width="120px">
          <el-select v-model="queryParams.state" placeholder="请选择车辆状态" clearable>
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
        <el-form-item label="认证状态" prop="certificationState" label-width="120px">
          <el-select v-model="queryParams.certificationState" placeholder="请选择认证状态" clearable>
            <el-option
              v-for="dict4 in dict.type.vehicle_certified_status"
              :key="dict4.value"
              :label="dict4.label"
              :value="dict4.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="推送状态" prop="sendStatus" label-width="120px">
          <el-select v-model="queryParams.sendStatus" placeholder="请选择推送状态" clearable>
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
      <el-table-column label="设备ID" align="center" v-if="false" prop="tspEquipmentId"/>
      <el-table-column label="车辆类型ID" align="center" v-if="false" prop="tspVehicleModelId"/>
      <el-table-column label="车辆型号ID" align="center" v-if="false" prop="tspVehicleStdModelId"/>
      <el-table-column label="序号" type="index" align="center">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1}}</span>
          </template>
        </el-table-column>
       <el-table-column label="VIN" align="center" prop="vin"></el-table-column>
       <el-table-column label="车牌号" align="center" prop="plateCode"></el-table-column>
       <el-table-column label="SIM" align="center" prop="sim"></el-table-column>
       <el-table-column label="车型" align="center" prop="vehicleType"></el-table-column>
       <el-table-column label="设备绑定状态" align="center" prop="bindStatus">
       <template slot-scope="scope">
          <dict-tag :options="dict.type.vehicle_binding_status" :value="scope.row.bindStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="关联账号" align="center" prop="mobile">
       <template slot-scope="scope">
          <dict-tag :options="dict.type.vehicle_relation_no" :value="scope.row.mobile"/>
        </template>
      </el-table-column>
      <el-table-column label="认证状态" align="center" prop="certificationState">
       <template slot-scope="scope">
          <dict-tag :options="dict.type.vehicle_certified_status" :value="scope.row.certificationState"/>
        </template>
      </el-table-column>
       <el-table-column label="车辆状态" align="center" prop="state">
       <template slot-scope="scope">
          <dict-tag :options="dict.type.vehicle_vehicle_status" :value="scope.row.state"/>
        </template>
      </el-table-column>
      <el-table-column label="推送状态" align="center" prop="sendStatus">
       <template slot-scope="scope">
          <dict-tag :options="dict.type.vehicle_push_status" :value="scope.row.sendStatus"/>
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
              icon="el-icon-connection"
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
              icon="el-icon-d-arrow-right"
              @click="handlePush(scope.row)"
              v-hasPermi="['system:deviceType:remove']"
            >推送</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-refresh"
              @click="handlePush(scope.row)"
              v-hasPermi="['system:deviceType:remove']"
            >更换车卡信息</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-s-claim"
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

      <!-- 查看详情对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1300px" append-to-body>
   
    <el-form ref="form" :model="form" label-width="120px">
      <el-tabs v-model="activeName" class="demo-tabs">

         <el-tab-pane label="基本信息" name="first">
          <h4 class="form-header h4" content-position="left">基本信息</h4>
        <el-form-item label="车辆类型ID" prop="tspVehicleModelId" v-if="false" :disabled="true"/>
        <el-form-item label="车辆型号ID" prop="tspVehicleStdModelId" v-if="false" :disabled="true"/>
        <div class="itemInline">
        <el-form-item label="车辆厂商" prop="manufacturer":required="true" >
          <el-input
            v-model="form.manufacturer"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item>
        <el-form-item label="车辆型号" prop="typeModelValue" label-width="120px">
      <el-cascader
        v-model="form.typeModelValue"
        style="width:100%"
        :options="option"
        @change="handleChange"
        :disabled="true"
        clerable
      /> 
       </el-form-item>
        <el-form-item label="CDU序列号" prop="cdu" >
          <el-input
            v-model="form.cdu"
            placeholder="请输入CDU序列号"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item>
        </div>
        <div class="itemInline">
        <el-form-item label="车辆配置名称" prop="configName" :required="true">
          <el-input
            v-model="form.configName"
            placeholder="请输入车辆配置名称"
            style="width:100%"
            :disabled="true"
            clearable
          />
        </el-form-item>
        <el-form-item label="外观颜色" prop="color" :required="true">          
            <el-select v-model="form.color" :disabled="true" placeholder="请选择外观颜色"clearable>
            <el-option
              v-for="dict1 in dict.type.vehicle_color"
              :key="dict1.value"
              :label="dict1.label"
              :value="dict1.label"
            />
          </el-select>
          </el-form-item> 
          <el-form-item label="VIN" prop="vin" :required="true">
          <el-input
            v-model="form.vin"
            placeholder="请输入VIN"
            style="width:100%"
            :disabled="true"
            clearable
          />
        </el-form-item>      
        </div>
        <div class="itemInline">
          <el-form-item label="批次号" prop="batchNumber" :required="true">
          <el-input
            v-model="form.batchNumber"
            placeholder="请输入批次号"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item>
        <el-form-item label="出厂日期" prop="manufactureDate">
        <el-date-picker v-model="form.manufactureDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
          :style="{width: '100%'}" :disabled="true" placeholder="选择日期时间" clearable></el-date-picker>
      </el-form-item>
      <el-form-item label="下线日期" prop="offlineDate">
        <el-date-picker v-model="form.offlineDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
          :style="{width: '100%'}" :disabled="true" placeholder="选择日期时间" clearable></el-date-picker>
      </el-form-item>
    </div>
    <div class="itemInline">
        <el-form-item label="电池包规格" prop="batterySpec" :required="true">          
            <el-select v-model="form.batterySpec" :disabled="true" placeholder="请选择电池包规格" clearable>
            <el-option
              v-for="dict2 in dict.type.battery_spec"
              :key="dict2.value"
              :label="dict2.label"
              :value="dict2.label"
            />
          </el-select>
          </el-form-item>
        <el-form-item label="电池包编号" prop="batteryPackNumber" :required="true">
          <el-input
            v-model="form.batteryPackNumber"
            placeholder="请输入电池包序列号"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item>
        <el-form-item label="发动机序列号" prop="engineNumber">
          <el-input
            v-model="form.engineNumber"
            placeholder="请输入发动机序列号"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item>
    </div>
    <div class="itemInline">
        <el-form-item label="电动机品牌" prop="electricMotorBrand" :required="true">          
            <el-select v-model="form.electricMotorBrand" :disabled="true" placeholder="请选择电动机品牌" clearable>
            <el-option
              v-for="dict3 in dict.type.electric_motor_brand"
              :key="dict3.value"
              :label="dict3.label"
              :value="dict3.label"
            />
          </el-select>
          </el-form-item>
        <el-form-item label="电动机序列号" prop="electricMotorNumber" :required="true">
          <el-input
            v-model="form.electricMotorNumber"
            placeholder="请输入电动机序列号"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item>        
    </div>
        <h4 class="form-header h4" content-position="left">当前设备信息</h4>
        <el-table ref="refTable1" v-loading="loading" :data="listEquipment">
      <el-table-column label="设备ID" align="center" v-if="false" prop="tspEquipmentId"/>
      <el-table-column label="设备型号ID" align="center" v-if="false" prop="tspEquipmentModelId"/>
      <el-table-column label="设备类型ID" align="center" v-if="false" prop="tspEquipmentTypeId"/>
      <el-table-column label="序号" type="index" align="center">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1}}</span>
          </template>
        </el-table-column>
       <el-table-column label="设备类型-型号" align="center" prop="typeModel"></el-table-column>
       <el-table-column label="设备SN" align="center" prop="sn"></el-table-column>
       <el-table-column label="车联网卡" align="center" prop="sim"></el-table-column>
       <el-table-column label="ICCID" align="center" prop="iccid"></el-table-column>
       <el-table-column label="IMEI" align="center" prop="imei"></el-table-column>
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
            @click="handleSelect(scope.row)"
            v-hasPermi="['system:deviceModel:add']"
          >选择设备</el-button>
          <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:deviceType:remove']"
            >解绑设备</el-button>
          </template>
        </el-table-column>
      </el-table>
        <h4 class="form-header h4" content-position="left">历史绑定设备</h4>
        <el-table ref="refTable2" v-loading="loading" :data="listHistoryEquipment">
      <el-table-column label="设备ID" align="center" v-if="false" prop="tspEquipmentId"/>
      <el-table-column label="设备型号ID" align="center" v-if="false" prop="tspEquipmentModelId"/>
      <el-table-column label="设备类型ID" align="center" v-if="false" prop="tspEquipmentTypeId"/>
      <el-table-column label="序号" type="index" align="center">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1}}</span>
          </template>
        </el-table-column>
       <el-table-column label="设备类型-型号" align="center" prop="typeModel"></el-table-column>
       <el-table-column label="设备SN" align="center" prop="sn"></el-table-column>
       <el-table-column label="车联网卡" align="center" prop="sim"></el-table-column>
       <el-table-column label="ICCID" align="center" prop="iccid"></el-table-column>
       <el-table-column label="IMEI" align="center" prop="imei"></el-table-column>
        <el-table-column label="绑定时间" align="center" prop="bindTime" width="180">
           <template slot-scope="scope">
            <span>{{ parseTime(scope.row.bindTime) }}</span>
          </template> 
        </el-table-column>
        <el-table-column label="解绑时间" align="center" prop="unbindTime" width="180">
           <template slot-scope="scope">
            <span>{{ parseTime(scope.row.unbindTime) }}</span>
          </template> 
        </el-table-column>
        <el-table-column label="上传时间" align="center" prop="uploadTime" width="180">
           <template slot-scope="scope">
            <span>{{ parseTime(scope.row.uploadTime) }}</span>
          </template> 
        </el-table-column>
      </el-table>
        <h4 class="form-header h4" content-position="left">其他信息</h4>
        <el-form-item label="标签" prop="label">          
            <el-select v-model="form.label" :disabled="true" placeholder="请选择" clearable>
            <el-option
              v-for="dict4 in dict.type.label_type"
              :key="dict4.value"
              :label="dict4.label"
              :value="dict4.label"
            />
          </el-select>
          </el-form-item>    
        <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" type="textarea" placeholder="请输入备注"
          :autosize="{minRows: 4, maxRows: 4}" :disabled="true" :style="{width: '70%'}"></el-input>
      </el-form-item>
         </el-tab-pane>
         <el-tab-pane label="销售信息" name="second">
          <h4 class="form-header h4" content-position="left">销售信息</h4>
        <div class="itemInline">  
        <el-form-item label="购买领域" prop="buyArea" label-width="120px" >
            <el-radio-group v-model="form.buyArea" :disabled="true" ref="radioGroup">
              <el-radio :label="1">私人用车</el-radio>
              <el-radio :label="0">单位用车</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="车辆用途" prop="useType" :required="true">          
            <el-select v-model="form.useType" :disabled="true" placeholder="请选择车辆用途" clearable>
            <el-option
              v-for="dict5 in dict.type.use_type"
              :key="dict5.value"
              :label="dict5.label"
              :value="dict5.label"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="是否是新车" prop="isNewCar" :required="true">          
            <el-select v-model="form.isNewCar" :disabled="true" placeholder="是否是新车" clearable>
            <el-option
              v-for="dict6 in dict.type.is_new_car"
              :key="dict6.value"
              :label="dict6.label"
              :value="dict6.label"
            />
          </el-select>
          </el-form-item>
        </div>
        <div class="itemInline">  
          <el-form-item label="购买方名称" prop="buyName" :required="true">
          <el-input
            v-model="form.buyName"
            placeholder="请输入购买方名称"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard" :required="true">
          <el-input
            v-model="form.idCard"
            placeholder="请输入身份证号"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item>   
        <el-form-item label="价税合计(小写)" prop="counter">
        <el-input-number v-model="form.counter" :disabled="true"></el-input-number>
        </el-form-item>
        </div>
        <div class="itemInline">  
          <el-form-item label="发票号码" prop="invoiceNumber" :required="true">
          <el-input
            v-model="form.invoiceNumber"
            placeholder="请输入发票号码"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item> 
        <el-form-item label="开票日期" prop="invoiceDate">
        <el-date-picker v-model="form.invoiceDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
          :style="{width: '100%'}" :disabled="true" placeholder="选择日期时间" clearable></el-date-picker>
      </el-form-item>
      <el-form-item label="是否三包" prop="isThreePackage" label-width="120px" >
            <el-radio-group v-model="form.isThreePackage" :disabled="true" ref="radioGroup">
              <el-radio :label="1">是</el-radio>
              <el-radio :label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </div>
        <div class="itemInline">  
          <el-form-item label="销货单位名称" prop="saleName">          
            <el-select v-model="form.saleName" :disabled="true" placeholder="请选择销货单位名称" clearable>
            <el-option
              v-for="dict7 in dict.type.sale_name"
              :key="dict7.value"
              :label="dict7.label"
              :value="dict7.label"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="销货单位地址" prop="saleAddress" >
          <el-input
            v-model="form.saleAddress"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item>
        <el-form-item label="车辆状态" prop="state">          
            <el-select v-model="form.state" :disabled="true" placeholder="请选择车辆状态" clearable>
            <el-option
              v-for="dict8 in dict.type.vehicle_status"
              :key="dict8.value"
              :label="dict8.label"
              :value="dict8.label"
            />
          </el-select>
          </el-form-item>
        </div>
        <div class="itemInline"> 
          <el-form-item label="销售渠道名称" prop="saleChannelName" :required="true">
          <el-input
            v-model="form.saleChannelName"
            placeholder="请输入销售渠道名称"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item> 
        <el-form-item label="办理员工姓名" prop="clerkName" :required="true">
          <el-input
            v-model="form.clerkName"
            placeholder="请输入办理员工姓名"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item> 
        <el-form-item label="销售渠道类型" prop="saleChannelType" :required="true">          
            <el-select v-model="form.saleChannelType" :disabled="true" placeholder="请选择销售渠道类型" clearable>
            <el-option
              v-for="dict9 in dict.type.sale_channel_type"
              :key="dict9.value"
              :label="dict9.label"
              :value="dict9.label"
            />
          </el-select>
          </el-form-item>
        </div>
        <div class="itemInline">  
          <el-form-item label="经销商省份" prop="selectProvince">          
            <el-select v-model="form.selectProvince" :disabled="true" placeholder="请选择省" clearable>
            <el-option
              v-for="item in provinces"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              @click.native="changeProvince()"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="经销商城市" prop="selectCity">          
            <el-select v-model="form.selectCity" :disabled="true" placeholder="请选择市" clearable>
            <el-option
              v-for="item in cities"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              @click.native="changeCity"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="经销商区县" prop="selectArea">          
            <el-select v-model="form.selectArea" :disabled="true" placeholder="请选择区" clearable>
            <el-option
              v-for="item in area"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="经销商" prop="dealerName" :required="true">          
            <el-select v-model="form.dealerName" :disabled="true" placeholder="请选择经销商" clearable>
            <el-option
              v-for="dict7 in dict.type.sale_name"
              :key="dict7.value"
              :label="dict7.label"
              :value="dict7.label"
            />
          </el-select>
          </el-form-item>
        </div>
        <el-form-item label="发票" prop="invoiceUrl">
        <el-upload
            :action="'/tsp/equipmentType/export'"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :disabled="true"
          >
          <i class="el-icon-plus"></i>
        </el-upload>
        </el-form-item>
        </el-tab-pane>
         <el-tab-pane label="上牌信息" name="third">
          <h4 class="form-header h4" content-position="left">上牌信息</h4>
        <div class="itemInline">  
          <el-form-item label="车管所名称" prop="vehicleLicenseOffice" :required="true">
          <el-input
            v-model="form.vehicleLicenseOffice"
            placeholder="请输入车管所名称"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item> 
      <el-form-item label="上牌地">
	     <el-cascader
		     size="large"
		     :options="options"
		     placeholder="请选择"
		     expand-trigger="hover"
		     ref="cascaderAddr"
		     v-model="form.selectedOptions"
         clerable
         :disabled="true"
         >
	     </el-cascader>
     </el-form-item>
     <el-form-item label="注册详细地址" prop="registerAddress">
        <el-input v-model="form.registerAddress" type="textarea" placeholder="请输入注册详细地址"
          :autosize="{minRows: 2, maxRows: 2}" :disabled="true" :style="{width: '100%'}"></el-input>
      </el-form-item>      
        </div>
        <div class="itemInline">
          <el-form-item label="上牌日期" prop="resgisterDate">
        <el-date-picker v-model="form.resgisterDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
          :style="{width: '95%'}" :disabled="true" placeholder="选择日期时间" clearable></el-date-picker>
      </el-form-item>  
      <el-form-item label="车牌颜色" prop="carColor" :required="true">          
            <el-select v-model="form.carColor" :disabled="true" placeholder="请选择车牌颜色" clearable>
            <el-option
              v-for="dict8 in dict.type.carColor"
              :key="dict8.value"
              :label="dict8.label"
              :value="dict8.label"
            />
          </el-select>
        </el-form-item>
      <el-form-item label="车牌号" prop="searchInput">
        <div class="dropdown-menu">
            <input type="text" placeholder="请输入车牌号" :disabled="true" v-model="form.searchInput" @focus="showList = true" @blur="showList = false">
              <ul v-if="showList">
                <li v-for="(item, index) in filteredItems" :key="index" @click="selectItem(item)">
                 {{ item }}
                </li>
              </ul>
          </div>
      </el-form-item>
        </div>
        <el-form-item label="上传车辆照片" prop="vehicleUrl">
        <el-upload
            :action="'/tsp/equipmentType/export'"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :disabled="true"
          >
          <i class="el-icon-plus"></i>
        </el-upload>
        </el-form-item>
        </el-tab-pane>
         <el-tab-pane label="绑定信息" name="fourth">
          <h4 class="form-header h4" content-position="left">车主绑定信息</h4>
      <div class="itemInline"> 
        <el-form-item label="车主手机号" prop="ownerPhone" :required="true">
          <el-input
            v-model="form.ownerPhone"
            placeholder="请输入车主手机号"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item> 
        <el-form-item label="车主姓名" prop="ownerName" :required="true">
          <el-input
            v-model="form.ownerName"
            placeholder="请输入车主姓名"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item>
        <el-form-item label="车主身份证号" prop="ownerIdCard" :required="true">
          <el-input
            v-model="form.ownerIdCard"
            placeholder="请输入车主身份证号"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item>
      </div>
      <div class="itemInline">
      <el-form-item label="请上传手持身份证正面照片"  prop="idCardUrl">
        <el-upload
            :action="'/tsp/equipmentType/export'"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :disabled="true"
          >
          <i class="el-icon-plus"></i>
        </el-upload>
        </el-form-item>
        <el-form-item label="请上传手持身份证反面照片" prop="idCardUrl1">
        <el-upload
            :action="'/tsp/equipmentType/export'"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :disabled="true"
          >
          <i class="el-icon-plus"></i>
        </el-upload>
        </el-form-item>
      </div>
      <div class="itemInline">
        <el-form-item  prop="idCardUrl">
        <el-upload
            :action="'/tsp/equipmentType/export'"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :disabled="true"
          >
          <i class="el-icon-plus"></i>
        </el-upload>
        </el-form-item>
        <el-form-item  prop="idCardUrl1">
        <el-upload
            :action="'/tsp/equipmentType/export'"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview""
            :disabled="true"
          >
          <i class="el-icon-plus"></i>
        </el-upload>
        </el-form-item> 
      </div>
      <h4 class="form-header h4" content-position="left">绑定记录</h4>
      <el-table ref="refTable3" v-loading="loading" :data="listBindingRecord">
        <el-table-column label="序号" type="index" align="center">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1}}</span>
          </template>
        </el-table-column>
        <el-table-column label="车主ID" align="center" v-if="false" prop="ownerId"/>
        <el-table-column label="手机号" align="center" prop="ownerPhone"></el-table-column>
        <el-table-column label="车主姓名" align="center" prop="ownerName"></el-table-column>
        <el-table-column label="身份证号" align="center" prop="ownerIdCard"></el-table-column>
        <el-table-column label="操作用户" align="center" prop="operatorName"></el-table-column>
        <el-table-column label="操作时间" align="center" prop="operateTime" width="180">
           <template slot-scope="scope">
            <span>{{ parseTime(scope.row.operateTime) }}</span>
          </template> 
        </el-table-column>
      </el-table>
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="listBindingRecord"
        />
        </el-tab-pane>
         <el-tab-pane label="出入库记录" name="fifth">
          <h4 class="form-header h4" content-position="left">出入库记录</h4>
       <el-table ref="refTable4" v-loading="loading" :data="listBoxRecord">
        <el-table-column label="序号" type="index" align="center">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1}}</span>
          </template>
        </el-table-column>
        <el-table-column label="车辆ID" align="center" v-if="false" prop="id"/>
        <el-table-column label="出入库类型" align="center" prop="boxType"></el-table-column>
        <el-table-column label="出入库时间" align="center" prop="boxTime" width="180">
           <template slot-scope="scope">
            <span>{{ parseTime(scope.row.boxTime) }}</span>
          </template> 
        </el-table-column>
        <el-table-column label="出入库名称" align="center" prop="boxName"></el-table-column>
        <el-table-column label="操作用户" align="center" prop="operatorName"></el-table-column>
        <el-table-column label="操作时间" align="center" prop="operateTime" width="180">
           <template slot-scope="scope">
            <span>{{ parseTime(scope.row.operateTime) }}</span>
          </template> 
        </el-table-column>
      </el-table>
    </el-tab-pane>
   
    </el-tabs>
  </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancel">关闭</el-button>
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
        <el-form-item label="车辆IDs" prop="vehicleIds" v-if="false" :disabled="true" ></el-form-item>
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
import { listVehicleMessage } from "../../../api/vehicle/vehicleMessage";

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
    //历史绑定设备
      listHistoryEquipment: [],
    //当前绑定设备
      listEquipment: [],
    //绑定记录
      listBindingRecord: [],
    //出入库记录
      listBoxRecord: [],
    //详情框弹窗
      activeName: "first",
    //查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        search: undefined,
        typeModelValue: [],
        tspVehicleModelId: undefined,
        tspVehicleStdModelId: undefined,
        bindStatus: undefined,
        mobile: undefined,
        certificationState: undefined,
        state: undefined,
        sendStatus: undefined,
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
      //导入弹窗参数
      upload: {
        title: "",
        open: false,
        isUploading: false,
        updateSupport: 0,
        url: "",
        headers: {Authorization: "Bearer " + getToken() },
      },
      //省市区
      provinces: [],
      cities: [],
      area: [],
      value:'',
      //省市区下拉框
      options: [],
      //车牌号参数
      showList: false,
      filteredItems: [],
      //报废表单验证规则
      rules: {
        password: [ 
          { required: true, message: "请输入登录密码", trigger: "blur" }  
        ],
      },
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
      listVehicleMessage(this.queryParams).then(response => {
        this.listVehicle = response.data.list;
        this.total = response.data.total;
      });
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
      this.queryParams.tspVehicleStdModelId=value[1]
      this.form.tspVehicleStdModelId=value[1]
      console.log(this.queryParams.tspVehicleStdModelId)
      console.log(this.form.tspVehicleStdModelId)
     }
     else
     {  
      this.queryParams.tspVehicleModelId=value[0]
      this.form.tspVehicleModelId=value[0]
      console.log(this.queryParams.tspVehicleModelId)
      console.log(this.form.tspVehicleModelId)
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
        this.queryParams.tspVehicleStdModelId = undefined;
        this.queryParams.tspVehicleModelId = undefined;
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
    /** 编辑车辆信息按钮操作 */
    handleUpdate(row) {

      if(row.id!= undefined) {
        
        this.title = "编辑车辆";
        this.$router.push("/vehicle/vehicle-add/index/"+this.title);
        this.form = row;
      }
    },
    /** 详情按钮操作 */
    
    handleDetail(row) {

      this.title = "查看详情";
      this.open = true;

    },
    //重置详情表单
    reset() {

      this.form = {};
      this.resetForm("form");
    },
    //重置报废表单
    reset2() {

      this.scrapForm = {};
      this.resetForm("scrapForm");
    },
    /** 详情弹框取消按钮操作 */
    
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 报废弹框取消按钮操作 */
    cancel2() {
        this.scrapOpen = false;
        this.reset2();
      },
    /** 车辆报废提交按钮 */
    submitForm2: function() {
        this.$refs["scrapForm"].validate(valid => {        
          if (valid) {          
            this.scrapForm.vehicleIds = this.vehicleIds;     
      }
            this.$modal.confirm("是否确认报废选中车辆？") .then(() => {            
              return  scrapdeviceModel(this.scrapForm);
            }).then(response => {            
              this.$modal.msgSuccess("车辆报废成功");            
              this.scrapOpen = false;
              this.getList();
            });
          }
        )},
    /** 导入出厂信息按钮操作 */
    handleImport() {  
        this.upload.title = "导入出厂信息";
        this.upload.url =process.env.VUE_APP_BASE_API+ "/tsp/equipmentType/importEquipmentModel";
        this.upload.open = true;
      },
    /** 导入销售信息按钮操作 */
    handleImport2() {  
        this.upload.title = "导入销售信息";
        this.upload.url =process.env.VUE_APP_BASE_API+ "/tsp/equipmentType/importEquipmentModel";
        this.upload.open = true;
      },
    /** 下载模板操作 */
    importTemplate() {
        if(this.upload.title=="导入出厂信息")
        {
          this.download('/tsp/equipmentType/importTypeTemplate', {
          }, `出厂信息导入模板${new Date().getTime()}.xlsx`)
        }
        else if(this.upload.title=="导入销售信息")
        {
          this.download('/tsp/equipmentType/importModelTemplate', {
          }, `销售信息导入模板${new Date().getTime()}.xlsx`)
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
    /** 删除单条车辆信息按钮操作 */
    
    handleDelete(row) {

      const vehicleId=row.id;

      if(vehicleId!= undefined) {

        this.$modal.confirm('是否确认删除VIN为"' + row.vin + '"的数据项？').then(() => {
          return deldeviceType(vehicleId);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
    }
  },
    /** 批量删除车辆信息按钮操作 */
     batchDelete() {
       
      const ids=this.vehicleIds;
      if(ids.length == 0) {
        this.$message.warning("请选择需要删除的车辆信息");
        return;
      } 
      else{
        this.$modal.confirm('是否确认删除选中的'+ids.length+'条数据项？').then(() => {
          return deldeviceType(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
     }
    },
    /** 导出车辆信息按钮操作 */
     handleExport() {
      this.download('/tsp/equipment/export', {
          ...this.queryParams
        }, `车辆信息_${new Date().getTime()}.xlsx`)

      },
    /** 导出出厂信息按钮操作 */
     handleExport2() {
        this.download('/tsp/equipment/export', {
          ...this.queryParams
        }, `出厂信息_${new Date().getTime()}.xlsx`)

      },
    /** 导出销售信息按钮操作 */
    handleExport3() {
      this.download('/tsp/equipment/export', {
          ...this.queryParams
        }, `销售信息_${new Date().getTime()}.xlsx`)

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


<style>

.itemInline {    
   display: flex;
   justify-content: space-between;
   align-items: center;
 }

.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}
</style>