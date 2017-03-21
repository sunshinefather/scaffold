package socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import org.apache.commons.lang.StringUtils;

public class Client implements Runnable {
	
    private Selector seletor =null;
    private SocketChannel socketChannel=null;
    private volatile boolean stop;
	private int port=9999;
	private String host="127.0.0.1";
	
	
	public Client(int port, String host) {
		super();
		this.host = host;
		if(StringUtils.isNotBlank(host)){
			this.port = port;	
		}
		
		if(port>0){
			this.port = port;	
		}
		try {
			seletor =Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);	
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

    protected void stop(){
    	this.stop=true;
    }
    
    private void doConnect() throws IOException{
    	System.out.println("是否连接的:"+socketChannel.isConnected());
    	if(socketChannel.connect(new InetSocketAddress(host,port))){
    		System.out.println("if是否连接的:"+socketChannel.isConnected());
    		socketChannel.register(seletor,SelectionKey.OP_READ);
    		dowirte(socketChannel,"获取当前时间:");
    	}else{
    		socketChannel.register(seletor,SelectionKey.OP_CONNECT);
    	}
    }
    
    private void handInput(SelectionKey key) throws IOException{
    	if(key.isValid()){
    		SocketChannel sc =  (SocketChannel)key.channel();
    		if(key.isConnectable()){
    			if(sc.finishConnect()){
    				sc.register(seletor, SelectionKey.OP_READ);
    				dowirte(sc,"给我时间");
    			}else{
    				System.exit(1);
    			}
    		}
    		if(key.isReadable()){
    			
    		}
    	}else{
    		System.out.println("无效的...");
    	}
    }
    
	private void dowirte(SocketChannel sc,String msg) throws IOException{
		if(msg!=null && msg.trim().length()>0){
			byte[] bytes =msg.getBytes("gb2312");
			ByteBuffer bb =ByteBuffer.allocate(bytes.length);
			bb.put(bytes);
			bb.flip();
			sc.write(bb);
		}
	}
	@Override
	public void run() {
       try{
    	   doConnect();
           while(!stop){
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
           }
   		if(seletor!=null ){
			try {
				seletor.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
       }catch(IOException e){
    	   e.printStackTrace();
    	   System.exit(1);
       }
	}

}
