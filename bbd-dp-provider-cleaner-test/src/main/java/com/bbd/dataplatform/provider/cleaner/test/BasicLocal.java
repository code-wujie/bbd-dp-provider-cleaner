/**  
 * bbdservice.com Inc
 * All Rights Reserved 2016
 */
package com.bbd.dataplatform.provider.cleaner.test;

import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

/**
 * 测试基类
 * 
 * @author diven
 * @version 1.0
 */
@Test
@ContextConfiguration(locations = { "classpath*:META-INF/local/*.xml" })
public class BasicLocal extends Basic {

}
