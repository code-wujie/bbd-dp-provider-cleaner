package com.bbd.dataplatform.provider.cleaner.test.local;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.bbd.dataplatform.provider.cleaner.facade.CleanerServiceFacade;
import com.bbd.dataplatform.provider.cleaner.test.BasicLocal;

/**
 * 测试用例
 * @author diven
 */
public class CleanerServiceFacadeLocalTest extends BasicLocal {

    /** 日志 */
    private final Logger logger = LoggerFactory.getLogger(CleanerServiceFacadeLocalTest.class);
    
    @Autowired
    private CleanerServiceFacade cleanerServiceFacade;
    
    @Test
    public void cleaner(){
    	List<Map<String, Object>> datas = readLinesToListMap("datas/crawler_data_for_qyxx.json");
    	for(Map<String, Object> data : datas){
    		logger.info(cleanerServiceFacade.handle(data).toString());
    	}
    }
    
}