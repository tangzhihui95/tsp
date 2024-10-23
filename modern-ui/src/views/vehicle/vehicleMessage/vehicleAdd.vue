<template>
      <div class="app-container">
        <!-- 定义步骤条 -->
    <el-steps :active="active" finish-status="success">
        <el-step title="基本信息" />
        <el-step title="销售信息" />
        <el-step title="上牌信息" />
        <el-step title="绑定信息" />
        <el-step title="出入库记录" />
    </el-steps>
        <el-form ref="form" :model="form" :rules="rules" label-width="180px">
        <div v-show="active == 1">
        <h4 class="form-header h4" content-position="left">基本信息</h4>
        <el-form-item label="车辆类型ID" prop="vehicleTypeId" v-if="false" :disabled="true"/>
        <el-form-item label="车辆型号ID" prop="vehicleModelId" v-if="false" :disabled="true"/>
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
        clerable
      /> 
       </el-form-item>
        <el-form-item label="CDU序列号" prop="cdu" >
          <el-input
            v-model="form.cdu"
            placeholder="请输入CDU序列号"
            style="width: 100%"
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
            clearable
          />
        </el-form-item>
        <el-form-item label="外观颜色" prop="color" :required="true">          
            <el-select v-model="form.color" placeholder="请选择外观颜色"clearable>
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
            clearable
          />
        </el-form-item>
        <el-form-item label="出厂日期" prop="manufactureDate">
        <el-date-picker v-model="form.manufactureDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
          :style="{width: '100%'}" placeholder="选择日期时间" clearable></el-date-picker>
      </el-form-item>
      <el-form-item label="下线日期" prop="offlineDate">
        <el-date-picker v-model="form.offlineDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
          :style="{width: '100%'}" placeholder="选择日期时间" clearable></el-date-picker>
      </el-form-item>
    </div>
    <div class="itemInline">
        <el-form-item label="电池包规格" prop="batterySpec" :required="true">          
            <el-select v-model="form.batterySpec" placeholder="请选择电池包规格" clearable>
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
            clearable
          />
        </el-form-item>
        <el-form-item label="发动机序列号" prop="engineNumber">
          <el-input
            v-model="form.engineNumber"
            placeholder="请输入发动机序列号"
            style="width: 100%"
            clearable
          />
        </el-form-item>
    </div>
    <div class="itemInline">
        <el-form-item label="电动机品牌" prop="electricMotorBrand" :required="true">          
            <el-select v-model="form.electricMotorBrand" placeholder="请选择电动机品牌" clearable>
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
            <el-select v-model="form.label" placeholder="请选择" clearable>
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
          :autosize="{minRows: 4, maxRows: 4}" :style="{width: '70%'}"></el-input>
      </el-form-item> 
      </div>            
        <div v-show="active == 2">
          <h4 class="form-header h4" content-position="left">销售信息</h4>
        <div class="itemInline">  
        <el-form-item label="购买领域" prop="buyArea" label-width="120px" >
            <el-radio-group v-model="form.buyArea" ref="radioGroup">
              <el-radio :label="1">私人用车</el-radio>
              <el-radio :label="0">单位用车</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="车辆用途" prop="useType" :required="true">          
            <el-select v-model="form.useType" placeholder="请选择车辆用途" clearable>
            <el-option
              v-for="dict5 in dict.type.use_type"
              :key="dict5.value"
              :label="dict5.label"
              :value="dict5.label"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="是否是新车" prop="isNewCar" :required="true">          
            <el-select v-model="form.isNewCar" placeholder="是否是新车" clearable>
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
            clearable
          />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard" :required="true">
          <el-input
            v-model="form.idCard"
            placeholder="请输入身份证号"
            style="width: 100%"
            clearable
          />
        </el-form-item>   
        <el-form-item label="价税合计(小写)" prop="counter">
        <el-input-number v-model="form.counter"></el-input-number>
        </el-form-item>
        </div>
        <div class="itemInline">  
          <el-form-item label="发票号码" prop="invoiceNumber" :required="true">
          <el-input
            v-model="form.invoiceNumber"
            placeholder="请输入发票号码"
            style="width: 100%"
            clearable
          />
        </el-form-item> 
        <el-form-item label="开票日期" prop="invoiceDate">
        <el-date-picker v-model="form.invoiceDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
          :style="{width: '100%'}" placeholder="选择日期时间" clearable></el-date-picker>
      </el-form-item>
      <el-form-item label="是否三包" prop="isThreePackage" label-width="120px" >
            <el-radio-group v-model="form.isThreePackage" ref="radioGroup">
              <el-radio :label="1">是</el-radio>
              <el-radio :label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </div>
        <div class="itemInline">  
          <el-form-item label="销货单位名称" prop="saleName">          
            <el-select v-model="form.saleName" placeholder="请选择销货单位名称" clearable>
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
        <el-form-item label="车辆状态" prop="vehicleStatus">          
            <el-select v-model="form.vehicleStatus" placeholder="请选择车辆状态" clearable>
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
            clearable
          />
        </el-form-item> 
        <el-form-item label="办理员工姓名" prop="clerkName" :required="true">
          <el-input
            v-model="form.clerkName"
            placeholder="请输入办理员工姓名"
            style="width: 100%"
            clearable
          />
        </el-form-item> 
        <el-form-item label="销售渠道类型" prop="saleChannelType" :required="true">          
            <el-select v-model="form.saleChannelType" placeholder="请选择销售渠道类型" clearable>
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
            <el-select v-model="form.selectProvince" placeholder="请选择省" clearable>
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
            <el-select v-model="form.selectCity" placeholder="请选择市" clearable>
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
            <el-select v-model="form.selectArea" placeholder="请选择区" clearable>
            <el-option
              v-for="item in area"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="经销商" prop="dealerName" :required="true">          
            <el-select v-model="form.dealerName" placeholder="请选择经销商" clearable>
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
            :on-success="imgSuccess"
            :on-error="imgError"
            :on-remove="imgRemove"
          >
          <i class="el-icon-plus"></i>
        </el-upload>
        </el-form-item>
        </div>
      <div v-show="active == 3">
        <h4 class="form-header h4" content-position="left">上牌信息</h4>
        <div class="itemInline">  
          <el-form-item label="车管所名称" prop="vehicleLicenseOffice" :required="true">
          <el-input
            v-model="form.vehicleLicenseOffice"
            placeholder="请输入车管所名称"
            style="width: 100%"
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
		   @change="handleChange1">
	     </el-cascader>
     </el-form-item>
     <el-form-item label="注册详细地址" prop="registerAddress">
        <el-input v-model="form.registerAddress" type="textarea" placeholder="请输入注册详细地址"
          :autosize="{minRows: 2, maxRows: 2}" :style="{width: '100%'}"></el-input>
      </el-form-item>      
        </div>
        <div class="itemInline">
          <el-form-item label="上牌日期" prop="resgisterDate">
        <el-date-picker v-model="form.resgisterDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
          :style="{width: '95%'}" placeholder="选择日期时间" clearable></el-date-picker>
      </el-form-item>  
      <el-form-item label="车牌颜色" prop="carColor" :required="true">          
            <el-select v-model="form.carColor" placeholder="请选择车牌颜色" clearable>
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
            <input type="text" placeholder="请输入车牌号" v-model="form.searchInput" @focus="showList = true" @blur="showList = false">
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
            :on-success="imgSuccess"
            :on-error="imgError"
            :on-remove="imgRemove"
          >
          <i class="el-icon-plus"></i>
        </el-upload>
        </el-form-item>
      </div>
      <div v-show="active == 4">
        <h4 class="form-header h4" content-position="left">车主绑定信息</h4>
      <div class="itemInline"> 
        <el-form-item label="车主手机号" prop="ownerPhone" :required="true">
          <el-input
            v-model="form.ownerPhone"
            placeholder="请输入车主手机号"
            style="width: 100%"
            clearable
          />
        </el-form-item> 
        <el-form-item label="车主姓名" prop="ownerName" :required="true">
          <el-input
            v-model="form.ownerName"
            placeholder="请输入车主姓名"
            style="width: 100%"
            clearable
          />
        </el-form-item>
        <el-form-item label="车主身份证号" prop="ownerIdCard" :required="true">
          <el-input
            v-model="form.ownerIdCard"
            placeholder="请输入车主身份证号"
            style="width: 100%"
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
            :on-success="imgSuccess"
            :on-error="imgError"
            :on-remove="imgRemove"
          >
          <i class="el-icon-plus"></i>
        </el-upload>
        </el-form-item>
        <el-form-item label="请上传手持身份证反面照片" prop="idCardUrl1">
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
      </div>
      <div class="itemInline">
        <el-form-item  prop="idCardUrl">
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
        <el-form-item  prop="idCardUrl1">
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
    </div>

      <div v-show="active == 5">
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
      </div>
     </el-form>
    <!-- 设置上一步下一步按钮 -->
     <div  class="button-footer">
    <el-button v-if="active < 5" type="primary" style="margin-top: 12px" @click="next">保存</el-button> 
    <el-button v-if="active > 1" type="primary" style="margin-top: 12px" @click="pre">上一步</el-button>
    <el-button v-if="active < 5" type="warning" style="margin-top: 12px" @click="next">下一步</el-button>
    <el-button v-if="active < 5" style="margin-top: 12px" @click="cancel">取消</el-button>
    <el-button v-if="active > 4" style="margin-top: 12px" @click="submit">完成</el-button>
     </div>
    </div>
  </template>

  <script>
