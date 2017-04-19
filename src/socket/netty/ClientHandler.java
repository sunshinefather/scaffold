package socket.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		byte[] req ="获取时间".getBytes("UTF-8");
        ctx.writeAndFlush(Unpooled.buffer(req.length).writeBytes(req));
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf bug =(ByteBuf)msg;
		byte[] req= new byte[bug.readableBytes()];
		bug.readBytes(req);
		String body =  new String(req, "UTF-8");
		System.out.println("得到数据:"+body);
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       ctx.close();
	}
	
}