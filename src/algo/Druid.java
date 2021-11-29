package algo;
/**
 * TODO
 * @author sunshine
 * @date 2021年10月18日
 */
public class Druid {
public static void main(String[] args) {
	/** mysql解密
	String publickey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJTL3PtAHnTefotmyCpDBpm8RVOd6p3UBQ4dFEgE2t8e1jYeqGrlExbo9kVJKiq8oKgL31GRzITefJJEYpOFrzsCAwEAAQ==";
	String password = "blcMmsu29bSVzTeddsGAtMkxWbVd5ij2sX5j733UNsGz5TLMeowMu6VYktIyXDpd6eHGm/ZwtbM2bXCtz8NlQA==";  
	System.out.println(ConfigTools.decrypt(publickey, password) );
	**/
	
	/** mysql加密
	String [] keyPair = ConfigTools.genKeyPair(512);
	//私钥
	String privateKey = keyPair[0];
	//公钥
	String publicKey = keyPair[1];
	//用私钥加密
	String password = ConfigTools.encrypt(privateKey,"fangluCN");

	System.out.println("privateKey:"+privateKey);
	System.out.println("publicKey:"+publicKey);
	System.out.println("password:"+password);	
	*/
	
	/**用户名加密
	StandardPBEStringEncryptor aa= new StandardPBEStringEncryptor();
	PooledPBEStringEncryptor bb = new PooledPBEStringEncryptor();
	bb.setPassword("123456");
	bb.setPoolSize(1);
	System.out.println(bb.decrypt("Jt7HgOrl2Gi/JgQ0RPnZ7zq26YzUpx7/"));
	aa.setPassword("123456");
	System.out.println(aa.encrypt("root"));
	System.out.println(aa.decrypt("Jt7HgOrl2Gi/JgQ0RPnZ7zq26YzUpx7/"));
	*/
	
	/** 排序算法
	int a [] = new int[]{18,62,68,82,65,9};
	for (int j = 0; j < a.length; j++) {
        for (int i = 0; i < a.length-j-1; i++) {
            if(a[i]<a[i+1]){   
                int temp = a[i];
                a[i] = a[i+1];
                a[i+1] = temp;
            }
        }
    }
     
    //把内容打印出来
    for (int i = 0; i < a.length; i++) {
        System.out.print(a[i] + " ");
    }
    System.out.println(" ");    
    */
}
}
