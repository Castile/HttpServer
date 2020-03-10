package src.server.user;

import src.com.server.core.Request;
import src.com.server.core.Response;
import src.com.server.core.Servlet;

/**
 * @author Hongliang Zhu
 * @create 2020-03-10 13:22
 */
public class RegisterServlet implements Servlet{
    @Override
    public void service(Request request, Response response) {
//        System.out.println("注册 RegisterServlet");
        // 关注业务逻辑
        String uname = request.getParameter("uname");
        String[] fav = request.getParameterValues("fav");

        response.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");

        response.print("<html>");
        response.print("<head>");
        response.print("<title>");
        response.print("注册成功");
        response.print("</title>");
        response.print("</head>");
        response.print("<body>");
        response.println("你注册的信息为：");
        response.println(uname);
        response.println("你喜欢的类型为：");
        for (String v: fav){
            if(v.equals("0") ){
                response.print("萝莉型");
            }
            if(v.equals("1") ){
                response.print("豪放型");
            }
            if(v.equals("2") ){
                response.print("御女型");
            }
            if(v.equals("3") ){
                response.print("可爱型");
            }
        }
        response.print("</body>");

    }
}
