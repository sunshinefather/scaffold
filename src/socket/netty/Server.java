package socket.netty;

import java.nio.charset.Charset;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Server {
	
	public void bind(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup wokerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, wokerGroup)
			 .channel(NioServerSocketChannel.class)
			 .option(ChannelOption.SO_BACKLOG,128)
			 .childOption(ChannelOption.SO_KEEPALIVE,true)
			 .childHandler(new ChannelInitializer<SocketChannel>(){
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new DelimiterBasedFrameDecoder(8192,Delimiters.lineDelimiter()));
					ch.pipeline().addLast(new CombinedChannelDuplexHandler<StringDecoder, StringEncoder>(new StringDecoder(Charset.forName("GBK")),new StringEncoder(Charset.forName("GBK"))));
					ch.pipeline().addLast(new ServerHandler());
				}
				 
			 });

			ChannelFuture f = b.bind(port);
			f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			wokerGroup.shutdownGracefully();
		}
	}
}
