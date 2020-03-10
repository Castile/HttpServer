package servletTest.java.servlet;

/**
 * @author Hongliang Zhu
 * @create 2020-03-10 13:22
 */
public class RegisterServlet implements Servlet{
    @Override
    public void service() {
        System.out.println("注册 RegisterServlet");
    }
}
