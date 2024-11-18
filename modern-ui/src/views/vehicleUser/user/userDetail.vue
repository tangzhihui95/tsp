<template>
    <div class="app-container">
      <el-form ref="form" :model="form" label-width="120px">
        <el-tabs v-model="activeName" class="demo-tabs">
            <el-tab-pane label="查看信息" name="first">
                <el-form-item label="用户ID" prop="userId" v-if="false" :disabled="true"/>
                <el-form-item label="车主姓名" prop="realName" :required="true">
                  <el-input
                    v-model="form.realName"
                    placeholder="请输入车主姓名"
                    style="width: 70%"
                    :disabled="true"
                    clearable
                 />
            </el-form-item>
            <el-form-item label="手机号(账号)" prop="mobile" label-width="120px" :required="true">
                <el-input
                v-model="form.mobile"
                placeholder="请输入手机号(账号)"
                style="width: 70%"
                :disabled="true"
                clearable
            />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard" :required="true">
          <el-input
            v-model="form.idCard"
            placeholder="请输入身份证号"
            style="width: 70%"
            :disabled="true"
            clearable
          />
        </el-form-item>
        <el-form-item label="出生日期" prop="bithDate">
        <el-date-picker v-model="form.bithDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
          :style="{width: '70%'}" :disabled="true" placeholder="选择日期时间" clearable></el-date-picker>
      </el-form-item>
      <el-form-item label="车主性别" prop="sex" label-width="120px" >
            <el-radio-group v-model="form.sex" :disabled="true" ref="radioGroup">
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
           :disabled="true"
           clerable
		  >
	     </el-cascader>
     </el-form-item>
     <el-form-item label="详细地址" prop="address" >
          <el-input
            v-model="form.address"
            placeholder="请输入详细地址"
            style="width: 70%"
            :disabled="true"
            clearable
          />
        </el-form-item>
        <el-form-item label="用户标签" prop="label" :required="true">          
            <el-select v-model="form.label" style="width: 70%" :disabled="true" placeholder="请选择" clearable>
            <el-option
              v-for="dict in dict.type.label_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.label"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="注册渠道" prop="channel" >
          <el-input
            v-model="form.channel"
            placeholder="请输入注册渠道"
            style="width: 70%"
            :disabled="true"
            clearable
          />
        </el-form-item>
        <el-form-item label="第三方授权" prop="auth" >
          <el-input
            v-model="form.auth"
            placeholder="请输入第三方授权"
            style="width: 70%"
            :disabled="true"
            clearable
          />
        </el-form-item>
    </el-tab-pane>
    <el-tab-pane label="当前绑定车辆" name="second">
        <el-table ref="refTable1" v-loading="loading" :data="listHistoryBindVehicle" >
      <el-table-column label="车辆ID" align="center" v-if="false" prop="id"/>
      <el-table-column label="设备ID" align="center" v-if="false" prop="tspEquipmentId"/>
      <el-table-column label="车辆类型ID" align="center" v-if="false" prop="tspVehicleModelId"/>
      <el-table-column label="车辆型号ID" align="center" v-if="false" prop="tspVehicleStdModelId"/>
      <el-table-column label="序号" type="index" align="center">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1}}</span>
          </template>
        </el-table-column>
       <el-table-column label="车牌号" align="center" prop="plateCode"></el-table-column>
       <el-table-column label="VIN码" align="center" prop="vin"></el-table-column>
       <el-table-column label="实名状态" align="center" prop="realNameStatus">
       <template slot-scope="scope">
          <dict-tag :options="dict.type.realName_status" :value="scope.row.realNameStatus"/>
        </template>
      </el-table-column>
       <el-table-column label="SIM" align="center" prop="sim"></el-table-column>
              <el-table-column label="同步时间" align="center" prop="syncTime" width="180">
                <template slot-scope="scope">
                 <span>{{ parseTime(scope.row.syncTime) }}</span>
               </template> 
             </el-table-column>
       <el-table-column label="一级车型/二级车型" align="center" prop="vehicleType"></el-table-column>
       <el-table-column label="电机编号" align="center" prop="motorNo"></el-table-column>
       <el-table-column label="设备分类/设备型号" align="center" prop="equipmentType"></el-table-column>
       <el-table-column label="设备SN" align="center" prop="sn"></el-table-column>
       <el-table-column label="设备ICCID" align="center" prop="iccid"></el-table-column>
       <el-table-column label="设备IMEI" align="center" prop="imei"></el-table-column>
      </el-table>
    </el-tab-pane>
    <el-tab-pane label="历史绑定车辆" name="third">
        <el-table ref="refTable" v-loading="loading" :data="listBindVehicle" >
      <el-table-column label="车辆ID" align="center" v-if="false" prop="id"/>
      <el-table-column label="设备ID" align="center" v-if="false" prop="tspEquipmentId"/>
      <el-table-column label="车辆类型ID" align="center" v-if="false" prop="tspVehicleModelId"/>
      <el-table-column label="车辆型号ID" align="center" v-if="false" prop="tspVehicleStdModelId"/>
      <el-table-column label="序号" type="index" align="center">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1}}</span>
          </template>
        </el-table-column>
       <el-table-column label="车牌号" align="center" prop="plateCode"></el-table-column>
       <el-table-column label="VIN码" align="center" prop="vin"></el-table-column>
       <el-table-column label="实名状态" align="center" prop="realNameStatus">
       <template slot-scope="scope">
          <dict-tag :options="dict.type.realName_status" :value="scope.row.realNameStatus"/>
        </template>
      </el-table-column>
       <el-table-column label="SIM" align="center" prop="sim"></el-table-column>
              <el-table-column label="同步时间" align="center" prop="syncTime" width="180">
                <template slot-scope="scope">
                 <span>{{ parseTime(scope.row.syncTime) }}</span>
               </template> 
             </el-table-column>
       <el-table-column label="一级车型/二级车型" align="center" prop="vehicleType"></el-table-column>
       <el-table-column label="电机编号" align="center" prop="motorNo"></el-table-column>
       <el-table-column label="设备分类/设备型号" align="center" prop="equipmentType"></el-table-column>
       <el-table-column label="设备SN" align="center" prop="sn"></el-table-column>
       <el-table-column label="设备ICCID" align="center" prop="iccid"></el-table-column>
       <el-table-column label="设备IMEI" align="center" prop="imei"></el-table-column>
      </el-table>
    </el-tab-pane>
    </el-tabs>
    <pagination
        v-show="total>0"
        :total="total"
        :page.sync="form.pageNum"
        :limit.sync="form.pageSize"
        @pagination="listUserDetail"
        />    
 </el-form>
 </div>
</template>

<script>
import { regionData } from 'element-china-area-data';

export default {
    name: 'vehicleUserDetail',
    dicts: [],
    data() {
        return {
        //详情默认页    
        activeName: 'first',
        //表单数据    
        form: {
            pageNum: 1,
            pageSize: 10,
        },
        //总条数
        total: 0,
        //注册地址下拉框
        options:regionData,
        selectedOptions: [],
        //当前绑定车辆列表
        listBindVehicle: [],
        //历史绑定车辆列表
        listHistoryBindVehicle: [],
        //遮罩层
        loading: false,
            
    }
 },
    created(){
       this.listUserDetail();
 },
    methods: {
    //获取详情数据
       listUserDetail() {
       
   }
 }


}
</script>