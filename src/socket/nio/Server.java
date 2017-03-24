package socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;

public class Server implements Runnable {
     private Selector seletor =null;
     private ServerSocketChannel acceptorSvr=null;
     private volatile boolean stop;
	 private int port=9999;
	 
     
	public Server(int port) {
		super();
		if(port>0){
			this.port = port;	
		}
		try {
			seletor =Selector.open();
			acceptorSvr = ServerSocketChannel.open();
			acceptorSvr.configureBlocking(false);
			acceptorSvr.bind(new InetSocketAddress(this.port),2048);
			acceptorSvr.register(seletor, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}

	private void dowirte(SocketChannel sc,String msg) throws IOException{
		if(msg!=null && msg.trim().length()>0){
			byte[] bytes =msg.getBytes("utf-8");
			ByteBuffer bb =ByteBuffer.allocate(bytes.length);
			bb.put(bytes);
			bb.flip();
			sc.write(bb);
		}
	}
	
    protected void stop(){
    	this.stop=true;
    }
    
    private void handInput(SelectionKey key) throws IOException{
    	if(key.isValid()){
    		if(key.isAcceptable()){
    			ServerSocketChannel ssc =  (ServerSocketChannel)key.channel();
    			SocketChannel sc= ssc.accept();
    			if(sc!=null){
        			sc.configureBlocking(false);
        			sc.register(seletor,SelectionKey.OP_READ);
    			}
    		}else if(key.isReadable()){
    			SocketChannel sc = (SocketChannel)key.channel();
    			ByteBuffer bbf= ByteBuffer.allocate(512);
    			 int rt = sc.read(bbf);
    			if(rt>0){
    				bbf.flip();
    				byte[] bt=new byte[bbf.remaining()];
    				bbf.get(bt);
    				System.out.println(new String(bt, "utf-8"));
    				dowirte(sc, "鲜虎:"+new Date().toString());
    			}else if(rt==0){
    				System.out.println("未读取到数据....");
    			}else if(rt ==-1){
    				System.out.println("客户端断开....");
    				sc.close();
    			}else{
    				System.out.println("其他数据");
    			}
    		}else{
    			System.out.println("其他不处理...");
    		}
    	}else{
    		System.out.println("无效的...");
    	}
    }
	@Override
	public void run() {
		while(!stop){
		  try{
			int num = seletor.select();
			System.out.println("获取到: "+num+" 个就绪数据");
			Iterator<SelectionKey> keys =  seletor.selectedKeys().iterator();//注:必须使用迭代,不能使用for循环
			while(keys.hasNext()){
	        	SelectionKey key = keys.next();
	        	keys.remove();//此处一定要保留
	        	try{
	        	handInput(key);
	        	}catch(IOException e){
	        		if(key!=null){
	        			key.cancel();
	        		}
	        		if(key.channel()!=null){
	        			key.channel().close();
	        		}
	        	}
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
		
		}
		if(seletor!=null ){
			try {
				seletor.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}