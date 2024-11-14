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

        <div v-show="active == 1">
          <el-form ref="form1" :model="form1" :rules="rules" label-width="180px">
          <el-table-column label="信息进度" v-if="false" prop="progress"/>
        <h4 class="form-header h4" content-position="left">基本信息</h4>
        <el-table-column label="车辆ID" v-if="false" prop="tspvehicleId"/>
        <el-form-item label="车辆类型ID" prop="tspVehicleModelId" v-if="false" :disabled="true"/>
        <el-form-item label="车辆型号ID" prop="tspVehicleStdModelId" v-if="false" :disabled="true"/>
        <div class="itemInline">
        <el-form-item label="车辆厂商" prop="providerName":required="true" >
          <el-input
            v-model="form1.providerName"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item>
        <el-form-item label="车辆型号" prop="vehicleTypeModel" label-width="120px">
      <el-cascader
        v-model="form1.vehicleTypeModel"
        style="width:100%"
        :options="option"
        @change="handleChange"
        clerable
      /> 
       </el-form-item>
        <el-form-item label="CDU序列号" prop="cduNum" >
          <el-input
            v-model="form1.cduNum"
            placeholder="请输入CDU序列号"
            style="width: 100%"
            clearable
          />
        </el-form-item>
        </div>
        <div class="itemInline">
        <el-form-item label="车辆配置名称" prop="configureName" :required="true">
          <el-input
            v-model="form1.configureName"
            placeholder="请输入车辆配置名称"
            style="width:100%"
            clearable
          />
        </el-form-item>
        <el-form-item label="外观颜色" prop="color" :required="true">          
            <el-select v-model="form1.color" placeholder="请选择外观颜色"clearable>
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
            v-model="form1.vin"
            placeholder="请输入VIN"
            style="width:100%"
            clearable
          />
        </el-form-item>      
        </div>
        <div class="itemInline">
          <el-form-item label="批次号" prop="batchNo" :required="true">
          <el-input
            v-model="form1.batchNo"
            placeholder="请输入批次号"
            style="width: 100%"
            clearable
          />
        </el-form-item>
        <el-form-item label="出厂日期" prop="exFactoryDate">
        <el-date-picker v-model="form1.exFactoryDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
          :style="{width: '100%'}" placeholder="选择日期时间" clearable></el-date-picker>
      </el-form-item>
      <el-form-item label="下线日期" prop="operateDate">
        <el-date-picker v-model="form1.operateDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
          :style="{width: '100%'}" placeholder="选择日期时间" clearable></el-date-picker>
      </el-form-item>
    </div>
    <div class="itemInline">
        <el-form-item label="电池包规格" prop="essModel" :required="true">          
            <el-select v-model="form1.essModel" placeholder="请选择电池包规格" clearable>
            <el-option
              v-for="dict2 in dict.type.ess_model"
              :key="dict2.value"
              :label="dict2.label"
              :value="dict2.label"
            />
          </el-select>
          </el-form-item>
        <el-form-item label="电池包编号" prop="essNum" :required="true">
          <el-input
            v-model="form1.essNum"
            placeholder="请输入电池包序列号"
            style="width: 100%"
            clearable
          />
        </el-form-item>
        <el-form-item label="发动机序列号" prop="engineNum">
          <el-input
            v-model="form1.engineNum"
            placeholder="请输入发动机序列号"
            style="width: 100%"
            clearable
          />
        </el-form-item>
    </div>
    <div class="itemInline">
        <el-form-item label="电动机品牌" prop="motorBrand" :required="true">          
            <el-select v-model="form1.motorBrand" placeholder="请选择电动机品牌" clearable>
            <el-option
              v-for="dict3 in dict.type.motor_brand"
              :key="dict3.value"
              :label="dict3.label"
              :value="dict3.label"
            />
          </el-select>
          </el-form-item>
        <el-form-item label="电动机序列号" prop="motorNum" :required="true">
          <el-input
            v-model="form1.motorNum"
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
      <el-table-column label="车辆ID" align="center" v-if="false" prop="tspvehicleId"/>
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
      <el-table-column label="车辆ID" align="center" v-if="false" prop="tspvehicleId"/>
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
        <el-table-column label="绑定时间" align="center" prop="createTime" width="180">
           <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template> 
        </el-table-column>
        <el-table-column label="解绑时间" align="center" prop="unBindTime" width="180">
           <template slot-scope="scope">
            <span>{{ parseTime(scope.row.unBindTime) }}</span>
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
            <el-select v-model="form1.label" placeholder="请选择" clearable>
            <el-option
              v-for="dict4 in dict.type.label_type"
              :key="dict4.value"
              :label="dict4.label"
              :value="dict4.label"
            />
          </el-select>
          </el-form-item>    
        <el-form-item label="备注" prop="remark">
        <el-input v-model="form1.remark" type="textarea" placeholder="请输入备注"
          :autosize="{minRows: 4, maxRows: 4}" :style="{width: '70%'}"></el-input>
      </el-form-item> 
        </el-form>
      </div>            
        <div v-show="active == 2">
          <el-form ref="form2" :model="form2" :rules="rules" label-width="180px"> 
            <el-table-column label="信息进度" v-if="false" prop="progress"/>
          <h4 class="form-header h4" content-position="left">销售信息</h4>
        <div class="itemInline">  
        <el-form-item label="购买领域" prop="purchaserState" label-width="120px" >
            <el-radio-group v-model="form2.purchaserState" ref="radioGroup">
              <el-radio :label="1">私人用车</el-radio>
              <el-radio :label="0">单位用车</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="车辆用途" prop="purpose" :required="true">          
            <el-select v-model="form2.purpose" placeholder="请选择车辆用途" clearable>
            <el-option
              v-for="dict5 in dict.type.purpose"
              :key="dict5.value"
              :label="dict5.label"
              :value="dict5.label"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="是否是新车" prop="newVehicleFlag" :required="true">          
            <el-select v-model="form2.newVehicleFlag" placeholder="是否是新车" clearable>
            <el-option
              v-for="dict6 in dict.type.is_new_vehicle"
              :key="dict6.value"
              :label="dict6.label"
              :value="dict6.label"
            />
          </el-select>
          </el-form-item>
        </div>
        <div class="itemInline">  
          <el-form-item label="购买方名称" prop="purchaser" :required="true">
          <el-input
            v-model="form2.purchaser"
            placeholder="请输入购买方名称"
            style="width: 100%"
            clearable
          />
        </el-form-item>
        <el-form-item label="身份证号" prop="vehicleIdCard" :required="true">
          <el-input
            v-model="form2.vehicleIdCard"
            placeholder="请输入身份证号"
            style="width: 100%"
            clearable
          />
        </el-form-item>   
        <el-form-item label="价税合计(小写)" prop="priceTax">
        <el-input-number v-model="form2.priceTax"></el-input-number>
        </el-form-item>
        </div>
        <div class="itemInline">  
          <el-form-item label="发票号码" prop="invoiceNo" :required="true">
          <el-input
            v-model="form2.invoiceNo"
            placeholder="请输入发票号码"
            style="width: 100%"
            clearable
          />
        </el-form-item> 
        <el-form-item label="开票日期" prop="invoicingDate">
        <el-date-picker v-model="form2.invoicingDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
          :style="{width: '100%'}" placeholder="选择日期时间" clearable></el-date-picker>
      </el-form-item>
      <el-form-item label="是否三包" prop="isSanBao" label-width="120px" >
            <el-radio-group v-model="form2.isSanBao" ref="radioGroup">
              <el-radio :label="1">是</el-radio>
              <el-radio :label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </div>
        <div class="itemInline">  
          <el-form-item label="销货单位名称" prop="salesUnitName">          
            <el-select v-model="form2.salesUnitName" placeholder="请选择销货单位名称" clearable>
            <el-option
              v-for="dict7 in dict.type.sale_name"
              :key="dict7.value"
              :label="dict7.label"
              :value="dict7.label"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="销货单位地址" prop="salesUnitAddress" >
          <el-input
            v-model="form2.salesUnitAddress"
            style="width: 100%"
            :disabled="true"
            clearable
          />
        </el-form-item>
        <el-form-item label="车辆状态" prop="vehicleStatus">          
            <el-select v-model="form2.vehicleStatus" placeholder="请选择车辆状态" clearable>
            <el-option
              v-for="dict8 in dict.type.state"
              :key="dict8.value"
              :label="dict8.label"
              :value="dict8.label"
            />
          </el-select>
          </el-form-item>
        </div>
        <div class="itemInline"> 
          <el-form-item label="销售渠道名称" prop="salesChannel" :required="true">
          <el-input
            v-model="form2.salesChannel"
            placeholder="请输入销售渠道名称"
            style="width: 100%"
            clearable
          />
        </el-form-item> 
        <el-form-item label="办理员工姓名" prop="employeeName" :required="true">
          <el-input
            v-model="form2.employeeName"
            placeholder="请输入办理员工姓名"
            style="width: 100%"
            clearable
          />
        </el-form-item> 
        <el-form-item label="销售渠道类型" prop="channelType" :required="true">          
            <el-select v-model="form2.channelType" placeholder="请选择销售渠道类型" clearable>
            <el-option
              v-for="dict9 in dict.type.channel_type"
              :key="dict9.value"
              :label="dict9.label"
              :value="dict9.label"
            />
          </el-select>
          </el-form-item>
        </div>
        <div class="itemInline">  
          <el-form-item label="经销商省份" prop="awardProvince">          
            <el-select v-model="form2.awardProvince" placeholder="请选择省" clearable>
            <el-option
              v-for="item in provinces"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              @click.native="changeProvince()"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="经销商城市" prop="awardCity">          
            <el-select v-model="form2.awardCity" placeholder="请选择市" clearable>
            <el-option
              v-for="item in cities"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              @click.native="changeCity"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="经销商区县" prop="awardArea">          
            <el-select v-model="form2.awardArea" placeholder="请选择区" clearable>
            <el-option
              v-for="item in area"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="经销商" prop="dealerId" :required="true">          
            <el-select v-model="form2.dealerId" placeholder="请选择经销商" clearable>
            <el-option
              v-for="dict7 in dict.type.sale_name"
              :key="dict7.value"
              :label="dict7.label"
              :value="dict7.label"
            />
          </el-select>
          </el-form-item>
        </div>
        <el-form-item label="发票" prop="invoiceImgUrls">
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
        </div>
      <div v-show="active == 3">
        <el-form ref="form3" :model="form3" :rules="rules" label-width="180px">
          <el-table-column label="信息进度" v-if="false" prop="progress"/>
        <h4 class="form-header h4" content-position="left">上牌信息</h4>
        <div class="itemInline">  
          <el-form-item label="车管所名称" prop="awardPlaceName" :required="true">
          <el-input
            v-model="form3.awardPlaceName"
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
		   v-model="form3.selectedOptions"
       clerable
		   @change="handleChange1">
	     </el-cascader>
     </el-form-item>
     <el-form-item label="注册详细地址" prop="awardPlaceAddress">
        <el-input v-model="form3.awardPlaceAddress" type="textarea" placeholder="请输入注册详细地址"
          :autosize="{minRows: 2, maxRows: 2}" :style="{width: '100%'}"></el-input>
      </el-form-item>      
        </div>
        <div class="itemInline">
          <el-form-item label="上牌日期" prop="currentUpPlaceDate">
        <el-date-picker v-model="form3.currentUpPlaceDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
          :style="{width: '95%'}" placeholder="选择日期时间" clearable></el-date-picker>
      </el-form-item>  
      <el-form-item label="车牌颜色" prop="plateColour" :required="true">          
            <el-select v-model="form3.plateColour" placeholder="请选择车牌颜色" clearable>
            <el-option
              v-for="dict8 in dict.type.plate_colour"
              :key="dict8.value"
              :label="dict8.label"
              :value="dict8.label"
            />
          </el-select>
        </el-form-item>
      
      <div class="join1" style="margin-right: 0px;">
      <el-form-item label="车牌号" prop="plateCodeName">
            <el-select v-model="form3.plateCodeName" placeholder="地区" style="width: 35%" clearable>
            <el-option
              v-for="item in plateCodeItems"
              :key="item.value"
              :label="item.label"
              :value="item.label"
            />
          </el-select>
      </el-form-item>
      </div>
      <div class="join2" style="margin-left: 0px;">
      <el-form-item  prop="plateCode">
        <el-input
            v-model="form3.plateCode"
            placeholder="请输入车牌号"
            style="width: 80%"
            clearable
          />
      </el-form-item>
    </div>
  </div> 
        <el-form-item label="上传车辆照片" prop="plateImgUrls">
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
      </div>
      <div v-show="active == 4">
        <el-form ref="form4" :model="form4" :rules="rules" label-width="180px">
          <el-table-column label="信息进度" v-if="false" prop="progress"/>
        <h4 class="form-header h4" content-position="left">车主绑定信息</h4>
      <div class="itemInline"> 
        <el-form-item label="车主手机号" prop="mobile" :required="true">
          <el-input
            v-model="form4.mobile"
            placeholder="请输入车主手机号"
            style="width: 100%"
            clearable
          />
        </el-form-item> 
        <el-form-item label="车主姓名" prop="realName" :required="true">
          <el-input
            v-model="form4.realName"
            placeholder="请输入车主姓名"
            style="width: 100%"
            clearable
          />
        </el-form-item>
        <el-form-item label="车主身份证号" prop="idCard" :required="true">
          <el-input
            v-model="form4.idCard"
            placeholder="请输入车主身份证号"
            style="width: 100%"
            clearable
          />
        </el-form-item>
      </div>
      <div class="itemInline">
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
      </div>
      <div class="itemInline">
        <el-form-item  prop="cardFrontImg">
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
        <el-form-item  prop="cardBackImg">
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
        <el-table-column label="车主ID" align="center" v-if="false" prop="tspUserId"/>
        <el-table-column label="手机号" align="center" prop="mobile"></el-table-column>
        <el-table-column label="车主姓名" align="center" prop="realName"></el-table-column>
        <el-table-column label="身份证号" align="center" prop="idCard"></el-table-column>
        <el-table-column label="操作用户" align="center" prop="operatorName"></el-table-column>
        <el-table-column label="操作时间" align="center" prop="operateTime" width="180">
           <template slot-scope="scope">
            <span>{{ parseTime(scope.row.operateTime) }}</span>
          </template> 
        </el-table-column>
      </el-table>
      <!-- <pagination
      v-show="total>0"
      :total="total"
      :page.sync="form.pageNum"
      :limit.sync="form.pageSize"
      @pagination="listdeviceType"
    /> -->
    </el-form>
    </div>

      <div v-show="active == 5">
    <el-form ref="form5" :model="form5" :rules="rules" label-width="180px">
      <el-table-column label="信息进度" v-if="false" prop="progress"/>
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
    </el-form>
      </div>
    <!-- 设置页面按钮 -->
     <div  class="button-footer">
    <el-button v-if="active < 5" type="primary"  style="margin-top: 12px" @click="next">保存</el-button> 
    <el-button v-if="active > 1" type="primary"  style="margin-top: 12px" @click="pre">上一步</el-button>
    <el-button v-if="active < 5" type="warning"  style="margin-top: 12px" @click="next">下一步</el-button>
    <el-button v-if="active < 5" style="margin-top: 12px" @click="cancel">取消</el-button>
    <el-button v-if="active > 4" style="margin-top: 12px" @click="submit">完成</el-button>
     </div>
    </div>
  </template>

  <script>
