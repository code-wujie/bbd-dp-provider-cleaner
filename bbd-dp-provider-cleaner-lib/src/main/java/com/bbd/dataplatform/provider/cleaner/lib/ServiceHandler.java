package com.bbd.dataplatform.provider.cleaner.lib;

import java.io.Serializable;
import java.util.Map;

import com.bbd.dataplatform.provider.common.facade.mode.FacadeResponse;

public abstract class ServiceHandler implements Serializable{

	private static final long serialVersionUID = -454820137626793568L;

	public abstract FacadeResponse handle(Map<String, Object> data);
	
}
