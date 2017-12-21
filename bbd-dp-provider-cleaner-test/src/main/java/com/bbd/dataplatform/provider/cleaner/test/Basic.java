package com.bbd.dataplatform.provider.cleaner.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.bbd.dataplatform.provider.common.json.JsonUtil;

public class Basic extends AbstractTestNGSpringContextTests{
	
	
	/**
	 * 读取文件公共函数
	 * @param path 文件路径
	 * @return list<String>
	 */
	public static List<String> readLines(String path){
		return readLines(path, String.class);
	}
	
	/**
	 * 读取文件公共函数
	 * @param path 文件路径
	 * @return list<String>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Map<String, Object>> readLinesToListMap(String path){
		List<Map> list = readLines(path, Map.class);
		List<Map<String, Object>> res = new ArrayList<>();
		for(Map item : list){
			res.add(item);
		}
		return res;
	}
	
	/**
	 * 读取文件公共函数
	 * @param path 文件路径
	 * @param cls  待转换的类型
	 * @return	list<T>
	 */
	public static <T> List<T> readLines(String path, Class<T> cls){
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(path),"utf-8"));
			List<T> lines = new ArrayList<>();
			String line = null;
			while((line=br.readLine()) != null){
				if(cls.equals(String.class)){
					lines.add(cls.cast(line));
				}
				else{
					lines.add(JsonUtil.toEntity(line, cls));
				}
			}
			br.close();
			return lines;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
