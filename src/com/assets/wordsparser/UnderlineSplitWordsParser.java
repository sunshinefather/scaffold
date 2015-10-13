package com.assets.wordsparser;
import org.apache.commons.lang.StringUtils;

import com.assets.utils.StringUtil;
/**
 * 按下划线转换驼峰式命名
 * @ClassName:  UnderlineSplitWordsParser   
 * @Description:TODO   
 * @author: sunshine  
 * @date:   2014年4月9日 上午10:18:00
 */
public class UnderlineSplitWordsParser implements WordsParser {
	
    @Override
	public String parseWords(String orginalString) {
		String[] items = orginalString.split(StringUtil.UNDER_LINE);
		String result = "";
		if(items.length==1){
			result =items[0];
		}else{
			for (int i = 0; i < items.length; i++) 
			 {
				if (i > 0) {
					result = result+StringUtils.capitalize(StringUtils.lowerCase(items[i]));
				}else{
					result = result + items[i].toLowerCase();
				}
			 }
		}
		return result;
	}
}