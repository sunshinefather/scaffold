package socket.netty.ws;

public class StartServer {
public static void main(String[] args) {
	int port =8080;
	try {
		if(args!=null && args.length>0){
			port =Integer.parseInt(args[0]);
		}
        new Server().bind(port);
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}
}
