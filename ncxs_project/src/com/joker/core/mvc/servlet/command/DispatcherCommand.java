package com.joker.core.mvc.servlet.command;

import com.joker.core.mvc.context.ApplicationContext;
import com.joker.core.mvc.controller.IController;
import com.joker.core.mvc.model.WebHelper;
import com.joker.core.mvc.servlet.context.HttpRequestContext;
import com.joker.core.mvc.view.View;

/**
 * 控制器处理模型数据返回视图处理命令
 * 
 * @author
 * 
 */
public class DispatcherCommand extends AbstractCommand{

	@Override
	protected boolean exec(HttpRequestContext httpRequestContext){
		// 获得上下文环境
		ApplicationContext applicationContext = ApplicationContext.getInstance();
		// 根据请求URI获得对应控制器
		IController controller = applicationContext.getControllerInstance(httpRequestContext.getRequestUri());
		View view = null;
		//封装webHelper
		WebHelper webHelper = new WebHelper(httpRequestContext.getRequest(),httpRequestContext.getResponse(),httpRequestContext.getMethod());
		if ( controller != null ){
			// 控制类返回具体的视图
			view = controller.service(webHelper,httpRequestContext.getModel());
			// 保存到上下文中
			httpRequestContext.setView(view);
		}else{
			webHelper.setError("404");
			//webHelper.setValue("msg","Request ["+httpRequestContext.getMethod()+"] parameter exception");
			webHelper.writeJson();
		}
		// 如果视图为null 则不继续进行 否则进行下一个节点处理
		return view == null ? false : true;
	}
}
