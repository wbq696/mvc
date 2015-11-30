package com.joker.core.mvc.context;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.joker.core.mvc.config.JokerConfig;
import com.joker.core.mvc.controller.IController;
import com.joker.core.mvc.utils.StringUtils;

/**
 * 上下文环境
 * @author
 * 
 */
public class ApplicationContext{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private static ApplicationContext applicationContext = new ApplicationContext();
	// 缓存
	private Map<String, IController> cache = new Hashtable<String, IController>();

	// 全局配置文件对象
	private JokerConfig jokerConfig;

	// servlet上下文对象
	private ServletContext servletContext;

	public JokerConfig getJokerConfig(){
		return jokerConfig;
	}

	/**
	 * 加载配置文件
	 * 
	 * @param jokerConfig
	 */
	public void loadJokerConfig(JokerConfig jokerConfig){
		
		if ( jokerConfig != null ){
			
			if(logger.isInfoEnabled()){
			
				logger.info("Loading JokerConfig Controller started");
			
			}
			
			long startTime = System.currentTimeMillis();
			
			Set<String> setKeys = jokerConfig.getKeys();
			
			String classPath = null;
			
			for(String key:setKeys){
				
				//键值获取classPath
				classPath = jokerConfig.getClassPath(key);
				
				IController controller = null;
				
				try{
					//考虑到多个URL的请求会对应一个classPath，以下为了保证只有一个classPath实例存在
					if (!StringUtils.isNull(classPath) ){
						if (cache.containsKey(classPath) ){
							controller = cache.get(classPath);
						}else{
							// 创建对象
							Class<?> classObject = Class.forName(classPath);
							controller = (IController)classObject.newInstance();
							cache.put(classPath, controller);
							logger.info("Loading url:" + key + " class:" + classPath + " success!");
						}
					}
				}
				catch (ClassNotFoundException e1){
					this.logger.error("Loading url:" + key + " class:" + classPath + " failed! Class not found: " + classPath + "", e1);
				}
				catch (InstantiationException e1){
					this.logger.error("Loading url:" + key + " class:" + classPath + " failed! Class newInstance error: " + classPath + "", e1);
				}
				catch (IllegalAccessException e1){
					this.logger.error("Loading url:" + key + " class:" + classPath + " failed! Class newInstance error: " + classPath + "", e1);
				}
			}
			if (logger.isInfoEnabled() ){
				long elapsedTime = System.currentTimeMillis() - startTime;
				logger.info("Loading JokerConfig Controller completed in " + elapsedTime + " ms!");
			}
		}

		this.jokerConfig = jokerConfig;
	}

	public ServletContext getServletContext(){
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext){
		this.servletContext = servletContext;
	}

	private ApplicationContext(){

	}

	public static ApplicationContext getInstance(){
		return applicationContext;
	}

	/**
	 * 获得Controller实例对象 应用享元模式
	 * 
	 * @param classPath
	 * @return
	 */
	public IController getControllerInstance(String url){
		// 获得url 对应的class处理路径
		String classPath = this.jokerConfig.getClassPath(url);
		// Controller处理对象实例
		IController controller = null;
		// 如果在缓存中存在 则从缓存中读取 否则创建一个对象返回 同时放入缓存中
		if (!StringUtils.isNull(classPath) ){
			if (cache.containsKey(classPath) ){
				controller = cache.get(classPath);
			}else{
				try{
					Class<?> classObject = Class.forName(classPath);
					controller = (IController)classObject.newInstance();
					cache.put(classPath, controller);
				}catch (Exception e){
					logger.error("Loading url:" + url + " class:" + classPath + " failed!", e);
				}
			}
		}else{
			logger.error("request url:" + url + "不存在!");
		}
		return controller;
	}
}
