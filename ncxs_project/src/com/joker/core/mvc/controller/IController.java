package com.joker.core.mvc.controller;

import com.joker.core.mvc.model.Model;
import com.joker.core.mvc.model.WebHelper;
import com.joker.core.mvc.view.View;

/**
 * 控制层接口
 * 
 * @author
 * 
 */
public interface IController{
	
	/**
	 * 方法调度服务
	 * @param webHelper 
	 * @param model 转发内容
	 * @return
	 */
	public View service(WebHelper wHelp,Model model);
	
}
