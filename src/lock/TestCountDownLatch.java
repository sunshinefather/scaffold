package lock;

import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch extends Thread {

	 private static CountDownLatch latch = new CountDownLatch(10);  
	 
	     public static void main(String[] args) {  
	         long start = System.currentTimeMillis();  
	         int i = 0;  
	         while (i <10000) {  
	             i++;  
	             new TestCountDownLatch().start();  
	         }  
	         try {  
	             latch.await();  //当计数减至零时后面的代码才会被执行
	         } catch (InterruptedException e) {  
	             e.printStackTrace();  
	         }  
	         System.out.println("总耗时=============:" + (System.currentTimeMillis() - start));  
	     }  
	   
	     public void  run() {  
	         System.out.println(Thread.currentThread().getName()+"...");  
	         try {  
	             Thread.sleep(1000);  
	         } catch (InterruptedException e) {  
	             e.printStackTrace();  
	         }  
	        latch.countDown();
	     }  
}