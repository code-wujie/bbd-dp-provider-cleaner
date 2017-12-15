package com.bbd.dataplatform.provider.cleaner.facade;

import java.util.Map;

import com.bbd.dataplatform.provider.common.facade.BaseServiceFacade;
import com.bbd.dataplatform.provider.common.facade.mode.FacadeResponse;

public interface CleanerServiceFacade extends BaseServiceFacade{
	
	public FacadeResponse handle(Map<String, Object> data);
	
}
