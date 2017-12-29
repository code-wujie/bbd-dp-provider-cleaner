package com.bbd.dataplatform.provider.cleaner.lib.utils;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bbd.dataplatform.provider.common.config.BaseConfig;
import com.bbd.dataplatform.provider.common.constant.Constants.BBDERRORCODE;
import com.bbd.dataplatform.provider.common.facade.mode.ErrorModel;
import com.bbd.dataplatform.provider.common.facade.mode.FacadeResponse;
import com.bbd.dataplatform.provider.common.facade.mode.ResponseCode;
import com.bbd.dataplatform.provider.common.worldtree.WorldtreerpcClientUtil;

public class CleanerUtil extends BaseConfig {

	private static final long serialVersionUID = -6124409946853491131L;
	
	private final static Logger logger = LoggerFactory.getLogger(CleanerUtil.class);
	
	private static final String CLEANER_ERROR_FLAG_KEY = "bbd_cleaner_exception_log";
	
	private static final String PUBLIC_NAMESPASE_CLEANER_CONFIG = "bbddp.clean_config_namespase_v1";
	
	private static final WorldtreerpcClientUtil clientUtil = WorldtreerpcClientUtil.getInstance(PUBLIC_NAMESPASE_CLEANER_CONFIG);

	/**
	 * 处理数据清洗的逻辑
	 * @param data 待处理的数据
	 * @return	FacadeResponse
	 */
	public static FacadeResponse deal(Map<String, Object> data){
		try {
			//获取清洗数据
			FacadeResponse response = new FacadeResponse();
			List<Map<String, Object>>  res = clientUtil.handler(data);
			for(Map<String, Object> item : res){
				if(item.containsKey(CLEANER_ERROR_FLAG_KEY)){
					response.setStatusCode(ResponseCode.ERROR);
					response.addError(new ErrorModel(BBDERRORCODE.BBD_RUN_TIME_ERROE, "清洗服务异常，存在：bbd_cleaner_exception_log。"));
				}
				response.addData(item);
			}
			return response;
		} catch (Exception e) {
			logger.error("数据清洗出现程序上的致命错误！", e);
			//封装返回
			FacadeResponse response = new FacadeResponse();
			response.addData(data);
			response.setStatusCode(ResponseCode.EXCEPTION);
			response.addError(new ErrorModel(BBDERRORCODE.BBD_RUN_TIME_ERROE, e.getMessage(), e));
			return response;
		}
	}
}
