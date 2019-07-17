package socket.netty;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Client{
	
	public void connect(int port,String host) throws Exception{
	   EventLoopGroup group =new NioEventLoopGroup();
	   try{
	   Bootstrap b =new Bootstrap();
	   b.group(group)
	    .channel(NioSocketChannel.class)
	   .option(ChannelOption.TCP_NODELAY, true)
	    .handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {	
				ch.pipeline().addLast(new DelimiterBasedFrameDecoder(8192,Delimiters.lineDelimiter()));
				ch.pipeline().addLast(new CombinedChannelDuplexHandler<StringDecoder, StringEncoder>(new StringDecoder(Charset.forName("GBK")),new StringEncoder(Charset.forName("GBK"))));
                ch.pipeline().addLast(new ClientHandler());
		}
		});
	   Channel cc = b.connect(host, port).sync().channel();
	   BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	   while(true){
		   cc.writeAndFlush(in.readLine() + "\r\n");
	   }
	   } catch (Exception e) {
		  System.out.println(e);
	   }finally{
		   group.shutdownGracefully();
	   }
	}
}
