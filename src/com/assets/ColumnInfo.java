package com.assets;

import org.apache.commons.lang.StringUtils;

import com.assets.wordsparser.UnderlineSplitWordsParser;
import com.assets.wordsparser.WordsParser;
/**
 * table字段
 * @ClassName:  ColumnInfo   
 * @Description:TODO   
 * @author: sunshine  
 * @date:   2014年4月10日 下午11:21:55
 */
public class ColumnInfo {
	private String name;//字段
	private String type;//字段类型
	private int size;//字段长度
	private int digits;//十进制位
	private boolean nullable;//可空
	private final WordsParser wordsParser=new UnderlineSplitWordsParser();
	private String remarks;//字段注释
	public ColumnInfo(String name, String type, int size, int decimalDigits,int nullable,String remarks) {
		this.name = name;
		this.type = type;
		this.size = size;
		this.digits = decimalDigits;
		this.remarks=remarks;
		if (nullable == 1)
			this.nullable = true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getDigits() {
		return digits;
	}

	public void setDigits(int digits) {
		this.digits = digits;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String parseJavaType() {
		String jdbcType = StringUtils.upperCase(getType());
		String result = "String";
		if ("DATE".equals(jdbcType)) {
			result = "Date";
		}
		if (getDigits() > 0) {		
			result = "BigDecimal";
		}
		return result;
	}

	public String parseJdbcType() {
		String javaType = parseJavaType();
		String result = "VARCHAR";
		if ("BigDecimal".equals(javaType)) {
			result = "NUMERIC";
		}
		if ("Date".equals(javaType)) {
			result = "DATE";
		}
		return result;
	}

	public String parseFieldName() {
		return wordsParser.parseWords(name);
	}
	
	@Override
	public String toString() {
		return name + " " + type + " " + size + " " + digits + " " + nullable;
	}
}
