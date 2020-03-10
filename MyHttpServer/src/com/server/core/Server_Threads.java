package src.com.server.core;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * 高度封装 测试小脚本Servlet
 *  解耦了业务
 *  整合配置文件
 *
 *  多线程处理加入分发器
 *
 *  加上404 、 505的处理 、 首页
 *
 * @author Hongliang Zhu
 * @create 2020-03-10 15:23
 */
public class Server_Threads {
    private ServerSocket serverSocket;
    private boolean isRunning;

    public static void main(String[] args) {
        Server_Threads server = new Server_Threads();
        server.start();
    }

    // 启动服务
    public void start() {
        try {
            serverSocket = new ServerSocket(8888);
            isRunning = true;
            recieve();
        } catch (IOException e) {
            System.out.println("服务启动失败........");
            stop();
        }

    }

    // 接受连接处理
    public void recieve() {

        while (isRunning){
            try {
                Socket client = serverSocket.accept();
                System.out.println("一个客户端建立了连接...");
                new Thread(new Dispatcher(client)).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    // 停止服务
    public void stop(){
        isRunning =  false;
        try {
            this.serverSocket.close();
            System.out.println("服务器已经停止");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}