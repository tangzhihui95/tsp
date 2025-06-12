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
    <div v-if="active!=0">
      <h4 class="form-header h4" content-position="left">当前设备信息</h4>
        <el-table ref="refTable1" v-loading="loading" :data="listEquipment">
      <el-table-column label="设备ID" align="center" v-if="false" prop="tspEquipmentId"/>
      <el-table-column label="设备型号ID" align="center" v-if="false" prop="tspEquipmentModelId"/>
      <el-table-column label="设备类型ID" align="center" v-if="false" prop="tspEquipmentTypeId"/>
      <el-table-column label="车辆ID" align="center" v-if="false" prop="tspvehicleId"/>
      <el-table-column label="序号" type="index" align="center">
          <template slot-scope="scope">
                 <span>1</span>
  <!--           <span>{{ scope.$index + 1}}</span> -->
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
            type="danger"
            icon="el-icon-edit"
            @click="handleSelect(scope.row)"
            v-hasPermi="['vehicle:device:add']"
          >选择设备</el-button>
          <el-button
              size="mini"
              type="danger"
              icon="el-icon-delete"
              @click="handleUnbind(scope.row)"
              v-hasPermi="['vehicle:device:remove']"
            >解绑设备</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    
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
        <el-form-item label="购买领域" prop="purchaserState" label-width="120px">
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
            <el-select v-model="form2.salesUnitName" placeholder="请选择销货单位名称" 
            @click.native="selectTrigger(form2.salesUnitName)" clearable>
            <el-option
              v-for="dict7 in sale_name"
              :key="dict7.id"
              :label="dict7.dealerName"
              :value="dict7.dealerName"
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
              v-for="dict7 in sale_name"
              :key="dict7.id"
              :label="dict7.dearlerName"
              :value="dict7.id"
            />
          </el-select>
          </el-form-item>
        </div>
    <el-form-item label="发票" prop="invoiceImgUrls">
      <div class="component-upload-image">
          <el-upload
            :action="uploadImgUrl"
            list-type="picture-card"
            :on-success="handleUploadSuccess1"
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
        <div class="component-upload-image">
          <el-upload
            :action="uploadImgUrl"
            list-type="picture-card"
            :on-success="handleUploadSuccess2"
            :before-upload="handleBeforeUpload"
            :limit="limit"
            :on-error="handleUploadError"
            :on-exceed="handleExceed"
            name="file"
            :on-remove="handleRemove"
            :show-file-list="true"
            :headers="headers"
            :file-list="fileList2"
            :on-preview="handlePictureCardPreview"
            :class="{hide: this.fileList2.length >= this.limit}"
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
        <el-form-item label="请上传手持身份证反面照片" prop="cardBackImg">
          <div class="component-upload-image">
          <el-upload
            :action="uploadImgUrl"
            list-type="picture-card"
            :on-success="handleUploadSuccess4"
            :before-upload="handleBeforeUpload"
            :limit="limit"
            :on-error="handleUploadError"
            :on-exceed="handleExceed"
            name="file"
            :on-remove="handleRemove"
            :show-file-list="true"
            :headers="headers"
            :file-list="fileList4"
            :on-preview="handlePictureCardPreview"
            :class="{hide: this.fileList4.length >= this.limit}"
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
      </div>
      <div class="itemInline">
        <el-form-item  prop="cardFrontImg">
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
       <el-form-item  prop="cardBackImg">
        <div class="component-upload-image">
          <el-upload
            :action="uploadImgUrl"
            list-type="picture-card"
            :on-success="handleUploadSuccess4"
            :before-upload="handleBeforeUpload"
            :limit="limit"
            :on-error="handleUploadError"
            :on-exceed="handleExceed"
            name="file"
            :on-remove="handleRemove"
            :show-file-list="true"
            :headers="headers"
            :file-list="fileList4"
            :on-preview="handlePictureCardPreview"
            :class="{hide: this.fileList4.length >= this.limit}"
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
      <pagination
      v-show="total>0"
      :total="total"
      :page.sync="form4.pageNum"
      :limit.sync="form4.pageSize"
      @pagination="listBindingRecord"
    />
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
    <!-- 选择设备弹窗 --> 
    <el-dialog :title="title" :visible.sync="open" width="1500px" append-to-body>
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="设备SN" prop="sn" label-width="100px">
          <el-input
            v-model="queryParams.sn"
            placeholder="sn"
            clearable
            @keyup.enter.native="handleQuery"
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
        <el-form-item label="设备类型-型号" prop="typeModelValue" label-width="120px">
      <el-cascader
        v-model="queryParams.typeModelValue"
        style="width:200px"
        size="small"
        :options="option"
        @change="handleChange2"
         clerable
      /> 
        </el-form-item>
        <el-form-item label="绑定状态" prop="bindStatus" label-width="120px">
          <el-select v-model="queryParams.bindStatus" placeholder="请选择绑定状态" clearable>
            <el-option
              v-for="dict in dict.type.bind_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
    </el-form>
      <el-table ref="refTable5" v-loading="loading" :data="selectEquipment" style="width: 100%">
         <el-table-column width="55" label="选择" align="center">
          <template slot-scope="scope">
            <el-radio
                class="radio"
                :label="scope.row"
                v-model="currentRow"
             >&emsp;&emsp;&emsp;</el-radio>
          </template>
        </el-table-column>
        <el-table-column label="序号" type="index" align="center">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1}}</span>
          </template>
        </el-table-column>
        <el-table-column label="设备ID" align="center" v-if="false" prop="tspEquipmentId"/>
        <el-table-column label="车联网卡" align="center" prop="sim"></el-table-column>
        <el-table-column label="ICCID" align="center" prop="iccid"></el-table-column>
        <el-table-column label="IMEI" align="center" prop="imei"></el-table-column>
        <el-table-column label="设备SN" align="center" prop="sn"></el-table-column>
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
      <el-table-column label="绑定状态" align="center" prop="bindStatus">
       <template slot-scope="scope">
          <dict-tag :options="dict.type.bind_status" :value="scope.row.bindStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
           <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template> 
        </el-table-column>
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="selectEquipment"
      />
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel1">取 消</el-button>
        </div>
      </el-dialog> 
    </div>
  </template>

  <script>
