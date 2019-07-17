package lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyRunnable implements Runnable {   
		
		    private static AtomicLong aLong = new AtomicLong(10000); //原子操作类
	        private String name; // 操作人   
		    private int x; // 操作数额   
		    private Lock lock;   
			MyRunnable(String name, int x, Lock lock) {   
		              this.name = name;   
		              this.x = x;   
		              this.lock = lock;   
		       }   
		
		      public void run() { 
		    	  lock.lock();
		             aLong.addAndGet(1);   
		             System.out.println(name + "执行了" + x + "，当前余额：" + aLong.addAndGet(x)); 
		          lock.unlock();
		       }   
  public static void main(String[] args) {
	  ExecutorService pool = Executors.newFixedThreadPool(10);
	  Lock lock = new ReentrantLock(false);
	  Runnable t1 = new MyRunnable("张三", 2000, lock);
	  Runnable t2 = new MyRunnable("李四", 3600, lock); 
	  Runnable t3 = new MyRunnable("王五", 2700, lock); 
	  Runnable t4 = new MyRunnable("老张", 600, lock);   
	  Runnable t5 = new MyRunnable("老牛", 1300, lock); 
	  Runnable t6 = new MyRunnable("胖子", 800, lock);
	  pool.execute(t1);
	  pool.execute(t2);
	  pool.execute(t3);
	  pool.execute(t4);
	  pool.execute(t5);
	  pool.execute(t6);
	  pool.shutdown();
}
}