package com.assets;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
/**
 * 表信息
 * @ClassName:  TableInfo   
 * @Description:TODO   
 * @author: sunshine  
 * @date:   2014年4月9日 上午10:22:55
 */
public class TableInfo {
	private final String ENDL = "\n";
	private final String TAB = "\t";
	private final String TAB2 = TAB + TAB;
	private final String TAB3 = TAB2 + TAB;
	private final String TAB4 = TAB2 + TAB2;
	private String name;
	private String primaryKey;//主键
	private String parserKey;//转换后的主键(小写)
	private List<ColumnInfo> columns;//table字段集
	private List<PropertyInfo> fields;//bean属性集

	public TableInfo(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public List<ColumnInfo> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnInfo> columns) {
		this.columns = columns;
	}

	public String getParserKey() {
		return parserKey;
	}

	public void setParserKey(String parserKey) {
		this.parserKey = parserKey;
	}

	public void addColumn(ColumnInfo column) {
		if (columns == null)
			columns = new ArrayList<ColumnInfo>();
		if (!columns.contains(column)) {
			columns.add(column);
			String type = column.parseJavaType();
			String name = column.parseFieldName();
			addFiled(new PropertyInfo(type, name,column.getRemarks()));
		}
	}

	public void addFiled(PropertyInfo field) {
		if (fields == null)
			fields = new ArrayList<PropertyInfo>();
		if (!fields.contains(field)) {
			fields.add(field);
		}

	}

	public List<PropertyInfo> getFields() {
		return fields;
	}
	
