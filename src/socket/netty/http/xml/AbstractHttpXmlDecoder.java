package socket.netty.http.xml;

import java.io.StringReader;
import java.nio.charset.Charset;
import javax.xml.bind.JAXBContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.internal.StringUtil;

public abstract class AbstractHttpXmlDecoder<T> extends MessageToMessageDecoder<T> {  
	  
    private Class<?> clazz;  
    // 是否输出码流的标志，默认为false  
    private boolean isPrint;  
    private final static String CHARSET_NAME = "UTF-8";  
    private final static Charset UTF_8 = Charset.forName(CHARSET_NAME); 
    private StringReader reader;
  
    // 当调用这个构造方法是，默认设置isPrint为false  
    protected AbstractHttpXmlDecoder(Class<?> clazz) {  
        this(clazz, false);  
    }  
  
    protected AbstractHttpXmlDecoder(Class<?> clazz, boolean isPrint) {  
        this.clazz = clazz;  
        this.isPrint = isPrint;  
    }  
  
    protected Object decode0(ChannelHandlerContext arg0, ByteBuf body) throws Exception {  
        String content = body.toString(UTF_8);  
        if (isPrint){
            System.out.println("请求内容:" + content);
        }
        if(!StringUtil.isNullOrEmpty(body.toString(UTF_8))){
        	JAXBContext jbt = JAXBContext.newInstance(clazz);
        	reader = new StringReader(body.toString(UTF_8));
        	Object obj = jbt.createUnmarshaller().unmarshal(reader);
        	reader.close();
            return obj;
        }else{
        	return clazz.newInstance();
        }

    }  
    
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
    	if(reader!=null){
    		reader.close();
    		reader = null;
    	}
        ctx.fireExceptionCaught(cause);
    }  
}