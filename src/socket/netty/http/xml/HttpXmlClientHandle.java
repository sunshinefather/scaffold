package socket.netty.http.xml;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HttpXmlClientHandle extends SimpleChannelInboundHandler<HttpXmlResponse> {  
	  
    @Override  
    public void channelActive(ChannelHandlerContext ctx) {  
        // 给客户端发送请求消息，HttpXmlRequest包含FullHttpRequest和Order这个了类  
        HttpXmlRequest request = new HttpXmlRequest(null, OrderFactory.create(123));  
        ctx.writeAndFlush(request);  
    }  
  
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {  
        cause.printStackTrace();  
        ctx.close();  
    }  
  
    @Override  
    protected void channelRead0(ChannelHandlerContext ctx, HttpXmlResponse msg) throws Exception {  
        System.out.println("接收到的响应头: " + msg.getHttpResponse().headers().entries());  
        System.out.println("接收到的响应内容 : " + msg.getResult());  
    }
}