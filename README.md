# judge-manager

作者的开源项目 `flight-data-record`（简写为 `judge`） 的管理器，提供了一个可以提供维护和查询的web ui界面。

[什么是judge？它到底是干什么的？](https://github.com/DwArFeng/judge)

## 界面

- 实体管理
  - 部件管理
  - 驱动器管理
  - 判断器管理
- 数据支持
  - 驱动器支持
  - 判断器支持
  
---

## 如何使用

1. 下载源码
   ```
   git clone git@github.com:DwArFeng/judge-manager.git
   ```
   对于中国用户，可以使用gitee进行高速下载。
   ```
   git clone git@gitee.com:dwarfeng/judge-manager.git
   ```
   
2. 项目打包

   进入项目根目录，执行maven命令
   ```
   mvn clean package
   ```
   该过程会下载node v12.16.1，中国用户的下载速度可能会很慢，请视情况翻墙或者提前准备文件。

3. 解压

   找到打包后的目标文件 
   ```
   judge-manager-distribution/target/judge-manager-distribution-1.0.0.a-SNAPSHOT-release.tar.gz
   ```
   将其解压至tomcat的根目录内即可。

4. 配置

   1. 确认tomcat的工作目录在tomcat的根目录下，否则需要将 conf/judge-manager 文件夹移动到tomcat的工作目录中。
      
      *提示: 如果您使用windows的tomcat，那么工作目录可能是 bin/*
      
   2. 配置conf文件夹下的内容
   
      conf/dubbo/connection.properties
      ``` properties
      dubbo.zookeeper.address=zookeeper://192.168.154.100:2181
      dubbo.port=20033
      dubbo.qos.port=21033
      dubbo.host=192.168.154.1
      ```
      
      conf/judge/exception.properties
      ``` properties
      # judgemanager 工程中 subgrade 的异常代号偏移量。
      judge_manager.exception_code_offset.subgrade=0
      ```
      
5. enjoy it

---

## 版本对应

|judge-manager版本|judge版本|
|:---|:---|
|1.0.0|1.1.1-1.1.x|
