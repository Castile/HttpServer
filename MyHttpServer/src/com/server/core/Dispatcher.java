package src.com.server.core;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
/**
 * 分发器  多线程
 * @author Hongliang Zhu
 * @create 2020-03-10 17:56
 */
public class Dispatcher implements  Runnable{

    private Socket client;
    private Response response;
    private Request request;

    public Dispatcher(Socket client) {
        this.client =client;
        try {
            // 获取请求协议
            // 获取响应协议
            request = new Request(client);
            response = new Response(client);
        } catch (IOException ex) {
            ex.printStackTrace();
            this.release();
        }
    }

    /**
     * 释放资源
     */
    private void release() {
        try {
            client.close();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }



    @Override
    public void run() {
        try {
            // 首页
            if(null == request.getUri() || request.getUri().equals("")){
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("index.html");
                if (is != null) {
                    byte[] bytes = is.readAllBytes();
                    response.print(new String(bytes));
                }
                response.pushToBrowser(200);
                is.close();
            }
            Servlet servlet = WebAPP.getServletFromUrl(request.getUri());
            if(null != servlet){
                servlet.service(request, response);
                // 关注了状态码
                response.pushToBrowser(200);

            }else{
                // error..
//                    response.println("你好我不好，我会马上好");
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("error.html");
                if (is != null) {
                    byte[] bytes = is.readAllBytes();
                    response.print(new String(bytes));
                }
                response.pushToBrowser(404);
                is.close();
            }
            } catch (IOException e) {
                try {
                    response.pushToBrowser(505);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

        }
        release();
    }
}
