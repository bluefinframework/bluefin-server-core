[![Build Status](https://travis-ci.org/bluefinframework/bluefin-server-core.svg?branch=master)](https://travis-ci.org/bluefinframework/bluefin-server-core)

#Bluefin
> Bluefin是一个致力于简便开发与测试人员分发APK 文件的微服务。


##  Features

* APK 与 MAPPING文件上传与存储
* APK 信息的hook服务
* APK 信息查询
* APK 下载
* 混淆信息还原
* 纯文件存储，备份单个文件夹即备份了整个项目。

## 使用方式

Bluefin Server基于Spring Boot开发，使用Maven构建。所以请确保运行的机器安装了Java环境，Maven环境，如果需要还原混淆信息，还需要配置`ANDROID_HOME`参数用来retrace混淆信息。


### 参数配置

Bluefin 除了可以通过Spring Boot的配置文件进行参数配置外，对于Bluefin特有的参数，同时支持配置文件与环境变量两种方式，由于大多数参数都有默认值，所以你即使不配置任何参数，也可以使用Bluefin的大部分功能。

参数值的权重顺序依次为：

	配置文件 > 环境变量 > 默认值
	
参数的详细信息见如下表格：


|名称     |配置文件名称|环境变量名称|默认值|含义|
|--------|-------|------|------|-----|
|host|server.address|BLUEFIN\_SERVER\_HOST|主机IP/127.0.0.1|bluefin启动的host|
|port|server.port|BLUEFIN\_SERVER\_PORT|2556|bluefin启动的端口|
|base url|output.baseurl|BLUEFIN\_BASE\_URL|host:port|定义对外公开的根url地址，当使用Docker部署或使用Nginx之类端口转发服务时，一定要注意定义该值，apk的下载链接会基于该值|
|store apk path|filestore.path|BLUEFIN\_FILE\_STORE\_PATH|用户目录下的.bluefin文件夹|定义apk与mapping文件的存储路径，这是Bluefin的核心目录|
|store tmp path|filestore.tmp|BLUEFIN\_FILE\_TMP\_PATH|用户目录下的.bluefin文件夹里的tmp文件夹|用于存放解析apk或者retrace时产生的临时文件|
|android sdk home|android.sdk.home|ANDROID_HOME|无|Android sdk的目录，用来获取retrace 文件|
|hook urls|hook.upload.apk.urls|BLUEFIN_HOOK_URLS|无|apk解析完毕后的hook回调地址，支持添加多个，用`,`分隔|


### 原生方式

* 如果clone了源码，在源码目录运行

 		mvn  spring-boot:run
 	
  默认就会在当前机器的2556端口开启了Bluefin服务.


### Docker

	docker run -it -p 2556:2556 -v $(pwd):/root saymagic/bluefin:v1.0

## 技巧

* Bluefin使用包名和id共同定位一个apk文件，这个id是由apk来进行定义的，Bluefin Server会解析`AndroidMainifest.xml`文件下名称为`bluefinidentify`的`meta-data`标签来作为改apk的id，一般建议使用打包时的时间戳，在打包时动态的来修改此值。如果不存在`bluefinidentify`的标签，Bluefin会使用此apk的version code作为id标识，这样的坏处是同一个版本多次打包时会是覆盖关系。

* Bluefin会解析`AndroidMainifest.xml`文件下名称为`bluefinUpdateInfo`的`meta-data`标签来作为apk的更新信息，如果该值存在，该值会在调用查询接口时一同返回。

* Bluefin会解析`AndroidMainifest.xml`文件下名称为`bluefinExtData`的`meta-data`标签来作为apk的附加信息，如果该值存在，该值会在调用查询接口时一同返回。


##  API 接口文档

详见:[http://doc.saymagic.cn/bluefin/162924](http://doc.saymagic.cn/bluefin/162924)


## Todo

* tmp文件夹数据的清理:

   对于tmp文件夹下的临时文件，目前Bluefin没有完善的清理机制，仍需手动清理。
   

## 测试

如果你想快速体验Bluefin的话，我搭建了一个临时站点[http://bluefin.saymagic.cn/](http://bluefin.saymagic.cn/),既然是临时，所以并不会保证稳定性。

## Licence

[gpl-3.0](https://opensource.org/licenses/gpl-3.0.html)