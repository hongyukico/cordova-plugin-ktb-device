# Cordova Device plugin

for iOS and Android, by [ktb](https://github.com/hongyukico)

## Description
获取设备基本信息插件，支持android、ios平台


* 1.0.0 Works with Cordova 3.x
* 1.0.1+ Works with Cordova >= 4.0

## Installation

```
cordova plugin add cordova-plugin-ktb-device
```


## Usage

```javascript
 
	var exec = require('cordova/exec');

	exports.getDeviceInfo = function(success, error) {
	exec(success, error, "DeviceInfo", "getDeviceInfo", []);
	};


```
