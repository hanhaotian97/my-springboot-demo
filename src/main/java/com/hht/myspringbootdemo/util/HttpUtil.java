package com.hht.myspringbootdemo.util;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.TreeMap;

/**
 * HTTP请求工具类
 * @author gql
 *
 */
public class HttpUtil {
	
	//连接超时时间，毫秒
    private final static int CONNECT_TIMEOUT = 600000;
    //字符编码
    private final static String DEFAULT_ENCODING = "UTF-8";

    /**
     * 获取requestParam中的参数
     * @return 参数字符串, 格式为 : 参数:参数值参数:参数值...参数:参数值
     */
    public static String getRequestParam(HttpServletRequest request) throws UnsupportedEncodingException {
        StringBuilder appSignMd5 = new StringBuilder();

        Map<String, String> treeMap = new TreeMap<>();
        Map<String, String[]> map = request.getParameterMap();
        for (String key : map.keySet()) {
            for (String value :  map.get(key)) {
                treeMap.put(key, value);
            }
        }

        for (String key : treeMap.keySet()) {
            String value = URLDecoder.decode(treeMap.get(key), "UTF-8");
            if (!"sign".equals(key)) {
                appSignMd5.append(key).append("=").append(value);
            }
        }
        return appSignMd5.toString();
    }

    /**
     * 获取requestBody中的json串
     * @return  json格式返回
     */
    public static String getBodyString(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(
                    new InputStreamReader(inputStream, Charset.forName("UTF-8")));

            char[] bodyCharBuffer = new char[1024];
            int len = 0;
            while ((len = reader.read(bodyCharBuffer)) != -1) {
                sb.append(new String(bodyCharBuffer, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
	
    /**
     * post请求（流）
     * @param urlStr 请求地址
     * @param data 参数
     * @param contentType 参数类型
     * @return 操作成功：返回响应内容，失败：返回null
     */
    public String sendPostStream(String urlStr, String data, String contentType){  
        BufferedReader reader = null;  
        try {  
            URL url = new URL(urlStr);  
            URLConnection conn = url.openConnection();  
            conn.setDoOutput(true);  
            conn.setConnectTimeout(CONNECT_TIMEOUT);  
            conn.setReadTimeout(CONNECT_TIMEOUT);  
            if(contentType != null)  
                conn.setRequestProperty("content-type", contentType);  
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), DEFAULT_ENCODING);  
            if(data == null)  
                data = "";  
            writer.write(data);   
            writer.flush();  
            writer.close();    
  
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));  
            StringBuilder sb = new StringBuilder();  
            String line = null;  
            while ((line = reader.readLine()) != null) {  
                sb.append(line);
                sb.append("\r\n");
            }
            String str = sb.toString();
            return str;  
        } catch (IOException e) {  
        	LogUtil.printErrorLog(e);
        } finally {  
            try {  
                if (reader != null)  
                    reader.close();  
            } catch (IOException e) { 
            	LogUtil.printErrorLog(e);
            }  
        }  
        return null;  
    }
    
    /**
     * 向指定URL发送GET方法的请求
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public String sendGetStream(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "/" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
            	LogUtil.printErrorLog(e2);
            }
        }
        return null;
    }
    
}
