<template>
  <el-container class="home-container">
    <el-header height="80px">
      <h2>飞行数据记录器管理服务</h2>
    </el-header>
    <el-container>
      <!-- 侧边栏 -->
      <el-aside width="230px">
        <!-- 菜单 -->
        <el-menu :router="true" active-text-color="#409bff" background-color="#2d3a4b"
                 class="el-menu-vertical-demo" default-active="2" text-color="#ffffff">
          <!-- 实体管理 -->
          <el-submenu index="entity_management">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>实体管理</span>
            </template>
            <!-- 数据点管理 -->
            <el-menu-item index="/home/section">
              <template slot="title">
                <i class="el-icon-s-grid"></i>
                <span>部件管理</span>
              </template>
            </el-menu-item>
            <!-- 驱动器管理 -->
            <el-menu-item index="/home/driver-info">
              <template slot="title">
                <i class="el-icon-s-grid"></i>
                <span>驱动器管理</span>
              </template>
            </el-menu-item>
            <!-- 判断器管理 -->
            <el-menu-item index="/home/judger-info">
              <template slot="title">
                <i class="el-icon-s-grid"></i>
                <span>判断器管理</span>
              </template>
            </el-menu-item>
          </el-submenu>
          <!-- 数据支持 -->
          <el-submenu index="data_support">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>数据支持</span>
            </template>
            <!-- 驱动器支持 -->
            <el-menu-item index="/home/driver-support">
              <template slot="title">
                <i class="el-icon-s-grid"></i>
                <span>驱动器支持</span>
              </template>
            </el-menu-item>
            <!-- 判断器支持 -->
            <el-menu-item index="/home/judger-support">
              <template slot="title">
                <i class="el-icon-s-grid"></i>
                <span>判断器支持</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'Home',
};
</script>

<style scoped type="less">
  @bg: #2d3a4b;
  @dark_gray: #889aa4;
  @light_gray: #eee;

  .home-container {
    width: 100%;
    height: 100%;
  }

  .el-header {
    display: flex;
    justify-content: flex-start;
    background-color: #1c242d;
    color: #889aa4;
    text-align: center;
  }

  .el-aside {
    background-color: #2d3a4b;
    color: #889aa4;
    line-height: 200px;
    padding: 0;
    margin: 0;
    width: auto;
  }

  .el-main {
    background-color: #eeeeee;
    color: #889aa4;
  }

  .el-menu {
    padding: 0;
    margin: 0;
    border-right: 0;
  }
</style>
