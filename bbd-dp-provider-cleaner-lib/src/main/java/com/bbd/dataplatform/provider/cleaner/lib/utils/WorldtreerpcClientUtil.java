package com.bbd.dataplatform.provider.cleaner.lib.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bbd.dataplatform.provider.common.config.BaseConfig;
import com.bbd.dataplatform.provider.common.json.JsonUtil;
import com.bbd.dataplatform.worldtreerpc.client.Config;
import com.bbd.dataplatform.worldtreerpc.client.WorldtreerpcClient;

/**
 * Worldtreer 封装工具
 * @author diven
 *
 */
public class WorldtreerpcClientUtil extends BaseConfig {

	private static final long serialVersionUID = -1964543001282704666L;
	
	private final static Logger logger = LoggerFactory.getLogger(WorldtreerpcClientUtil.class);
	
	private static final String FILED_CLEANER_WORLDTREE_ZKHOSTS_KEY = "cleaner_worldtree_hosts";
	private static final String FILED_CLEANER_WORLDTREE_WATCHER_PATH_KEY = "cleaner_worldtree_watcher_path";
	private static final String FILED_CLEANER_WORLDTREE_INTERFACE_KEY = "cleaner_worldtree_interface";
	private static final String PUBLIC_NAMESPASE_CLEANER_CONFIG = "bbddp.clean_config_namespase_v1";
	
	private WorldtreerpcClient client;
	private static WorldtreerpcClientUtil util;
	
	private String rpcInterface;
	private String cleaner_worldtree_hosts;
	private String cleaner_worldtree_watcher_path;
	
	/** 构造函数 **/
	private WorldtreerpcClientUtil(){
		//获取配置
		while (true) {
			this.cleaner_worldtree_hosts = config.getString(PUBLIC_NAMESPASE_CLEANER_CONFIG, FILED_CLEANER_WORLDTREE_ZKHOSTS_KEY, null);
			this.cleaner_worldtree_watcher_path = config.getString(PUBLIC_NAMESPASE_CLEANER_CONFIG, FILED_CLEANER_WORLDTREE_WATCHER_PATH_KEY, null);
			this.rpcInterface = config.getString(PUBLIC_NAMESPASE_CLEANER_CONFIG, FILED_CLEANER_WORLDTREE_INTERFACE_KEY, null);
			if(StringUtils.isBlank(this.cleaner_worldtree_hosts) || StringUtils.isBlank(this.cleaner_worldtree_watcher_path) || StringUtils.isBlank(this.rpcInterface)){
				logger.error("Worldtree config is error，please check！！");
				try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
			}else{
				break;
			}
		}
		//连接配置
		Config rpcConfig = new Config();
        rpcConfig.setZKHosts(cleaner_worldtree_hosts);
        rpcConfig.setWatcherPath(cleaner_worldtree_watcher_path);
		this.client = new WorldtreerpcClient(rpcConfig);
	}
	
	/**
	 * 单例
	 * @return	单例
	 * @throws Exception 
	 */
	public static WorldtreerpcClientUtil getInstance(){
		if (util == null) {  
			synchronized (WorldtreerpcClientUtil.class) {  
				if (util == null) {  
					util = new WorldtreerpcClientUtil();
				}  
			}  
		} 
		return util;
	}
	
	/**
	 * 调用清洗服务
	 * @param data 单条数据
	 * @return	清洗结果
	 * @throws Exception 
	 */
	public List<Map<String, Object>> cleaner(Map<String, Object> data) throws Exception{
		if(data != null && !data.isEmpty()){
			List<Map<String, Object>> tmps = new ArrayList<>();
			tmps.add(data);
			return this.cleaner(tmps);
		}
		else{
			return new ArrayList<>();
		}
	}
	
	/**
	 * 调用清洗服务
	 * @param datas 数据列表
	 * @return 清洗结果
	 * @throws Exception 
	 */
	public List<Map<String, Object>> cleaner(List<Map<String, Object>> datas) throws Exception{
		List<String> cleanerDatas = this.beforeClean(datas);
		String response = client.request(this.rpcInterface, cleanerDatas);
		List<String> res = JsonUtil.ToList(response, String.class);
		return this.afterClean(res);
	}

	/**
	 * 清洗前的预处理
	 * @param datas 待处理的数据
	 * @return	datas
	 */
	private List<String> beforeClean(List<Map<String, Object>> datas){
		List<String> res = new ArrayList<>();
		for(Map<String, Object> item : datas){
			Map<String, Object> tmp = this.toNormalStringMap(item);
			if(tmp!= null && !tmp.isEmpty()){
				res.add(JsonUtil.toString(tmp));
			}
		}
		return res;
	}
	
	/**
	 * 清洗后的预处理
	 * @param datas 清洗得到的结构集
	 * @return datas
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> afterClean(List<String> datas){
		List<Map<String, Object>> list = new ArrayList<>();
		for(String item : datas){
			if(StringUtils.isNotBlank(item)){
				Map<String, Object> map = JsonUtil.ToEntity(item, Map.class);
				Map<String, Object> res = this.toNormalObjectMap(map);
				list.add(res);
			}
		}
		return list;
	}
	
	/**
	 * 将标准数据结构转换成清洗模块所支持的结构
	 * @param map 待处理数据
	 * @return	map
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> toNormalStringMap(Map<String, Object> map){
		Map<String, Object> res = new HashMap<>();
		for(String key : map.keySet()){
			Object value = map.get(key);
			if(value !=null && value instanceof List){
				List<Object> listres = new ArrayList<>();
				List<Object> listObj = (List<Object>) value;
				for(Object obj : listObj){
					if(obj !=null && obj instanceof Map){
						listres.add(toNormalStringMap((Map<String, Object>) obj));
					}
					else{
						listres.add(obj);
					}
				}
				res.put(key, JsonUtil.toString(listres));
			}
			else if(value !=null && value instanceof Map){
				res.put(key, JsonUtil.toString(value));
			}
			else{
				res.put(key, value);
			}
		}
		return map;
	}
	
	/**
	 * 将清洗模块返回的值转换为标准数据结构
	 * @param map 待处理数据
	 * @return	map
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> toNormalObjectMap(Map<String, Object> map){
		Map<String, Object> res = new HashMap<>();
		for(String key : map.keySet()){
			Object value = map.get(key);
			if(value !=null && value instanceof String){
				String tempString = String.valueOf(value);
				if(tempString.startsWith("{") && tempString.endsWith("}")){
					try {
						Map<String, Object> tmp = JsonUtil.ToEntity(tempString, Map.class);
						value = toNormalObjectMap(tmp);
					} catch (Exception e) {}
				}
				else if(tempString.startsWith("[") && tempString.endsWith("]")){
					try {
						List<Map<String, Object>> tmpslist = new ArrayList<>();
						List<Map<String, Object>> tmps = JsonUtil.ToListMap(tempString);
						for(Map<String, Object> tmp : tmps){
							Map<String, Object> tmpValue = toNormalObjectMap(tmp);
							tmpslist.add(tmpValue);
						}
						value = tmpslist;
					} catch (Exception e) {}
				}
			}
			res.put(key, value);
		}
		return res;
	}
}
