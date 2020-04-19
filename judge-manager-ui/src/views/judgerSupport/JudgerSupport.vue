<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item>管理服务</el-breadcrumb-item>
      <el-breadcrumb-item>数据支持</el-breadcrumb-item>
      <el-breadcrumb-item>驱动器支持</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片工作区 -->
    <el-card class="box-card">
      <!-- 搜索与添加区域 -->
      <div style="margin-top: 15px;">
        <el-row :gutter="20">
          <el-col :span="9">
            <el-input
              class="input-with-select"
              clearable
              placeholder="请输入驱动器支持ID，支持任意部位匹配"
              v-model="id2Search"
            >
              <el-button
                @click="handleIdLikeSearch"
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
              v-model="label2Search"
            >
              <el-button
                @click="handleLabelLikeSearch"
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
          width="400px"
        >
        </el-table-column>
        <el-table-column
          label="描述"
          prop="description"
        >
        </el-table-column>
        <el-table-column
          :formatter="exampleContentFormatter"
          label="配置示例"
          prop="example_content"
          width="100px">
        </el-table-column>
        <el-table-column :width="150" label="操作">
          <template slot-scope="scope">
            <el-button-group>
              <el-button
                @click="handleShowDetailDialog(scope.row)"
                size="mini"
                type="success">详细信息
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
              :total="parseInt(driverSupport.count)"
              @current-change="onPageChanged"
              background
              layout="prev, pager, next">
            </el-pagination>
          </div>
        </el-col>
      </el-row>
    </el-card>
    <!-- 添加数据点对话框 -->
    <el-dialog
      :visible.sync="showDetailVisible"
      center
      title="详细信息">
      <el-form
        :model="anchorDriverSupport"
        label-position="right"
        label-width="80px"
        ref="createPermissionForm"
        status-icon>
        <el-form-item label="ID" prop="key">
          <el-input
            :readonly="true"
            v-model="anchorDriverSupport.key"
          ></el-input>
        </el-form-item>
        <el-form-item label="标签" prop="label">
          <el-input
            :readonly="true"
            v-model="anchorDriverSupport.label"
          ></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            :readonly="true"
            autosize
            type="textarea"
            v-model="anchorDriverSupport.description"
          ></el-input>
        </el-form-item>
        <el-form-item label="配置示例" prop="exampleContent">
          <el-input
            :readonly="true"
            autosize
            type="textarea"
            v-model="anchorDriverSupport.exampleContent"
          ></el-input>
        </el-form-item>
      </el-form>
      <div class="dialog-footer" slot="footer">
        <el-button @click="showDetailVisible = false" type="primary">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  all, idLike, labelLike,
} from '../../api/driverSupport';

export default {
  name: 'Permission',
  data() {
    return {
      driverSupport: {},
      pageSize: 15,
      currentPage: 1,
      anchorDriverSupport: {
        key: '',
        label: '',
        description: '',
        exampleContent: '',
      },
      showDetailVisible: false,
      id2Search: '',
      label2Search: '',
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
          this.driverSupport = res.data;
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
          this.driverSupport = res.data;
          this.currentPage = res.data.current_page + 1;
          return null;
        });
    },
    handleShowDetailDialog(row) {
      if (this.$refs.updatePermissionForm !== undefined) {
        this.$refs.updatePermissionForm.resetFields();
      }
      this.anchorDriverSupport.key = row.key.string_id;
      this.anchorDriverSupport.label = row.label;
      this.anchorDriverSupport.description = row.description;
      this.anchorDriverSupport.exampleContent = row.example_content;
      this.showDetailVisible = true;
    },
    exampleContentFormatter() {
      return '见详细页面';
    },
    handleIdLikeSearch() {
      if (this.id2Search === '') {
        this.lookupAll();
      } else {
        idLike(this.id2Search, 0, this.pageSize)
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
    handleLabelLikeSearch() {
      if (this.label2Search === '') {
        this.lookupAll();
      } else {
        labelLike(this.label2Search, 0, this.pageSize)
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
</style>
