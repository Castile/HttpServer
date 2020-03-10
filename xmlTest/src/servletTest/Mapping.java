package servletTest;

import java.util.HashSet;
import java.util.Set;

/**
 *
 *     <servlet-mapping>
 *         <servlet-name>login</servlet-name>
 *         <url-pattern>/login</url-pattern>
 *         <url-pattern>/g</url-pattern>
 *     </servlet-mapping>
 *
 *
 * @author Hongliang Zhu
 * @create 2020-03-10 11:48
 */
public class Mapping {


    private String name;
    private Set<String> patterns;


    public Mapping() {
        patterns = new HashSet<>();

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatterns(Set<String> patterns) {
        this.patterns = patterns;
    }

    public String getName() {
        return name;
    }

    public Set<String> getPatterns() {
        return patterns;
    }

    public void addPattern(String pattern){
        this.patterns.add(pattern);
    }

    @Override
    public String toString() {
        return "Mapping{" +
                "name='" + name + '\'' +
                ", patterns=" + patterns +
                '}';
    }

}