import { getToken } from "@/utils/auth";
import { regionData } from 'element-china-area-data';
import { addVehicleMessage,getVehicleMessage,updateVehicleMessage,
  queryVehicleAuth, bindVehicleEquipment,listDealerName,dealerAddress} from '../../../api/vehicle/vehicleMessage';
import {listdeviceModel} from "@/api/equipment/model";
import { vehicleTypeModel } from "../../../api/vehicle/vehicleType";


export default {
  name: "vehicleAdd",
  dicts:['vehicle_color','battery_spec','motor_brand','is_new_vehicle','purpose','state',
  'channel_type','plate_colour','ess_model','bind_status'],
  props: {
	       type: [String, Object, Array],
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
      //发票图片    
	    fileList1: [],
      //车辆图片
      fileList2: [],
      //身份证正面图片
      fileList3: [],
      //身份证反面图片
      fileList4: [],
      //查询表单
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        typeModelValue: undefined,
        tspEquipmentModelId: undefined, 
        tspEquipmentTypeId: undefined, 
        sn: undefined,
        bindStatus: undefined,
      },
      //历史绑定设备
      listHistoryEquipment: [],
      //当前绑定设备
      listEquipment: [
       {index: '1'}
      ],
      //绑定记录
      listBindingRecord: [],
      //出入库记录
      listBoxRecord: [],
      //选择设备列表
      selectEquipment: [],
      currentRow: null,
      //总条数
      total: 0,
      //弹窗
      open: false,
      showSearch: true,
      //弹窗标题
      title: '选择设备',
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
      //销货单位名称
      sale_name:[],
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
    
    this.loading = true;
    listDealerName().then(response => {
      this.sale_name=response.data});
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

  this.loading = false;

  },

  methods: {
//获取设备信息列表
    getList() {
      this.loading = true;
        listdeviceModel(this.queryParams).then(response => {
          this.selectEquipment = response.data.list;
          this.total = response.data.total;
          this.loading = false;
          this.showDialog();
        });
      },

      showDialog() {
            alert(`Selected: ${this.refTable1.typeModel}`);
        },
//选中销货单位名称获取地址
    selectTrigger(value){
      console.log(value)
      const dealerName = value;
      console.log(dealerName)
        dealerAddress(dealerName).then(response => {

          this.form2.salesUnitAddress = response.data;
        
        })
    },      
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
//设备类型-型号下拉框
    handleChange2(value) {
      console.log(value)
      if(value.length>1)
     {
      //this.tspEquipmentTypeId=value[0]
      this.queryParams.tspEquipmentModelId=value[2]
      //console.log(this.tspEquipmentTypeId)
      console.log(this.queryParams.tspEquipmentModelId)
     }
     else
     {  
      this.queryParams.tspEquipmentTypeId=value[0]
      //this.tspEquipmentModelId=undefined
      console.log(this.queryParams.tspEquipmentTypeId)
      //console.log(this.tspEquipmentModelId)
     }
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
// 选择设备按钮
    handleSelect(row){
     //this.currentRow = row;
     this.open = true;
     this.getList();
    },
// 解绑设备按钮
    handleUnbind(row){
      this.$confirm("确定解绑该设备吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        unbindVehicleEquipment(this.form1.tspVehicleId, row.tspEquipmentId).then(response => {
          this.$message({
            type: "success",
            message: "解绑成功!"
          });
          this.getList();
        });
      }).catch(() => {
        this.$message({
          type: "info",
          message: "已取消解绑"
        }); 
      });
    },
