package com.joker.core.mvc.model;

import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.alibaba.fastjson.JSONObject;
import com.joker.core.mvc.utils.JsonUtils;
import com.joker.core.mvc.utils.StringUtils;

public class WebHelper {

	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	//请求的具体方法名称
	private String method;
	
	private JSONObject jsonObj = new JSONObject();
	
	public WebHelper(HttpServletRequest request,HttpServletResponse response,String method){
		this.request = request;
		this.response = response;
		this.method = method;
	}

	/**
	 * 获取方法名称
	 */
	public String getMethod(){
		return method;
	}

	/**
	 * 获取String 类型数据
	 * @param paraName
	 * @param defVal
	 * @return
	 */
	public String getString(String paraName,String defVal){
		String stringValue = getString(paraName);
		if(StringUtils.isNull(paraName)){
			return defVal;
		}
		return stringValue;
	}
	
	/**
	 * 获取String 类型数据
	 * @param key
	 * @return
	 */
	public String getString(String paraName){
		String strVal = request.getParameter(paraName);
		if(StringUtils.isNull(strVal)){
			try{
				strVal = request.getAttribute(paraName).toString();
			}catch(Exception e){}
		}
	    return strVal;
	}
	
	/**
	 * 获取Int类型数据
	 * @param paraName
	 * @return
	 */
	public int getInt(String paraName){
		return Integer.parseInt(getString(paraName,"0"));
	}
	
	/**
	 * 获取Int类型数据
	 * @param paraName
	 * @param defVal
	 * @return
	 */
	public int getInt(String paraName,int defVal){
		return Integer.parseInt(getString(paraName,""+defVal));
	}
	
	/**
	 * 获取Long类型数据
	 * @param paraName
	 * @return
	 */
	public long getLong(String paraName){
		return Long.valueOf(getString(paraName,"0"));
	}
	
	/**
	 * 获取Long类型数据
	 * @param paraName
	 * @param defVal
	 * @return
	 */
	public long getLong(String paraName,long defVal){
		return Long.valueOf(getString(paraName,""+defVal));
	}
	
	
	/**
	 * 根据表单名称获取文件信息
	 * @param name
	 * @return
	 */
	public Part getPart(String name){
		try {
			return request.getPart(name);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取文件信息
	 * @return
	 */
    public Collection<Part> getParts(){
		try {
		   return request.getParts();
        }catch (Exception e) {
        	return null;
	    }
	}
    
	
	/**
	 *  往客户端写入JSONOBJ内容
	 * @author BingQiang.Wang
	 * @since  2015年10月9日下午3:54:39
	 */
	public void writeJson(){
		try {
			PrintWriter writer = this.response.getWriter();
			writer.write(jsonObj.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			System.out.println("客户端写入内容失败");
		}
	}
	
	/**
	 * 向JSON对象中写入内容
	 * @param jsonObj
	 * @param name
	 * @param value
	 * @throws Exception
	 * @author changqing/2012-9-8/2012
	 */
	public void setValue(String key, Object value) {
		JsonUtils.setValue(jsonObj, key, value);
	}
	
	/**
	 * 向JSON对象中写入成功内容
	 * 
	 * @param jsonObj
	 * @throws Exception
	 * @author changqing/2012-9-8/2012
	 */
	public void setSuccess() {
		JsonUtils.setSuccess(jsonObj);
	}

	/**
	 * 向JSON对象中写入失败内容
	 * 
	 * @param jsonObj
	 * @throws Exception
	 * @author changqing/2012-9-8/2012
	 */
	public void setError(String errCode) {
		JsonUtils.setFailed(jsonObj, errCode);
	}
}
