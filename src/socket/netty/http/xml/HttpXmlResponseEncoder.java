package socket.netty.http.xml;

import java.util.List;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class HttpXmlResponseEncoder extends AbstractHttpXmlEncoder<HttpXmlResponse> {
	  
    protected void encode(ChannelHandlerContext ctx, HttpXmlResponse msg, List<Object> out) throws Exception {  
        ByteBuf body = encode0(ctx, msg.getResult());  
        FullHttpResponse response = msg.getHttpResponse();  
        if (response == null) {  
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body);  
        } else {  
            response = new DefaultFullHttpResponse(msg.getHttpResponse().protocolVersion(),msg.getHttpResponse().status(), body);  
        }  
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/xml");  
        response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, body.readableBytes());  
        response.headers().set(HttpHeaderNames.SERVER,"radar-web-container/0.1.0");  
        out.add(response);  
    }  
} 