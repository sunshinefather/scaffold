package socket.netty;



public class Test {
 public static void main(String[] args) {
	int port =9999;
	try {
		if(args!=null && args.length>0){
			port =Integer.parseInt(args[0]);
		}
        new Client().connect(port, "127.0.0.1");
       // Thread.sleep(10*1000);
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}
}
