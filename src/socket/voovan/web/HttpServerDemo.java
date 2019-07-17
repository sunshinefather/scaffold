package socket.voovan.web;

import org.voovan.http.server.WebServer;
import org.voovan.http.server.context.WebServerConfig;

public class HttpServerDemo {
	public static void main(String[] args) {
        //1.构造一个 WebServer 实例.
		WebServerConfig webconfig =new WebServerConfig();
		webconfig.setHost("8001");
		webconfig.setContextPath("/");
        WebServer webServer = WebServer.newInstance(webconfig);

        //2.注册路由.
        webServer.get("/test",(req, resp) -> {
            resp.body().write("OK");
        });

        //3.调用 HttpServer 方法.
        webServer.serve();
       }
}
