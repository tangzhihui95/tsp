<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="设备类型" prop="search" label-width="100px">
          <el-input
            v-model="queryParams.search"
            placeholder="类型/扩展信息类型"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="设备类型-型号" prop="modelName" label-width="120px">
          <el-select v-model="queryParams.modelName" placeholder="请选择" clearable>
            <el-option
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
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
          >新增分类</el-button>
        </el-col>
        <el-col :span="1.5" >
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['system:equipmentType:import']"
            >导入设备类型</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['system:deviceModel:import']"
            >导入设备型号</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['system:deviceTypeModel:export']"
            >导出</el-button>
          </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['system:deviceType:remove']"
          >批量删除</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
  
      <el-table ref="refTable" v-loading="loading" :data="listdeviceType"
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
      <el-table-column label="设备型号ID" align="center" v-if="false" prop="tspEquipmentModelId"/> 
          <el-table-column prop="modelName" label="设备型号" align="center"/>
          <el-table-column label="供应商" prop="suppliers" width="180" align="center"></el-table-column>
          <el-table-column label="生产批次" prop="batchNumber" width="180" align="center"></el-table-column>
          <el-table-column label="创建时间" align="center" prop="createTime">
           <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template> 
        </el-table-column>
        <el-table-column label="关联设备"  align="center"></el-table-column>
          <el-table-column label="操作"  align="center">
            <template slot-scope="scope">
              <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row.ids)"
              v-hasPermi="['system:deviceType:edit']"
            >编辑型号</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row.ids)"
              v-hasPermi="['system:deviceType:remove']"
            >删除型号</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
    </template>
  </el-table-column>
      <el-table-column label="设备类型ID" align="center" v-if="false" prop="tspEquipmentTypeId"/>
      <el-table-column label="序号" type="index" align="center">
        <template slot-scope="scope">
          <span>{{ scope.$index + 1}}</span>
        </template>
      </el-table-column>
        <el-table-column label="设备类型" align="center" prop="name"></el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
           <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template> 
        </el-table-column>
         <el-table-column label="是否为终端" align="center" prop="isTerminal" />
        <el-table-column label="设备扩展信息类型" align="center" prop="extraType" />
        <el-table-column label="关联设备数" align="center" prop="count" /> 
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row.tspequipmenttypeId)"
            v-hasPermi="['system:deviceModel:add']"
          >添加型号</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row.tspEquipmentTypeId)"
              v-hasPermi="['system:deviceType:edit']"
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
        @pagination="listdeviceType"
      />
  
      <!-- 添加或修改设备类型对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="设备类型" prop="name"label-width="90px":required="true">
          <el-select v-model="form.name"  placeholder="请选择设备类型" clearable>
            <el-option
              v-for="dict in listdeviceType"
              :key="dict.tspEquipmentTypeId"
              :label="dict.name"
              :value="dict.tspEquipmentTypeId"
              @click.native="changeTypeName(dict)"
            />
          </el-select>
        </el-form-item>
          <el-form-item label="设备扩展信息" prop="extraType" label-width="120px":required="true">          
            <el-select v-model="form.extraType" placeholder="请输入设备扩展信息" clearable>
            <el-option
              v-for="dict in listdeviceType"
              :key="dict.tspEquipmentTypeId"
              :label="dict.extraType"
              :value="dict.tspEquipmentTypeId"
              @click.native="changeExtraType(dict)"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="是否为终端设备" prop="isTerminal" label-width="120px">
            <el-radio-group v-model="form.isTerminal">
              <el-radio
                v-for="dict in listdeviceType"
                :key="dict.tspEquipmentTypeId"
                :label="dict.isTerminal"
              >{{dict.isTerminal}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import { listdeviceType, adddeviceType,updatedeviceType,getdeviceType,} from "@/api/equipment/deviceType";
import { get } from "sortablejs";

  
  export default {
    name: "listdeviceType",
    dicts: ['sys_normal_disable'],
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
        listdeviceType: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          search: undefined,
          modelName: undefined, 
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          name: [
            { required: true, message: "设备类型不能为空", trigger: "blur" }
          ],
          extraType: [
            { required: true, message: "设备扩展信息不能为空", trigger: "blur" }
          ]
        }
      };
    },
    created() {
      this.getList();
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
        listdeviceType(this.queryParams).then(response => {
          this.listdeviceType = response.data.list;
          this.total = response.data.total;
          this.loading = false;
        });
      },
      /** 设备类型下拉框label改变 */
      
      changeTypeName(listdeviceType){
        this.form.name = listdeviceType.name;
      },
      /** 设备扩展信息类型下拉框label改变 */
      changeExtraType(listdeviceType){
        this.form.extraType = listdeviceType.extraType;
      },  
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          name: undefined,
          extraType: undefined,
          isTerminal: "0",
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
     /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.tspEquipmentTypeId)
        this.single = selection.length!=1
        this.multiple = !selection.length
      },
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

      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "新增分类";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const tspEquipmentTypeId = row.deviceTypeId || this.ids
        getdeviceType(tspEquipmentTypeId).then(response => {
          this.form = response.data.list;
          this.open = true;
          this.title = "编辑分类";
        });
      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.tspEquipmentTypeId != undefined) {
              updatedeviceType(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              adddeviceType(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const deviceTypeIds = row.deviceTypeId || this.ids;
        this.$modal.confirm('是否确认删除设备类型序号为"' + deviceTypeIds + '"的数据项？').then(function() {
          return deldeviceType(deviceTypeIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导入按钮操作 */
      handleImport() {
        this.$modal.confirm('是否确认导入设备类型数据？').then(function() {
          return this.$refs.importForm.submit();          
        }).then(() => {          
          this.$modal.msgSuccess("导入成功");
          this.getList();
        }).catch(() => {});        
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('system/deviceType/export', {
          ...this.queryParams
        }, `deviceType_${new Date().getTime()}.xlsx`)
      }
    }
  };
  </script>