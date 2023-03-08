package seleniumhq;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * <br/>Description : ibox接口逆向
 * <br/>CreateTime : 2023/1/11
 * @author hanhaotian
 */
public class TestIboxInterfaceReverse {

    //https://ibox.art/zh-cn/find/  一共调用5个加密网关接口

    /**
     * https://web-001.cloud.servicewechat.com/wxa-qbase/jsoperatewxdata
     * 请求参数:
     * {"appid":"wxb5b2c81edbd4cf69","data":{"qbase_api_name":"tcbapi_get_service_info","qbase_req":"{\"client_random\":\"0.299402814435187_1673417920012\",\"system\":\"\"}","qbase_options":{"identityless":true,"resourceAppid":"wxb5b2c81edbd4cf69","resourceEnv":"ibox-3gldlr1u1a8322d4","config":{"database":{"realtime":{"maxReconnect":5,"reconnectInterval":5000,"totalConnectionTimeout":null}}},"appid":"wxb5b2c81edbd4cf69","env":"ibox-3gldlr1u1a8322d4"},"qbase_meta":{"session_id":"1673417920018","sdk_version":"wx-web-sdk/WEBDOMAIN_1.0.0 (1655460325000)","filter_user_info":false},"cli_req_id":"1673417920269_0.8303717093827652"}}     * 返回:
     * 出参:
     * {"base_resp":{"ret":0},"data":"{\"timestamp\":1673417920344,\"service_url\":\"https:\/\/servicewechat.com\/wxa-qbase\/container_service?token=64_yiO6BffY8V9luhXgyVnidA0TDuIVtvTtahgMK4_o5Ce3Ra5q6NPylTrCDisudi_8dhU8uw4vqqdbTeDDD93rXruavWNJ22aMLY7T1w\",\"token\":\"64_yiO6BffY8V9luhXgyVnidA0TDuIVtvTtahgMK4_o5Ce3Ra5q6NPylTrCDisudi_8dhU8uw4vqqdbTeDDD93rXruavWNJ22aMLY7T1w\",\"expires_in\":1800,\"heartbeat_period\":30,\"key\":\"Ii\/vuUwqr3XK6nxWoqNvRQ==\",\"baseresponse\":{\"errcode\":0,\"stat\":{\"qbase_cost_time\":8}},\"http\":true}"}     * <p>
     * 关键字段:token,key,timestamp
     * }"
     */

    /**
     * ："{
     * "data":"IdNM7m9PDj6mAiISOuv3RaPgeNhChzEBfrBH1NVmuh3MTPtvMEKHZ+OwcQOhSOY2iRfp+6+vlu71ngAc/z9H4eTzDtfDI7quvjM49mxdEnH10MJy8WLba4X6JXtO1SGjBd1pSnaSVYRXeKR9Gxthwc6FX6LpOW3OblKAH+owHGA=",
     * "encryptKey":"PLOHUuKTxmmv6gDsiK8vG6TFcHrcgoahgQ4gX823WKyMG1S9boUmypOPEh9XOH70TaVqGHaDe1EPXe2ThDn7y0s6/s/2r9Nj+rE5peumoCTlOBRqfd6Cedy+AoQKHCifPgt+FL+gDW9ziEswJ4Vl4ayRch6MPxOe/IYhmKUiBvM="
     * }"
     */
    private static String data = "IdNM7m9PDj6mAiISOuv3RaPgeNhChzEBfrBH1NVmuh3MTPtvMEKHZ+OwcQOhSOY2iRfp+6+vlu71ngAc/z9H4eTzDtfDI7quvjM49mxdEnH10MJy8WLba4X6JXtO1SGjBd1pSnaSVYRXeKR9Gxthwc6FX6LpOW3OblKAH+owHGA=";
    private static String key = "PLOHUuKTxmmv6gDsiK8vG6TFcHrcgoahgQ4gX823WKyMG1S9boUmypOPEh9XOH70TaVqGHaDe1EPXe2ThDn7y0s6/s/2r9Nj+rE5peumoCTlOBRqfd6Cedy+AoQKHCifPgt+FL+gDW9ziEswJ4Vl4ayRch6MPxOe/IYhmKUiBvM=";

    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String cKey = key;//"jkl;POIU1234++==";
        // 需要加密的字串
        String cSrc = data;//"www.gowhere.so6666666666664444253让2繁体VGTV他GV兔宝宝吧好";
        System.out.println(cSrc);
        // 加密
        String enString = TestIboxInterfaceReverse.Encrypt(cSrc, cKey);
        System.out.println("加密后的字串是：" + enString);

        // 解密
        String DeString = TestIboxInterfaceReverse.Decrypt(enString, cKey);
        System.out.println("解密后的字串是：" + DeString);
        //Decrypt(data, key);
    }


    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        /*if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }*/
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            /*// 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }*/
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

}
