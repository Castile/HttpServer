package src.com.server.core;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author Hongliang Zhu
 * @create 2020-03-10 17:29
 */
public class WebAPP {
    private static  webContext webContext;
    static {
        try {

            // 获取解析工厂
            //SAX解析
            //1、获取解析工厂
            SAXParserFactory factory=SAXParserFactory.newInstance();
            //2、从解析工厂获取解析器
            SAXParser parse =factory.newSAXParser();
            //3、编写处理器
            //4、加载文档 Document 注册处理器
            WebHandler handler=new WebHandler();
            //5、解析
            parse.parse(Thread.currentThread().getContextClassLoader()
                            .getResourceAsStream("web.xml")
                    ,handler);
            webContext = new webContext(handler.getEntities(), handler.getMappings());

        }catch (Exception e){
            System.out.println("解析配置文件错误");
        }
    }

    /**
     * 通过url获取配置文件对应的servlet
     * @param url
     * @return
     */
    public static Servlet getServletFromUrl(String url){

        String className = webContext.getClZName("/"+url);
        System.out.println(className);
        Class clazz = null;
        try {
            clazz = Class.forName(className);
            Servlet servlet = (Servlet) clazz.getConstructor().newInstance();
            return servlet;
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return null;

    }



}
