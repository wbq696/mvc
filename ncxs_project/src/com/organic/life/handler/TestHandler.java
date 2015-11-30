package com.organic.life.handler;

import com.joker.core.mvc.BaseHandler;
import com.joker.core.mvc.model.Model;
import com.joker.core.mvc.model.WebHelper;
import com.joker.core.mvc.view.View;

public class TestHandler extends BaseHandler{

	@Override
	public View service(WebHelper wHelp, Model model){
		String method = wHelp.getMethod();
		switch(method){
           case "test":
        	   return getTest(model);
           case "111":
        	   return test111();
           default :
        	   return execute(wHelp);
		}
	}
	/**
	 * 测试
	 * @return
	 */
	private View getTest(Model model){
		model.put("name", "你好啊！");
		return new View("/nnn/view.jk?password=123456");
	}
	/**
	 * 测试
	 * @return
	 */
	private View test111(){
		return new View("/nnn/view.jk?password=000000");
	}
	
}
