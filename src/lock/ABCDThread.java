package lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ABCDThread {
	   private Lock lock = new ReentrantLock();
	   private Condition condition = lock.newCondition();  
	   private int count;  
	 
	   public static void main(String[] args) {  
	       ExecutorService executorService = Executors.newCachedThreadPool();
	       ABCDThread abc = new ABCDThread();  
	       executorService.execute(abc.new Run("A", 1));
	       executorService.execute(abc.new Run("B", 2));
	       executorService.execute(abc.new Run("C", 3)); 
	       executorService.execute(abc.new Run("D", 4)); 
	       executorService.shutdown();  
	   }  
	 
	   class Run implements Runnable {  
	       private String _name = "";  
	       private int _threadNum;  
	       private int ct=0;
	       private boolean flag= true;
	       public Run(String name, int threadNum) {  
	           _name = name;  
	           _threadNum = threadNum;  
	       }  
	 
	       @Override  
	       public void run() {  
 	    	   lock.lock();
	            try{
	               while (flag) {
	                   if (count % 4 == _threadNum - 1) {
	                       System.out.print(_name);  
	                       count++;
	                       
	                       if(++ct>=10){
	                    	   flag=false;    
	                       }
	                       if(count % 4==0){
	                    	   System.out.println(); 
	                       }
		            	   condition.signalAll(); 
	                   } else {  
	                       try {
	                    	   condition.await();
	                       } catch (InterruptedException e) {  
	                           e.printStackTrace();  
	                       }  
	                   }  
	               }  
	           }finally{
	        	   lock.unlock(); 
	           }
	       }
	       }  
	   } 
