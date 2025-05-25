# ms-umapp

小程序微服务项目采用Java模块化的架构思想，通过maven构建工具将小程序微服务按模块解耦，利用springcloud微服务架构思想将各独立模块整合，做到核心功能下沉，独立功能单独部署。

## ms-umapp-auth
小程序微服务的授权服务
服务端口号：70750

## ms-umapp-gateway
小程序微服务的网关服务
服务端口号：70751

## ms-umapp-user
小程序微服务用户服务
服务端口号：70752

## ms-umapp-common
小程序微服务的通用组件模块，其中包含各个功能模块的封装和配置
主要包括以下组件模块：
### ms-umapp-common-core 核心业务模块
### ms-umapp-common-feign feign配置模块
### ms-umapp-common-mybatis mybaitis配置模块
### ms-umapp-common-redis redis配置模块
### ms-umapp-common-util util工具模块

## ms-umapp-service
小程序微服务的业务小程序服务，该服务为模块组，下边有多个service服务
### ms-umapp-service-technology 技术嘉年华小程序
### ms-umapp-service-sharebook 共享图书小程序

## ms-umapp-feign
小程序微服务的feign接口模块，其与各个微服务对应，将微服务中的model从服务中抽离出来，与feign接口组合，解决了多模块项目循环引用的问题。
### ms-umapp-feign-user user微服务的feign接口模块