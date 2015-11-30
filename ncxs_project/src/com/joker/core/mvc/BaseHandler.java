package com.joker.core.mvc;

import com.joker.core.mvc.controller.IController;
import com.joker.core.mvc.model.WebHelper;
import com.joker.core.mvc.view.View;

/**
 * 基础程序处理类
 * @author
 */
public abstract class BaseHandler implements IController{
	/**
	 * Handler层默认方法
	 * @param webHelper
	 * @return
	 */
	protected View execute(WebHelper wHelp){
		wHelp.writeJson();
		return null;
	}
}
