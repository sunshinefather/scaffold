package com.assets.wordsparser;
import org.apache.commons.lang.StringUtils;

import cn.sccl.common.util.StringUtil;
/**
 * 下划线左边的单词小写,下划线右边的单词首字母大写,且去掉下划线
 * @ClassName:  UnderlineSplitWordsParser   
 * @Description:TODO   
 * @author: sunshine  
 * @date:   2014年4月9日 上午10:18:00
 */
public class UnderlineSplitWordsParser implements WordsParser {

	public String parseWords(String orginalString) {
		orginalString=StringUtils.lowerCase(orginalString);
		String[] items = orginalString.split(StringUtil.UNDER_LINE);
		String result = "";
		for (int i = 0; i < items.length; i++) {
			if (i > 0) {
				result = result+StringUtils.capitalize(items[i]);
			} else {
				result = result + items[i].toLowerCase();
			}
		}
		return result;
	}
}