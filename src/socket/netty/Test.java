package socket.netty;



public class Test {
 public static void main(String[] args) {
	int port =9999;
	try {
		if(args!=null && args.length>0){
			port =Integer.parseInt(args[0]);
		}
        new Client().connect(port, "127.0.0.1");
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}
}
