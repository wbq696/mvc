package com.joker.core.mvc.view;

/**
 * 视图类
 * 
 * @author
 * 
 */
public class View {
	// 外部请求响应后转向url地址内容
	private String url;

	//是否重定向
	private boolean isRedirect;
	
	public View(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isRedirect() {
		return isRedirect;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
}
