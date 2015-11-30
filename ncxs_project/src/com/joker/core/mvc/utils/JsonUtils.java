package com.joker.core.mvc.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.joker.core.mvc.constant.OpStatusConstant;

/**
 * JSON文本转换工具.主要提供将文本转换为对象的功能
 * 
 * @author BingQiang.Wang
 * 
 */
public class JsonUtils {
	
	private static String JSON_EXE_STATUS = "exeStatus";
	
	private static String JSON_EXE_ERROE_CODE = "errCode";

	/**
	 * 获取执行结果的错误代码
	 * 
	 * @param json
	 * @return
	 * @author YongChao.Yang/2012-9-16/2012
	 */
	public static String getErrorCode(String jsonStr) {
		JSONObject jo = JSON.parseObject(jsonStr);
		if (OpStatusConstant.OPERATE_STATUS_FAILED == jo.getIntValue(JSON_EXE_STATUS)) {
			return jo.getString("errCode");
		}
		return "";
	}

	/**
	 * 获取json执行结果值
	 * 
	 * @param json
	 * @return
	 * @author YongChao.Yang/2012-9-16/2012
	 */
	public static int getExeStatus(String json) {
		JSONObject jo = JSON.parseObject(json);
		return jo.getIntValue(JSON_EXE_STATUS);
	}

	/**
	 * 设置执行结果为正确
	 * 
	 * @param jsonObj
	 * @return
	 * @author YongChao.Yang/2012-9-16/2012
	 */
	public static JSONObject setSuccess(JSONObject jsonObj) {
		jsonObj.put(JSON_EXE_STATUS, String.valueOf(OpStatusConstant.OPERATE_STATUS_SUCESSED));
		return (JSONObject) jsonObj;

	}

	/**
	 * 设置执行结果为错误
	 * 
	 * @param jsonObj
	 * @return
	 * @author YongChao.Yang/2012-9-16/2012
	 */
	public static JSONObject setFailed(JSONObject jsonObj, String errCode) {
		jsonObj.put(JSON_EXE_STATUS, String.valueOf(OpStatusConstant.OPERATE_STATUS_FAILED));
		jsonObj.put(JSON_EXE_ERROE_CODE, errCode);
		return jsonObj;
	}

	
	/**
	 * 向JSON对象中注入键值对
	 * @param jsonObj
	 * @param key
	 * @param value
	 * @return
	 * @author YongChao.Yang/2012-9-16/2012
	 */
	public static void setValue(JSONObject jsonObj, String key, Object value) {
		if (!StringUtils.isNull(key)) {
			value = value == null ? "" : value;
			jsonObj.put(key, value);
		} else {
			System.out.println("json设置键值对异常,KEY值为空");
		}
	}

}