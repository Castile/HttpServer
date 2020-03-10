package servletTest;

/**
 *     <servlet>
 *         <servlet-name>login</servlet-name>
 *         <servlet-class>com.sxt.server.basic.servlet.LoginServlet</servlet-class>
 *     </servlet>
 *
 *
 * @author Hongliang Zhu
 * @create 2020-03-10 11:46
 */
public class Entity {
    private String name;
    private String clz;

    public Entity() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }

    public String getName() {
        return name;
    }

    public String getClz() {
        return clz;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", clz='" + clz + '\'' +
                '}';
    }
}
