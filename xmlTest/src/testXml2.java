import com.sun.xml.internal.ws.util.QNameMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hongliang Zhu
 * @create 2020-03-10 10:57
 */
public class testXml2 {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        // 获取解析工厂
        //SAX解析
        //1、获取解析工厂
        SAXParserFactory factory=SAXParserFactory.newInstance();
        //2、从解析工厂获取解析器
        SAXParser parse =factory.newSAXParser();
        //3、编写处理器
        //4、加载文档 Document 注册处理器
        PersonHandler handler=new PersonHandler();
        //5、解析
        parse.parse(Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("p.xml")
                ,handler);

        //获取数据
        List<Person> persons = handler.getPersons();
        for (Person p : persons){
            System.out.println(p.toString());
        }



    }

}

class PersonHandler extends DefaultHandler {

    private List<Person> persons;
    private Person person;
    private String tag;


    @Override
    public void startDocument() throws SAXException {
        persons = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(null != qName){
            tag = qName;
        }
        if(tag.equals("person")){
            person = new Person();
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String contents = new String(ch, start, length).trim();
        if(contents.length() > 0){
            if(tag.equals("name")){
                person.setName(contents);
            }
            if(tag.equals("age")){
                person.setAge(Integer.valueOf(contents));
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals("person")){
            persons.add(person);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        person.toString();

    }

    public List<Person> getPersons() {
        return persons;
    }
}
