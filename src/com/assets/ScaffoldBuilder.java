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

	protected final static String PKG_PREFIX = "com.mdks.module.";
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
	protected TableInfo tableInfo;
	protected String moduleName;
	protected String moduleNameCN;
	private final Map<String, String> mapping;//标签

	public ScaffoldBuilder(String moduleName, TableInfo tableInfo,String moduleNameCN) {
		this.moduleName=moduleName;
		this.moduleNameCN=moduleNameCN;
		this.pkgName = PKG_PREFIX+StringUtils.lowerCase(this.moduleName)+StringUtil.DOT;
		this.tableInfo = tableInfo;
		mapping = new HashMap<String, String>();
		mapping.put("className",tableInfo.getClassName());//bean名称
		mapping.put("lflClassName", StringUtils.uncapitalize(tableInfo.getClassName()));//首字母小写的bean名称
		mapping.put("tblName", tableInfo.getName());//表名
		mapping.put("moduleNameCN", moduleNameCN);//模块中文名称(表备注)
		mapping.put("beanPath", getBeanPath());//bean生成的路径,
		mapping.put("daoPath", getDaoPath());//dao接口生成的路径
		mapping.put("servicePath", getServicePath());//service接口生成的路径
		mapping.put("serviceImplPath", getServiceImplPath());//service接口实现生成的路径
		mapping.put("propertysDeclareInfo", tableInfo.getPropertysDeclareInfo());//bean的属性
		mapping.put("resultMap", tableInfo.getResultMap());//结果集配置映射文件
		mapping.put("otherCondition", tableInfo.getOtherCondition());//其他条件配置映射文件	
		DevLog.debug(tableInfo.getPrimaryKey());
		mapping.put("tablePK", tableInfo.getPrimaryKey());//表中的主键
		
		DevLog.debug(tableInfo.getParserKey());
		mapping.put("beanPK", tableInfo.getParserKey());//转换后的bean对应的主键
		
		mapping.put("uflBeanPK",StringUtils.capitalize(tableInfo.getParserKey()));//首字母大写的bean对应的主键
		
		DevLog.debug(tableInfo.getFindByLike());
		mapping.put("findByLike", tableInfo.getFindByLike());//按条件查找语句
		
		DevLog.debug(tableInfo.getSelectStatement());
		mapping.put("selectSql", tableInfo.getSelectStatement());//查询语句表达式
		
		DevLog.debug(tableInfo.getInsertStatement());
		mapping.put("insertSql", tableInfo.getInsertStatement());//插入语句
		
		DevLog.debug(tableInfo.getInsertStatement());
		mapping.put("columnNames", tableInfo.getColumnNames());//所有字段
		
		DevLog.debug(tableInfo.getUpdateStatement());
		mapping.put("updateSql", tableInfo.getUpdateStatement());//修改sql语句
		mapping.put("author",AUTHOR);//作者
		mapping.put("beanImports",tableInfo.getModelImports());//bean需要导入的类
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
		return pkgName + PKG_SUFFIX_MODEL + tableInfo.getClassName();
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
		return pkgName + PKG_SUFFIX_DAO + tableInfo.getClassName() + DAO_SUFFIX;
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
		return pkgName + PKG_SUFFIX_SERVICE +"I"+tableInfo.getClassName() + SERVICE_SUFFIX;
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
		return pkgName+ PKG_SUFFIX_SERVICE + PKG_IMPL + StringUtil.DOT + tableInfo.getClassName() + "Service"
				+ StringUtils.capitalize(PKG_IMPL);
	}
    /**
     * 生成的文件列表
     * @return
     */
	public List<FileGenerator> buildGenerators() {
		List<FileGenerator> list = new ArrayList<FileGenerator>();
		
		// bean
		list.add(new FileGenerator(pkgName + "bean", tableInfo.getClassName(), "Bean.txt", mapping));
		
		// dao
		list.add(new FileGenerator(pkgName + "dao", tableInfo.getClassName() + "Dao", "DAO.txt", mapping));
		
		// Service
		list.add(new FileGenerator(pkgName + "service", "I"+tableInfo.getClassName() + "Service", "Service.txt", mapping));
		list.add(new FileGenerator(pkgName + "service.impl", tableInfo.getClassName() + "ServiceImpl", "ServiceImpl.txt", mapping));
	
		//mybatisSQL映射配置文件
		list.add(new FileGenerator(pkgName + "mappers", tableInfo.getClassName()+"Mapper", "SqlMap.txt", mapping, "xml"));
		
		// controller
		list.add(new FileGenerator(pkgName + "controller", tableInfo.getClassName() + "Controller", "Controller.txt", mapping));

		return list;
	}
}
