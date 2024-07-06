# SSM开发社交网站 - 书评网

## 项目介绍

本项目是一个使用SSM（Spring/Spring MVC/MyBatis）框架开发的社交书评网站，名为“书评网”。项目旨在展示如何整合Spring、Spring MVC和MyBatis框架，以及如何使用MyBatis-Plus插件和一些前端技术来开发一个功能完整的Web应用。

## 主要知识点

- SSM整合配置
- MyBatis-Plus配置与应用
- Kaptcha验证码组件使用
- 富文本编辑器wangEditor
- Spring Task任务调度

## SSM整合配置

### 环境配置

- 新建Maven工程，添加Web模块
- 配置`pom.xml`文件以添加Spring webmvc依赖和其他相关依赖

### Spring与Spring MVC配置

- 配置`web.xml`以启用DispatcherServlet
- 配置字符集过滤器以解决中文乱码问题

### Spring与MyBatis配置

- 配置数据源与连接池
- 配置SqlSessionFactory和Mapper扫描器

### 其他组件整合

- 配置logback日志输出
- 声明式事务配置
- 整合JUnit单元测试

## MyBatis-Plus整合

### 介绍

- MyBatis-Plus是一个MyBatis的增强工具，用于自动实现Mapper CRUD操作。

### 整合步骤

- 在`pom.xml`中引入MyBatis-Plus依赖
- 更改Spring XML中的SqlSessionFactory实现类
- 在`mybatis-config.xml`中增加MyBatis-Plus分页插件

### 开发步骤

- 使用MyBatis-Plus核心注解和BaseMapper接口API

## 基于SSM的社交网站开发

### 前端技术

- 使用Bootstrap前端组件库开发响应式布局

### 实体类与Mapper

- 创建与数据库表对应的实体类
- 配置Mapper接口和XML文件

### 服务层

- 创建服务接口和实现类

### 控制器

- 新建控制类以获取数据库信息并展示

### 分页查询与Ajax动态加载

- 实现图书分页查询功能
- 使用Ajax动态加载图书信息

### JS模板引擎使用

- 使用Art-Template作为JavaScript模板引擎

## 开始使用

1. 克隆或下载本项目
2. 导入Maven工程到您喜欢的IDE
3. 配置数据库连接
4. 运行项目并访问`localhost`查看结果