// 查询按钮
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
// 重置按钮
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.typeModelValue = "-1";
      this.queryParams.tspEquipmentModelId = undefined;
      this.queryParams.tspEquipmentTypeId = undefined;
      this.handleQuery();
    },
//绑定确定按钮
    submitForm: function() {
      // 添加到列表
      this.listEquipment.push({...this.selectEquipment})
      this.$message.success('添加成功')
      this.getList();
      this.loading.close();
        // if (this.currentRow != undefined) {
        //       bindVehicleEquipment(this.form1.tspVehicleId, this.currentRow.tspEquipmentId).then(response => {
        //         this.$modal.msgSuccess("设备绑定成功");
        //         this.open = false;
        //         this.getList();
        //       });
        //     } else {
        //       adddeviceModel(this.form).then(response => {
        //         this.$modal.msgSuccess("新增设备成功");
        //         this.open = false;
        //         this.getList();
        //       });
        //     }
        
      }, 
// 取消按钮
      cancel1() {
        this.open = false;
      },  
 // 删除图片
    handleRemove(file, fileList) {
      if(fileList == this.fileList1){
        const findex = this.fileList1.map(f => f.url).indexOf(file.url);
          if (findex > -1) {
              this.fileList1.splice(findex, 1);
              //console.log(this.fileList1);
              //this.form2.invoiceImgUrls.splice(findex, 1);
              this.$emit("input", this.listToString(this.fileList1));

              //console.log(this.form2.invoiceImgUrls);

            }
      }
      else if(fileList == this.fileList2){
        const findex = this.fileList2.map(f => f.url).indexOf(file.url);
          if (findex > -1) {
              this.fileList2.splice(findex, 1);
              //console.log(this.fileList2);
              //this.form3.plateImgUrls.splice(findex, 1);
              this.$emit("input", this.listToString(this.fileList2));

              //console.log(this.form3.plateImgUrls);

            }
      }
      else if(fileList == this.fileList3){
        const findex = this.fileList3.map(f => f.url).indexOf(file.url);
          if (findex > -1) {
              this.fileList3.splice(findex, 1);
              //console.log(this.fileList3);
              //this.form4.cardFrontImg.splice(findex, 1);
              this.$emit("input", this.listToString(this.fileList3));

              //console.log(this.form4.cardFrontImg);

            }
      }
      else{
        const findex = this.fileList4.map(f => f.url).indexOf(file.url);
          if (findex > -1) {
              this.fileList4.splice(findex, 1);
              //console.log(this.fileList4);
              //this.form4.cardBackImg.splice(findex, 1);
              this.$emit("input", this.listToString(this.fileList4));

              //console.log(this.form4.cardBackImg);

            }
      }


        },
  // 发票图片上传成功回调
    handleUploadSuccess1(res) {
        this.fileList1.push({url:res.url});
        //this.fileList1.push({url:res.data})
        console.log(this.fileList1);
        //console.log(this.form2.invoiceImgUrls);
        this.$emit("input", this.listToString(this.fileList1));
        this.loading.close();
        //this.getList();
      },
  // 车辆图片上传成功回调
  handleUploadSuccess2(res) {
        this.fileList2.push({url:res.url});
        //this.fileList2.push({url:res.data})
        console.log(this.fileList2);
        //console.log(this.form3.plateImgUrls);
        this.$emit("input", this.listToString(this.fileList2));
        this.loading.close();
        //this.getList();
      },
  // 身份证正面图片上传成功回调
  handleUploadSuccess3(res) {
        this.fileList3.push({url:res.url});
        //this.fileList3.push({url:res.data})
        console.log(this.fileList3);
        //console.log(this.form4.cardFrontImg);
        this.$emit("input", this.listToString(this.fileList3));
        this.loading.close();
        //this.getList();
      },
  // 身份证反面图片上传成功回调
  handleUploadSuccess4(res) {
        this.fileList4.push({url:res.url});
        //this.fileList4.push({url:res.data})
        console.log(this.fileList4);
        //console.log(this.form4.cardBackImg);
        this.$emit("input", this.listToString(this.fileList4));
        this.loading.close();
        //this.getList();
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
        //this.getList();
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
      this.form2.invoiceImgUrls = [];
      this.form3.plateImgUrls = [];
      this.form4.cardFrontImg = [];
      this.form4.cardBackImg = [];
      if (list.length > 0) {
        if(list == this.fileList1){
          for (i = 0; i < list.length; i++) {
              strs  = "";
              if (list[i].url.includes("50881")){
               strs += list[i].url.substring(25);
               //strs += list[i].url;
               this.form2.invoiceImgUrls[i] = strs;
            }
            else{
               strs += list[i].url.substring(24);
               //strs += list[i].url;
               this.form2.invoiceImgUrls[i] = strs;
            }   
          }
        }
        else if(list == this.fileList2){
          for (i = 0; i < list.length; i++) {
              strs  = "";
              if (list[i].url.includes("50881")){
               strs += list[i].url.substring(25);
               //strs += list[i].url;
               this.form3.plateImgUrls[i] = strs;
            }
            else{
               strs += list[i].url.substring(24);
               //strs += list[i].url;
               this.form3.plateImgUrls[i] = strs;
            }   
          }
        }
        else if(list == this.fileList3){
          for (i = 0; i < list.length; i++) {
              strs  = "";
              if (list[i].url.includes("50881")){
               strs += list[i].url.substring(25);
               //strs += list[i].url;
               this.form4.cardFrontImg[i] = strs;
            }
            else{
               strs += list[i].url.substring(24);
               //strs += list[i].url;
               this.form4.cardFrontImg[i] = strs;
            }   
          }          
        }
        else{
          for (i = 0; i < list.length; i++) {
              strs  = "";
              if (list[i].url.includes("50881")){
               strs += list[i].url.substring(25);
               //strs += list[i].url;
               this.form4.cardBackImg[i] = strs;
            }
            else{
               strs += list[i].url.substring(24);
               //strs += list[i].url;
               this.form4.cardBackImg[i] = strs;
            }   
          }
        }

        }   

        console.log(this.form2.invoiceImgUrls);
        console.log(this.form3.plateImgUrls);
        console.log(this.form4.cardFrontImg);
        console.log(this.form4.cardBackImg);
        //return strs != '' ? strs.substring(0, strs.length - 1) : '';
      },     

},

// 全局过滤器
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

 input[aria-hidden="true"] {
    display: none !important;
}

.el-radio:focus:not(.is-focus):not(:active):not(.is-disabled) .el-radio__inner {
    box-shadow: none;
}

/* 确保按钮可见 */
/* .el-button {
  opacity: 1 !important;
  visibility: visible !important;
} */

</style>
  