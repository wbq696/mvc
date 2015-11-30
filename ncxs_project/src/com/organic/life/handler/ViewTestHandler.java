package com.organic.life.handler;

import com.joker.core.mvc.BaseHandler;
import com.joker.core.mvc.model.Model;
import com.joker.core.mvc.model.WebHelper;
import com.joker.core.mvc.view.View;

public class ViewTestHandler extends BaseHandler{

	@Override
	public View service(WebHelper wHelp,Model model) {
		wHelp.setValue("中文","测试中文！");
		wHelp.setValue("method",wHelp.getMethod());
		wHelp.setValue("name",wHelp.getString("name"));
		wHelp.setValue("password",wHelp.getString("password"));
		wHelp.setSuccess();
		wHelp.writeJson();
	   return null;
	}
	
	
}
