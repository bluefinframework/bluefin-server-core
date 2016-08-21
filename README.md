# Bluefin 
> Service for distribution apk.


##  Features

* Saving apk and mapping files
* Apk information hook service
* Inquire for apk information 
* APK Downloaded
* Confusing information recovery

## Useage

Bluefin Server based on Spring Boot development using Maven build. So be sure to run the machine installed Java environment, Maven environment, if you need to restore the confusing information, but also need to configure the `ANDROID_HOME` environment variable.


### Parameter Configuration

Bluefin addition to parameters that can be configured through Spring Boot configuration files, the specific parameters for Bluefin, while supporting the configuration file and environment variables in two ways, since most of the parameters have default values, so even if you do not configure any parameters to be use most features of Bluefin.

The weight of the order parameter values as follows:

	Profiles > Environment Variables > Defaults
	
For more information about the parameters, see the table below:


|Name     |Profile Name|Environment Name|Default|Explanation|
|--------|-------|------|------|-----|
|host|server.address|BLUEFIN\_SERVER\_HOST|host IP/127.0.0.1|bluefin host|
|port|server.port|BLUEFIN\_SERVER\_PORT|2556|bluefin port|
|base url|output.baseurl|BLUEFIN\_BASE\_URL|host:port|Root url address publicly defined, when Docker deploy or use Nginx like port forwarding service, must pay attention to the definition of the value , apk's download url will be based on the value|
|store apk path|filestore.path|BLUEFIN\_FILE\_STORE\_PATH|.bluefin file folder under the user's directory|the path to save mapping and apk file, which is the core directory Bluefin|
|store tmp path|filestore.tmp|BLUEFIN\_FILE\_TMP\_PATH|.bluefin/tmp file folder under the user's directory|use to store temporary files which generated by parsing apk or retrace|
|android sdk home|android.sdk.home|ANDROID_HOME||Android sdk directory, used to obtain the trace file|
|hook urls|hook.upload.apk.urls|BLUEFIN_HOOK_URLS||hook callback address when apk parsing flow finished , multiple addresses is supported, use `,` to separated|


### Run with maven

* If the clone source, run in the source directory

 		mvn  spring-boot:run
 	
The default will open  Bluefin services with port 2556 in current machine.

### Run with Docker

	docker run -it -p 2556:2556 -v $(pwd):/root saymagic/bluefin:v1.0

##Skills

* Package name and id co-locate a apk file, the id is apk to define, Bluefin would parses `AndroidManifest.xml` file and the name of `bluefinidentify ` in `meta-data` label  would be as the id, generally recommended using the timestamp as the id and dynamically to modify this value at the time of packaging. If `bluefinidentify` label does not exist, Bluefin use this apk's version code as id, such harm would be covered relationship when package a same version multiple times.


* Bluefin would parses `AndroidManifest.xml` file and the name of `bluefinUpdateInfo` in `meta-data` label would be as the update information，If the value exists, the value would return when you call  query interface.

* Bluefin would parses `AndroidManifest.xml` file and the name of `bluefinExtData` in `meta-data` label would be as the additional information，If the value exists, the value would return when you call  query interface.



##  API 

See:[http://doc.saymagic.cn/bluefin/162924](http://doc.saymagic.cn/bluefin/162924)


## Todo

* Clean up the data in tmp folder 

* Internal procedures to resolve confusion information

## Licence

[gpl-3.0](https://opensource.org/licenses/gpl-3.0.html)