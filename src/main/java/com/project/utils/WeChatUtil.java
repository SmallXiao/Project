package com.project.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.project.entity.Account;


public class WeChatUtil {

    private static final Log               log                          = LogFactory.getLog(WeChatUtil.class);

    public static Map<String, AccessToken> accessTokens                 = new HashMap<String, AccessToken>();

    public static Map<String, JsApiTicket> jsApiTickets                 = new HashMap<String, JsApiTicket>();

    public static Map<String, AccessToken> qyAccessTokes                = new HashMap<String, AccessToken>();

    public static String                   access_token_url             = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public static final String             oauth2_access_token          = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    public static String                   menu_create_url              = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    // 发送普通消息url
    public static String                   send_custom_message_url      = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
    // 发送模板消息url
    public static String                   send_template_msg_url        = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    public static String                   user_list_url                = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";

    public static String                   qrcode_create_url            = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
    public static String                   qrcode_show_url              = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=QTICKET";

    //获取企业号AccessToken
    public static String                   qy_access_token_url          = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=CORPSECRET";
    //获取一键授权的企业号的获取企业号AccessToken
    public static String                   qy_access_token_callback_url = "https://qyapi.weixin.qq.com/cgi-bin/service/get_corp_token?suite_access_token=";
    
    //企业号创建菜单
    public static String                   qy_create_menu               = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN&agentid=agentId";

    //企业号发送消息
    public static String                   qy_send_message              = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";

    //根据OAuth2获取的code得到用户的userid
    public static String                   qy_get_userid_by_code        = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE&agentid=AGENTID";
    //调用JSsdk时获取jsapi_ticket
    public static String                   jsapi_ticket_url             = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
    public static String                   jsapi_ticket_url_qy          = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=ACCESS_TOKEN";

    
    //根据人员id获取人员
    public static String     				qy_get_member 				 = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=userId";
    
