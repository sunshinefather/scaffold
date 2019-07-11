package socket.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ServerHandler extends SimpleChannelInboundHandler<String>{
	
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("系统消息:《"+incoming.remoteAddress()+"》:异常断开\r\n");
		ctx.close();
	}


	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		Channel incoming = ctx.channel();
		for(Channel channel:channels){
			if(channel!=incoming){
				channel.writeAndFlush("用户:《"+incoming.remoteAddress()+"》 说："+msg+"\r\n");
			}else{
				channel.writeAndFlush("我:"+msg+"\r\n");
			}
		}
		
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		for(Channel channel:channels){
			channel.writeAndFlush("系统消息:《"+incoming.remoteAddress()+"》 加入房间\r\n");
		}
		channels.add(incoming);
	}


	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		for(Channel channel:channels){
			channel.writeAndFlush("系统消息:《"+incoming.remoteAddress()+"》 退出房间\r\n");
		}
		channels.remove(incoming);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		for(Channel channel:channels){
			if(channel!=incoming){
				channel.writeAndFlush("系统消息:《"+incoming.remoteAddress()+"》 上线了\r\n");
			}else{
				channel.writeAndFlush("我上线了\r\n");
			}
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		for(Channel channel:channels){
			if(channel!=incoming){
				channel.writeAndFlush("系统消息:《"+incoming.remoteAddress()+"》 掉线了\r\n");
			}else{
				channel.writeAndFlush("我掉线了\r\n");
			}
		}
	}
}