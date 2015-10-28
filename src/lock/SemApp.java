package lock;

import java.util.concurrent.Semaphore;

public class SemApp
{
    public static void main(String[] args)
    {
        Runnable limitedCall = new Runnable() {
            final Semaphore available = new Semaphore(2);//锁的延伸··,同一时刻可以两个线程访问资源
            int count = 0;
            public void run()
            {        
                try
                {
                    available.acquire();//获取资源
                    int num = count++;   
                    System.out.println("Executing long-running action for #" + num);
                    available.release();//释放资源
                }
                catch (InterruptedException intEx)
                {
                    intEx.printStackTrace();
                }
            }
        };
        
        for (int i=0; i<10000; i++)
           new Thread(limitedCall).start();
    }
}