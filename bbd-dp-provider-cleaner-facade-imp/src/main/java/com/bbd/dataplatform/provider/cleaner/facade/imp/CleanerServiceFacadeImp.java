package com.bbd.dataplatform.provider.cleaner.facade.imp;

import java.util.Map;

import com.bbd.dataplatform.provider.cleaner.facade.CleanerServiceFacade;
import com.bbd.dataplatform.provider.cleaner.lib.ServiceHandler;
import com.bbd.dataplatform.provider.common.facade.mode.FacadeResponse;

public class CleanerServiceFacadeImp implements CleanerServiceFacade{
	
	private static final long serialVersionUID = 8646579703537063211L;
	
	private ServiceHandler cleanerServiceHandler;

	public FacadeResponse handle(Map<String, Object> data){
		return cleanerServiceHandler.handle(data);
	}

	public ServiceHandler getCleanerServiceHandler() {
		return cleanerServiceHandler;
	}

	public void setCleanerServiceHandler(ServiceHandler cleanerServiceHandler) {
		this.cleanerServiceHandler = cleanerServiceHandler;
	}
	
}
