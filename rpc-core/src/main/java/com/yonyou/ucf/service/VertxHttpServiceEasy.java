package com.yonyou.ucf.service;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/7 下午1:39
 * @description HTTP服务器启动-初始版
 */
public class VertxHttpServiceEasy implements HttpService {
    public void start(int port) {
        Vertx vertx=Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        server.requestHandler(req->{
           //处理HTTP 请求
            System.out.println("Received request:"+req.method()+" "+req.uri());
           //发送HTTP响应
            req.response().putHeader("content-type","text/plain")
                    .end("Hello from Vert.x HTTP server");
        });
        //启动HTTP服务器并监听指定端口
        server.listen(port,reslut->{
        if (reslut.succeeded()) {
            System.out.println("Vert.x HTTP server started on port "+port);
        } else {
            System.out.println("Vert.x HTTP server failed to start on port "+port);
        }
        });
    }
}
