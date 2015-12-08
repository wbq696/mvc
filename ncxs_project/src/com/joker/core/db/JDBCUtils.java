package com.joker.core.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JDBCUtils {
	
	private static final Log logger = LogFactory.getLog(JDBCUtils.class);
	
    //声明线程共享变量
    private static ThreadLocal<Map<String,Connection>> connTainer = new ThreadLocal<Map<String,Connection>>();
    
    //开启事务的线程
    private static ThreadLocal<Long> tranThreadIds = new ThreadLocal<Long>();
    
    /**
     * 获取数据连接Connection
     * @return
     */
    public static Connection getConnection(String jdbcName){
        Connection conn =null;
        try{
        	 Map<String,Connection> mapConn = connTainer.get();
        	 //mapConn 为空
             if(mapConn == null){
                conn = JDBCCache.getConnection(jdbcName);
                if(tranThreadIds!=null){
                	conn.setAutoCommit(false);
                }
                mapConn = new HashMap<String,Connection>();
                mapConn.put(jdbcName, conn);
                connTainer.set(mapConn);
             }else{
            	conn = connTainer.get().get(jdbcName);
            	 //Connection为空
            	if(conn == null){
            		conn = JDBCCache.getConnection(jdbcName);
                    if(tranThreadIds!=null){
                    	conn.setAutoCommit(false);
                    }
            		mapConn.put(jdbcName, conn);
            	}
             }
        }catch(Exception e){
            logger.error("获取获取失败!",e);
        }
        return conn;
    }
    
    /**
     * 开启事务
     */
    public static void startTransaction(){
       tranThreadIds.set(Thread.currentThread().getId());
    }
    
    /**
     * 提交事务
     */
    public static void commit(){
    	//从当前线程上获取连接if(conn!=null){//如果连接为空，则不做处理
    	Map<String,Connection> mapConn = connTainer.get();
    	Set<String> keys = mapConn.keySet();
    	for(String key:keys){
    		Connection conn = mapConn.get(key);
    		if(conn!=null){
    			 try {
    				 conn.commit();//提交事务
				} catch (SQLException e) {
					logger.error("提交事务异常!",e);
				}
    		}
    	}
    }
 
    /**
     * 事务回滚
     */
    public static void rollback(){
    	//从当前线程上获取连接if(conn!=null){//如果连接为空，则不做处理
    	Map<String,Connection> mapConn = connTainer.get();
    	Set<String> keys = mapConn.keySet();
    	for(String key:keys){
    		Connection conn = mapConn.get(key);
    		if(conn!=null){
    			 try {
    				 conn.rollback();
				} catch (SQLException e) {
					logger.error("提交事务异常!",e);
				}
    		}
    	}  	
    }
    
    /**
     * 关闭Connection连接
     */
    public static void close(){
    	Map<String,Connection> mapConn = connTainer.get();
    	Set<String> keys = mapConn.keySet();
    	for(String key:keys){
    		Connection conn = mapConn.get(key);
    		if(conn!=null){
    			 try {
    				conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
					logger.error("conn close 异常",e);
				}
    		}
    	}
        try {
        	//从当前线程移除连接切记
        	connTainer.remove();
        	///从当前线程移除线程事务
        	tranThreadIds.remove();
        } catch (Exception e) {
        	logger.error("从当前线程移除连接异常!",e);
        }
    }
}
