package src.com.server.core;
import src.server.user.LoginServlet;
import src.server.user.RegisterServlet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * 高度封装 测试小脚本Servlet
 *  解耦了业务
 *
 * @author Hongliang Zhu
 * @create 2020-03-10 15:23
 */
public class ServerTest {
    private ServerSocket serverSocket;

    public static void main(String[] args) {
        ServerTest server3 = new ServerTest();
        server3.start();
    }

    // 启动服务
    public void start() {
        try {
            serverSocket = new ServerSocket(8888);
            recieve();
        } catch (IOException e) {
            System.out.println("服务启动失败........");
        }

    }

    // 接受连接处理
    public void recieve() {
        try {

            Socket client = serverSocket.accept();
            System.out.println("一个客户端建立了连接...");
            // 封装请求
            Request request = new Request(client);
            // 封装响应
            Response response = new Response(client);
            Servlet servlet = null;
            if(request.getUri().equals("login")) {
                servlet = new LoginServlet();
            }
            else if(request.getUri().equals("reg")) {
                servlet = new RegisterServlet();
            }else{
                // 首页
            }
            servlet.service(request, response);
            // 关注了状态码
            response.pushToBrowser(200);

        } catch (IOException e) {
            System.out.println("客户端错误！！");
        }

    }

    // 停止服务
    public void stop() {

    }
}