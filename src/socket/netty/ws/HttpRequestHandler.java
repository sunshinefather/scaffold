package socket.netty.ws;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest>{
    
    private final String homePageUri;
    private static final File homePageHtml;
    static {
    	try {
	    	URL location = HttpRequestHandler.class.getClassLoader().getResource("WebsocketChatClient.html");
	    	String path = location.toURI().toString();
	    	path = !path.contains("file:") ? path : path.substring(5);
	    	homePageHtml = new File(path);
    	} catch (Exception e) {
    	  throw new IllegalStateException("初始化错误...", e);
    	}
    	}
    
    
	public HttpRequestHandler(String homePageUri) {
		this.homePageUri = homePageUri;
	}


	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
	    if(homePageUri.equals(request.uri())){
	    	if(HttpUtil.is100ContinueExpected(request)){
	    		send100Continue(ctx);
	    	}
	    	RandomAccessFile file = new RandomAccessFile(homePageHtml, "r");
	    	HttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
	    	response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
	    	boolean keepAlive = HttpUtil.isKeepAlive(request);
	    	if (keepAlive) {
	    	response.headers().set(HttpHeaderNames.CONTENT_LENGTH, file.length());
	    	response.headers().set(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
	    	}
	    	ctx.write(response);
	    	if (ctx.pipeline().get(SslHandler.class) == null) {
	    	   ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
	    	} else {
	    	   ctx.write(new ChunkedNioFile(file.getChannel()));
	    	}
	    	ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
	    	if (!keepAlive) {
	    	future.addListener(ChannelFutureListener.CLOSE);
	    	}
	    	file.close();
	    }
		
	}

	private static void send100Continue(ChannelHandlerContext ctx) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
		ctx.writeAndFlush(response);
		}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("系统消息:《"+incoming.remoteAddress()+"》:异常断开\r\n");
		ctx.close();
	}
}