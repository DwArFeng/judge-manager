<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item>管理服务</el-breadcrumb-item>
      <el-breadcrumb-item>实体管理</el-breadcrumb-item>
      <el-breadcrumb-item>驱动器管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片工作区 -->
    <el-card class="box-card">
      <!-- 搜索与添加区域 -->
      <div style="margin-top: 15px;">
        <el-row :gutter="20">
          <el-col :span="21">
            <el-input
              class="input-with-select"
              clearable
              oninput="this.value=this.value.replace(/[^\d.]/g,'');"
              placeholder="请输入数据点ID，仅支持精确查询"
              v-model="sectionId2Search"
            >
              <el-button
                @click="handleSectionIdSearch"
                icon="el-icon-search"
                slot="append"
              ></el-button>
            </el-input>
          </el-col>
          <el-col :span="3">
            <el-button @click="handleShowCreateDialog" class="top-el-button" type="primary">添加驱动器
            </el-button>
          </el-col>
        </el-row>
      </div>
      <!-- 表格 -->
      <el-table
        :data="driverInfo.data"
        stripe
        style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left">
              <el-form-item label="内容">
                <el-input
                  :readonly="true"
                  autosize
                  type="textarea"
                  v-model="props.row.content"
                ></el-input>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          label="ID"
          prop="key.long_id"
          width="180px"
        >
        </el-table-column>
        <el-table-column
          label="数据点ID"
          prop="section_key.long_id"
          width="180px"
        >
        </el-table-column>
        <el-table-column
          :formatter="enabledFormatter"
          label="是否启用"
          prop="enabled"
          width="80px"
        >
        </el-table-column>
        <el-table-column
          label="类型"
          prop="type"
          width="300px">
        </el-table-column>
        <el-table-column
          label="备注"
          prop="remark">
        </el-table-column>
        <el-table-column :width="150" label="操作">
          <template slot-scope="scope">
            <el-button-group>
              <el-button
                @click="handleShowUpdateDialog(scope.row)"
                size="mini">编辑
              </el-button>
              <el-button
                @click="handleDelete(scope.row.key.long_id)"
                size="mini"
                type="danger">删除
              </el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页信息区域 -->
      <el-row justify="end" type="flex">
        <el-col>
          <div class="pagination-container">
            <el-pagination
              :current-page.sync="currentDriverInfoPage"
              :hide-on-single-page="true"
              :page-size="driverInfoPageSize"
              :total="parseInt(driverInfo.count)"
              @current-change="onDriverInfoPageChanged"
              background
              layout="prev, pager, next">
            </el-pagination>
          </div>
        </el-col>
      </el-row>
    </el-card>
    <!-- 添加驱动器对话框 -->
    <el-dialog
      :visible.sync="createDriverVisible"
      center
      title="添加驱动器">
      <el-form
        :model="anchorDriverInfo"
        :rules="createDriverInfoRules"
        label-position="right"
        label-width="80px"
        ref="createDriverInfoForm"
        status-icon
      >
        <el-form-item label="驱动器ID" prop="key">
          <el-input
            oninput="this.value=this.value.replace(/[^\d.]/g,'');"
            placeholder="请输入驱动器的主键，如不输入则自动生成"
            v-model="anchorDriverInfo.key"
          ></el-input>
        </el-form-item>
        <el-form-item label="数据点ID" prop="sectionKey">
          <el-input
            oninput="this.value=this.value.replace(/[^\d.]/g,'');"
            placeholder="请输入数据点ID"
            v-model="anchorDriverInfo.sectionKey"
          ></el-input>
        </el-form-item>
        <el-form-item label="启用" prop="enabled">
          <el-switch
            active-text="启用"
            inactive-text="不启用"
            v-model="anchorDriverInfo.enabled"
          ></el-switch>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-input
            placeholder="请输入驱动器类型"
            v-model="anchorDriverInfo.type"
          >
            <el-button
              @click="handleShowSupportSelector"
              icon="el-icon-search"
              slot="append"
            ></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="配置内容" prop="content">
          <el-input
            autosize
            placeholder="请输入驱动器配置内容"
            type="textarea"
            v-model="anchorDriverInfo.content"
          ></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            placeholder="请输入驱动器备注"
            v-model="anchorDriverInfo.remark"></el-input>
        </el-form-item>
      </el-form>
      <div class="dialog-footer" slot="footer">
        <el-button @click="createDriverVisible = false">取消</el-button>
        <el-button @click="handleCreate" type="primary">确定</el-button>
      </div>
    </el-dialog>
    <!-- 更新驱动器对话框 -->
    <el-dialog
      :visible.sync="updateDriverVisible"
      center
      title="更新驱动器">
      <el-form
        :model="anchorDriverInfo"
        :rules="updateDriverInfoRules"
        label-position="right"
        label-width="80px"
        ref="updateDriverInfoForm"
        status-icon
      >
        <el-form-item label="驱动器ID" prop="key">
          <el-input
            disabled
            v-model="anchorDriverInfo.key"
          ></el-input>
        </el-form-item>
        <el-form-item label="数据点ID" prop="sectionKey">
          <el-input
            disabled
            placeholder="请输入驱动器ID"
            v-model="anchorDriverInfo.sectionKey"
          ></el-input>
        </el-form-item>
        <el-form-item label="启用" prop="enabled">
          <el-switch
            active-text="启用"
            inactive-text="不启用"
            v-model="anchorDriverInfo.enabled"
          ></el-switch>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-input
            placeholder="请输入驱动器类型"
            v-model="anchorDriverInfo.type"
          >
            <el-button
              @click="handleShowSupportSelector"
              icon="el-icon-search"
              slot="append"
            ></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="配置内容" prop="content">
          <el-input
            autosize
            placeholder="请输入驱动器配置内容"
            type="textarea"
            v-model="anchorDriverInfo.content"
          ></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            placeholder="请输入驱动器备注"
            v-model="anchorDriverInfo.remark"></el-input>
        </el-form-item>
      </el-form>
      <div class="dialog-footer" slot="footer">
        <el-button @click="updateDriverVisible = false">取消</el-button>
        <el-button @click="handleUpdate" type="primary">确定</el-button>
      </div>
    </el-dialog>
    <!-- 驱动器支持选择对话框 -->
    <el-dialog
      :visible.sync="selectDriverSupportVisible"
      center
      title="选择驱动器支持">
      <!-- 搜索区域 -->
      <div style="margin-top: 15px;">
        <el-row :gutter="20">
          <el-col :span="9">
            <el-input
              class="input-with-select"
              clearable
              placeholder="请输入驱动器支持ID，支持任意部位匹配"
              v-model="driverSupportId2Search"
            >
              <el-button
                @click="handleDriverSupportIdLikeSearch"
                icon="el-icon-search"
                slot="append"
              ></el-button>
            </el-input>
          </el-col>
          <el-col :span="15">
            <el-input
              class="input-with-select"
              clearable
              placeholder="请输入驱动器支持标签名称，支持任意部位匹配"
              v-model="driverSupportLabel2Search"
            >
              <el-button
                @click="handleDriverSupportLabelLikeSearch"
                icon="el-icon-search"
                slot="append"
              ></el-button>
            </el-input>
          </el-col>
        </el-row>
      </div>
      <!-- 表格 -->
      <el-table
        :data="driverSupport.data"
        stripe
        style="width: 100%">
        <el-table-column
          label="ID"
          prop="key.string_id"
          width="180px">
        </el-table-column>
        <el-table-column
          label="标签"
          prop="label"
          width="200px"
        >
        </el-table-column>
        <el-table-column
          label="描述"
          prop="description"
        >
        </el-table-column>
        <el-table-column :width="150" label="操作">
          <template slot-scope="scope">
            <el-button-group>
              <el-button
                @click="handleSupportSelected(scope.row)"
                size="mini"
                type="success">选择
              </el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页信息区域 -->
      <el-row justify="end" type="flex">
        <el-col>
          <div class="pagination-container">
            <el-pagination
              :current-page.sync="currentDriverSupportPage"
              :hide-on-single-page="true"
              :page-size="driverSupportPageSize"
              :total="parseInt(driverSupport.count)"
              @current-change="onDriverSupportPageChanged"
              background
              layout="prev, pager, next">
            </el-pagination>
          </div>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import {
  exists as existsDriverInfo, all as allDriverInfo, childForSection, insert, remove, update,
} from '../../api/driverInfo';
import { exists as existsSection } from '../../api/section';
import {
  all as allDriverSupport,
  idLike as idLikeDriverSupport,
  labelLike as labelLikeDriverSupport,
} from '../../api/driverSupport';

