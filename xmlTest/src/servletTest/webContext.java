package servletTest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理数据  映射到正确的类
 * @author Hongliang Zhu
 * @create 2020-03-10 12:51
 */
public class webContext {
    private List<Entity> entities;
    private List<Mapping> mappings;
    // key --> servlet-name   value ——> servlet-class
    Map<String, String> EntityMap = new HashMap<>();
    // key --> url-patterns   value ——> ervlet-name
    Map<String, String> MapingMap = new HashMap<>();

    public webContext(List<Entity> entities, List<Mapping> mappings) {
        this.entities = entities;
        this.mappings = mappings;

        // 将entityList 映射成map
        for (Entity entity: entities){
            EntityMap.put(entity.getName(), entity.getClz());
        }
        // mappings 映射成map
        for (Mapping mapping: mappings){
           for (String pattern: mapping.getPatterns()){
               MapingMap.put(pattern, mapping.getName());
           }
        }


    }

    /**
     * 将指定的url映射成对应的class Name
     * @param pattern
     * @return
     */
    public String getClZName(String pattern){
        String name = MapingMap.get(pattern);
        return EntityMap.get(name);
    }




}
