package socket.netty.http.xml;

import java.util.List;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;

public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<FullHttpResponse>{

	protected HttpXmlResponseDecoder(Class<?> clazz, boolean isPrintLog) {
		super(clazz, isPrintLog);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, FullHttpResponse msg, List<Object> out) throws Exception {
		HttpXmlResponse xmlResponse = new HttpXmlResponse(msg, decode0(ctx, msg.content()));
		out.add(xmlResponse);
	}

}