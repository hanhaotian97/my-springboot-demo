package demo;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ClientProxyHttpClientHttp {
    // 代理服务器
    final static String proxyHost = "180.118.242.121";
    final static Integer proxyPort = 35191;

    private static HttpHost proxy = null;

    private static RequestConfig reqConfig = null;

    static {
        proxy = new HttpHost(proxyHost, proxyPort, "http");
        reqConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(10000) // 设置连接超时时间
                .setSocketTimeout(10000) // 设置读取超时时间
                .setExpectContinueEnabled(false).setProxy(new HttpHost(proxyHost, proxyPort)).setCircularRedirectsAllowed(true) // 允许多次重定向
                .build();
    }

    public static void main(String[] args) {
        doGetRequest();
        //doPostRequest();
        //getForHuTool();
    }

    private static void getForHuTool() {
        HttpRequest req = HttpRequest.get(targetUrl).setHttpProxy(proxyHost, proxyPort).timeout(5000).setFollowRedirects(true);
        HttpResponse resp = req.execute();
        String body = resp.body();
        System.out.println(body);
    }

    static String targetUrl = "https://api.codeleven.cn/nft-live/server/market-api/detail?type=MINUTE_5&platformId=20&albumId=2296&pageNum=0";

    public static void doGetRequest() {
        // 目标地址

        try {
            // 设置url参数 (可选)
            Map<String, String> urlParams = new HashMap<>();
            urlParams.put("uid", "1234567");
            String paramStr = EntityUtils.toString(new UrlEncodedFormEntity(paramsAdapter(urlParams), "UTF-8"));
            //HttpGet httpGet = new HttpGet(targetUrl + "?" + paramStr);
            HttpGet httpGet = new HttpGet(targetUrl);
            String result = doRequest(httpGet);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void doPostRequest() {
        try {
            // 要访问的目标页面
            HttpPost httpPost = new HttpPost("http://httpbin.org/post");

            // 设置表单参数
            Map<String, String> formParams = new HashMap<>();
            formParams.put("uid", "1234567");
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(paramsAdapter(formParams), "utf-8");
            httpPost.setEntity(uefEntity);
            String result = doRequest(httpPost);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置请求头
     * @param httpReq
     */
    private static void setHeaders(HttpRequestBase httpReq) {
        httpReq.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36");
        httpReq.addHeader("xxx", "xxx");
    }

    /**
     * 执行请求
     * @param httpReq
     * @return
     */
    public static String doRequest(HttpRequestBase httpReq) {
        String result = new String();
        httpReq.setConfig(reqConfig);
        try {
            // 设置请求头
            setHeaders(httpReq);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 执行请求
            CloseableHttpResponse httpResp = httpClient.execute(httpReq);

            // 保存Cookie

            // 获取http code
            int statusCode = httpResp.getStatusLine().getStatusCode();
            System.out.println(statusCode);

            HttpEntity entity = httpResp.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }

            httpResp.close();
            httpClient.close();
            httpReq.abort();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    /**
     * 参数适配器，将系统定义的请求参数转换成HttpClient能够接受的参数类型
     * <p>
     * 系统定义的请求参数
     * @return HttpClient要求的参数类型
     */
    private static List<NameValuePair> paramsAdapter(Map<String, String> map) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        for (Entry<String, String> entry : map.entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return nvps;
    }

}