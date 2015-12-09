package com.assets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.assets.utils.DevLog;
import com.assets.utils.StringUtil;

public class ScaffoldBuilder {
	protected final Log logger = LogFactory.getLog(getClass());

	protected final static String PKG_PREFIX = "com.wishcloud.web.module.";
	protected final static String PKG_SUFFIX_MODEL = "bean.";
	protected final static String PKG_SUFFIX_DAO = "dao.";
	protected final static String PKG_SUFFIX_SERVICE = "service.";
	protected final static String PKG_IMPL = "impl";
	protected final static String PKG_SUFFIX_CONTROLLER = "controller.";
	protected final static String PKG_SUFFIX_MAPPER = "mappers";
	protected final static String MAPPER_SUFFIX="Mapper";
	protected final static String DAO_SUFFIX="Dao";
	protected final static String SERVICE_SUFFIX="Service";
	protected final static String CONTROLLER_SUFFIX="Controller";
	
	protected final static String AUTHOR="sunshine";
	
	protected String pkgName;//模块基本包路径
	protected String clzName;
	protected TableInfo tableInfo;
	protected String moduleName;
	protected String moduleNameCN;
	private final Map<String, String> mapping;//标签

	public ScaffoldBuilder(String moduleName,String clzName, TableInfo tableInfo,String moduleNameCN) {
		this.moduleName=moduleName;
		this.moduleNameCN=moduleNameCN;
		this.pkgName = PKG_PREFIX+StringUtils.lowerCase(this.moduleName)+StringUtil.DOT;
		this.clzName = clzName;
		this.tableInfo = tableInfo;
		mapping = new HashMap<String, String>();
		mapping.put("clzName", clzName);//bean名称
		mapping.put("moduleNameCN", moduleNameCN);//bean名称
		mapping.put("clzNameLC", StringUtils.uncapitalize(clzName));//首字母小写的bean名称
		mapping.put("tblName", tableInfo.getName());//表名
		mapping.put("beanPath", getBeanPath());
		mapping.put("daoPath", getDaoPath());
		mapping.put("servicePath", getServicePath());
		mapping.put("serviceImplPath", getServiceImplPath());
		//bean的属性
		mapping.put("propertysDeclareInfo", tableInfo.getPropertysDeclareInfo());
		//结果集配置映射文件
		mapping.put("resultMap", tableInfo.getResultMap());
		//其他条件配置映射文件
		mapping.put("otherCondition", tableInfo.getOtherCondition());		

		DevLog.debug(tableInfo.getPrimaryKey());
		mapping.put("primaryKey", tableInfo.getPrimaryKey());
		
		DevLog.debug(tableInfo.getParserKey());
		mapping.put("parserKey", tableInfo.getParserKey());
		
		mapping.put("capitalizeKey",StringUtils.capitalize(tableInfo.getParserKey()));
		
		DevLog.debug(tableInfo.getFindByLike());
		mapping.put("findByLike", tableInfo.getFindByLike());
		
		DevLog.debug(tableInfo.getSelectStatement());
		mapping.put("selectStatement", tableInfo.getSelectStatement());
		
		DevLog.debug(tableInfo.getInsertStatement());
		mapping.put("insertStatement", tableInfo.getInsertStatement());
		
		DevLog.debug(tableInfo.getInsertStatement());
		mapping.put("columnNames", tableInfo.getColumnNames());
		
		DevLog.debug(tableInfo.getUpdateStatement());
		mapping.put("updateStatement", tableInfo.getUpdateStatement());
		mapping.put("author",AUTHOR);
	}
	
	/**
	 * bean路径
	 * @Description: TODO
	 * @Title: getModelPath 
	 * @author: sunshine  
	 * @param: @return      
	 * @return: String      
	 * @throws 
	 * @date: 2014年4月11日 上午11:12:44
	 */
	public String getBeanPath() {
		return pkgName + PKG_SUFFIX_MODEL + clzName;
	}
	
    /**
     * DAO接口路径
     * @Description: TODO
     * @Title: getDaoPath 
     * @author: sunshine  
     * @param: @return      
     * @return: String      
     * @throws 
     * @date: 2014年4月11日 上午11:47:52
     */
	public String getDaoPath() {
		return pkgName + PKG_SUFFIX_DAO + clzName + DAO_SUFFIX;
	}
	
    /**
     * Service接口路径
     * @Description: TODO
     * @Title: getServicePath 
     * @author: sunshine  
     * @param: @return      
     * @return: String      
     * @throws 
     * @date: 2014年4月11日 上午11:51:40
     */
	public String getServicePath() {
		return pkgName + PKG_SUFFIX_SERVICE +"I"+clzName + SERVICE_SUFFIX;
	}
	
    /**
     * Service实现类路径
     * @Description: TODO
     * @Title: getServiceImplPath 
     * @author: sunshine  
     * @param: @return      
     * @return: String      
     * @throws 
     * @date: 2014年4月11日 上午11:52:05
     */
	public String getServiceImplPath() {
		return pkgName+ PKG_SUFFIX_SERVICE + PKG_IMPL + StringUtil.DOT + clzName + "Service"
				+ StringUtils.capitalize(PKG_IMPL);
	}
    /**
     * 生成的文件列表
     * @return
     */
	public List<FileGenerator> buildGenerators() {
		List<FileGenerator> list = new ArrayList<FileGenerator>();
		
		// bean
		list.add(new FileGenerator(pkgName + "bean", clzName, "Model.txt", mapping));
		
		// dao
		list.add(new FileGenerator(pkgName + "dao", clzName + "Dao", "DAO.txt", mapping));
		
		// Service
		list.add(new FileGenerator(pkgName + "service", "I"+clzName + "Service", "Service.txt", mapping));
		list.add(new FileGenerator(pkgName + "service.impl", clzName + "ServiceImpl", "ServiceImpl.txt", mapping));
	
		//mybatisSQL映射配置文件
		list.add(new FileGenerator(pkgName + "mappers", clzName+"Mapper", "SqlMap.txt", mapping, "xml"));
		
		// controller
		list.add(new FileGenerator(pkgName + "controller", clzName + "Controller", "Controller.txt", mapping));

		return list;
	}
}
