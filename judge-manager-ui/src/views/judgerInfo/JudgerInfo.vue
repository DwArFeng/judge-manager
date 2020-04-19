<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item>管理服务</el-breadcrumb-item>
      <el-breadcrumb-item>实体管理</el-breadcrumb-item>
      <el-breadcrumb-item>判断器管理</el-breadcrumb-item>
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
            <el-button @click="handleShowCreateDialog" class="top-el-button" type="primary">添加判断器
            </el-button>
          </el-col>
        </el-row>
      </div>
      <!-- 表格 -->
      <el-table
        :data="judgerInfo.data"
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
              :current-page.sync="currentJudgerInfoPage"
              :hide-on-single-page="true"
              :page-size="judgerInfoPageSize"
              :total="parseInt(judgerInfo.count)"
              @current-change="onJudgerInfoPageChanged"
              background
              layout="prev, pager, next">
            </el-pagination>
          </div>
        </el-col>
      </el-row>
    </el-card>
    <!-- 添加判断器对话框 -->
    <el-dialog
      :visible.sync="createJudgerVisible"
      center
      title="添加判断器">
      <el-form
        :model="anchorJudgerInfo"
        :rules="createJudgerInfoRules"
        label-position="right"
        label-width="80px"
        ref="createJudgerInfoForm"
        status-icon
      >
        <el-form-item label="判断器ID" prop="key">
          <el-input
            oninput="this.value=this.value.replace(/[^\d.]/g,'');"
            placeholder="请输入判断器的主键，如不输入则自动生成"
            v-model="anchorJudgerInfo.key"
          ></el-input>
        </el-form-item>
        <el-form-item label="数据点ID" prop="sectionKey">
          <el-input
            oninput="this.value=this.value.replace(/[^\d.]/g,'');"
            placeholder="请输入数据点ID"
            v-model="anchorJudgerInfo.sectionKey"
          ></el-input>
        </el-form-item>
        <el-form-item label="启用" prop="enabled">
          <el-switch
            active-text="启用"
            inactive-text="不启用"
            v-model="anchorJudgerInfo.enabled"
          ></el-switch>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-input
            placeholder="请输入判断器类型"
            v-model="anchorJudgerInfo.type"
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
            placeholder="请输入判断器配置内容"
            type="textarea"
            v-model="anchorJudgerInfo.content"
          ></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            placeholder="请输入判断器备注"
            v-model="anchorJudgerInfo.remark"></el-input>
        </el-form-item>
      </el-form>
      <div class="dialog-footer" slot="footer">
        <el-button @click="createJudgerVisible = false">取消</el-button>
        <el-button @click="handleCreate" type="primary">确定</el-button>
      </div>
    </el-dialog>
    <!-- 更新判断器对话框 -->
    <el-dialog
      :visible.sync="updateJudgerVisible"
      center
      title="更新判断器">
      <el-form
        :model="anchorJudgerInfo"
        :rules="updateJudgerInfoRules"
        label-position="right"
        label-width="80px"
        ref="updateJudgerInfoForm"
        status-icon
      >
        <el-form-item label="判断器ID" prop="key">
          <el-input
            disabled
            v-model="anchorJudgerInfo.key"
          ></el-input>
        </el-form-item>
        <el-form-item label="数据点ID" prop="sectionKey">
          <el-input
            disabled
            placeholder="请输入判断器ID"
            v-model="anchorJudgerInfo.sectionKey"
          ></el-input>
        </el-form-item>
        <el-form-item label="启用" prop="enabled">
          <el-switch
            active-text="启用"
            inactive-text="不启用"
            v-model="anchorJudgerInfo.enabled"
          ></el-switch>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-input
            placeholder="请输入判断器类型"
            v-model="anchorJudgerInfo.type"
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
            placeholder="请输入判断器配置内容"
            type="textarea"
            v-model="anchorJudgerInfo.content"
          ></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            placeholder="请输入判断器备注"
            v-model="anchorJudgerInfo.remark"></el-input>
        </el-form-item>
      </el-form>
      <div class="dialog-footer" slot="footer">
        <el-button @click="updateJudgerVisible = false">取消</el-button>
        <el-button @click="handleUpdate" type="primary">确定</el-button>
      </div>
    </el-dialog>
    <!-- 判断器支持选择对话框 -->
    <el-dialog
      :visible.sync="selectJudgerSupportVisible"
      center
      title="选择判断器支持">
      <!-- 搜索区域 -->
      <div style="margin-top: 15px;">
        <el-row :gutter="20">
          <el-col :span="9">
            <el-input
              class="input-with-select"
              clearable
              placeholder="请输入判断器支持ID，支持任意部位匹配"
              v-model="judgerSupportId2Search"
            >
              <el-button
                @click="handleJudgerSupportIdLikeSearch"
                icon="el-icon-search"
                slot="append"
              ></el-button>
            </el-input>
          </el-col>
          <el-col :span="15">
            <el-input
              class="input-with-select"
              clearable
              placeholder="请输入判断器支持标签名称，支持任意部位匹配"
              v-model="judgerSupportLabel2Search"
            >
              <el-button
                @click="handleJudgerSupportLabelLikeSearch"
                icon="el-icon-search"
                slot="append"
              ></el-button>
            </el-input>
          </el-col>
        </el-row>
      </div>
      <!-- 表格 -->
      <el-table
        :data="judgerSupport.data"
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
              :current-page.sync="currentJudgerSupportPage"
              :hide-on-single-page="true"
              :page-size="judgerSupportPageSize"
              :total="parseInt(judgerSupport.count)"
              @current-change="onJudgerSupportPageChanged"
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
  exists as existsJudgerInfo, all as allJudgerInfo, childForSection, insert, remove, update,
} from '../../api/judgerInfo';
import { exists as existsSection } from '../../api/section';
import {
  all as allJudgerSupport,
  idLike as idLikeJudgerSupport,
  labelLike as labelLikeJudgerSupport,
} from '../../api/judgerSupport';

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
    const validateJudgerInfoNotExists = (rule, value, callback) => {
      if (value === '') {
        callback();
      } else {
        existsJudgerInfo(value)
          .then((res) => {
            if (res.meta.code !== 0) {
              callback(new Error('无法验证判断器信息是否存在'));
              return null;
            }
            if (res.data) {
              callback(new Error('判断器信息已经存在'));
              return null;
            }
            callback();
            return null;
          })
          .catch(() => {
            callback(new Error('无法验证判断器信息是否存在'));
            return null;
          });
      }
    };

    return {
      judgerInfo: {},
      judgerSupport: {},
      judgerInfoPageSize: 15,
      judgerSupportPageSize: 6,
      currentJudgerInfoPage: 1,
      currentJudgerSupportPage: 1,
      createJudgerVisible: false,
      updateJudgerVisible: false,
      selectJudgerSupportVisible: false,
      anchorJudgerInfo: {
        key: '',
        sectionKey: '',
        enabled: true,
        remark: '',
        content: '',
        type: '',
      },
      createJudgerInfoRules: {
        key: [
          {
            validator: validateJudgerInfoNotExists,
            trigger: 'blur',
          },
        ],
        sectionKey: [
          {
            required: true,
            message: '必须指定判断器所属的数据点',
            trigger: 'blur',
          },
          { validator: validateSectionKeyExists, trigger: 'blur' },
        ],
        type: [
          {
            required: true,
            message: '请输入判断器的类型',
            trigger: ['change', 'blur'],
          },
        ],
      },
      updateJudgerInfoRules: {
        type: [
          {
            required: true,
            message: '请输入判断器的类型',
            trigger: ['change', 'blur'],
          },
        ],
      },
      sectionId2Search: '',
      judgerSupportId2Search: '',
      judgerSupportLabel2Search: '',
    };
  },
  created() {
    this.lookupAllJudgerInfo();
  },
  methods: {
    onJudgerInfoPageChanged() {
      this.lookupAllJudgerInfo();
    },
    onJudgerSupportPageChanged() {
      this.lookupAllJudgerSupport();
    },
    lookupAllJudgerInfo() {
      allJudgerInfo(this.currentJudgerInfoPage - 1, this.judgerInfoPageSize)
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
            return allJudgerInfo(res.data.total_pages - 1, this.judgerInfoPageSize);
          }
          this.judgerInfo = res.data;
          this.currentJudgerInfoPage = res.data.current_page + 1;
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
          this.judgerInfo = res.data;
          this.currentJudgerInfoPage = res.data.current_page + 1;
          return null;
        });
    },
    lookupAllJudgerSupport() {
      allJudgerSupport(this.currentJudgerSupportPage - 1, this.judgerSupportPageSize)
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
            return allJudgerSupport(res.data.total_pages - 1, this.judgerSupportPageSize);
          }
          this.judgerSupport = res.data;
          this.currentJudgerSupportPage = res.data.current_page + 1;
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
          this.judgerSupport = res.data;
          this.currentJudgerSupportPage = res.data.current_page + 1;
          return null;
        });
    },
    handleShowCreateDialog() {
      this.createJudgerVisible = true;
    },
    handleShowUpdateDialog(row) {
      if (this.$refs.updateJudgerInfoForm !== undefined) {
        this.$refs.updateJudgerInfoForm.resetFields();
      }
      this.anchorJudgerInfo.key = row.key.long_id;
      this.anchorJudgerInfo.sectionKey = row.section_key.long_id;
      this.anchorJudgerInfo.enabled = row.enabled;
      this.anchorJudgerInfo.type = row.type;
      this.anchorJudgerInfo.remark = row.remark;
      this.anchorJudgerInfo.content = row.content;
      this.updateJudgerVisible = true;
    },
    handleCreate() {
      if (this.$refs.createJudgerInfoForm === undefined) {
        return;
      }
      this.$refs.createJudgerInfoForm.validate((bool) => {
        if (!bool) {
          return;
        }
        insert(
          this.anchorJudgerInfo.key,
          this.anchorJudgerInfo.sectionKey,
          this.anchorJudgerInfo.enabled,
          this.anchorJudgerInfo.remark,
          this.anchorJudgerInfo.content,
          this.anchorJudgerInfo.type,
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
              message: `判断器 ${res.data.long_id} 创建成功`,
              type: 'success',
              center: true,
            });
            this.createJudgerVisible = false;
            this.lookupAllJudgerInfo();
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
      if (this.$refs.updateJudgerInfoForm === undefined) {
        return;
      }
      this.$refs.updateJudgerInfoForm.validate((bool) => {
        if (!bool) {
          return;
        }
        update(
          this.anchorJudgerInfo.key,
          this.anchorJudgerInfo.sectionKey,
          this.anchorJudgerInfo.enabled,
          this.anchorJudgerInfo.remark,
          this.anchorJudgerInfo.content,
          this.anchorJudgerInfo.type,
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
              message: '判断器更新成功',
              type: 'success',
              center: true,
            });
            this.updateJudgerVisible = false;
            this.lookupAllJudgerInfo();
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
      this.$confirm('此操作将永久删除此判断器,同时造成相关过滤数据点位信息丢失。<br>'
          + '<b>我们强烈建议您只是将该判断器的可用性设置成false，而不是删除。</b><br>'
          + '是否确定要删除该判断器?', '提示', {
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
          this.lookupAllJudgerInfo();
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
        this.lookupAllJudgerInfo();
      } else {
        childForSection(this.sectionId2Search, 0, this.judgerInfoPageSize)
          .then((res) => {
            if (res.meta.code !== 0) {
              this.$message({
                showClose: true,
                message: '请求异常，请稍后再试',
                center: true,
              });
              return null;
            }
            this.judgerInfo = res.data;
            this.currentJudgerInfoPage = res.data.current_page + 1;
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

            this.judgerInfo.count = 1;
            this.judgerInfo.current_page = 0;
            this.judgerInfo.data = [res.data];
            this.judgerInfo.rows = this.judgerInfoPageSize;
            this.judgerInfo.totle_pages = 1;
            this.currentJudgerInfoPage = 1;
            return null;
          });
      }
    },
    handleShowSupportSelector() {
      this.lookupAllJudgerSupport();
      this.selectJudgerSupportVisible = true;
    },
    handleJudgerSupportIdLikeSearch() {
      if (this.judgerSupportId2Search === '') {
        this.lookupAllJudgerSupport();
      } else {
        idLikeJudgerSupport(this.judgerSupportId2Search, 0, this.judgerSupportPageSize)
          .then((res) => {
            if (res.meta.code !== 0) {
              this.$message({
                showClose: true,
                message: '请求异常，请稍后再试',
                center: true,
              });
              return null;
            }
            this.judgerSupport = res.data;
            this.currentJudgerSupportPage = res.data.current_page + 1;
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
    handleJudgerSupportLabelLikeSearch() {
      if (this.judgerSupportLabel2Search === '') {
        this.lookupAllJudgerSupport();
      } else {
        labelLikeJudgerSupport(this.judgerSupportLabel2Search, 0, this.judgerSupportPageSize)
          .then((res) => {
            if (res.meta.code !== 0) {
              this.$message({
                showClose: true,
                message: '请求异常，请稍后再试',
                center: true,
              });
              return null;
            }
            this.judgerSupport = res.data;
            this.currentJudgerSupportPage = res.data.current_page + 1;
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
      this.anchorJudgerInfo.type = row.key.string_id;
      this.anchorJudgerInfo.content = row.example_content;
      this.selectJudgerSupportVisible = false;
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
