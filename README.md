## 服务依赖
* dubbo 版本：`2.5.7`
* config-center 版本：`1.0.3`

## 服务目标
* 调用清洗服务，实现数据的透传.
* 将标准数据转换为清洗服务所兼容的格式.
* 将清洗以后非标准数据转换为标准数据.
* 增加数据清洗失败的监控.

## 服务打包
* 测试环境打包：clean package -Ptest
* 生产环境打包：clean package -Ppro

## 服务部署
* 将assembly module target下的zip包进行解压，bin/start.sh即可启动。
* 相关命令关注bin下的shell脚本。

## 输入数据数据结构
```javascript
{    
    "esdate":"2016年8月31日",
    "company_type":"有限责任公司",
	"operating_period":"永久经营",
	"sharesimpawn":[],
	"yzwf":[],
	"type":"深圳",
	"sbzc":[],
	"czls":[],
	"credit_code":"91440300MA5DK64M9Q",
	"bbd_type":"qyxy_shenzhen",
	"sjdw":[],
	"regno":"440306117299843",
	"fzjg":[],
	"approval_date":"2016-08-31",
	"jyyc":[],
	"bgxx":[],
	"gdxx":[
		{
			"sub_detail":[],
			"invest_amount":"60.0000",
			"invest_ratio":"60.0000%",
			"paid_detail":[],
			"shareholder_name":"韦永志"
		},
		{
			"sub_detail":[],
			"invest_amount":"40.0000",
			"invest_ratio":"40.0000%",
			"paid_detail":[],
			"shareholder_name":"闭世荣"
		}
	]   
}
```

## 输出数据数据结构

```javascript
{    
    "esdate":"2016年08月31日",
	"company_type":"有限责任公司",
	"operating_period":"9999年01月01日",
	"sharesimpawn":[],
	"yzwf":[],
	"type":"深圳",
	"sbzc":[],
	"czls":[],
	"credit_code":"91440300MA5DK64M9Q",
	"bbd_type":"qyxy_shenzhen",
	"sjdw":[],
	"regno":"440306117299843",
	"fzjg":[],
	"approval_date":"2016年08月31日",
	"jyyc":[],
	"bgxx":[],
	"gdxx":[
		{
			"sub_detail":[],
			"invest_amount":"60.0000",
			"invest_ratio":"60.0000%",
			"paid_detail":[],
			"shareholder_name":"韦永志"
		},
		{
			"sub_detail":[],
			"invest_amount":"40.0000",
			"invest_ratio":"40.0000%",
			"paid_detail":[],
			"shareholder_name":"闭世荣"
		}
	]   
}
```

注意：输出数据中，包含有二级字段，但二级字段或多级字段不在进行字符串的序列化。