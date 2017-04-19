package socket.netty.ws;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

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
					ch.pipeline().addLast(new HttpServerCodec());
					ch.pipeline().addLast(new HttpObjectAggregator(64*1024));
					ch.pipeline().addLast(new ChunkedWriteHandler());
					ch.pipeline().addLast(new HttpRequestHandler("/ws"));
					ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws"));
					ch.pipeline().addLast(new TextWebSocketFrameHandler());
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
