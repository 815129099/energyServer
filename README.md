# energyServer
服务端
-----

* [项目演示](http://47.106.172.176:666/ "项目演示")  
   管理员账户：admin 密码：111111(请不要删除相关数据)  
   数据只有19年6月、7月、8月的
* 能源采集系统介绍：系统分三层包括硬件、后端和前端，后端通过Netty网络框架与硬件通信，前后端完全分离，后端通过Nginx跨域与前端交互。
* 硬件：后端采用JNA编程调用C编译的dll接口对传输数据进行编码与解码，再通过Netty网络框架与硬件采集器进行数据传输。可定时和手动采集硬件数据，存储于MarriaDB主从数据库中（有数据库优化经验），数据量级达千万，redis作为数据库缓存。
* 后端：Java作为服务端语言，SpringBoot整合MybatisPlus处理DAO接口，整合shiro处理权限，MD5加密解密，异常处理，整合邮箱和短信接口发送通知，Scheduled定时采集，POI操作Excel ，log4j日志。
* 前端：webpack+vue脚手架搭建前端开发环境，ElementUI响应式UI框架，vue.js作为渐进式框架构建用户界面， vuex动态路由控制导航菜单，axios与后台交互，v-charts生成图表。
* [前端源码](https://github.com/815129099/energyCode "前端源码")
