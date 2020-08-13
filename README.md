# 乐优商城
## 一、项目介绍
1. 项目概述
	- 乐优商城是一个模仿如京东、天猫等大型电商购物网站（B2C）。
	- 用户可以在线购买商品、加入购物车、下单、秒杀商品和对已购商品进行评价。
	- 管理员可以在后台管理商品的上下架和促销活动。
	- 管理员可以监控商品销售状况
	- 客服可以在后台处理退款操作

2. 项目用途
	- 该项目原型来源于黑马程序员。
	- 通过该项目可以宏观的了解大型系统架构。
	- 该项目所使用的技术都是java当前流行的技术，学习该项目可以把当前所学的大部分java技术结合运用。

## 二、系统架构

### 1. 架构图
![乐优商城架构](http://imostro.xyz:8090/upload/2020/07/1525703759035-007862d499fe4858bc5c7b4cc3fa8e7a.png)

### 2. 架构分析
整个乐优商城宏观上可分为：前台门户系统和后台管理系统。

- 前台门户系统：
	- 前台门户面向的是客户，包含与客户交互的一切功能例如：
		- 用户注册与登录
		- 浏览和搜索商品
		- 加入购物车
		- 下单和评价商品
	- 前台系统我们会使用Thymeleaf模板引擎技术来完成页面开发。出于SEO优化的考虑，我们将不采用单页应用。

- 后台管理系统:
	- 后台系统主要包含以下功能：
		- 商品管理，包括商品分类、品牌、商品规格等信息的管理
		- 销售管理，包括订单统计，订单退款处理，促销活动生成等
		- 用户管理，包括用户控制，冻结，解锁等
		- 权限管理，整个网站的权限控制，采用智威汤逊鉴权方案，对用户及API进行权限控制
		- 统计，各种数据的统计分析展示
	- 后台系统会采用前后端分离开发，而且整个后台管理系统会使用Vue.js框架搭建出单页应用（SPA）。

另外，乐优商城采用SOA的架构，上述系统共享着相同的微服务集群:
- 服务中心：
- 商品微服务：商品及商品分类，品牌，库存等的服务
- 搜索微服务：实现搜索服务
- 订单微服务：实现订单相关服务
- 购物车微服务：实现购物车相关服务
- 用户中心：用户的登录注册等服务


## 三、技术选型
#### 前端技术：
|技术	|说明|官网|
|-------|-------|-------|
|Vue|前端框架|https://vuejs.org/|
|Vue-Router|路由框架|https://router.vuejs.org/|
|Vuex|全局状态管理框架|	https://vuex.vuejs.org/|
|webpack|前端构建工具|https://webpack.js.org/|
|Vuetify|Vue UI框架|https://vuetifyjs.com/en/|
|Ajax|前端HTTP框架|	https://github.com/axios/axios|
|Vuex|全局状态管理框架|	https://vuex.vuejs.org/|


后端技术：

|技术	|说明|官网|
|-------|-------|-------|
|SpringBoot|容器+MVC框架|https://spring.io/projects/spring-boot|
|MyBatis|ORM框架|http://www.mybatis.org/mybatis-3/zh/index.html|
|Swagger-UI|文档生产工具|https://github.com/swagger-api/swagger-ui|
|Hibernator-Validator|验证框架|http://hibernate.org/validator|
|Elasticsearch|	搜索引擎|https://github.com/elastic/elasticsearch|
|RabbitMQ|消息队列|https://www.rabbitmq.com/|
|Redis|	分布式缓存|https://redis.io/|
|OSS|	对象存储|https://github.com/aliyun/aliyun-oss-java-sdk|
|Docker|应用容器引擎|https://www.docker.com|
|Druid|	数据库连接池|https://github.com/alibaba/druid|
|JWT|WT登录支持|https://github.com/jwtk/jjwt|
|Lombok|简化对象封装工具|https://github.com/rzwitserloot/lombok|

