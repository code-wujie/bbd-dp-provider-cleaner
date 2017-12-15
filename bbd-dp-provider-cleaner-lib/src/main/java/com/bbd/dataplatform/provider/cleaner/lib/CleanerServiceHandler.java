package com.bbd.dataplatform.provider.cleaner.lib;

import java.util.Map;

import com.bbd.dataplatform.provider.cleaner.lib.utils.CleanerUtil;
import com.bbd.dataplatform.provider.common.facade.mode.FacadeResponse;

public class CleanerServiceHandler extends ServiceHandler{

	private static final long serialVersionUID = -4296037745545892248L;

	@Override
	public FacadeResponse handle(Map<String, Object> data) {
		long startTime = System.currentTimeMillis();
		FacadeResponse response = CleanerUtil.deal(data);
		long endTime = System.currentTimeMillis();
		response.setHandlingTime(endTime - startTime);
		return response;
	}

}