    /**
     * 生成bean属性
     * @Description: TODO
     * @Title: getPropertysDeclareInfo 
     * @author: sunshine  
     * @param: @return      
     * @return: String      
     * @throws 
     * @date: 2014年4月11日 下午3:21:28
     */
	public String getPropertysDeclareInfo() {
		StringBuffer sb = new StringBuffer();//属性
		StringBuffer sb1 = new StringBuffer();//get()和set()方法
		for (PropertyInfo field : fields) {
			String name=field.getName();
			String type=field.getType();
			if(field.getRemarks()!=null && "".equals(field.getRemarks().trim())){
				sb.append(TAB+"/**"+field.getRemarks()+"*/"+ENDL);
			}
			sb.append(TAB+"private "+type+" "+name+";"+ENDL);
			//set()
			sb1.append(TAB+"public void set" + StringUtils.capitalize(name)+ 
					"( " + type + " " + name + " ) {"+ENDL);
			        sb1.append(TAB);
			        sb1.append(TAB);
					sb1.append("this." + name + " = " + name + ";"+ENDL);
					sb1.append(TAB);
					sb1.append( "}"+ENDL);
			//get()
			sb1.append(TAB+"public " + type + " get" +StringUtils.capitalize(name)+ "(){"+ENDL);
					        sb1.append(TAB);
					        sb1.append(TAB);
							sb1.append( "return " + name + ";"+ENDL);
					        sb1.append(TAB);
							sb1.append( "}"+ENDL);	
		}
		return sb.toString()+sb1.toString();
	}
    /**
     * 生成查询
     * @Description: TODO
     * @Title: getSelectStatement 
     * @author: sunshine  
     * @param: @return      
     * @return: String      
     * @throws 
     * @date: 2014年4月11日 下午3:21:58
     */
	public String getSelectStatement() {
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append(this.getColumnNames());
		sb.append(" from " + name);
		return sb.toString();
	}
    /**
     * 生成插入
     * @Description: TODO
     * @Title: getInsertStatement 
     * @author: sunshine  
     * @param: @return      
     * @return: String      
     * @throws 
     */
	public String getInsertStatement() {
		StringBuffer sb = new StringBuffer();
		ColumnInfo col = null;
		sb.append("insert into " + name);
		sb.append("( " + this.getColumnNames() + " ) values (");
		for (int i = 0; i < columns.size(); i++) {
			col = columns.get(i);
			if(col.getType().toUpperCase().equals("DATE")){
				sb.append("#{" + col.parseFieldName() +"}");
			}else{
				sb.append("#{" + col.parseFieldName()+"}");
			}
			if (i + 1 != columns.size()) {
				sb.append(",");
			}
		}
		sb.append(" )");
		return sb.toString();
	}
    /**
     * 生成修改
     * @Description: TODO
     * @Title: getUpdateStatement 
     * @author: sunshine  
     * @param: @return      
     * @return: String      
     * @throws 
     */
	public String getUpdateStatement() {
		StringBuffer sb = new StringBuffer();
		sb.append(TAB2+"update " + name + " set "+primaryKey + "=#{"+parserKey+"}"+ENDL);
		ColumnInfo col = null;
		for (int i = 0; i < columns.size(); i++) {
			col = columns.get(i);
			if(!col.getName().toLowerCase().equals(primaryKey.toLowerCase())){
				if("String".equals(col.parseJavaType())){
					sb.append(TAB2+"<if test=\""+col.parseFieldName()+" !=null and "+col.parseFieldName()+" !='' \">"+ENDL);
				}else{
					sb.append(TAB2+"<if test=\""+col.parseFieldName()+" !=null \">"+ENDL);
				}
				sb.append(TAB3+","+col.getName() + "=#{" + col.parseFieldName() +"}"+ENDL);
				sb.append(TAB2+"</if>"+ENDL);
			}
	
		}
		sb.append(TAB+" where " + primaryKey + "=#{"+parserKey+"}");
		return sb.toString();
	}
    /**
     * 生成table与bean映射结果集
     * @Description: TODO
     * @Title: getResultMap 
     * @author: sunshine  
     * @param: @return      
     * @return: String      
     * @throws
     */
	public String getResultMap() {
		StringBuffer sb = new StringBuffer();
		sb.append(TAB3);
		sb.append("<id property=\"" + this.parserKey+ "\" column=\"" + this.primaryKey+"\" />");
		sb.append(ENDL);
		for (ColumnInfo col : columns) {
			if(!col.getName().equals(this.primaryKey)){
				sb.append(TAB3);
				sb.append("<result property=\"" + col.parseFieldName() + "\" column=\"" + col.getName()+"\" />");
		                   //+ "\" jdbcType=\"" + col.parseJdbcType() + "\" />");
				sb.append(ENDL);
			}
		}
		return sb.toString();
	}
	/**
	 * 生成查询条件
	 * @Description: TODO
	 * @Title: getOtherCondition 
	 * @author: sunshine  
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String getOtherCondition() {
		StringBuffer sb = new StringBuffer();
		for (ColumnInfo col : columns) {
			sb.append(TAB3);
			sb.append("<if test=\"" + col.parseFieldName() + " != null and " + col.parseFieldName() + " != '' \" > ");
			sb.append(" and "+ col.getName() +"=#{"+ col.parseFieldName()+"}");
			sb.append("</if>");
			sb.append(ENDL);
		}
		return sb.toString();
	}
	
    /**
     * 生成模糊查询条件
     * @Description: TODO
     * @Title: getFindByLike 
     * @author: sunshine  
     * @param: @return      
     * @return: String      
     * @throws
     */
	public String getFindByLike() {
		StringBuffer sb = new StringBuffer();
		for (ColumnInfo col : columns) {
				sb.append(TAB3);
				if("String".equals(col.parseJavaType())){
					sb.append("<if test=\""+col.parseFieldName()+" !=null and "+col.parseFieldName()+" !='' \">"+ENDL);
				}else{
					sb.append("<if test=\""+col.parseFieldName()+" !=null \">"+ENDL);
				}
				if(col.getName().toLowerCase().endsWith("title") || col.getName().toLowerCase().endsWith("subject")
					||col.getName().toLowerCase().endsWith("keyWords") || col.getName().toLowerCase().endsWith("content")){
					sb.append(TAB4+"<![CDATA[ and instr("+ col.getName() +",#{"+ col.parseFieldName()+"})>0 ]]>"+ENDL);
				}else{
					sb.append(TAB4+" and `"+ col.getName() +"`=#{"+ col.parseFieldName()+"}"+ENDL);
				}
				sb.append(TAB3+"</if>"+ENDL);
		}
		return sb.toString();
	}
    /**
     * 生成所有字段
     * @Description: TODO
     * @Title: getColumnNames 
     * @author: sunshine  
     * @param: @return      
     * @return: String      
     * @throws
     */
	public String getColumnNames() {
		StringBuffer sb = new StringBuffer();
		ColumnInfo column = null;
		for (int i = 0; i < columns.size(); i++) {
			column = columns.get(i);
			sb.append("`"+column.getName()+"`");
			if (i + 1 != columns.size()) {
				sb.append(",");
			}
		}
		return sb.toString();
	}
}