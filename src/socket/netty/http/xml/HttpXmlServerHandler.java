package socket.netty.http.xml;

import java.util.ArrayList;
import java.util.List;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class HttpXmlServerHandler extends SimpleChannelInboundHandler<HttpXmlRequest> {  
	  
	@Override
    public void channelRead0(final ChannelHandlerContext ctx, HttpXmlRequest xmlRequest) throws Exception {  
        HttpRequest request = xmlRequest.getRequest();  
        Order order = (Order) xmlRequest.getBody();  
        // 输出解码获得的Order对象  
        System.out.println("Http server receive request : " + order);  
        dobusiness(order);  
        System.out.println(order);  
        ChannelFuture future = ctx.writeAndFlush(new HttpXmlResponse(null, order));  
        if (HttpUtil.isKeepAlive(request)) {  
            future.addListener(new GenericFutureListener<Future<? super Void>>() {  
                public void operationComplete(Future future) throws Exception {  
                    ctx.close();  
                }  
            });  
        }  
    }  
  
    private void dobusiness(Order order) {  
        order.setBillTo("河南道洛阳大唐");  
        order.setShipTo("中国南京市江苏省龙眠大道");  
    }  
  
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
        cause.printStackTrace();  
        // 在链路没有关闭并且出现异常的时候发送给客户端错误信息  
        if (ctx.channel().isActive()) {  
            sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);  
        }  
    }  
  
    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {  
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,  
                Unpooled.copiedBuffer("失败: " + status.toString() + "\r\n", CharsetUtil.UTF_8));  
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");  
        response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes()); 
        response.headers().set(HttpHeaderNames.SERVER,"radar-web-container/0.1.0");  
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);  
    }
}