package socket.voovan;

import java.nio.ByteBuffer;
import org.voovan.network.IoHandler;
import org.voovan.network.IoSession;
import org.voovan.tools.log.Logger;

public class ClientHandlerTest implements IoHandler {
	@Override
    public Object onConnect(IoSession session) {
        Logger.simple("连接成功");
        session.setAttribute("key", "attribute value");
        String msg = new String("我连接上来了\r\n");
        return msg;
    }

    @Override
    public void onDisconnect(IoSession session) {
        Logger.simple("断开连接");
    }

    @Override
    public Object onReceive(IoSession session, Object obj) {
        Logger.simple("客户端收到: "+obj.toString());
        Logger.simple("属性: "+session.getAttribute("key"));
        return null;
    }

    @Override
    public void onException(IoSession session, Exception e) {
        Logger.simple("客户端异常");
        Logger.error(e);
        session.close();
    }

    @Override
    public void onSent(IoSession session, Object obj) {
    	if(obj instanceof ByteBuffer) {
    		ByteBuffer sad = (ByteBuffer)obj;
            sad = (ByteBuffer)sad.rewind();
            Logger.simple("客户端发送: "+new String(sad.array()));
		}else{
			Logger.simple("客户端发送: "+ obj);
		}
        
    }

	@Override
	public void onIdle(IoSession session) {
		Logger.simple(session.remoteAddress()+":"+session.remotePort()+" 空闲了....");
		
	}
}
