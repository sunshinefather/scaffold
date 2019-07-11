package com.assets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.assets.utils.StringUtil;

public class ScaffoldGen {
	private static final String NULLABLE = "NULLABLE";//可空
	private static final String DECIMAL_DIGITS = "DECIMAL_DIGITS";//十进制位
	private static final String COLUMN_SIZE = "COLUMN_SIZE";//字段长度
	private static final String TYPE_NAME = "TYPE_NAME";//类型
	private static final String REMARKS="REMARKS";//注释
	private static final String COLUMN_NAME = "COLUMN_NAME";//字段名称
	private static final String JDBC_USER = "jdbc.user";//用户
	private static final String JDBC_PASSWORD = "jdbc.password";//密码
	private static final String JDBC_URL = "jdbc.url";//连接url
	private static final String JDBC_DRIVER = "jdbc.driver";//jdbc驱动
	private static final String JDBC_SCHEMA = "jdbc.schema";//数据库所有者
	private static final String CONFIG_PROPERTIES = "jdbc.properties";//配置文件
	private final Log log = LogFactory.getLog(getClass());
	private Connection conn=null;//连接
	private String schema=null;
	private DatabaseMetaData metaData=null;//源数据
	private String className=null;//类名
	private String tblName=null;//表名
	private String moduleName=null;//模块名
	private String moduleNameCN=null;//模块名
    /**
     * 构造方法1
     * @Title:  ScaffoldGen   
     * @Description:    TODO
     * @param:  @param pkgName 包名
     * @param:  @param tblName  表名
     * @throws
     */
	public ScaffoldGen(String tblName,String moduleNameCN) {
		
		this(tblName,tblName,tblName,moduleNameCN);
		
	}
    /**
     * 构造方法2
     * @Title:  ScaffoldGen   
     * @Description:    TODO
     * @param:  @param pkgName 包名
     * @param:  @param className 类名
     * @param:  @param tblName 表名
     * @throws
     */
	public ScaffoldGen(String moduleName, String tblName,String moduleNameCN) {
		this(moduleName,null,tblName,moduleNameCN);
	}
	/**
	 * 
	 * @Title:  ScaffoldGen   
	 * @Description:    TODO
	 * @param:  @param moduleName 模块名
	 * @param:  @param className 类名
	 * @param:  @param tblName 表名
	 * @param:  @param moduleNameCN  模块说明
	 * @throws
	 */
	public ScaffoldGen(String moduleName,String className, String tblName,String moduleNameCN) {
		this.moduleName=moduleName;
		this.moduleNameCN=moduleNameCN;
		if(StringUtil.isNotBlank(className)){
			this.className = StringUtils.capitalize(className);//首字母大写
		}
		this.tblName = tblName.toLowerCase();//转换成全小写
	}
	
	public void execute() throws SQLException {
		if (!initConnection()) {
			System.out.println("数据库连接败?,请检查ClassPath路径下的配置文件" + CONFIG_PROPERTIES);
			return;
		}
		TableInfo tableInfo = parseDbTable(this.tblName);
		if (tableInfo == null) {
			return;
		}
		ScaffoldBuilder sf = new ScaffoldBuilder(this.moduleName,tableInfo,this.moduleNameCN);
		List<FileGenerator> list = sf.buildGenerators();
		for (FileGenerator gen : list) {
			 gen.execute();
		}
	}
	/**
	 * 初始化数据库连接
	 * @Description: TODO
	 * @Title: initConnection 
	 * @author: sunshine  
	 * @param: @return      
	 * @return: boolean      
	 * @throws 
	 * @date: 2014年4月11日 上午10:58:10
	 */
	private boolean initConnection() {
		
		Properties config;
		
		String driver = null;
		String url = StringUtils.EMPTY;
		String user = StringUtils.EMPTY;
		String password = StringUtils.EMPTY;
		String schema = StringUtils.EMPTY;
		try {
			config = new Properties();
			config.load(this.getClass().getClassLoader().getResourceAsStream(CONFIG_PROPERTIES));
			driver = config.getProperty(JDBC_DRIVER);
			url = config.getProperty(JDBC_URL);
			user = config.getProperty(JDBC_USER);
			password = config.getProperty(JDBC_PASSWORD);
			schema = config.getProperty(JDBC_SCHEMA);
			if (StringUtil.isNotBlank(schema)) {
				this.schema = schema;
			}
			if (StringUtil.isBlank(this.schema)) {
				this.schema = user.toUpperCase();
			}
			Class.forName(driver);
		} catch (IOException e1) {
			e1.printStackTrace();
			log.fatal("Jdbc连接配置文件不能找到 - " + CONFIG_PROPERTIES);
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.fatal("Jdbc 驱动不能找到 - " + driver);
			return false;
		}

		try {   
			  Properties props =new Properties();
              props.put("remarksReporting","true");//取得注释
              props.put("user", user);
              props.put("password", password);
			  conn = DriverManager.getConnection(url,props);
			if (conn == null) {
				log.fatal("数据库连接为空");
				return false;
			}
			metaData = conn.getMetaData();
			if (metaData == null) {
				log.fatal("数据库元数据为空");
				return false;
			}
		} catch (SQLException e) {
			log.fatal("数据库连接败");
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 更具字段映射成表对象
	 * @Description: TODO
	 * @Title: parseDbTable 
	 * @author: sunshine  
	 * @param: @param tableName
	 * @param: @return
	 * @param: @throws SQLException      
	 * @return: TableInfo      
	 * @throws 
	 * @date: 2014年4月11日 上午10:58:24
	 */
	private TableInfo parseDbTable(String tableName) throws SQLException {
		TableInfo tableInfo = new TableInfo(tableName);
		if(StringUtil.isNotBlank(className)){
			tableInfo.setClassName(className);
		}
		ResultSet rs = null;
		log.trace("解析表开始...");
		try {
			rs = metaData.getPrimaryKeys(null, schema, tableName);
			if (rs.next()) {
				String keyName = rs.getString(COLUMN_NAME);
				ColumnInfo keyInfo = new ColumnInfo(keyName, "bigint",20,0,0,"主键");
				tableInfo.setPrimaryKey(keyName);
				tableInfo.setParserKey(keyInfo.parseFieldName());
				tableInfo.addColumn(keyInfo);
			}
			else{
				System.out.println(tableName+"表没有主键");
				return null;
			}
		} catch (SQLException e) {
			log.error(tableName + "表解析错误");
			e.printStackTrace();
			return null;
		}
		log.info("主键: " + tableInfo.getPrimaryKey());
		
		//解析表字段
		try {
			rs = metaData.getColumns(conn.getCatalog(), schema, tableName, null);
			if (!rs.next()) {
				log.fatal(schema + "." + tableName + " 表没找到");
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//封装表对象
		try {
			while (rs.next()) {
				String columnName = rs.getString(COLUMN_NAME);
				String columnType = rs.getString(TYPE_NAME);
				int datasize = rs.getInt(COLUMN_SIZE);	
				int digits = rs.getInt(DECIMAL_DIGITS);
				int nullable = rs.getInt(NULLABLE);
				String remarks=rs.getString(REMARKS);
				ColumnInfo colInfo = new ColumnInfo(columnName, columnType, datasize, digits, nullable,remarks);
				tableInfo.addColumn(colInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(tableName + " 表解析错误");
		}
		log.trace("恭喜,解析完毕!");
		return tableInfo;
	}
}