import { regionData } from 'element-china-area-data';

export default {
  name: "vehicleAdd",
  dicts: {
    type: {
      vehicle_color: [],
      battery_spec: [],
      electric_motor_brand: [],
      label_type: [],
      use_type: [],
      is_new_car: [],
      sale_name: [],
      vehicle_status: [],
      sale_channel_type: [],
      carColor: [],
  },
},
  props: {
    items: {
      type: Array,
      required: true
    }
},
  data() {
    return {
      //车辆信息表单
      form: {},
      active: 1,
      //历史绑定设备
      listHistoryEquipment: [],
      //当前绑定设备
      listEquipment: [],
      //绑定记录
      listBindingRecord: [],
      //出入库记录
      listBoxRecord: [],
      //遮罩层
      loading: false,
      //车型下拉框
      typeModelValue: [],
      option: [],
      //车牌号参数
      showList: false,
      filteredItems: [],
      //购买领域
      buyArea: [
      { value: 1, label: "私人用车" },
      { value: 0, label: "单位用车" },
      ],
      //是否三包
      isThreePackage: [
      { value: 1, label: "是" },      
      { value: 0, label: "否" },
      ],
      //省市区
      provinces: regionData,
      cities: [],
      area: [],
      value:'',
      //上牌省市区下拉框
      options:regionData,
      selectedOptions: [],
      rules: {
        manufacturer: [
          { required: true, message: "请输入厂商", trigger: "blur" },
          { min: 2, max: 50, message: "长度在 2 到 50 个字符", trigger: "blur" }
        ],
        typeModelValue: [
          { required: true, message: "请选择车型", trigger: "change" }
        ],
        cdu: [
          { required: true, message: "请输入CDU序列号", trigger: "blur" },
          { min: 1, max: 50, message: "长度在 1 到 50 个字符", trigger: "blur" }
        ],
        configName: [
          { required: true, message: "请输入车辆配置名称", trigger: "blur" },
          { min: 2, max: 50, message: "长度在 2 到 50 个字符", trigger: "blur" }
        ],
        color: [
          { required: true, message: "请选择外观颜色", trigger: "change" }
        ],
        vin: [
          { required: true, message: "请输入VIN", trigger: "blur" },
          { min: 17, max: 17, message: "长度为17位", trigger: "blur" }
        ],
        batchNumber: [
          { required: true, message: "请输入批次号", trigger: "blur" },
          { min: 1, max: 50, message: "长度在 1 到 50 个字符", trigger: "blur" }
        ],
        manufactureDate: [
          { required: true, message: "请选择出厂日期", trigger: "change" }
        ],
        offlineDate: [
          { required: true, message: "请选择下线日期", trigger: "change" }
        ],
        batterySpec: [
          { required: true, message: "请选择电池包规格", trigger: "change" }
        ],
        batteryPackNumber: [
          { required: true, message: "请输入电池包序列号", trigger: "blur" },
          { min: 1, max: 50, message: "长度在 1 到 50 个字符", trigger: "blur" }
        ],
        engineNumber: [
          { required: true, message: "请输入发动机序列号", trigger: "blur" },
          { min: 1, max: 50, message: "长度在 1 到 50 个字符", trigger: "blur" }
        ],
        electricMotorBrand: [
          { required: true, message: "请选择电动机品牌", trigger: "change" }
        ],
        electricMotorNumber: [
          { required: true, message: "请输入电动机序列号", trigger: "blur" },
          { min: 1, max: 50, message: "长度在 1 到 50 个字符", trigger: "blur" }
        ],
      }
      
    }
  },
//监听车牌变化
  watch: {
    searchInput(newVal) {
      this.filteredItems = this.items.filter(item => item.toLowerCase().includes(newVal.toLowerCase()));
    }
  },
  methods: {
//选择车牌号
selectItem(item) {
      this.form.searchInput = item;
      this.showList = false;
      this.$emit('selected', item);
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
//获取省市区数据
    changeProvince() {
      //console.log(this.provinces)
      //console.log(this.form.selectProvince)
      this.cities = [];
      this.area = [];
      this.form.selectCity = "";
      this.form.selectArea = "";
      let cityItem = this.provinces.filter(
          (item) => item.value === this.form.selectProvince
        );
        if (cityItem[0]) {
          this.cities = cityItem[0].children;
        } 
      },
    changeCity() {
      console.log("城市选择")
      console.log(this.form.selectCity)
      this.area = [];
      this.form.selectArea = "";
      let areaItem = this.cities.filter(
          (item) => item.value === this.form.selectCity
        );
        if (areaItem[0]) {
          this.area = areaItem[0].children;
        }
        console.log(this.form.selectArea);

       },
// 上牌信息省市区
    handleChange1(value) {
  
			  console.log(this.selectedOptions)
        console.log(value)
			
      },
//下一步/保存按钮
    next() {
              if (this.active++ > 5) this.active = 1
              this.$modal.msgSuccess("保存成功");   
          },
// 上一步按钮
    pre() {
              if (this.active-- < 2) this.active = 1
        },
// 取消按钮
    cancel(){
            
              this.$store.dispatch("tagsView/delView", this.$route);
              //this.$router.go(-1);
              this.$router.push("/vehicle/vehicle-message/index/");
        },
//完成按钮
    submit() {
              this.$store.dispatch("tagsView/delView", this.$route);
              //this.$router.go(-1);
              this.$router.push("/vehicle/vehicle-message/index/");
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
 .button-footer {
   display: flex;
   justify-content: center;
   align-items:center;
 }
 .dropdown-menu {
  position: relative;
  display: inline-block;
}

.dropdown-menu input {
  width: 190px;
  height: 36px;
  box-sizing: border-box;
  padding: 5px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.dropdown-menu ul {
  position: absolute;
  top: 100%;
  left: 0;
  background-color: #fff;
  border: 1px solid #ccc;
  max-height: jpg
}
</style>
  