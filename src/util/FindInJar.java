package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
/**
 * 指定目录下搜索class文件所在的jar包列表
 * @author sunshine
 * @date 2021年10月15日
 */
public class FindInJar {  

   /**
       * 搜索class文件所在的jar文件列表
    * @param className 搜索的class全路径文件名称
	* @param dir jar所在的目录
	* @param recurse  是否递归搜索子目录
	* @return List<String> 返回 存在
	* @author sunshine
	* @date 2021年10月15日
	*/
    public static List<String>  searchClassByDir(String className,String dir, boolean recurse) {  
    	List<String> jarFiles= new ArrayList<>();
            File d = new File(dir);  
            if (!d.isDirectory()) {  
                return jarFiles;  
            }  
            File[] files = d.listFiles();  
            try {
            for (int i = 0; i < files.length; i++) {  
                if (recurse && files[i].isDirectory()) {  
                	jarFiles.addAll(searchClassByDir(className,files[i].getAbsolutePath(), true));  
                } else {  
                    String filename = files[i].getAbsolutePath();  
                    if (filename.endsWith(".jar")) {
                        ZipFile zip = new ZipFile(filename);
                        Enumeration<? extends ZipEntry> entries = zip.entries();  
                        while (entries.hasMoreElements()) {
                            ZipEntry entry = entries.nextElement();  
                            String thisClassName = entry.getName().replace('/','.');  
                            if (thisClassName.equals(className + ".class")) {  
                                jarFiles.add(filename);  
                            }  
                        } 
                        zip.close();
                    }  
                }  
            } 
			} catch (IOException e) {
				e.printStackTrace();
			}  
        return jarFiles;
    }  
   
    public static void main(String args[]) {
        //jar包所在的位置  
        //List<String> jarFiles = FindInJar.searchClassByDir("com.aisino.platform.view.servlet.SubmitServlet","E:\\soft\\A6", true);  
        List<String> jarFiles = FindInJar.searchClassByDir("com.aisino.platform.veng.servlet.SubmitServlet","E:\\soft\\A6", true);  
        if (jarFiles.size() == 0) {  
            System.out.println("Not Found");  
        } else {  
            for (int i = 0; i < jarFiles.size(); i++) {  
                System.out.println(jarFiles.get(i));  
            }  
        }  
    }  
  
}
