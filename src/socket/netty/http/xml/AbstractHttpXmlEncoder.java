package socket.netty.http.xml;

import java.io.StringWriter;
import java.nio.charset.Charset;
import javax.xml.bind.JAXBContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public abstract class AbstractHttpXmlEncoder<T> extends MessageToMessageEncoder<T> {  
    final static String CHARSET_NAME = "UTF-8";  
    final static Charset UTF_8 = Charset.forName(CHARSET_NAME);
    private StringWriter writer;
    protected ByteBuf encode0(ChannelHandlerContext ctx, Object body) throws Exception {
    	JAXBContext jbt = JAXBContext.newInstance(body.getClass());
    	writer = new StringWriter();
    	jbt.createMarshaller().marshal(body,writer);
        ByteBuf encodeBuf = Unpooled.copiedBuffer(writer.toString(), UTF_8);
        writer.close();
        return encodeBuf;
    }  
  
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
    	if(writer != null){
    		writer.close();
    		writer = null;
    	}
       ctx.fireExceptionCaught(cause);
    }  
  
}