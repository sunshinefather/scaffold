package socket.voovan;

import org.voovan.network.aio.AioSocket;
import org.voovan.network.filter.StringFilter;
import org.voovan.network.messagesplitter.LineMessageSplitter;

public class AioSocketTest {
	public static void main(String[] args) throws Exception {
        AioSocket socket = new AioSocket("127.0.0.1",2031,-1);
        socket.handler(new ClientHandlerTest());
        socket.filterChain().add(new StringFilter());
        socket.messageSplitter(new LineMessageSplitter());
        socket.start();
    }
}
