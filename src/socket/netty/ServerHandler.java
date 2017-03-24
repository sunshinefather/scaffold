package socket.netty;

import java.util.Date;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf bug =(ByteBuf)msg;
		byte[] req= new byte[bug.readableBytes()];
		bug.readBytes(req);
		String body =  new String(req, "UTF-8");
		System.out.println("收到指令:"+body);
		ByteBuf resp = Unpooled.copiedBuffer(new Date().toString().getBytes("UTF-8"));
		ctx.write(resp);
		//输入q断开客户端
		if("q".equalsIgnoreCase(body)){
			ctx.disconnect();
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	

}
