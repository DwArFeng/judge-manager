<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item>管理服务</el-breadcrumb-item>
      <el-breadcrumb-item>实体管理</el-breadcrumb-item>
      <el-breadcrumb-item>部件管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片工作区 -->
    <el-card class="box-card">
      <!-- 搜索与添加区域 -->
      <div style="margin-top: 15px;">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-input
              class="input-with-select"
              clearable
              oninput="this.value=this.value.replace(/[^\d.]/g,'');"
              placeholder="请输入部件ID，仅支持精确查询"
              v-model="id2Search"
            >
              <el-button @click="handleIdSearch" icon="el-icon-search" slot="append"></el-button>
            </el-input>
          </el-col>
          <el-col :span="15">
            <el-input
              class="input-with-select"
              clearable
              placeholder="请输入部件名称，支持任意部位匹配"
              v-model="name2Search"
            >
              <el-button
                @click="handleNameLikeSearch"
                icon="el-icon-search"
                slot="append"
              ></el-button>
            </el-input>
          </el-col>
          <el-col :span="3">
            <el-button @click="handleShowCreateDialog" class="top-el-button" type="primary">添加部件
            </el-button>
          </el-col>
        </el-row>
      </div>
      <!-- 表格 -->
      <el-table
        :data="section.data"
        stripe
        style="width: 100%">
        <el-table-column
          label="ID"
          prop="key.long_id"
          width="180px">
        </el-table-column>
        <el-table-column
          label="名称"
          prop="name">
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
              :current-page.sync="currentPage"
              :hide-on-single-page="true"
              :page-size="pageSize"
              :total="parseInt(section.count)"
              @current-change="onPageChanged"
              background
              layout="prev, pager, next">
            </el-pagination>
          </div>
        </el-col>
      </el-row>
    </el-card>
    <!-- 添加部件对话框 -->
    <el-dialog
      :visible.sync="createSectionVisible"
      center
      title="添加部件">
      <el-form
        :model="anchorSection"
        :rules="createPermissionRules"
        label-position="right"
        label-width="80px"
        ref="createPermissionForm"
        status-icon>
        <el-form-item label="ID" prop="key">
          <el-input
            oninput="this.value=this.value.replace(/[^\d.]/g,'');"
            placeholder="请输入部件的主键，如不输入则自动生成"
            v-model="anchorSection.key"
          ></el-input>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input
            placeholder="请输入部件名称"
            v-model="anchorSection.name"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            placeholder="请输入部件备注"
            v-model="anchorSection.remark"></el-input>
        </el-form-item>
      </el-form>
      <div class="dialog-footer" slot="footer">
        <el-button @click="createSectionVisible = false">取消</el-button>
        <el-button @click="handleCreate" type="primary">确定</el-button>
      </div>
    </el-dialog>
    <!-- 更新部件对话框 -->
    <el-dialog
      :visible.sync="updatePermissionVisible"
      center
      title="更新部件">
      <el-form
        :model="anchorSection"
        :rules="updatePermissionRules"
        label-position="right"
        label-width="80px"
        ref="updatePermissionForm"
        status-icon>
        <el-form-item label="ID" prop="key">
          <el-input
            :disabled="true"
            v-model="anchorSection.key"></el-input>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input
            v-model="anchorSection.name"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            placeholder="请输入部件备注"
            v-model="anchorSection.remark"></el-input>
        </el-form-item>
      </el-form>
      <div class="dialog-footer" slot="footer">
        <el-button @click="updatePermissionVisible = false">取消</el-button>
        <el-button @click="handleUpdate" type="primary">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  all, exists, insert, inspect, nameLike, remove, update,
} from '../../api/section';

