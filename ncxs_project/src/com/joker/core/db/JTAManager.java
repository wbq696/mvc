package com.joker.core.db;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 通过容器注入的UserTransaction对象管理JTA事务对象
 * 
 * @author Administrator
 */
public class JTAManager {
	
	private static UserTransaction ut = null;
	
	private static final Log logger = LogFactory.getLog(JTAManager.class);
	
	static {
		Context ctx;
		try {
			logger.debug("初始化事务管理器...");
			ctx = new InitialContext();
			ut = (UserTransaction) ctx.lookup("java:comp/UserTransaction");
		} catch (NamingException e) {
			logger.error("初始化事务管理器失败", e);
		}
	}

	/**
	 * 获取UserTransaction对象
	 * @author Administrator/下午1:50:22/2012
	 */
	public static UserTransaction getUserTransaction() {
		return ut;
	}
}