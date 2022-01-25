package com.assets;
import java.awt.Color;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;

import util.DbUtil;
/**
 * mysql数据库表导出为doc文档
 * @author:sunshine
 * @version:v1.0.0
 * @date:2018年5月4日
 */
public class MysqlDB2Doc {
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
    private static String schema = "clinic_medicine_erp"; //目标数据库 名  
    private static String url = "jdbc:mysql://111.231.203.172:3306/"+schema;//链接url  
    private static String username = "root"; //用户名  
    private static String password = "shyzs,,.//"; //密码  

    //查询所有表的sql语句  
    private static String sql_get_all_tables = "select table_name,TABLE_COMMENT from INFORMATION_SCHEMA.tables where TABLE_SCHEMA='"+schema+"' and TABLE_TYPE='BASE TABLE'";
    //查询所有字段的sql语句  
    private static String sql_get_all_columns = "select column_name,data_type,character_octet_length,COLUMN_COMMENT,is_nullable,COLUMN_key from information_schema.`COLUMNS` where TABLE_NAME='{table_name}' and TABLE_SCHEMA='"+schema+"'";
    public static void main(String[] args) throws Exception {  
        //初始化word文档  
        Document document = new Document(PageSize.A4); 
        RtfWriter2.getInstance(document,new FileOutputStream("f:/"+schema+".doc"));  
        document.open();  
        //查询开始  
        Connection conn = DbUtil.getConnection(url, username, password);
        //获取所有表  
        List<String []> tables = DbUtil.getDataBySQL(sql_get_all_tables,conn);
        int i=1;
        for (Iterator<String []> iterator = tables.iterator(); iterator.hasNext();) {  
            String [] arr = iterator.next();
            //循环获取字段信息  
            System.out.print(i+".正在处理数据表-----------"+arr[0]);
            addTableMetaData(document,arr,i);
            List<String []> columns = DbUtil.getDataBySQL(sql_get_all_columns.replace("{table_name}", arr[0]),conn);
            addTableDetail(document,columns);
            addBlank(document);
            System.out.println("...done");
            i++;
        }  
        System.out.print("...处理完成,打开"+"f:/"+schema+".doc 查看");
        document.close();  
        conn.close();
    } 
    
    /** 
     * 添加一个空行 
     * @param document 
     * @throws Exception 
     */  
    public static void addBlank(Document document)throws Exception{  
        Paragraph ph = new Paragraph("");
        ph.setAlignment(Paragraph.ALIGN_LEFT); 
        document.add(ph);
    }  
    /** 
     * 添加包含字段详细信息的表格 
     * @param document 
     * @param arr1 
     * @param columns 
     * @throws Exception 
     */  
    public static void addTableDetail(Document document,List<String []> columns)throws Exception{  
        Table table = new Table(6);  
        table.setWidth(100f);//表格 宽度100%  
        table.setBorderWidth(1);  
        table.setBorderColor(Color.BLACK);  
        table.setPadding(0);  
        table.setSpacing(0);  
        Cell cell1 = new Cell("序号");// 单元格  
        cell1.setHeader(true);  
          
        Cell cell2 = new Cell("列名");// 单元格  
        cell2.setHeader(true); 
          
        Cell cell3 = new Cell("类型");// 单元格  
        cell3.setHeader(true); 
          
        Cell cell4 = new Cell("长度");// 单元格  
        cell4.setHeader(true); 
          
        Cell cell5 = new Cell("键");// 单元格  
        cell5.setHeader(true); 
          
        Cell cell6 = new Cell("备注");// 单元格  
        cell6.setHeader(true);
        //设置表头格式  
        Color bkc = new Color(153, 204, 255);
        table.setWidths(new float[]{8f,30f,15f,8f,10f,29f});
        cell1.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell1.setBackgroundColor(bkc);
        cell2.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell2.setBackgroundColor(bkc);
        cell3.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell3.setBackgroundColor(bkc);
        cell4.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell4.setBackgroundColor(bkc);
        cell5.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell5.setBackgroundColor(bkc);
        cell6.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell6.setBackgroundColor(bkc);
        table.addCell(cell1);  
        table.addCell(cell2);  
        table.addCell(cell3);  
        table.addCell(cell4);  
        table.addCell(cell5);
        table.addCell(cell6);
        table.endHeaders();// 表头结束  
        int x = 1;
        for (Iterator<String []> iterator = columns.iterator(); iterator.hasNext();) {  
            String [] arr2 = iterator.next();
            Cell c1 = new Cell(x+"");
            Cell c2 = new Cell(arr2[0]);
            Cell c3 = new Cell(arr2[1]);
            Cell c4 = new Cell(arr2[2]);
              
            String key = keyType.get(arr2[5]);
            if(key==null)key = "";
            Cell c5 = new Cell(key);
            Cell c6 = new Cell(arr2[3]);
            c1.setHorizontalAlignment(Cell.ALIGN_CENTER);
            c2.setHorizontalAlignment(Cell.ALIGN_CENTER);
            c3.setHorizontalAlignment(Cell.ALIGN_CENTER);
            c4.setHorizontalAlignment(Cell.ALIGN_CENTER);
            c5.setHorizontalAlignment(Cell.ALIGN_CENTER);
            c6.setHorizontalAlignment(Cell.ALIGN_CENTER);
            table.addCell(c1);
            table.addCell(c2);
            table.addCell(c3);
            table.addCell(c4);
            table.addCell(c5);
            table.addCell(c6);
            x++;
        }  
        document.add(table);
    }  
    /** 
     * 增加表概要信息 
     * @param dcument 
     * @param arr 
     * @param i 
     * @throws Exception 
     */  
    public static void addTableMetaData(Document dcument,String [] arr,int i) throws Exception{  
        Paragraph ph = new Paragraph(i+". 表名: "+arr[0]+"	    描述: "+(arr[1]==null?"":arr[1]));
        ph.setAlignment(Paragraph.ALIGN_LEFT); 
        dcument.add(ph);
    }
}