export default {
  name: 'Permission',
  data() {
    const validateSectionNotExists = (rule, value, callback) => {
      if (value === '') {
        callback();
      } else {
        exists(value)
          .then((res) => {
            if (res.meta.code !== 0) {
              callback(new Error('无法验证部件是否存在'));
              return null;
            }
            if (res.data) {
              callback(new Error('部件已经存在'));
              return null;
            }
            callback();
            return null;
          })
          .catch(() => {
            callback(new Error('无法验证部件是否存在'));
            return null;
          });
      }
    };

    return {
      section: {},
      pageSize: 15,
      currentPage: 1,
      createSectionVisible: false,
      updatePermissionVisible: false,
      anchorSection: {
        key: '',
        name: '',
        remark: '',
      },
      createPermissionRules: {
        key: [
          {
            validator: validateSectionNotExists,
            trigger: 'blur',
          },
        ],
        name: [
          {
            required: true,
            message: '部件名称是部件重要的助记符，请指定有意义的值，以免与其它部件混淆',
            trigger: 'blur',
          },
        ],
      },
      updatePermissionRules: {
        name: [
          {
            required: true,
            message: '部件名称是部件重要的助记符，请指定有意义的值，以免与其它部件混淆',
            trigger: 'blur',
          },
        ],
      },
      id2Search: '',
      name2Search: '',
    };
  },
  created() {
    this.lookupAll();
  },
  methods: {
    onPageChanged() {
      this.lookupAll();
    },
    lookupAll() {
      all(this.currentPage - 1, this.pageSize)
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
            return all(res.data.total_pages - 1, this.pageSize);
          }
          this.section = res.data;
          this.currentPage = res.data.current_page + 1;
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
          this.section = res.data;
          this.currentPage = res.data.current_page + 1;
          return null;
        });
    },
    handleShowCreateDialog() {
      this.createSectionVisible = true;
    },
    handleShowUpdateDialog(row) {
      if (this.$refs.updatePermissionForm !== undefined) {
        this.$refs.updatePermissionForm.resetFields();
      }
      this.anchorSection.key = row.key.long_id;
      this.anchorSection.name = row.name;
      this.anchorSection.remark = row.remark;
      this.updatePermissionVisible = true;
    },
    handleCreate() {
      if (this.$refs.createPermissionForm === undefined) {
        return;
      }
      this.$refs.createPermissionForm.validate((bool) => {
        if (!bool) {
          return;
        }
        insert(
          this.anchorSection.key,
          this.anchorSection.name,
          this.anchorSection.remark,
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
              message: `部件 ${res.data.long_id} 创建成功`,
              type: 'success',
              center: true,
            });
            this.createSectionVisible = false;
            this.lookupAll();
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
      if (this.$refs.updatePermissionForm === undefined) {
        return;
      }
      this.$refs.updatePermissionForm.validate((bool) => {
        if (!bool) {
          return;
        }
        update(
          this.anchorSection.key,
          this.anchorSection.name,
          this.anchorSection.remark,
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
              message: '部件更新成功',
              type: 'success',
              center: true,
            });
            this.updatePermissionVisible = false;
            this.lookupAll();
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
      this.$confirm('此操作将永久删除此部件。<br>'
          + '删除部件时会同时删除部件的驱动器设置和判断器设置。<br>'
          + '<b>我们强联建议您只是禁用所有的驱动器以及判断器，'
          + '而不是删除部件。</b><br>'
          + '是否继续?',
      '提示', {
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
          this.lookupAll();
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
    handleIdSearch() {
      if (this.id2Search === '') {
        this.lookupAll();
      } else {
        exists(this.id2Search)
          .then((res) => {
            if (res.data) {
              return inspect(this.id2Search);
            }
            this.$message({
              showClose: true,
              message: `部件 ${this.id2Search} 不存在`,
              type: 'warning',
              center: true,
            });
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

            this.section.count = 1;
            this.section.current_page = 0;
            this.section.data = [res.data];
            this.section.rows = this.pageSize;
            this.section.totle_pages = 1;
            this.currentPage = 1;
            return null;
          });
      }
    },
    handleNameLikeSearch() {
      if (this.name2Search === '') {
        this.lookupAll();
      } else {
        nameLike(this.name2Search, 0, this.pageSize)
          .then((res) => {
            if (res.meta.code !== 0) {
              this.$message({
                showClose: true,
                message: '请求异常，请稍后再试',
                center: true,
              });
              return null;
            }
            this.section = res.data;
            this.currentPage = res.data.current_page + 1;
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
