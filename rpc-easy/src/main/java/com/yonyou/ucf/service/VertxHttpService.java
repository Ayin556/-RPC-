package com.yonyou.ucf.service;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/7 下午1:39
 * @description HTTP服务器启动-改造版
 */
public class VertxHttpService implements HttpService {
    public void start(int port) {
        Vertx vertx=Vertx.vertx();
        //创建HTTP服务器
        HttpServer server = vertx.createHttpServer();
        //监听端口并处理请求
        server.requestHandler(new HttpServerHandler());
        server.listen(port,result ->{
            if(result.succeeded()){
                System.out.println("Server started on port "+port);
            } else {
                System.out.println("Server failed to start on port "+result.cause());
            }
        });
    }
}
