package com.joker.core.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class JDBCCache {
	
	private static final Log logger = LogFactory.getLog(JDBCCache.class);
	
	//声明数据源共享变量
    private static Map<String,DataSource> dataSourceMap  = new HashMap<String, DataSource>();
    
    /**
     * 根据数据源名称获取数据库连接
     * @param jdbcName
     * @return
     * @throws SQLException
     */
    static Connection getConnection(String jdbcName) throws SQLException {
		if(dataSourceMap.get(jdbcName) == null){
			initDateSource(jdbcName);
		}
		return dataSourceMap.get(jdbcName).getConnection();
	}
	
	/**
	 * 初始化数据源，根据配置文件名称
	 * @param jdbcName
	 */
	private synchronized static void initDateSource(String jdbcName){
	 try{
		 if(dataSourceMap.get(jdbcName) == null){
	         InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream(jdbcName+".properties");
	         Properties props = new Properties();
	         props.load(in);
	         DataSource dataSource = DruidDataSourceFactory.createDataSource(props);
	         dataSourceMap.put(jdbcName,dataSource);
		 }
     }catch(Exception ex){
    	 logger.error(jdbcName+".properties文件数据源初始化异常！",ex);
     }
	}
    
}