import { regionData } from 'element-china-area-data';
import { addVehicleMessage,getVehicleMessage,updateVehicleMessage,queryVehicleAuth } from '../../../api/vehicle/vehicleMessage';
import { vehicleTypeModel } from "../../../api/vehicle/vehicleType";


export default {
  name: "vehicleAdd",
  dicts:['vehicle_color','battery_spec','motor_brand','is_new_vehicle','purpose','state','channel_type','plate_colour','ess_model'],

  data() {
    return {
      //车辆信息表单
      form1: {
      
        pageNum: 1,
        pageSize:10,
        providerName: '摩登汽车有限公司',
      },
      form2:{
        
        pageNum: 1,
        pageSize:10,
      },
      form3:{
      
        pageNum: 1,
        pageSize:10,
      },
      form4:{
        
        pageNum: 1,
        pageSize:10,
      },
      form5:{
       
        pageNum: 1,
        pageSize:10,
      },
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
      vehicleTypeModel: [],
      option: [],
      //车牌号参数
      plateCodeItems: [
           {value: '京', label: '京'},{value: '津', label: '津'},
           {value: '沪', label: '沪'},{value: '渝', label: '渝'},
           {value: '桂', label: '桂'},{value: '宁', label: '宁'},
           {value: '蒙', label: '蒙'},{value: '新', label: '新'},
           {value: '藏', label: '藏'},{value:'港',label:'港'},
           {value: '澳',label:'澳'},{value: '粤', label: '粤'},
           {value: '湘', label: '湘'},{value: '鄂', label: '鄂'},
           {value: '冀', label: '冀'},{value: '豫', label: '豫'},
           {value: '黑',label:'黑'},{value: '吉', label: '吉'},
           {value: '辽', label: '辽'},{value: '晋', label: '晋'},
           {value: '陕', label: '陕'},{value: '青', label: '青'},
           {value: '鲁', label: '鲁'},{value: '苏', label: '苏'},
           {value: '皖', label: '皖'},{value: '浙', label: '浙'},
           {value: '闽', label: '闽'},{value: '赣', label: '赣'},
           {value: '琼', label: '琼'},{value: '甘', label: '甘'},
           {value: '贵', label: '贵'},{value: '川', label: '川'},
           {value: '云', label: '云'},{value: '台', label: '台'},

      ],
      //购买领域
      purchaserState: [
      { value: 1, label: "私人用车" },
      { value: 0, label: "单位用车" },
      ],
      //是否三包
      isSanBao: [
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
        vehicleTypeModel: [
          { required: true, message: "请选择车型", trigger: "change" }
        ],
        cduNum: [
          { required: true, message: "请输入CDU序列号", trigger: "blur" },
          { min: 1, max: 50, message: "长度在 1 到 50 个字符", trigger: "blur" }
        ],
        configureName: [
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
        batchNo: [
          { required: true, message: "请输入批次号", trigger: "blur" },
          { min: 1, max: 50, message: "长度在 1 到 50 个字符", trigger: "blur" }
        ],
        exFactoryDate: [
          { required: true, message: "请选择出厂日期", trigger: "change" }
        ],
        operateDate: [
          { required: true, message: "请选择下线日期", trigger: "change" }
        ],
        essModel: [
          { required: true, message: "请选择电池包规格", trigger: "change" }
        ],
        essNum: [
          { required: true, message: "请输入电池包序列号", trigger: "blur" },
          { min: 1, max: 50, message: "长度在 1 到 50 个字符", trigger: "blur" }
        ],
        motorBrand: [
          { required: true, message: "请选择电动机品牌", trigger: "change" }
        ],
        motorNum: [
          { required: true, message: "请输入电动机序列号", trigger: "blur" },
          { min: 1, max: 50, message: "长度在 1 到 50 个字符", trigger: "blur" }
        ],
        purpose: [
          { required: true, message: "请选择车辆用途", trigger: "blur" },
          { min: 2, max: 50, message: "长度在 2 到 50 个字符", trigger: "blur" }
        ],
        newVehicleFlag: [
          { required: true, message: "请选择是否是新车", trigger: "change" }
        ],
        purchaser: [
          { required: true, message: "请输入购买方名称", trigger: "change" }
        ],
        invoiceNo: [
          { required: true, message: "请输入发票号码", trigger: "blur" },
          { min: 1, max: 50, message: "长度在 1 到 50 个字符", trigger: "blur" }
        ],
        invoicingDate: [
          { required: true, message: "请选择开票日期", trigger: "change" }
        ],
        salesUnitName: [
          { required: true, message: "请选择销货单位名称", trigger: "blur" },
          { min: 2, max: 50, message: "长度在 2 到 50 个字符", trigger: "blur" }
        ],
        salesChannel: [
          { required: true, message: "请输入销售渠道名称", trigger: "blur" },
          { min: 2, max: 50, message: "长度在 2 到 50 个字符", trigger: "blur" }
        ],
        employeeName: [
          { required: true, message: "请输入办理员工姓名", trigger: "blur" },
          { min: 2, max: 50, message: "长度在 2 到 50 个字符", trigger: "blur" }
        ],
        channelType: [
          { required: true, message: "请选择销售渠道类型", trigger: "change" }
        ],
        dealerId: [
          { required: true, message: "请选择经销商", trigger: "blur" },
          { min: 1, max: 50, message: "长度在 1 到 50 个字符", trigger: "blur" }
        ],
        awardPlaceName: [
          { required: true, message: "请输入车管所名称", trigger: "blur" },
          { min: 2, max: 50, message: "长度在 2 到 50 个字符", trigger: "blur" }
        ],
        plateColour: [
          { required: true, message: "请选择车牌颜色", trigger: "change" }
        ],
        mobile: [
          { required: true, message: "请输入车主手机号", trigger: "blur" },
          { min: 11, max: 11, message: "请输入正确的手机号", trigger: "blur" }
        ],
        realName: [
          { required: true, message: "请输入车主姓名", trigger: "blur" },
          { min: 2, max: 50, message: "长度在 2 到 50 个字符", trigger: "blur" }
        ],
        vehicleIdCard:[
          { required: true,min: 18, max: 18,
            message: "身份证长度为18位,由数值或数值加X组成", trigger: "blur" },
        ],
        vehicleStatus:[
          { required: true, message: "请选择车辆状态", trigger: "change" }
        ],

      }
      
    }
  },

  //获取车型下拉框及编辑信息
  created() {
   
    this.getVehicleType();
    const tspVehicleId = this.$route.params && this.$route.params.tspVehicleId;
    if(tspVehicleId){
      this.form1.tspVehicleId = tspVehicleId;
      getVehicleMessage(this.form1.tspVehicleId).then(response => {
          this.form1 = response.data;
          this.form2 = response.data;
          this.form3 = response.data;
          this.form4 = response.data;
          this.form5 = response.data;
          this.form1.vehicleTypeModel = [response.data.tspVehicleModelId,response.data.tspVehicleStdModelId];
      } 
   )
  //  queryVehicleAuth(this.form1.tspVehicleId).then(response => {
  //        this.listBindingRecord = response.data.tspUser;
  //  })
  }

  },

  methods: {

//选择车牌号
    selectItem(item) {
      this.form.plateCode = item;
      this.showList = false;
      this.$emit('selected', item);
    },
//给车型下拉框赋值
    getVehicleType() {

      this.vehicleTypeModel = []
      vehicleTypeModel(this.form1).then(response => {
        this.option = response.data;
      });
    },
//查询车型下拉框获取值
    handleChange(value) {
  
      console.log(value)
      if(value.length>1)
    {
    this.form1.tspVehicleModelId=value[0]
    this.form1.tspVehicleStdModelId=value[1]
    console.log(this.form1.tspVehicleModelId)
    console.log(this.form1.tspVehicleStdModelId)
    }
    else
    {  
    this.form1.tspVehicleModelId=value[0]
    this.form1.tspVehicleStdModelId=undefined
    console.log(this.form1.tspVehicleModelId)
    console.log(this.form1.tspVehicleStdModelId)
    }

    },
//获取省市区数据
    changeProvince() {
      //console.log(this.provinces)
      //console.log(this.form.awardProvince)
      this.cities = [];
      this.area = [];
      this.form2.awardCity = "";
      this.form2.awardArea = "";
      let cityItem = this.provinces.filter(
          (item) => item.value === this.form2.awardProvince
        );
        if (cityItem[0]) {
          this.cities = cityItem[0].children;
        } 
      },
    changeCity() {
      console.log("城市选择")
      console.log(this.form2.awardCity)
      this.area = [];
      this.form2.awardArea = "";
      let areaItem = this.cities.filter(
          (item) => item.value === this.form2.awardCity
        );
        if (areaItem[0]) {
          this.area = areaItem[0].children;
        }
        console.log(this.form2.awardArea);

       },
// 上牌信息省市区
    handleChange1(value) {


        this.form3.awardProvince = value[0];
        this.form3.awardCity = value[1];
        this.form3.awardArea = value[2];
			  console.log(this.selectedOptions)
        console.log(value)
			
      },

//下一步/保存按钮
    next() {
      
      if (this.active == 1) {
        
        this.$refs["form1"].validate(valid => {
          if (valid) {
            this.form1.progress = 1;
            if (this.form1.tspVehicleId!= undefined) {
              updateVehicleMessage(this.form1).then(response => {
                this.$modal.msgSuccess("保存成功");
              });
            } else {
              addVehicleMessage(this.form1).then(response => {
                this.$modal.msgSuccess("保存成功");
              });
            }
          }
        });
              
      }
      else if (this.active == 2) {

        this.$refs["form2"].validate(valid => {
          if (valid) {
            this.form2.progress = 2;
            if (this.form1.tspVehicleId!= undefined) {
              updateVehicleMessage(this.form2).then(response => {
                this.$modal.msgSuccess("保存成功");
              });
            } else {
              addVehicleMessage(this.form2).then(response => {
                this.$modal.msgSuccess("保存成功");
              });
            }
          }
        });
      }
      else if (this.active == 3) {

        this.$refs["form3"].validate(valid => { 
          if (valid) {
            this.form3.progress = 3;
            if (this.form1.tspVehicleId!= undefined) {
              updateVehicleMessage(this.form3).then(response => {
                this.$modal.msgSuccess("保存成功");
              });
            } else {
              addVehicleMessage(this.form3).then(response => {
                this.$modal.msgSuccess("保存成功");
              });
            }
          }
        });
      }
      else if (this.active == 4) {

        this.$refs["form4"].validate(valid => {
          if (valid) {
            this.form4.progress = 4;
            if (this.form1.tspVehicleId!= undefined) {
              updateVehicleMessage(this.form4).then(response => {
                this.$modal.msgSuccess("保存成功");
              });
            } else {
              addVehicleMessage(this.form4).then(response => {
                this.$modal.msgSuccess("保存成功");
              });
            }
          }
        });
      }
      else
      {
        this.form5.progress = 5;
      }
  if (this.active++ > 5) this.active = 1
   },

// 上一步按钮
    pre() {
              if (this.active-- < 2) this.active = 1
        },
// 取消按钮
    cancel(){
            
              this.$store.dispatch("tagsView/delView", this.$route);
              this.$router.go(-1);
              //this.$router.push("/vehicle/vehicle-message/index/");
        },
//完成按钮
    submit() {
              this.$store.dispatch("tagsView/delView", this.$route);
              this.$router.go(-1);
              //this.$router.push("/vehicle/vehicle-message/index/");
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
 .join1{
   display: inline-block;
   margin-right: 0;
   font-size: 0;
   border-collapse: collapse;
 }
 .join2{
   display:inline-block;
   margin-left: 0;
   font-size: 0;
   border-collapse: collapse;
 }

</style>
  