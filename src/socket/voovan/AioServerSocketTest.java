package socket.voovan;

import java.io.IOException;
import org.voovan.network.aio.AioServerSocket;
import org.voovan.network.filter.StringFilter;
import org.voovan.network.messagesplitter.LineMessageSplitter;

public class AioServerSocketTest {
	public static void main(String[] args) throws IOException {
        AioServerSocket serverSocket = new AioServerSocket("127.0.0.1",2031,10000,3000);
        serverSocket.handler(new ServerHandlerTest());
        serverSocket.filterChain().add(new StringFilter());
        serverSocket.messageSplitter(new LineMessageSplitter());
        serverSocket.start();
    }
}
