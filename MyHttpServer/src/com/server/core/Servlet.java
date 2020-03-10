package src.com.server.core;

/**
 *
 * //   服务器小脚本接口
 * @author Hongliang Zhu
 * @create 2020-03-10 17:15
 */
public interface Servlet {
    void service(Request request, Response response);
//    void doGet(Request request, Response response);
//    void doPost(Request request, Response response);
}