export default {
  name: 'Permission',
  data() {
    const validateSectionKeyExists = (rule, value, callback) => {
      existsSection(value)
        .then((res) => {
          if (res.meta.code !== 0) {
            callback(new Error('无法验证数据点是否存在'));
            return null;
          }
          if (!res.data) {
            callback(new Error('数据点不存在'));
            return null;
          }
          callback();
          return null;
        })
        .catch(() => {
          callback(new Error('无法验证数据点是否存在'));
          return null;
        });
    };
    const validateDriverInfoNotExists = (rule, value, callback) => {
      if (value === '') {
        callback();
      } else {
        existsDriverInfo(value)
          .then((res) => {
            if (res.meta.code !== 0) {
              callback(new Error('无法验证驱动器信息是否存在'));
              return null;
            }
            if (res.data) {
              callback(new Error('驱动器信息已经存在'));
              return null;
            }
            callback();
            return null;
          })
          .catch(() => {
            callback(new Error('无法验证驱动器信息是否存在'));
            return null;
          });
      }
    };

    return {
      driverInfo: {},
      driverSupport: {},
      driverInfoPageSize: 15,
      driverSupportPageSize: 6,
      currentDriverInfoPage: 1,
      currentDriverSupportPage: 1,
      createDriverVisible: false,
      updateDriverVisible: false,
      selectDriverSupportVisible: false,
      anchorDriverInfo: {
        key: '',
        sectionKey: '',
        enabled: true,
        remark: '',
        content: '',
        type: '',
      },
      createDriverInfoRules: {
        key: [
          {
            validator: validateDriverInfoNotExists,
            trigger: 'blur',
          },
        ],
        sectionKey: [
          {
            required: true,
            message: '必须指定驱动器所属的数据点',
            trigger: 'blur',
          },
          { validator: validateSectionKeyExists, trigger: 'blur' },
        ],
        type: [
          {
            required: true,
            message: '请输入驱动器的类型',
            trigger: ['change', 'blur'],
          },
        ],
      },
      updateDriverInfoRules: {
        type: [
          {
            required: true,
            message: '请输入驱动器的类型',
            trigger: ['change', 'blur'],
          },
        ],
      },
      sectionId2Search: '',
      driverSupportId2Search: '',
      driverSupportLabel2Search: '',
    };
  },
  created() {
    this.lookupAllDriverInfo();
  },
  methods: {
    onDriverInfoPageChanged() {
      this.lookupAllDriverInfo();
    },
    onDriverSupportPageChanged() {
      this.lookupAllDriverSupport();
    },
    lookupAllDriverInfo() {
      allDriverInfo(this.currentDriverInfoPage - 1, this.driverInfoPageSize)
        .then((res) => {
          if (res.meta.code !== 0) {
            this.$message({
              showClose: true,
              message: '请求异常，请稍后再试',
              center: true,
            });
            return null;
          }
          // 当查询的页数大于总页数，自动查询最后一页。
          if (res.data.current_page >= res.data.total_pages && res.data.total_pages > 0) {
            return allDriverInfo(res.data.total_pages - 1, this.driverInfoPageSize);
          }
          this.driverInfo = res.data;
          this.currentDriverInfoPage = res.data.current_page + 1;
          return null;
        })
        .catch((err) => {
          this.$message({
            showClose: true,
            message: `通信错误，异常信息: ${err.message}`,
            type: 'error',
            center: true,
          });
          return null;
        })
        .then((res) => {
          if (res == null) {
            return null;
          }
          if (res.meta.code !== 0) {
            this.$message({
              showClose: true,
              message: '请求异常，请稍后再试',
              center: true,
            });
            return null;
          }
          this.driverInfo = res.data;
          this.currentDriverInfoPage = res.data.current_page + 1;
          return null;
        });
    },
    lookupAllDriverSupport() {
      allDriverSupport(this.currentDriverSupportPage - 1, this.driverSupportPageSize)
        .then((res) => {
          if (res.meta.code !== 0) {
            this.$message({
              showClose: true,
              message: '请求异常，请稍后再试',
              center: true,
            });
            return null;
          }
          // 当查询的页数大于总页数，自动查询最后一页。
          if (res.data.current_page >= res.data.total_pages && res.data.total_pages > 0) {
            return allDriverSupport(res.data.total_pages - 1, this.driverSupportPageSize);
          }
          this.driverSupport = res.data;
          this.currentDriverSupportPage = res.data.current_page + 1;
          return null;
        })
        .catch((err) => {
          this.$message({
            showClose: true,
            message: `通信错误，异常信息: ${err.message}`,
            type: 'error',
            center: true,
          });
          return null;
        })
        .then((res) => {
          if (res == null) {
            return null;
          }
          if (res.meta.code !== 0) {
            this.$message({
              showClose: true,
              message: '请求异常，请稍后再试',
              center: true,
            });
            return null;
          }
          this.driverSupport = res.data;
          this.currentDriverSupportPage = res.data.current_page + 1;
          return null;
        });
    },
    handleShowCreateDialog() {
      this.createDriverVisible = true;
    },
    handleShowUpdateDialog(row) {
      if (this.$refs.updateDriverInfoForm !== undefined) {
        this.$refs.updateDriverInfoForm.resetFields();
      }
      this.anchorDriverInfo.key = row.key.long_id;
      this.anchorDriverInfo.sectionKey = row.section_key.long_id;
      this.anchorDriverInfo.enabled = row.enabled;
      this.anchorDriverInfo.type = row.type;
      this.anchorDriverInfo.remark = row.remark;
      this.anchorDriverInfo.content = row.content;
      this.updateDriverVisible = true;
    },
    handleCreate() {
      if (this.$refs.createDriverInfoForm === undefined) {
        return;
      }
      this.$refs.createDriverInfoForm.validate((bool) => {
        if (!bool) {
          return;
        }
        insert(
          this.anchorDriverInfo.key,
          this.anchorDriverInfo.sectionKey,
          this.anchorDriverInfo.enabled,
          this.anchorDriverInfo.remark,
          this.anchorDriverInfo.content,
          this.anchorDriverInfo.type,
        )
          .then((res) => {
            if (res.meta.code !== 0) {
              this.$message({
                showClose: true,
                message: '创建失败',
                type: 'error',
                center: true,
              });
              return null;
            }
            this.$message({
              showClose: true,
              message: `驱动器 ${res.data.long_id} 创建成功`,
              type: 'success',
              center: true,
            });
            this.createDriverVisible = false;
            this.lookupAllDriverInfo();
            return null;
          })
          .catch((err) => {
            this.$message({
              showClose: true,
              message: `通信错误，异常信息: ${err.message}`,
              type: 'error',
              center: true,
            });
            return null;
          });
      });
    },
    handleUpdate() {
      if (this.$refs.updateDriverInfoForm === undefined) {
        return;
      }
      this.$refs.updateDriverInfoForm.validate((bool) => {
        if (!bool) {
          return;
        }
        update(
          this.anchorDriverInfo.key,
          this.anchorDriverInfo.sectionKey,
          this.anchorDriverInfo.enabled,
          this.anchorDriverInfo.remark,
          this.anchorDriverInfo.content,
          this.anchorDriverInfo.type,
        )
          .then((res) => {
            if (res.meta.code !== 0) {
              this.$message({
                showClose: true,
                message: '更新失败',
                type: 'error',
                center: true,
              });
              return null;
            }
            this.$message({
              showClose: true,
              message: '驱动器更新成功',
              type: 'success',
              center: true,
            });
            this.updateDriverVisible = false;
            this.lookupAllDriverInfo();
            return null;
          })
          .catch((err) => {
            this.$message({
              showClose: true,
              message: `通信错误，异常信息: ${err.message}`,
              type: 'error',
              center: true,
            });
            return null;
          });
      });
    },
    handleDelete(key) {
      this.$confirm('此操作将永久删除此驱动器,同时造成相关过滤数据点位信息丢失。<br>'
          + '<b>我们强烈建议您只是将该驱动器的可用性设置成false，而不是删除。</b><br>'
          + '是否确定要删除该驱动器?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        dangerouslyUseHTMLString: true,
        type: 'warning',
      })
        .then(() => remove(key))
        .catch(() => Promise.reject(new Error('canceled')))
        .then((res) => {
          if (res.meta.code !== 0) {
            this.$message({
              showClose: true,
              message: '删除失败',
              type: 'error',
              center: true,
            });
            return null;
          }
          this.$message({
            type: 'success',
            message: '删除成功!',
          });
          this.lookupAllDriverInfo();
          return null;
        })
        .catch((err) => {
          if (err.message === 'canceled') {
            return null;
          }
          this.$message({
            showClose: true,
            message: `通信错误，异常信息: ${err.message}`,
            type: 'error',
            center: true,
          });
          return null;
        });
    },
    handleSectionIdSearch() {
      if (this.sectionId2Search === '') {
        this.lookupAllDriverInfo();
      } else {
        childForSection(this.sectionId2Search, 0, this.driverInfoPageSize)
          .then((res) => {
            if (res.meta.code !== 0) {
              this.$message({
                showClose: true,
                message: '请求异常，请稍后再试',
                center: true,
              });
              return null;
            }
            this.driverInfo = res.data;
            this.currentDriverInfoPage = res.data.current_page + 1;
            return null;
          })
          .catch((err) => {
            this.$message({
              showClose: true,
              message: `通信错误，异常信息: ${err.message}`,
              type: 'error',
              center: true,
            });
            return null;
          })
          .then((res) => {
            if (res == null) {
              return null;
            }
            if (res.meta.code !== 0) {
              this.$message({
                showClose: true,
                message: '请求异常，请稍后再试',
                center: true,
              });
              return null;
            }

            this.driverInfo.count = 1;
            this.driverInfo.current_page = 0;
            this.driverInfo.data = [res.data];
            this.driverInfo.rows = this.driverInfoPageSize;
            this.driverInfo.totle_pages = 1;
            this.currentDriverInfoPage = 1;
            return null;
          });
      }
    },
    handleShowSupportSelector() {
      this.lookupAllDriverSupport();
      this.selectDriverSupportVisible = true;
    },
    handleDriverSupportIdLikeSearch() {
      if (this.driverSupportId2Search === '') {
        this.lookupAllDriverSupport();
      } else {
        idLikeDriverSupport(this.driverSupportId2Search, 0, this.driverSupportPageSize)
          .then((res) => {
            if (res.meta.code !== 0) {
              this.$message({
                showClose: true,
                message: '请求异常，请稍后再试',
                center: true,
              });
              return null;
            }
            this.driverSupport = res.data;
            this.currentDriverSupportPage = res.data.current_page + 1;
            return null;
          })
          .catch((err) => {
            this.$message({
              showClose: true,
              message: `通信错误，异常信息: ${err.message}`,
              type: 'error',
              center: true,
            });
            return null;
          });
      }
    },
    handleDriverSupportLabelLikeSearch() {
      if (this.driverSupportLabel2Search === '') {
        this.lookupAllDriverSupport();
      } else {
        labelLikeDriverSupport(this.driverSupportLabel2Search, 0, this.driverSupportPageSize)
          .then((res) => {
            if (res.meta.code !== 0) {
              this.$message({
                showClose: true,
                message: '请求异常，请稍后再试',
                center: true,
              });
              return null;
            }
            this.driverSupport = res.data;
            this.currentDriverSupportPage = res.data.current_page + 1;
            return null;
          })
          .catch((err) => {
            this.$message({
              showClose: true,
              message: `通信错误，异常信息: ${err.message}`,
              type: 'error',
              center: true,
            });
            return null;
          });
      }
    },
    handleSupportSelected(row) {
      this.anchorDriverInfo.type = row.key.string_id;
      this.anchorDriverInfo.content = row.example_content;
      this.selectDriverSupportVisible = false;
    },
    enabledFormatter(row) {
      return row.enabled ? '是' : '否';
    },
  },
};
</script>

<style scoped>
  .pagination-container {
    text-align: center;
  }

  .top-el-button {
    width: 100%;
  }
</style>
