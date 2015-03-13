package com.assets.utils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
public class Utf8BomRemover{
	    /**
	     * 扫描文件
	     * @Title: DealSrcFiles
	     * @Description: TODO  
	     * @param: @param path      
	     * @return: void
	     * @author: sunshine  
	     * @throws
	     */
	    public static void DealSrcFiles(String path) {  
	        if (path.charAt(path.length() - 1) !='/') {  
	            path += '/';
	        }  
	        File file = new File(path);
	        if (!file.exists()) {  
	            System.out.println("错误:文件路径不存在");
	            return;
	        }  
	        String[] filelist = file.list();
	        for (int i = 0; i < filelist.length; i++) {  
	            File temp = new File(path + filelist[i]);
	            if ((temp.isDirectory() && !temp.isHidden() && temp.exists())) {  
	                DealSrcFiles(path + filelist[i]);
	            } else {  
                    try {  
                        trimBom(path + filelist[i]);
                    } catch (Exception eee) {  
                        System.out.println(eee.getMessage());
                    }  
	            }  
	        }  
	    }  
        /**
         * 读取流中前面的字符，看是否有bom，如果有bom，将bom头先读掉丢弃
         * @Title: getInputStream
         * @Description: TODO  
         * @param: @param in
         * @param: @return
         * @param: @throws IOException      
         * @return: InputStream
         * @author: sunshine  
         * @throws
         */
	    public static InputStream getInputStream(InputStream in) throws IOException {  
	  
	        PushbackInputStream testin = new PushbackInputStream(in);
	        int ch = testin.read();
	        if (ch != 0xEF) {
	            testin.unread(ch);
	        } else if ((ch = testin.read()) != 0xBB) {
	            testin.unread(ch);
	            testin.unread(0xef);
	        } else if ((ch = testin.read()) != 0xBF) {
	            throw new IOException("错误的UTF-8格式文件");
	        }
	        return testin;
	  
	    }  
        /**
         * 去掉bom头
         * @Title: trimBom
         * @Description: TODO  
         * @param: @param fileName
         * @param: @throws IOException      
         * @return: void
         * @author: sunshine  
         * @throws
         */
	    public static void trimBom(String fileName) throws IOException {
	        if (noBomFile(fileName)) {
	            System.out.println("跳过文件:" + fileName);
	            return;
	        }  
	        FileInputStream fin = new FileInputStream(fileName);
	        // 开始写临时文件  
	        InputStream in = getInputStream(fin);
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        byte b[] = new byte[4096];
	        int len = 0;
	        while (in.available() > 0) {  
	            len = in.read(b, 0, 4096);
	            bos.write(b, 0, len);
	        }
	        in.close();
	        fin.close();
	        bos.close();
	        FileOutputStream out = new FileOutputStream(fileName);
	        out.write(bos.toByteArray());
	        out.close();
	        System.out.println("处理过的文件:" + fileName);
	    }  
	  
	    /**
	     * 检查是否包含bom
	     * @Title: noBomFile
	     * @Description: TODO  
	     * @param: @param fname
	     * @param: @return
	     * @param: @throws IOException      
	     * @return: boolean
	     * @author: sunshine  
	     * @throws
	     */
	    private static boolean noBomFile(String  fname) throws IOException {  
	        FileInputStream fin = new FileInputStream(fname);
	        PushbackInputStream testin = new PushbackInputStream(fin);
	        int ch = testin.read();
	        int ch2 = testin.read();
	        int ch3 = testin.read();
	        testin.close();
	        if (ch == 0xEF && ch2 == 0xBB && ch3 == 0xBF) {  
	            return false;
	        } else {
	            return true;
	        }  
	    }
	    public static void main(String[] args) throws IOException {  
	        String path = "F:\\zyt_project\\ImCrm\\src\\";
	        DealSrcFiles(path);
	    }  
	}