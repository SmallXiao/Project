package com.project.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class WeChatUtil {

	private static final Logger log = LogManager.getLogger(WeChatUtil.class);

	public static Map<String, AccessToken> accessTokens = new HashMap<String, AccessToken>();

	public static Map<String, JsApiTicket> jsApiTickets = new HashMap<String, JsApiTicket>();

	public static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public static final String oauth2_access_token = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 发送普通消息url
	public static String send_custom_message_url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	// 发送模板消息url
	public static String send_template_msg_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	public static String user_list_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";

	public static String qrcode_create_url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
	public static String qrcode_show_url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=QTICKET";

	// 调用JSsdk时获取jsapi_ticket
	public static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	public static String jsapi_ticket_url_qy = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=ACCESS_TOKEN";
	
	public static String create_product = "https://api.weixin.qq.com/scan/product/create?access_token=ACCESS_TOKEN";

	public static JsApiTicket getJsApiTicket(String appid, AccessToken token,
			int isqy) {

		long newDate = 0;
		long oldDate = 7000000;
		String ticket = null;
		if (jsApiTickets.get(appid) != null) {
			ticket = jsApiTickets.get(appid).getTicket();
			newDate = new Date().getTime();
			oldDate = jsApiTickets.get(appid).getDate().getTime();
		}
		if (ticket != null && !"".equals(ticket) && newDate - oldDate < 7000000) {
			return jsApiTickets.get(appid);
		}
		JsApiTicket jsApiTicket = new JsApiTicket();
		jsApiTicket.setDate(new Date());
		String requestUrl = "";
		if (isqy == 0) {
			requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN",
					token.getToken());
		} else {
			requestUrl = jsapi_ticket_url_qy.replace("ACCESS_TOKEN",
					token.getToken());
		}
		JSONObject jsonObject = HttpUtil.httpsRequest(requestUrl, "GET", null);

		if (jsonObject != null) {
			try {
				jsApiTicket.setTicket(jsonObject.getString("ticket"));
				jsApiTicket.setExpiresIn(jsonObject.getIntValue("expires_in"));
			} catch (JSONException e) {
				jsApiTicket = null;
				log.error("获取Ticket失败(errcode:"
						+ jsonObject.getIntValue("errcode")
						+ ")："
						+ WeChatErrorCode.ERRORCODE.get(Integer
								.valueOf(jsonObject.getIntValue("errcode"))));
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
		if (token != null && !"".equals(token) && newDate - oldDate < 7000000) {
			return accessTokens.get(appid);
		}
		AccessToken accessToken = new AccessToken();
		accessToken.setDate(new Date());
		String requestUrl = access_token_url.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		JSONObject jsonObject = HttpUtil.httpsRequest(requestUrl, "GET", null);

		if (jsonObject != null) {
			try {
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				log.error("获取TOKEN失败(errcode:"
						+ jsonObject.getIntValue("errcode")
						+ ")："
						+ WeChatErrorCode.ERRORCODE.get(Integer
								.valueOf(jsonObject.getIntValue("errcode"))));
			}
		}
		accessTokens.put(appid, accessToken);
		return accessToken;
	}

	public static String getOauth2OpenId(String appid, String appsecret,
			String code) {
		String openid = "";
		String requestUrl = oauth2_access_token.replace("APPID", appid)
				.replace("SECRET", appsecret).replace("CODE", code);
		JSONObject jsonObject = HttpUtil.httpsRequest(requestUrl, "GET", null);

		if (jsonObject != null) {
			try {
				openid = jsonObject.getString("openid");

			} catch (JSONException e) {
				log.error("获取失败(errcode:"
						+ jsonObject.getIntValue("errcode")
						+ ")："
						+ WeChatErrorCode.ERRORCODE.get(Integer
								.valueOf(jsonObject.getIntValue("errcode"))));
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

	// 创建服务号菜单
	public static int createMenu(String jsonMenu, String accessToken) {
		int result = 0;

		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);

		JSONObject jsonObject = HttpUtil.httpsRequest(url, "POST", jsonMenu);

		if ((jsonObject != null) && (jsonObject.getIntValue("errcode") != 0)) {
			result = jsonObject.getIntValue("errcode");
			log.error("创建服务号菜单失败(errcode:" + result + ")："
					+ WeChatErrorCode.ERRORCODE.get(Integer.valueOf(result)));
		}
		return result;
	}

	public static int sendCustomMsg(String jsonMsg, String accessToken) {
		int result = 0;
		String url = send_custom_message_url.replace("ACCESS_TOKEN",
				accessToken);
		JSONObject jsonObject = HttpUtil.httpsRequest(url, "POST", jsonMsg);
		if (jsonObject == null) {
			log.error("send custom msg jsonObject is null， content：" + jsonMsg);
			return -1;
		}
		if ((jsonObject != null) && (jsonObject.getIntValue("errcode") != 0)) {
			result = jsonObject.getIntValue("errcode");
			log.error("send custom msg(errcode:" + result + ")："
					+ WeChatErrorCode.ERRORCODE.get(Integer.valueOf(result))
					+ " content：" + jsonMsg);
			return result;
		}

		log.info("send custom msg content:" + jsonMsg);
		return result;
	}

	public static int sendTemplateMsg(String jsonMsg, String accessToken) {
		int result = 0;
		String url = send_template_msg_url.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = HttpUtil.httpsRequest(url, "POST", jsonMsg);
		if ((jsonObject != null) && (jsonObject.getIntValue("errcode") != 0)) {
			result = jsonObject.getIntValue("errcode");
			log.error("发送消息失败(errcode:" + result + ")："
					+ WeChatErrorCode.ERRORCODE.get(Integer.valueOf(result)));
		}
		return result;
	}
	
	public static int createProduct(String  accessToken) {
		
//		String url = create_product.replace("ACCESS_TOKEN", accessToken);
//		HttpUtil.httpsRequest(url, "POST", outputStr);
		return 0;
		
	}
	

}
