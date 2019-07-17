package socket.netty.http.xml;

import java.net.InetSocketAddress;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

public class HttpXmlClient {  
	  
    public void connect(int port) throws Exception {  
        EventLoopGroup group = new NioEventLoopGroup();  
        try {  
            Bootstrap b = new Bootstrap();  
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)  
                    .handler(new ChannelInitializer<SocketChannel>() {  
                        @Override  
                        public void initChannel(SocketChannel ch) throws Exception {  
                            // xml解码器  
                            ch.pipeline().addLast("http-decoder", new HttpClientCodec());  
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));  
                            ch.pipeline().addLast("xml-decoder", new HttpXmlResponseDecoder(Order.class, true)); 
                            // xml编码器  
                            ch.pipeline().addLast("xml-encoder", new HttpXmlRequestEncoder());  
                            ch.pipeline().addLast("xmlClientHandler", new HttpXmlClientHandle());  
                        }  
                    });  
            ChannelFuture f = b.connect(new InetSocketAddress(port)).sync();  
            f.channel().closeFuture().sync();  
        } finally {  
            group.shutdownGracefully();  
        }  
    }  
  
    public static void main(String[] args) throws Exception {  
        int port = 8080;  
        if (args != null && args.length > 0) {  
            try {  
                port = Integer.valueOf(args[0]);  
            } catch (NumberFormatException e) {  
            }  
        }  
        new HttpXmlClient().connect(port);  
    }  
}