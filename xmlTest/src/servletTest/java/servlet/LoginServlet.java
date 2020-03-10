package servletTest.java.servlet;

/**
 * @author Hongliang Zhu
 * @create 2020-03-10 13:20
 */
public class LoginServlet implements Servlet{
    @Override
    public void service() {
        System.out.println("登录 LoginServlet");
    }
}
