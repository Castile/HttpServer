package src.server.user;

import src.com.server.core.Request;
import src.com.server.core.Response;
import src.com.server.core.Servlet;

/**
 * @author Hongliang Zhu
 * @create 2020-03-10 13:20
 */
public class LoginServlet implements Servlet{
    @Override
    public void service(Request request, Response response) {
        response.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");

        response.print("<html>");
        response.print("<head>");
        response.print("<title>");
        response.print("第一个小脚本Servlet");
        response.print("</title>");
        response.print("</head>");
        response.print("<body>");
        response.print("欢迎回来！"+request.getParameter("uname"));
        response.print("</body>");
        response.print("</html>");
//        System.out.println("登录 LoginServlet");
    }
}
