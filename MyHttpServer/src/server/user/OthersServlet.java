package src.server.user;

import src.com.server.core.Request;
import src.com.server.core.Response;
import src.com.server.core.Servlet;

/**
 *  其他页面
 * @author Hongliang Zhu
 * @create 2020-03-10 17:45
 */
public class OthersServlet implements Servlet{

    @Override
    public void service(Request request, Response response) {
        response.print("其他测试页面");
    }
}
