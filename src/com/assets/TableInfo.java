package com.assets;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.assets.wordsparser.UnderlineSplitWordsParser;
/**
 * 表信息
 * @ClassName:  TableInfo   
 * @Description:TODO   
 * @author: sunshine  
 * @date:   2014年4月9日 上午10:22:55
 */
public class TableInfo {
	private final String ENDL = "\n";
	private final String TAB1 = "\t";
	private final String TAB2 = TAB1 + TAB1;
	private final String TAB3 = TAB2 + TAB1;
	private final String TAB4 = TAB2 + TAB2;
	private String name;
	private String className;
	private String tablePK;//主键
	private String beanPK;//转换后的主键(小写)
	private List<ColumnInfo> columns;//table字段集
	private List<PropertyInfo> fields;//bean属性集

	public TableInfo(String name) {
		 setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.className=StringUtils.capitalize(new UnderlineSplitWordsParser().parseWords(name));
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPrimaryKey() {
		return tablePK;
	}

	public void setPrimaryKey(String tablePK) {
		this.tablePK = tablePK;
	}

	public List<ColumnInfo> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnInfo> columns) {
		this.columns = columns;
	}

	public String getParserKey() {
		return beanPK;
	}

	public void setParserKey(String beanPK) {
		this.beanPK = beanPK;
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
		StringBuffer sb2 = new StringBuffer();//toString()方法
		sb2.append(TAB1+"@Override"+ENDL);
		sb2.append(TAB1+"public String toString() {"+ENDL);
		sb2.append(TAB2+"return \"[");
		sb2.append(getClassName());
		sb2.append(" [");
		boolean isFirst=true;
		for (PropertyInfo field : fields) {
			String name=field.getName();
			String type=field.getType();
			if(field.getRemarks()!=null && !"".equals(field.getRemarks().trim())){
				sb.append(TAB1+"/**"+field.getRemarks()+"*/"+ENDL);
			}
			if(!isFirst){
				sb2.append(", ");	
				isFirst=false;
			}
			sb2.append(name);
			sb2.append("=\"");
			sb2.append(" + ");
			sb2.append(name);
			sb2.append(" + \"");
			sb.append(TAB1+"private "+type+" "+name+";"+ENDL);
			//set()
			sb1.append(TAB1+"public void set" + StringUtils.capitalize(name)+ 
					"(" + type + " " + name + ") {"+ENDL);
			        sb1.append(TAB1);
			        sb1.append(TAB1);
					sb1.append("this." + name + " = " + name + ";"+ENDL);
					sb1.append(TAB1);
					sb1.append( "}"+ENDL);
			//get()
			sb1.append(TAB1+"public " + type + " get" +StringUtils.capitalize(name)+ "(){"+ENDL);
					        sb1.append(TAB1);
					        sb1.append(TAB1);
							sb1.append( "return " + name + ";"+ENDL);
					        sb1.append(TAB1);
							sb1.append( "}"+ENDL);	
		}
		sb2.append("]\";"+ENDL);
		sb2.append(TAB1+"}");
		return sb.toString()+sb1.toString()+sb2.toString();
	}
	/**
	 * 生成bean需要导入的包
	 * @Title: getModelImports
	 * @Description:
	 * @param: @return      
	 * @return: String
	 * @author: sunshine  
	 * @throws
	 */
	public String getModelImports() {
		StringBuffer sb = new StringBuffer();
		boolean dateIsWrited=false;
		boolean bigDecimalIsWrited=false;
		for (PropertyInfo field : fields) {
			String type=field.getType();
            if("Date".equals(type) && !dateIsWrited){
            	sb.append("import java.util.Date;"+ENDL);
            	dateIsWrited=true;
            }else if("BigDecimal".equals(type) && !bigDecimalIsWrited){
            	sb.append("import java.math.BigDecimal;"+ENDL);
            	bigDecimalIsWrited=true;
            }
		}
		return sb.toString();
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
		sb.append("select <include refid=\"columns\"/> from ");
		sb.append(name);
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
		sb.append("(<include refid=\"columns\"/>) values (");
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
		sb.append(")");
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
		sb.append(TAB2+"update " + name + " "+ENDL);
		sb.append(TAB2+"<set>" +ENDL);
		ColumnInfo col = null;
		for (int i = 0; i < columns.size(); i++) {
			col = columns.get(i);
			if(!col.getName().toLowerCase().equals(tablePK.toLowerCase())){
				if("String".equals(col.parseJavaType())){
					sb.append(TAB3+"<if test='"+col.parseFieldName()+" !=null and "+col.parseFieldName()+" != \"\" ' >"+ENDL);
				}else{
					sb.append(TAB3+"<if test='"+col.parseFieldName()+" !=null '>"+ENDL);
				}
				sb.append(TAB4+col.getName() + " = #{" + col.parseFieldName() +"},"+ENDL);
				sb.append(TAB3+"</if>"+ENDL);
			}
	
		}
		sb.append(TAB2+"</set>" +ENDL);
		sb.append(TAB1+" where " + tablePK + " = #{"+beanPK+"}");
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
		sb.append("<id property=\"" + this.beanPK+ "\" column=\"" + this.tablePK+"\" />");
		sb.append(ENDL);
		for (ColumnInfo col : columns) {
			if(!col.getName().equals(this.tablePK)){
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
			if(!("create_user_id".equalsIgnoreCase(col.getName())
			   || "create_datetime".equalsIgnoreCase(col.getName())	
			   || "modify_user_id".equalsIgnoreCase(col.getName())	
			   || "modify_datetime".equalsIgnoreCase(col.getName())	
			   || "delete_flag".equalsIgnoreCase(col.getName())	
				)) {
				sb.append(TAB3);
				sb.append("<if test='" + col.parseFieldName() + " != null ' > ");
				sb.append(" and `"+ col.getName() +"` = #{"+ col.parseFieldName() +"}");
				sb.append("</if>");
				sb.append(ENDL);	
			}
		}
		return sb.toString();
	}
	
    /**
     * 生成查询条件
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
				sb.append("<if test='"+col.parseFieldName()+" != null ' >"+ENDL);
				if(col.getName().toLowerCase().endsWith("title") || col.getName().toLowerCase().endsWith("subject")
					||col.getName().toLowerCase().endsWith("keyWords") || col.getName().toLowerCase().endsWith("content")){
					sb.append(TAB4+"<![CDATA[ and instr("+ col.getName() +",#{"+ col.parseFieldName()+"})>0 ]]>"+ENDL);
				}else{
					sb.append(TAB4+" and `"+ col.getName() +"` = #{"+ col.parseFieldName()+"}"+ENDL);
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