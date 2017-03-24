package socket.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.SocketChannel;

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
                 ch.pipeline().addLast(new ClientHandler());			
			}
		});
	   ChannelFuture f= b.connect(host, port).sync();
	   f.channel().closeFuture().sync();
	   }finally{
		   group.shutdownGracefully();
	   }
	}
}
