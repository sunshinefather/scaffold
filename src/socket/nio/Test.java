package socket.nio;


public class Test {
 public static void main(String[] args) {
	int port =9999;
	try {
		if(args!=null && args.length>0){
			port =Integer.parseInt(args[0]);
		}
		int i=100000;
		while(i-->0){
			Client client =new Client(port,"127.0.0.1");
			Thread clientThread = new Thread(client,"客户端"+i);
			clientThread.start();	
		}

		//server.stop();
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}
}