	private static String component_verify_ticket="";
    private static AccessToken suit_access_token;
	
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            TrustManager[] tm = { new TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());

            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestMethod(requestMethod);
            httpUrlConn.setConnectTimeout(5000);
            httpUrlConn.setReadTimeout(5000);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            if (outputStr != null) {
                OutputStream outputStream = httpUrlConn.getOutputStream();

                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();

            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            httpUrlConn = null;
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("微信服务器连接超时！", ce);
        } catch (Exception e) {
            log.error("HTTPS请求错误!", e);
        }
        return jsonObject;
    }

    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            //TrustManager[] tm = { new TrustManager() };

            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

            //httpUrlConn.setDoOutput(true);
            //httpUrlConn.setDoInput(true);
            //httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestMethod(requestMethod);
            httpUrlConn.setConnectTimeout(5000);
            httpUrlConn.setReadTimeout(5000);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            if (outputStr != null) {
                OutputStream outputStream = httpUrlConn.getOutputStream();

                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();

            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            httpUrlConn = null;
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("微信服务器连接超时！", ce);
        } catch (Exception e) {
            log.error("HTTP请求错误!", e);
        }
        return jsonObject;
    }

    public static JSONObject httpGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpGet.setConfig(requestConfig);

        try {
            response = httpClient.execute(httpGet, new BasicHttpContext());

            if (response.getStatusLine().getStatusCode() != 200) {

                System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
                        + ", url=" + url);
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");

                JSONObject result = JSONObject.fromObject(resultStr);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null)
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        return null;
    }

    public static JSONObject httpPost(String url, String msgContent) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type", "application/json");

        StringEntity requestEntity = new StringEntity(msgContent, "UTF-8");
        httpPost.setEntity(requestEntity);

        try {
            response = httpClient.execute(httpPost, new BasicHttpContext());

            if (response.getStatusLine().getStatusCode() != 200) {
                log.error("request url failed, http code=" + response.getStatusLine().getStatusCode() + ", url=" + url);
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "UTF-8");
                JSONObject result = JSONObject.fromObject(resultStr);
                return result;
            }
        } catch (Exception e) {
            log.error("request url=" + url + ", exception, msg=" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (response != null)
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    public static String httpsRequestRest(String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = new StringBuffer();
        try {
            TrustManager[] tm = { new TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());

            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestMethod(requestMethod);
            httpUrlConn.setConnectTimeout(5000);
            httpUrlConn.setReadTimeout(5000);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            if (outputStr != null) {
                OutputStream outputStream = httpUrlConn.getOutputStream();

                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();

            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            httpUrlConn = null;
            //jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("微信服务器连接超时！", ce);
        } catch (Exception e) {
            log.error("HTTPS请求错误!", e);
        }
        return buffer.toString();
    }

    public static String httpRequestRest(String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = new StringBuffer();
        try {
            //TrustManager[] tm = { new TrustManager() };

            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

            //httpUrlConn.setDoOutput(true);
            //httpUrlConn.setDoInput(true);
            //httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestMethod(requestMethod);
            //httpUrlConn.setConnectTimeout(5000);
            //httpUrlConn.setReadTimeout(5000);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            if (outputStr != null) {
                OutputStream outputStream = httpUrlConn.getOutputStream();

                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();

            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            httpUrlConn = null;
            //jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("微信服务器连接超时！", ce);
        } catch (Exception e) {
            log.error("HTTP请求错误!", e);
        }
        return buffer.toString();
    }

    @SuppressWarnings("rawtypes")
    public static JSONObject httpRequestPost(String requestUrl, String requestMethod, String outputStr,
            Map requestParamsMap) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        StringBuffer params = new StringBuffer();
        // 组织请求参数
        Iterator it = requestParamsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry element = (Map.Entry) it.next();
            params.append(element.getKey());
            params.append("=");
            params.append(element.getValue());
            params.append("&");
        }
        if (params.length() > 0) {
            params.deleteCharAt(params.length() - 1);
        }
        try {
            //TrustManager[] tm = { new TrustManager() };

            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            PrintWriter printWriter = null;
            //httpUrlConn.setDoOutput(true);
            //httpUrlConn.setDoInput(true);
            //httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestMethod(requestMethod);
            httpUrlConn.setConnectTimeout(5000);
            httpUrlConn.setReadTimeout(5000);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }
            if ("POST".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.setDoOutput(true);
                httpUrlConn.setDoInput(true);
                httpUrlConn.connect();
                printWriter = new PrintWriter(httpUrlConn.getOutputStream());
                // 发送请求参数
                printWriter.write(params.toString());
                // flush输出流的缓冲
                printWriter.flush();
                // 根据ResponseCode判断连接是否成功
                int responseCode = httpUrlConn.getResponseCode();
                if (responseCode != 200) {
                    log.error(" Error===" + responseCode);
                } else {
                    log.info("Post Success!");
                }
            }

            if (outputStr != null) {
                OutputStream outputStream = httpUrlConn.getOutputStream();

                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();

            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            httpUrlConn = null;
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("微信服务器连接超时！", ce);
        } catch (Exception e) {
            log.error("HTTP请求错误!", e);
        }
        return jsonObject;
    }

    public static JSONObject httpRequesttemp(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            //TrustManager[] tm = { new TrustManager() };

            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

            //httpUrlConn.setDoOutput(true);
            //httpUrlConn.setDoInput(true);
            //httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            if (outputStr != null) {
                OutputStream outputStream = httpUrlConn.getOutputStream();

                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();

            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            httpUrlConn = null;
            //jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("微信服务器连接超时！", ce);
        } catch (Exception e) {
            log.error("HTTP请求错误!", e);
        }
        return jsonObject;
    }

    public static JsApiTicket getJsApiTicket(String appid, AccessToken token, int isqy) {

        long newDate = 0;
        long oldDate = 7000000;
        String ticket = null;
        if (jsApiTickets.get(appid) != null) {
            ticket = jsApiTickets.get(appid).getTicket();
            newDate = new Date().getTime();
            oldDate = jsApiTickets.get(appid).getDate().getTime();
        }
        if (!StringUtils.isBlank(ticket) && newDate - oldDate < 7000000) {
            return jsApiTickets.get(appid);
        }
        JsApiTicket jsApiTicket = new JsApiTicket();
        jsApiTicket.setDate(new Date());
        String requestUrl = "";
        if (isqy == 0) {
            requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", token.getToken());
        } else {
            requestUrl = jsapi_ticket_url_qy.replace("ACCESS_TOKEN", token.getToken());
        }
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

        if (jsonObject != null) {
            try {
                jsApiTicket.setTicket(jsonObject.getString("ticket"));
                jsApiTicket.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                jsApiTicket = null;
                log.error("获取Ticket失败(errcode:" + jsonObject.getInt("errcode") + ")："
                        + WeChatErrorCode.ERRORCODE.get(Integer.valueOf(jsonObject.getInt("errcode"))));
            }
        }
        jsApiTickets.put(appid, jsApiTicket);
        return jsApiTicket;
    }

    public static AccessToken getAccessToken(String appid, String appsecret) {
        long newDate = 0;
        long oldDate = 7000000;
        String token = null;
        if (accessTokens.get(appid) != null) {
            token = accessTokens.get(appid).getToken();
            newDate = new Date().getTime();
            oldDate = accessTokens.get(appid).getDate().getTime();
        }
        if (!StringUtils.isBlank(token) && newDate - oldDate < 7000000) {
            return accessTokens.get(appid);
        }
        AccessToken accessToken = new AccessToken();
        accessToken.setDate(new Date());
        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

        if (jsonObject != null) {
            try {
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                log.error("获取TOKEN失败(errcode:" + jsonObject.getInt("errcode") + ")："
                        + WeChatErrorCode.ERRORCODE.get(Integer.valueOf(jsonObject.getInt("errcode"))));
            }
        }
        accessTokens.put(appid, accessToken);
        return accessToken;
    }

    public static AccessToken getQyAccessToken(String corpID, String secret) {
        long newDate = 0;
        long oldDate = 7000000;
        String token = null;
        if (qyAccessTokes.get(corpID) != null) {
            token = qyAccessTokes.get(corpID).getToken();
            newDate = new Date().getTime();
            oldDate = qyAccessTokes.get(corpID).getDate().getTime();
        }
        if (!StringUtils.isBlank(token) && newDate - oldDate < 7000000) {
            return qyAccessTokes.get(corpID);
        }
        AccessToken qyAccessToke = new AccessToken();
        qyAccessToke.setDate(new Date());
        String requestUrl = qy_access_token_url.replace("CORPID", corpID).replace("CORPSECRET", secret);
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

        if (jsonObject != null) {
            try {
                qyAccessToke.setToken(jsonObject.getString("access_token"));
                qyAccessToke.setExpiresIn(7200);
            } catch (JSONException e) {
                qyAccessToke = null;
                log.error("获取TOKEN失败(errcode:" + jsonObject.getInt("errcode") + ")："
                        + WeChatErrorCode.ERRORCODE.get(Integer.valueOf(jsonObject.getInt("errcode"))));
            }
        }
        qyAccessTokes.put(corpID, qyAccessToke);
        return qyAccessToke;
    }

    public static AccessToken getQyAccessToken_callback(String permanentid, String corpID) {
        long newDate = 0;
        long oldDate = 7000000;
        String token = null;
        if (qyAccessTokes.get(corpID) != null) {
            token = qyAccessTokes.get(corpID).getToken();
            newDate = new Date().getTime();
            oldDate = qyAccessTokes.get(corpID).getDate().getTime();
        }
        if (!StringUtils.isBlank(token) && newDate - oldDate < 7000000) {
            return qyAccessTokes.get(corpID);
        }
        AccessToken qyAccessToke = new AccessToken();
        qyAccessToke.setDate(new Date());
        String requestUrl = null;
        if (getsuitAccessToken() != null) {
            requestUrl = qy_access_token_callback_url + getsuitAccessToken().getToken();
        } else {
            requestUrl = qy_access_token_callback_url + "";
        }
        HashMap<String, String> m = new HashMap<String, String>();
        m.put("suite_id", "tje97b218644981619");
        m.put("auth_corpid", corpID);
        m.put("permanent_code", permanentid);
        JSONObject jsonObject = httpsRequest(requestUrl, "POST", JSON.toJSONString(m));

        if (jsonObject != null) {
            try {
                qyAccessToke.setToken(jsonObject.getString("access_token"));
                qyAccessToke.setExpiresIn(7200);
            } catch (JSONException e) {
                qyAccessToke = null;
                log.error("获取TOKEN失败(errcode:" + jsonObject.getInt("errcode") + ")："
                        + WeChatErrorCode.ERRORCODE.get(Integer.valueOf(jsonObject.getInt("errcode"))));
            }
        }
        qyAccessTokes.put(corpID, qyAccessToke);
        return qyAccessToke;
    }

    public static String getOauth2OpenId(String appid, String appsecret, String code) {
        String openid = "";
        String requestUrl = oauth2_access_token.replace("APPID", appid).replace("SECRET", appsecret)
                .replace("CODE", code);
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

        if (jsonObject != null) {
            try {
                openid = jsonObject.getString("openid");

            } catch (JSONException e) {
                log.error("获取失败(errcode:" + jsonObject.getInt("errcode") + ")："
                        + WeChatErrorCode.ERRORCODE.get(Integer.valueOf(jsonObject.getInt("errcode"))));
            }
        }
        return openid;
    }

    public static String saveImageToDisk(String url_path) {
        InputStream inputStream = getInputStream(url_path);
        byte[] data = new byte[1024];
        FileOutputStream fileOutputStream = null;
        int len = 0;
        try {

            fileOutputStream = new FileOutputStream(
                    "D:\\work\\seeyon-wx\\out\\artifacts\\seeyon_wx_war_exploded\\img\\a.jpg");
            while ((len = inputStream.read()) != -1) {
                fileOutputStream.write(data, 0, len);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public static InputStream getInputStream(String URL_PATH) {
        InputStream inputStream = null;
        HttpsURLConnection httpsURLConnection = null;
        try {
            URL url = new URL(URL_PATH);
            TrustManager[] tm = { new TrustManager() };
            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
            try {
                sslContext.init(null, tm, new SecureRandom());
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }

            SSLSocketFactory ssf = sslContext.getSocketFactory();
            if (url != null) {
                httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setSSLSocketFactory(ssf);
                httpsURLConnection.setConnectTimeout(3000); // 设置网络的超时时间
                httpsURLConnection.setRequestMethod("GET"); // 设置本次http请求使用GET方式
                int responseCode = httpsURLConnection.getResponseCode();
                if (responseCode == 200) {
                    // 从服务器端得到输入流
                    inputStream = httpsURLConnection.getInputStream();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    //创建服务号菜单
    public static int createMenu(String jsonMenu, String accessToken) {
        int result = 0;

        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);

        JSONObject jsonObject = httpsRequest(url, "POST", jsonMenu);

        if ((jsonObject != null) && (jsonObject.getInt("errcode") != 0)) {
            result = jsonObject.getInt("errcode");
            log.error("创建菜单失败(errcode:" + result + ")：" + WeChatErrorCode.ERRORCODE.get(Integer.valueOf(result)));
        }
        return result;
    }

    //创建企业号菜单
    public static int createQyMenu(String jsonMenu, String accessToken, String agentId) {
        int result = 0;

        String url = qy_create_menu.replace("ACCESS_TOKEN", accessToken).replace("agentId", agentId);

        JSONObject jsonObject = httpsRequest(url, "POST", jsonMenu);

        if ((jsonObject != null) && (jsonObject.getInt("errcode") != 0)) {
            result = jsonObject.getInt("errcode");
            log.error("创建菜单失败(errcode:" + result + ")：" + WeChatErrorCode.ERRORCODE.get(Integer.valueOf(result)));
        }
        return result;
    }

    public static String getUseridByOauthCode(String code, Account account) {
        AccessToken accessToken;
        if (account.getPermanentcode() != null && !"".equals(account.getPermanentcode())) {
            accessToken = getQyAccessToken_callback(account.getPermanentcode(), account.getCorpid());
        } else {
            accessToken = getQyAccessToken(account.getCorpid(), account.getQySecret());
        }

        String url = qy_get_userid_by_code.replace("ACCESS_TOKEN", accessToken.getToken()).replace("CODE", code)
                .replace("AGENTID", account.getAgentid());
        JSONObject jsonObject = httpsRequest(url, "GET", null);
        if ((jsonObject != null)) {
            String userid = jsonObject.getString("UserId");
            if (StringUtils.isBlank(userid)) {
                int errcode = jsonObject.getInt("errcode");
                log.error("根据OAuth2 code 获取用户信息失败(errcode:" + errcode + ")："
                        + WeChatErrorCode.ERRORCODE.get(Integer.valueOf(errcode)));
                return null;
            }
            return userid;
        }
        return null;
    }

    public static int sendCustomMsg(String jsonMsg, String accessToken) {
        int result = 0;
        String url = send_custom_message_url.replace("ACCESS_TOKEN", accessToken);
        JSONObject jsonObject = httpsRequest(url, "POST", jsonMsg);
        if ((jsonObject != null) && (jsonObject.getInt("errcode") != 0)) {
            result = jsonObject.getInt("errcode");
            log.error("发送消息失败(errcode:" + result + ")：" + WeChatErrorCode.ERRORCODE.get(Integer.valueOf(result)));
        }
        return result;
    }

    public static int sendTextMessage_qy(String jsonMsg, String accessToken) {
        int result = 0;
        String url = qy_send_message.replace("ACCESS_TOKEN", accessToken);
        JSONObject jsonObject = httpsRequest(url, "POST", jsonMsg);
        if ((jsonObject != null) && (jsonObject.getInt("errcode") != 0)) {
            result = jsonObject.getInt("errcode");
            //String invaliduser = jsonObject.getString("invaliduser");
            log.error("发送消息失败(errcode:" + result + ")：" + WeChatErrorCode.ERRORCODE.get(Integer.valueOf(result)));
            //log.warn("无效人员：" + invaliduser);
        }
        return result;
    }

    public static int sendTemplateMsg(String jsonMsg, String accessToken) {
        int result = 0;
        String url = send_template_msg_url.replace("ACCESS_TOKEN", accessToken);
        JSONObject jsonObject = httpsRequest(url, "POST", jsonMsg);
        if ((jsonObject != null) && (jsonObject.getInt("errcode") != 0)) {
            result = jsonObject.getInt("errcode");
            log.error("发送消息失败(errcode:" + result + ")：" + WeChatErrorCode.ERRORCODE.get(Integer.valueOf(result)));
        }
        return result;
    }
    
    /**
     * 得到企业号后台某人员信息
     * @param userId
     * @param corpid
     * @param secret
     * @return
     */
    public static int getMember(String userId, String corpid, String secret) {
        AccessToken at = getQyAccessToken(corpid, secret);
        String accessToken = at.getToken();
        String url = qy_get_member.replace("ACCESS_TOKEN", accessToken).replace("userId", userId);
        JSONObject jsonObject = httpsRequest(url, "GET", null);
        int result = 0;
        if ((jsonObject != null) && (jsonObject.getInt("errcode") != 0)) {
            result = jsonObject.getInt("errcode");
            log.error("获取人员失败(errcode:" + result + ")：" + WeChatErrorCode.ERRORCODE.get(Integer.valueOf(result)));
        }
        return result;
    }
    
    /**
     * 生成套件
     * @return
     */
    public static AccessToken getsuitAccessToken() {
        long newDate = 0;
        long oldDate = 7000000;
        String token = null;
        if (suit_access_token != null){
            token = suit_access_token.getToken();
            newDate = new Date().getTime();
            oldDate = suit_access_token.getDate().getTime();
        }
        if(StringUtils.isNotBlank(token) && newDate - oldDate < 7000000){
            return suit_access_token;
        }
        suit_access_token = new AccessToken();
        suit_access_token.setDate(new Date());
        HashMap<String, String> m = new HashMap<String, String>();
        m.put("suite_id","tje97b218644981619");
        m.put("suite_secret","k4LSL0RPU3sgY3ZOPSeTom55q4-BFCDMqa70aVKUli83PCUnPS9IXMX0rMaCNHm7");
        m.put("suite_ticket",component_verify_ticket);

        JSONObject jsonObject = WeChatUtil.httpsRequest("https://qyapi.weixin.qq.com/cgi-bin/service/get_suite_token", "POST", JSON.toJSONString(m));

        //JSONObject jsonObject = httpsRequest(requestUrl, "POST", null);

        if (jsonObject != null) {
            try {
                suit_access_token.setToken(jsonObject.getString("suite_access_token"));
                suit_access_token.setExpiresIn(7200);
            } catch (JSONException e) {
                suit_access_token = null;
                log.error("获取TOKEN失败(errcode:" + jsonObject.getInt("errcode") + ")："
                        + WeChatErrorCode.ERRORCODE.get(Integer.valueOf(jsonObject.getInt("errcode"))));
            }
        }

        return suit_access_token;
    }

}
