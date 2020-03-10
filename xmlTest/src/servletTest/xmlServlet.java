package servletTest;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import servletTest.java.servlet.Servlet;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;

/**
 * @author Hongliang Zhu
 * @create 2020-03-10 11:44
 */
public class xmlServlet {
    public static void main(String[] args) throws Exception {
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

        webContext webContext = new webContext(handler.getEntities(), handler.getMappings());
        String className = webContext.getClZName("/reg");
        System.out.println(className);
        Class clazz = Class.forName(className);
        Servlet servlet = (Servlet) clazz.getConstructor().newInstance();
        servlet.service();


    }
}

class WebHandler extends DefaultHandler {

    private List<Entity> entities = new ArrayList<>();
    private List<Mapping> mappings = new ArrayList<>();
    private Entity entity;
    private Mapping mapping;
    private String tag;
    private boolean isMapPing;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(null != qName){
            tag = qName;
        }
        if(tag.equals("servlet")){ // entity
            entity = new Entity();
            isMapPing = false;
        }
        if(tag.equals("servlet-mapping")){ // mapping
            mapping = new Mapping();
            isMapPing = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String contents = new String(ch, start, length).trim();
        if(contents.length() > 0){
            if(!isMapPing){
                if(tag.equals("servlet-name")){
                    entity.setName(contents);
                }
                if(tag.equals("servlet-class")){
                    entity.setClz(contents);
                }
            }else{
                if(tag.equals("servlet-name")){
                    mapping.setName(contents);
                }
                if(tag.equals("url-pattern")){
                    mapping.addPattern(contents);
                }
            }


        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals("servlet")){
            entities.add(entity);
        }
        if(qName.equals("servlet-mapping")){
            mappings.add(mapping);
        }
    }


    public List<Entity> getEntities() {
        return entities;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }
}
