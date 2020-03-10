package src.com.server.core;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

/**
 * @author Hongliang Zhu
 * @create 2020-03-10 15:24
 */
public class Response {
    BufferedWriter bw = null;
    // 正文
    private StringBuilder content;
    //  协议头信息
    private StringBuilder headInfo;
    private int len ; // 正文的字节数

    private final String BLANK= " ";
    private final String CRLF = "\r\n"; // 换行符

    private Response() {
        content = new StringBuilder();
        headInfo = new StringBuilder();
        len = 0;
    }
    public Response(Socket client) {
        this();
        try {
            bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

        } catch (IOException e) {
            e.printStackTrace();
            headInfo = null;
        }
    }
    public Response(OutputStream os) {
        this();
        bw = new BufferedWriter(new OutputStreamWriter(os));

    }

    // 动态添加内容
    public Response print(String info){
        content.append(info);
        len += info.getBytes().length;

        return this;
    }
    public Response println(String info){
        content.append(info).append(CRLF);
        len += (info+CRLF).getBytes().length;
        return this;
    }

    // 推送响应信息
    public void pushToBrowser(int code) throws IOException {
        if(null == headInfo){
            code = 505;
        }
        createHeadInfo(code);
        bw.append(headInfo);
        bw.append(content);
        bw.flush();

    }




    // 构建头信息
    private void createHeadInfo(int code){

        // 1. 响应行：HTTP/1.1 200 OK
        headInfo.append("HTTP/1.1").append(BLANK);
        headInfo.append(code).append(BLANK);
        switch (code){
            case 100:
                headInfo.append("OK").append(CRLF);
            case 404:
                headInfo.append("Not Found".toUpperCase()).append(CRLF);
            case 505:
                headInfo.append("server error".toUpperCase()).append(CRLF);
        }
        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Server:").append("Server/0.0.1;charset=utf-8").append(CRLF);
        headInfo.append("Content-type:").append("text/html").append(CRLF);
        headInfo.append("Content-length:").append(len).append(CRLF);
        headInfo.append(CRLF); // 空行

    }
}
