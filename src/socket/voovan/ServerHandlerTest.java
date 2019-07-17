package socket.voovan;

import java.nio.ByteBuffer;
import org.voovan.network.IoHandler;
import org.voovan.network.IoSession;
import org.voovan.network.exception.SocketDisconnectByRemote;
import org.voovan.tools.log.Logger;

public class ServerHandlerTest implements IoHandler {

	@Override
    public Object onConnect(IoSession session) { 
        Logger.simple(session.remoteAddress()+":"+session.remotePort()+" 连接成功....");
        session.setAttribute("ip", session.remoteAddress());
        session.setAttribute("port",session.remotePort());
        session.setAttribute("time",System.currentTimeMillis());
        return null;
    }

    @Override
    public void onDisconnect(IoSession session) {
        Logger.simple(System.currentTimeMillis()-Long.valueOf(session.getAttribute("time").toString())+": "+session.getAttribute("ip")+":"+session.getAttribute("port")+" 断开连接....");
    }

    @Override
    public Object onReceive(IoSession session, Object obj) {
        Logger.simple("服务端收到: "+obj.toString());
        return "===="+obj.toString().trim()+" ===== \r\n";
    }

    @Override
    public void onException(IoSession session, Exception e) {
        if(e instanceof SocketDisconnectByRemote){
            Logger.simple("连接断开--> by client");
        }else {
            Logger.error("服务端异常:",e);
        }
        session.close();
    }

    @Override
    public void onSent(IoSession session, Object obj) {
    	if(obj instanceof ByteBuffer) {
            ByteBuffer sad = (ByteBuffer)obj;
            sad = (ByteBuffer)sad.rewind();
            Logger.simple("服务端发送: "+new String(sad.array()));
		}else{
			Logger.simple("服务端发送: "+ obj);
		}

    }

	@Override
	public void onIdle(IoSession session) {
		Logger.simple(System.currentTimeMillis()-Long.valueOf(session.getAttribute("time").toString())+": "+session.remoteAddress()+":"+session.remotePort()+" 空闲啦....");
		
	}

}
