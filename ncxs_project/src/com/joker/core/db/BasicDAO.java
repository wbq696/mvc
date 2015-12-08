package com.joker.core.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class BasicDAO{

	protected Log logger;

    public BasicDAO(){
    	logger = LogFactory.getLog(this.getClass());
    }
    
    /**
     * 关闭资源
     * @param statement
     * @throws SQLException
     */
    public void close(Statement st){
    	close(st,null);
    }
    
    /**
     * 关闭资源
     * @param statement
     * @param rs
     * @throws SQLException
     */
    public void close(Statement st,ResultSet rs){
    	if(rs!=null){
    		try {
				rs.close();
			} catch (SQLException e) {
				logger.error("ResultSet close 异常!",e);
			}
    	}
    	if(st!=null){
    		try {
				st.close();
			} catch (SQLException e) {
				logger.error("Statement close 异常!",e);
			}
    	}
    }
}
