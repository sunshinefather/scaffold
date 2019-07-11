package com.assets.wordsparser;

import org.apache.commons.lang.StringUtils;
/**
 * 全部大写
 * @ClassName:  UpperCaseWordsParser   
 * @Description:TODO   
 * @author: sunshine  
 * @date:   2014年4月9日 上午10:18:25
 */
public class UpperCaseWordsParser implements WordsParser {
	
    @Override
	public String parseWords(String orginalString) {
		return StringUtils.upperCase(orginalString);
	}
}
