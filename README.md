# my-project
基于spring cloud（后端服务）+ vue（前端）的模板项目

---

也可以用来入门学习vue和spring boot 和 spring cloud和作为脚手架搭建项目基础框架

前端vue使用的ui是[vue-ant-design](https://vue.ant.design)

---

### 服务调用链路监控
- zipkin服务端的jar包可以到官网https://zipkin.io/pages/quickstart.html自行下载,然后放到目录my-project/zipkin-jar下，
修改startup.bat里面的zipkin.jar的文件名，启动之，在浏览器输入http://localhost:9411可以访问微服务链路监控
---

### 启动方式
- 1.启动my-project/eureka，服务注册中心
- 2.启动my-project/server/gateway，网关服务
- 3.启动my-project/server/account（用户账户管理）或者file（文件系统）等其他服务，
服务对应的数据库连接在application.yml文件中，根据需要创建对应的数据库，初始化用户账户的sql脚本在
db/data-mysql.sql，账户密码是：admin 123456
- 4.在my-project/web-client下按照readme.md的描述启动前端
- 5.浏览器输入http://localhost:8080访问
---


### 示例图片

- 登录界面

![Image text](https://raw.githubusercontent.com/a497556016/my-project/master/web-client/src/assets/sample/login.jpg)

- 主页

![Image text](https://github.com/a497556016/my-project/blob/master/web-client/src/assets/sample/index.jpg?raw=true)

- 项目文档结构

![Image text](https://github.com/a497556016/my-project/blob/master/web-client/src/assets/sample/devloper.jpg?raw=true)
