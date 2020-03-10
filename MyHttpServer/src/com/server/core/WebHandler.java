package src.com.server.core;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理器
 */
public class WebHandler extends DefaultHandler {

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
