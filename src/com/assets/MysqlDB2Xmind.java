package com.assets;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.xmind.core.ISheet;
import org.xmind.core.ITopic;
import org.xmind.core.IWorkbook;

import util.DbUtil;
import util.XmindUtil;
/**
 * mysql数据库转Xmind
 * api:https://blog.51cto.com/u_7932852/3753021
 * @author sunshine
 * @date 2022年1月25日
 */
public class MysqlDB2Xmind {
	//键类型字典  
    private static Map<String,String> keyType = new HashMap<String,String>();
    //初始化jdbc  
    static{  
        try {  
            keyType.put("PRI", "主键");
            keyType.put("UNI", "唯一键");
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();
        }  
    }
    private static String schema = "emr";
    private static String url = "jdbc:mysql://172.27.33.4:3306/"+schema;
    private static String username = "his_test";
    private static String password = "ha&xa,,5Acd";
    //查询mysql所有数据库
    private static String sql_get_all_db ="select a.SCHEMA_NAME from information_schema.SCHEMATA a";
    //private static String sql_get_all_db ="select a.SCHEMA_NAME from information_schema.SCHEMATA a WHERE a.SCHEMA_NAME LIKE 'c%' or a.SCHEMA_NAME LIKE 'n%'";
    //查询所有表的sql语句  
    private static String sql_get_all_tables = "select table_name,TABLE_COMMENT from INFORMATION_SCHEMA.tables where TABLE_SCHEMA='{db_name}' and TABLE_TYPE='BASE TABLE'";
    //查询表对应所有字段的sql语句  
    private static String sql_get_all_columns = "select column_name,data_type,character_octet_length,COLUMN_COMMENT,is_nullable,COLUMN_key from information_schema.`COLUMNS` where TABLE_NAME='{table_name}' and TABLE_SCHEMA='{db_name}'";
    public static void main(String[] args) throws Exception {  
        String rootTopicName = "HIS数据库";
        String xmindPath = "U:/tmp/HIS数据库梳理.xmind";
        IWorkbook workbook = XmindUtil.createWorkbook();
        ISheet sheet = XmindUtil.getDefaultSheet(workbook, "画布1");
        ITopic rootTopic = XmindUtil.getRootTopic(sheet,rootTopicName);
        ITopic t1 = XmindUtil.createTopic(workbook,"框架");
        ITopic t2 = XmindUtil.createTopic(workbook,"分类");
        ITopic t3 = XmindUtil.createTopic(workbook,"业务");
        ITopic t4 = XmindUtil.createTopic(workbook,"问题");
        XmindUtil.bindTopic(rootTopic, t1);
        XmindUtil.bindTopic(rootTopic, t2);
        XmindUtil.bindTopic(rootTopic, t3);
        XmindUtil.bindTopic(rootTopic, t4);
        //查询开始  
        Connection conn = DbUtil.getConnection(url, username, password);
        
        //获取所有数据库
        List<String []> dbs = DbUtil.getDataBySQL(sql_get_all_db,conn);
        for (Iterator<String []> db = dbs.iterator(); db.hasNext();) {  
            String [] dbRecode = db.next();
            //循环获取数据库信息  
            List<String []> tables = DbUtil.getDataBySQL(sql_get_all_tables.replace("{db_name}", dbRecode[0]),conn);
            ITopic dbTopic = XmindUtil.createTopic(workbook,dbRecode[0]+" 共"+tables.size()+"张表");
            for (Iterator<String []> table = tables.iterator(); table.hasNext();) {  
                String [] tableRecode = table.next();
                //循环获取数据库信息  
                List<String []> columns = DbUtil.getDataBySQL(sql_get_all_columns.replace("{table_name}", tableRecode[0]).replace("{db_name}", dbRecode[0]),conn);
                ITopic tableTopic = XmindUtil.createTopic(workbook,tableRecode[0]+"("+tableRecode[1]+") 共"+columns.size()+"个字段");
                for (Iterator<String []> column = columns.iterator(); column.hasNext();) {  
                    String [] columnRecode = column.next();
                    //循环获取数据库信息  
                    ITopic columnTopic = XmindUtil.createTopic(workbook,columnRecode[0]+"("+columnRecode[3]+")");
                    XmindUtil.bindTopic(tableTopic,columnTopic);
                } 
                XmindUtil.bindTopic(dbTopic,tableTopic);
            }
            XmindUtil.bindTopic(t2,dbTopic);  
        }  
        conn.close();
        XmindUtil.save(workbook, xmindPath);
        System.out.println("生成"+xmindPath+"完成!");
    }

}
