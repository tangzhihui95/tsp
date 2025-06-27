<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="车辆查询" prop="search" label-width="100px">
          <el-input
            v-model="queryParams.search"
            placeholder="车牌号、SN、VIN"
            clearable
            @keyup.enter.native="handleQuery"
            clerable
          />
        </el-form-item>
        <el-form-item label="采集时间">
          <el-date-picker
            v-model="dateRange"
            style="width: 280px"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :default-time="['00:00:00', '23:59:59']"
          ></el-date-picker>
        </el-form-item>
  
        <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
  
        <el-table v-loading="loading" :data="listData" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序号" align="center" prop="dictId">
                <template slot-scope="scope">
                 <span>1</span>
                </template>
            </el-table-column>
            <el-table-column label="采集时间" align="center" prop="collectTime" width="150"/>
            <el-table-column label="接收时间" align="center" prop="createTime" width="150"/>
            <el-table-column label="数据类型" align="center" prop="dataType" />
            <el-table-column label="最高电压电池子系统号" align="center" prop="vehicleState"  width="200"/>
            <el-table-column label="最高电压电池单体代号" align="center" prop="chargeState" width="200"/>
            <el-table-column label="电池单体电压最高值(V)" align="center" prop="dcDcState" width="200"/>
            <el-table-column label="最低电压电池单体代号" align="center" prop="runMode" width="200"/>
            <el-table-column label="电池单体电压最低值(V)" align="center" prop="speed" width="180"/>
            <el-table-column label="最高温度子系统号" align="center" prop="mileage" width="150"/>
            <el-table-column label="最高温度探针序号" align="center" prop="totalVoltage" width="150"/>
            <el-table-column label="最高温度值(°C)" align="center" prop="totalCurrent" width="150"/>
            <el-table-column label="最低温度子系统号" align="center" prop="soc" width="180"/>
            <el-table-column label="最低温度值(°C)" align="center" prop="dcDcState" width="150"/>
          </el-table>
        <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="listData"
      />
    </div>
  </template>
  
  <script>
  import { listData } from "@/api/integrate/historyData/vehicleIntegrate";

  export default {
    name: "listData",
    dicts: ['listData'],
    data() {
      return {
      // 遮罩层
      //loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 历史表格数据
      listData: [],
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        search: undefined,
      },

      }
    },
    methods:{
    /** 查询字典类型列表 */
    getList() {
      this.loading = true;
      listData(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.listData = response.data.list;
          this.total = response.data.total;
          this.loading = false;
        }
      );
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.dictId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
        /** 搜索按钮操作 */
        handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
     /** 重置按钮操作 */
     resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },

    },
  };

  </script>