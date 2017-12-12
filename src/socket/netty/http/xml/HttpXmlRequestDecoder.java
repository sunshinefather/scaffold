package socket.netty.http.xml;

import java.util.List;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class HttpXmlRequestDecoder extends AbstractHttpXmlDecoder<FullHttpRequest> {  
	  
    public HttpXmlRequestDecoder(Class<?> clazz) {  
        this(clazz, false);  
    }  
  
    public HttpXmlRequestDecoder(Class<?> clazz, boolean isPrint) {  
        super(clazz, isPrint);  
    }  
  
    @Override  
    protected void decode(ChannelHandlerContext ctx, FullHttpRequest req, List<Object> out) throws Exception {  
        // 返回客户端错误信息  
        if (!req.decoderResult().isSuccess()) {  
            sendError(ctx,HttpResponseStatus.BAD_REQUEST);  
            return;  
        }  
        try{
        HttpXmlRequest request = new HttpXmlRequest(req, decode0(ctx, req.content()));  
        // 将请求交给下一个解码器处理  
        out.add(request);  
        }catch(Exception e){
        	sendError(ctx,HttpResponseStatus.BAD_REQUEST);
        }
    }  
  
    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {  
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,status,Unpooled.copiedBuffer("Failure: 请求失败\r\n", CharsetUtil.UTF_8));  
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());  
        response.headers().set(HttpHeaderNames.SERVER,"radar-web-container/0.1.0");  
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);  
    }  
}