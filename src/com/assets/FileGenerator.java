package com.assets;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.assets.utils.StringUtil;
/**
 * 生成文件
 * @ClassName:  FileGenerator   
 * @Description:TODO   
 * @author: sunshine  
 * @date:   2014年4月11日 上午10:48:24
 */
public class FileGenerator {
	private final static String TEMPLATE_PATH = "com/assets/template/";//模板路径地址
	private final static String SRC_PATH = "out" + StringUtil.FILE_SEPARATOR;
	protected String pkgPath;
	protected String className;
	protected String template;//模板文件名称
	protected String suffix;//生成文件后缀
	private final Map<String, String> mapping;//替换模板标签键

	public FileGenerator(String pkgPath, String className, String template, Map<String, String> mapping) {
		this(pkgPath, className, template, mapping, "java");
	}

	public FileGenerator(String pkgPath, String className, String template, Map<String, String> mapping,
			String fileSuffix) {
		this.pkgPath = pkgPath;
		this.className = className;
		this.template = template;
		this.mapping = mapping;
		this.suffix = fileSuffix;
	}

	public String getTargetFilePath() {
		String result = pkgPath.replace(StringUtil.DOT, StringUtil.FILE_SEPARATOR);
		result = result + StringUtil.FILE_SEPARATOR;
		result = result + className + StringUtil.DOT + suffix;
		result = SRC_PATH + result;
		return result;
	}
	
	public void execute() {
		String tmplFile = TEMPLATE_PATH + template;
		InputStream templateInputStream = getClass().getClassLoader().getResourceAsStream(tmplFile);

		if (templateInputStream == null) {
			System.out.println("[警告:] " + tmplFile + " 不存在");
			return;
		}

		File f = new File(getTargetFilePath());
		if (f.exists()) {
			System.out.println(f.getAbsoluteFile() + " 已经存在");
			return;
		}
		try {
			System.out.println(f.getAbsoluteFile() + " 开始创建");
			createFileWithDirs(f);
			writeContentWithTemplate(new PrintStream(f), templateInputStream, mapping);
			System.out.println(f.getAbsoluteFile() + " 创建成功");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(f.getAbsoluteFile() + " 创建失败");
		}
	}
    /**
     * 写入文件
     * @Description: TODO
     * @Title: writeContentWithTemplate 
     * @author: sunshine  
     * @param: @param ps 写入到这儿
     * @param: @param templateInputStream 读取模板
     * @param: @param mapping 替换的标签
     * @param: @throws IOException      
     * @return: void      
     * @throws 
     * @date: 2014年4月11日 上午10:39:22
     */
	private void writeContentWithTemplate(PrintStream ps, InputStream templateInputStream, Map<String, String> mapping)
			throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(templateInputStream));
		String line = null;
		while ((line = br.readLine()) != null) {
			line = replaceWithMapping(line, mapping);
			ps.println(line);
		}
		br.close();
	}
    /**
     * 替换标签
     * @Description: TODO
     * @Title: replaceWithMapping 
     * @author: sunshine  
     * @param: @param srcLine 要替换的字符源串
     * @param: @param mapping 标签键值对集合
     * @param: @return      
     * @return: String      
     * @throws 
     * @date: 2014年4月11日 上午10:24:07
     */
	private String replaceWithMapping(String srcLine, Map<String, String> mapping) {
		final String TAG_BEGI = "${";
		final String TAG_END = "}";
		String result = srcLine;
		for (String key : mapping.keySet()) {
				String value = mapping.get(key);
				result = StringUtils.replace(result, TAG_BEGI + key + TAG_END, value);
		}
		result = StringUtils.replace(result, TAG_BEGI + "pkgPath" + TAG_END, pkgPath);
		return result;
	}
    /**
     * 创建文件及文件夹
     * @Description: TODO
     * @Title: createFileWithDirs 
     * @author: sunshine  
     * @param: @param f
     * @param: @return
     * @param: @throws IOException      
     * @return: boolean      
     * @throws 
     * @date: 2014年4月11日 上午10:27:39
     */
	public boolean createFileWithDirs(File f) throws IOException {
		File parentDir = f.getParentFile();
		boolean parentCreated = false;
		if (!parentDir.exists()) {
			parentCreated = parentDir.mkdirs();
		}
		if (parentCreated) {
			return f.createNewFile();
		}
		return false;
	}
}