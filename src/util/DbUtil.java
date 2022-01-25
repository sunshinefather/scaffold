package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbUtil {
    /** 
     * 根据SQL语句查询出列表 
     * @param sql 
     * @param conn 
     * @return 
     */  
    public static List<String []> getDataBySQL(String sql,Connection conn){  
        Statement stmt = null;
        ResultSet rs = null;
        List<String []> list = new ArrayList<String []>();
        try {  
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){  
                String [] arr = new String[rs.getMetaData().getColumnCount()];
                for(int i=0;i<arr.length;i++){  
                    arr[i] = rs.getString(i+1);
                }  
                list.add(arr);
            }  
        } catch (SQLException e) {  
            e.printStackTrace();
        }finally{  
            try {  
                if(rs!=null)rs.close();
                if(stmt!=null)stmt.close();
            } catch (SQLException e) {  
                e.printStackTrace();
            }  
        }  
        return list;
    }
    
    /** 
     * 获取数据库连接 
     * @return 
     */  
    public static Connection getConnection(String url,String username,String password){  
        try {  
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {  
            e.printStackTrace();
        }  
        return null;
    }  
}
