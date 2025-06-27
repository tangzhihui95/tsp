<template>
  <div class="app-container">

    <el-tabs v-model="activeTab" type="border-card" class="compact-tabs" >
      <!-- 整车数据 -->
      <el-tab-pane label="整车数据" name="vehicle">
        <vehicle-data :data="vehicleData" :loading="loading" />
      </el-tab-pane>

      <!-- 驱动电机数据 -->
      <el-tab-pane label="驱动电机数据" name="motor">
        <motor-data :data="motorData" :loading="loading" />
      </el-tab-pane>

      <!-- 极值数据 -->
      <el-tab-pane label="极值数据" name="extreme">
        <extreme-data :data="extremeData" :loading="loading" />
      </el-tab-pane>

      <!-- 报警数据 -->
      <el-tab-pane label="报警数据" name="alarm">
        <alarm-data :data="alarmData" :loading="loading" />
      </el-tab-pane>

      <!-- 可充电储能装置电压数据 -->
      <el-tab-pane label="储能电压数据" name="voltage">
        <voltage-data :data="voltageData" :loading="loading" />
      </el-tab-pane>

      <!-- 可充电储能装置电流数据 -->
      <el-tab-pane label="储能电流数据" name="current">
        <current-data :data="currentData" :loading="loading" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

  <script>
  import vehicleData from "@/views/integrate/historyData/vehicleData.vue";
  import motorData from "@/views/integrate/historyData/motorData.vue";
  import extremeData from "@/views/integrate/historyData/extremeData.vue";

  export default {
    name: "listData",
    dicts: ['listData'],
    components:
    {
    vehicleData,
    motorData,
    extremeData
    },
    data() {
      return {
      //tab
      activeTab: 'vehicle',
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

<style scoped>
.compact-tabs {
  padding: 15px;
}
/* 紧凑型标签页样式 */
.compact-tabs ::v-deep .el-tabs__item {
  height: 32px;
  line-height: 32px;
  font-size: 15px;
  padding: 0 12px;
  font-weight: bold; /* 添加这行实现字体加粗 */
}

.compact-tabs {
  width: 100%; /* 设置固定宽度 */
  max-width: 100%; /* 确保在小屏幕上不会溢出 */
}

.compact-tabs ::v-deep .el-tabs__nav {
  border: none;
}

.compact-tabs ::v-deep .el-tabs__header {
  margin: 0;
  border-bottom: 1px solid #e6e6e6;
}

.compact-tabs ::v-deep .el-tabs__item.is-active {
  background-color: #f0f7ff;
  border-bottom-color: #409eff;
}

/* 调整内容区域 */
.compact-tabs ::v-deep .el-tabs__content {
  padding: 12px;
  background: #fff;
  border: 1px solid #e6e6e6;
  border-top: none;
}
</style>


