package com.bbd.dataplatform.provider.cleaner.test.lib;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.bbd.dataplatform.provider.cleaner.lib.ServiceHandler;
import com.bbd.dataplatform.provider.cleaner.test.BasicLibs;

/**
 * 测试用例
 * @author diven
 */
public class CleanerServiceFacadeLibTest extends BasicLibs{

    /** 日志 */
    private final Logger logger = LoggerFactory.getLogger(CleanerServiceFacadeLibTest.class);
    
    @Autowired
    private ServiceHandler cleanerServiceHandler;
    
    @Test
    public void cleaner(){
    	List<Map<String, Object>> datas = readLinesToListMap("datas/crawler_data_for_qyxx.json");
    	for(Map<String, Object> data : datas){
    		logger.info(cleanerServiceHandler.handle(data).toString());
    	}
    }
    
}