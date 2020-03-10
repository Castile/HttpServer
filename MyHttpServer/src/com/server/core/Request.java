package src.com.server.core;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.*;

/**
 * 封装请求协议
 * 目标：  获取method、uri以及请求参数
 *
 * @author Hongliang Zhu
 * @create 2020-03-10 15:48
 */
public class Request {
    InputStream is = null;
    byte[] data = new byte[1204 * 1024*1024];
    private String requestInfo; // 协议信息
    private String method; // 请求方式
    private String uri; // 请求的uri
    private String queryStr; // 请求参数
    // 储存参数
    private Map<String, List<String>> parameterMap;

    private final String CRLF = "\r\n"; // 换行符

    public Request(InputStream is) {
        parameterMap = new HashMap<>();
        int len = 0;
        try {
            len = is.read(data);
            this.requestInfo = new String(data, 0, len);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
       // 分解字符串
        parseRequestInfo();
        // 转换成Map   fav=1&fav=2&uname=zhu&age=18
        convertMap();
    }

    public Request(Socket client) throws IOException {
        this(client.getInputStream());
    }

    private void parseRequestInfo(){
        System.out.println(requestInfo);
        System.out.println("======1、获取请求方式 =====");
        this.method = this.requestInfo.substring(0,this.requestInfo.indexOf("/")).trim().toLowerCase();
        System.out.println("======2、 获取url ========");
        int startIdx = this.requestInfo.indexOf("/")+1;
        int endIdx = this.requestInfo.indexOf("HTTP/");
        this.uri = this.requestInfo.substring(startIdx, endIdx).trim();
//        System.out.println(uri);
        // 获取？的位置
        int queryIndex = this.uri.indexOf("?");
        if(queryIndex >= 0){ // 存在请求参数
            String[] urlArray = this.uri.split("\\?");
            this.uri = urlArray[0];
            this.queryStr = urlArray[1];
        }

        System.out.println("=======3、 获取请求参数 ======");
        // 如果get 已经获取， 如果post 可能在请求体中
        if(method.equals("post")){
            String qStr = this.requestInfo.substring(this.requestInfo.lastIndexOf(CRLF)).trim();
            if(null == queryStr){
                queryStr = qStr;
            }else{
                queryStr += "&"+qStr;
            }
        }
        queryStr = ( queryStr == null) ? "": queryStr;
        System.out.println("method:" + method +", uri : "+uri+", queryStr: "+queryStr);
    }

    //处理请求参数为Map
    private void convertMap(){
        // 分割字符串
        String[] keyValues = this.queryStr.split("&");
        for(String query: keyValues){
            String[] kv = query.split("=");
            kv = Arrays.copyOf(kv,2); // 保证长度为2
            // 获取key和value
            String key = kv[0];
            String value = kv[1]==null?null:decode(kv[1],"UTF-8");
            // 存储
            if(!parameterMap.containsKey(key)){
                parameterMap.put(key,  new ArrayList<String>());
            }
            parameterMap.get(key).add(value);

        }

    }

    /**
     * 处理中文
     * @return
     */
    private String decode(String value, String encoding){
        try {
            return java.net.URLDecoder.decode(value, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }



    // 获取value  多个值
    public String[] getParameterValues(String key){
        List<String> values = this.parameterMap.get(key);
        if(null == values || values.size() < 1){
            return null;
        }
        return values.toArray(new String[0]);  // 转换成数组
    }

    // 获取一个值
    public String getParameter(String key){
        String[] values = getParameterValues(key);
        return values == null?null:values[0];
    }


    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getQueryStr() {
        return queryStr;
    }
}
