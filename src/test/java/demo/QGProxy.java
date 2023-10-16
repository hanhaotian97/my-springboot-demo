package demo;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

public class QGProxy {
    static String targetUrl = "https://api.codeleven.cn/nft-live/server/market-api/detail?type=MINUTE_5&platformId=20&albumId=2296&pageNum=0";
    //{"IP":"183.156.78.28","port":"32029","deadline":"2023-03-27 16:29:56"
    static String proxyIp = "183.156.78.28";
    static Integer proxyPort = 32029;
    final static String authKey = "C5F56FA8";
    final static String password = "F64877F563B9";

    public Response request() throws IOException {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp, proxyPort));
        OkHttpClient client = new OkHttpClient.Builder().proxy(proxy).proxyAuthenticator((route, response) -> {
            String credential = Credentials.basic(authKey, password);
            return response.request().newBuilder().header("Proxy-Authorization", credential).build();
        }).build();
        Request request = new Request.Builder().url(targetUrl).get().build();
        return client.newCall(request).execute();
    }

    public static void main(String[] args) {
        HttpRequest req = HttpRequest.get("https://proxy.qg.net/extract?Key=C5F56FA8&Num=1&AreaId=330100&Isp=&DataFormat=json&DataSeparator=&Detail=0&Pool=1").timeout(5000).setFollowRedirects(true);
        JSONObject jsonObject = JSON.parseObject(req.execute().body());
        System.out.println(jsonObject+"\n");

        JSONArray data = jsonObject.getJSONArray("Data");
        Object o = data.get(0);
        JSONObject o1 = (JSONObject) o;
        proxyIp = o1.getString("IP");
        proxyPort = o1.getInteger("port");

        //getReq();
        getForHuTool();
    }

    private static void getReq() {
        QGProxy qgProxy = new QGProxy();
        try {
            Response resp = qgProxy.request();
            System.out.println(resp.body().string());
        } catch (Exception e) {
            System.out.printf("failed to proxy: %s\n", e.getMessage());
        }
    }


    private static void getForHuTool() {
        HttpRequest req = HttpRequest.get(targetUrl)//.basicProxyAuth(authKey, password)
         .setHttpProxy(proxyIp, proxyPort).timeout(5000).setFollowRedirects(true);
        HttpResponse resp = req.execute();
        String body = resp.body();
        System.out.println(body);
    }
}