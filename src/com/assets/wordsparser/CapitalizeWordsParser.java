package com.assets.wordsparser;

import org.apache.commons.lang.StringUtils;
/**
 * 首字母大写
 * @ClassName:  UncapitalizeWordsParser   
 * @Description:TODO   
 * @author: sunshine  
 * @date:   2014年4月9日 上午10:18:25
 */
public class CapitalizeWordsParser implements WordsParser {

	public String parseWords(String orginalString) {
		return StringUtils.capitalize(orginalString);
	}
}
