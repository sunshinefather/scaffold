package socket.nio;


public class Test {
 public static void main(String[] args) {
	int port =9999;
	try {
		if(args!=null && args.length>0){
			port =Integer.parseInt(args[0]);
		}
		Server server = new Server(port);
		Thread thread = new Thread(server,"服务端");
		thread.start();
		//server.stop();
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}
}
