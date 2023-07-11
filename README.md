

# 智慧医疗(yygh)

#### 一、简介

​	智慧医疗即为网上预约挂号系统，网上预约挂号是近年来开展的一项便民就医服务，旨在缓解看病难、挂号难的就医难题，许多患者为看一次病要跑很多次医院，最终还不一定能保证看得上医生。网上预约挂号全面提供的预约挂号业务从根本上解决了这一就医难题。随时随地轻松挂号！不用排长队！

####  二、核心技术

1、SpringBoot

2、SpringCloud

（1）Nacos注册中心

（2）Feign

（3）GateWay

3、Redis

（1）使用Redis作为缓存

（2）验证码有效时间、支付二维码有效时间

4、MongoDB

（1）使用MongoDB存储 医院相关数据

5、EasyExcel

（1）操作excel表格，进行读和写操作

6、MyBatisPlus

7、RabbitMQ

（1）订单相关操作，发送mq消息

8、Docker

（1）下载镜像 docker pull 

（2）创建容器 docker run

9、阿里云OSS

10、阿里云短信服务 

11、微信登录/支付

12、定时任务

#### 三、主要功能

**后台管理系统**

1、医院设置管理

（1）医院设置列表、添加、锁定、删除

（2）医院列表、详情、排班、下线

2、数据管理

（1）数据字典树形显示、导入、导出

3、用户管理

（1）用户列表、查看、锁定

（2）认证用户审批

4、订单管理

（1）订单列表、详情

5、统计管理

（1）预约统计

**前台用户系统**

1、首页数据显示

（1）医院列表

2、医院详情显示

（1）医院科室显示

3、用户登录功能

（1）手机号登录（短信验证码发送）

（2）微信扫描登录

4、用户实名认证

5、就诊人管理

（1）列表、添加、详情、删除

6、预约挂号功能

（1）排班和挂号详情信息

（2）确认挂号信息

（3）生成预约挂号订单

（4）挂号订单支付（微信）

（5）取消预约订单

7、就医提醒功能

#### 四 、核心业务流程

![image](https://github.com/zhouhuan2003/yygh/assets/109257747/a2ef8f9f-c0e8-483b-baee-a58230204f66)


#### 五、服务架构

![image](https://github.com/zhouhuan2003/yygh/assets/109257747/2610ceb9-390e-476c-aff0-b8bc2a530a0e)


#### 六、工程结构

后端工程

![image](https://github.com/zhouhuan2003/yygh/assets/109257747/c3a8ab13-117d-4e8f-8a43-745a943eda83)


工程说明： 

| 工程模块名称               | 作用                |
| -------------------------- | ------------------- |
| common                     | 公共模块            |
| hospital-manage            | 医院接口模拟端        |
| model                      | 所有的model类       |
| service(二级父工程)        | api接口服务父节点   |
| service_hosp               | 医院api接口服务     |
| service_order              | 订单api接口服务     |
| service_client(二级父工程) | feign服务调用父节点 |
| service-hosp-client        |                     |
| service-order-client       | 订单api接口         |
| server-gateway             | 服务网关            |

前端说明

![image](https://github.com/zhouhuan2003/yygh/assets/109257747/6dcb4b19-0c21-4744-8f5f-5c287be25ef2)


| 工程模块名称              | 作用         |
| ------------------------- | ------------ |
| vue-admin-template-master | 管理平台前端 |
| yygh-site                 | 用户前端     |

#### 七、项目预览
​	1.用户端主页
![image](https://github.com/zhouhuan2003/yygh/assets/109257747/4ac40a9e-9a4a-41a1-8f71-78c577cb6aba)
![image](https://github.com/zhouhuan2003/yygh/assets/109257747/780ddf4a-a833-48f5-9bb1-1291c76166a3)
​	2.用户端登录页面
![image](https://github.com/zhouhuan2003/yygh/assets/109257747/55623261-52b8-457e-b6ce-22f8a335e946)
![image](https://github.com/zhouhuan2003/yygh/assets/109257747/0706a257-a856-496f-8c54-1cae445a6072)
  3.就诊人管理
  ![image](https://github.com/zhouhuan2003/yygh/assets/109257747/e332b818-131f-40a3-a8c7-bcc5490960da)
  ![image](https://github.com/zhouhuan2003/yygh/assets/109257747/8384d2aa-f51f-4526-bd6a-140cd896c20d)
  4.预约挂号
  ![image](https://github.com/zhouhuan2003/yygh/assets/109257747/646c2c9d-9df8-4b1f-bc35-fe42bbace142)
  ![image](https://github.com/zhouhuan2003/yygh/assets/109257747/18edbfe0-aab4-4cfc-b760-a7c9ef57d2ec)
  ![image](https://github.com/zhouhuan2003/yygh/assets/109257747/de7a5933-5f2f-418a-b895-c5e4e6623a74)
  ![image](https://github.com/zhouhuan2003/yygh/assets/109257747/de1b7d79-dad7-4ab6-9e73-b818133e5bcd)
  5.后台管理
  ![image](https://github.com/zhouhuan2003/yygh/assets/109257747/8fa28dcc-3845-4424-a153-8f1c9f6ed457)
  ![image](https://github.com/zhouhuan2003/yygh/assets/109257747/33d1f96f-b708-47a6-a7ae-af3c0e842b39)
  ![image](https://github.com/zhouhuan2003/yygh/assets/109257747/3a41818c-abff-4ff0-8413-4fe958a021f4)
  ![image](https://github.com/zhouhuan2003/yygh/assets/109257747/1b2a5522-adf3-4560-b0c8-a6016e577521)












