/**
 * 
 */
package com.project.utils;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * @author Macx
 *
 */
public class CommonUtils {

	private static final Log log = LogFactory.getLog(CommonUtils.class);

	private static String wxURL;

	private static String model;

	private static String RESTNAME;

	private static String RESTPASSWORD;

	private static Properties config;

	private static String APPID;

	private static String APPSECRET;

	private static String corpID;

	private static String agentId;

	private static String secret;

	private static String qyToken;

	private static String encodingAesKey;

	private static Invocable invocableEngine;

	private static String deployment;

	private static String qyAdmin;

	private static String qyPassword;

	public static void initScriptEngine(String appRoot) {
		if (invocableEngine == null) {
			ScriptEngineManager sem = new ScriptEngineManager();
			ScriptEngine se = sem.getEngineByName("javascript");
			try {
				invocableEngine = (Invocable) se;
				Reader reader = new FileReader(new File(appRoot,
						"js/common/crypto.js".replace('/', File.separatorChar)));
				se.eval(reader);
				reader.close();
				se.eval("function decrypt(a,b){var v=CryptoJS.DES.decrypt(a,b);return ''+CryptoJS.enc.Utf8.stringify(v);}");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String decrypt(String cryptData, String seed)
			throws Exception {
		return (String) invocableEngine.invokeFunction("decrypt", cryptData,
				seed);
	}

	private static Properties getconfig() {
		if (config != null) {
			return config;
		}
		InputStream input = null;
		input = Constants.class.getClassLoader().getResourceAsStream(
				"sysinfo.properties");
		config = new Properties();
		try {
			config.load(input);
		} catch (IOException e) {
			log.error("加载配置文件中的内容失败", e);
			e.printStackTrace();
		}
		return config;
	}

	public static String getwxurl() {
		if (!StringUtils.isEmpty(wxURL)) {
			return wxURL;
		}

		wxURL = StringUtils.trim((String) getconfig().get("wx_url"));
		return wxURL;
	}

	public static String getDeployModel() {
		if (!StringUtils.isEmpty(model)) {
			return model;
		}
		model = StringUtils.trim((String) getconfig().get("deploy_model"));
		return model;
	}

	public static String getDeployment() {
		if (!StringUtils.isEmpty(deployment)) {
			return deployment;
		}
		deployment = StringUtils.trim((String) getconfig().get("deployment"));
		return deployment;
	}

	public static String getrestname() {
		if (!StringUtils.isEmpty(RESTNAME)) {
			return RESTNAME;
		}

		RESTNAME = StringUtils.trim((String) getconfig().get("rest_name"));
		return RESTNAME;
	}

	public static String getrestpassword() {
		if (!StringUtils.isEmpty(RESTPASSWORD)) {
			return RESTPASSWORD;
		}

		RESTPASSWORD = StringUtils.trim((String) getconfig().get(
				"rest_password"));
		return RESTPASSWORD;
	}

	public static String getappid() {
		if (!StringUtils.isEmpty(APPID)) {
			return APPID;
		}

		APPID = StringUtils.trim((String) getconfig().get("appid"));
		return APPID;
	}

	public static String getappsecret() {
		if (!StringUtils.isEmpty(APPSECRET)) {
			return APPSECRET;
		}

		APPSECRET = StringUtils.trim((String) getconfig().get("appsecret"));
		return APPSECRET;
	}

	public static String getCorpID() {
		if (!StringUtils.isEmpty(corpID)) {
			return corpID;
		}
		corpID = StringUtils.trim((String) getconfig().get("CorpID"));
		return corpID;
	}

	public static String getAgentId() {
		if (!StringUtils.isEmpty(agentId)) {
			return agentId;
		}
		agentId = StringUtils.trim((String) getconfig().get("agentId"));
		return agentId;
	}

	public static String getSecret() {
		if (!StringUtils.isEmpty(secret)) {
			return secret;
		}
		secret = StringUtils.trim((String) getconfig().get("Secret"));
		return secret;
	}

	public static String getQyToken() {
		if (!StringUtils.isEmpty(qyToken)) {
			return qyToken;
		}
		qyToken = StringUtils.trim((String) getconfig().get("qy_token"));
		return qyToken;
	}

	public static String getEncodingAesKey() {
		if (!StringUtils.isEmpty(encodingAesKey)) {
			return encodingAesKey;
		}
		encodingAesKey = StringUtils.trim((String) getconfig().get(
				"encoding_aes_key"));
		return encodingAesKey;
	}

	public static String getqyAdmin() {
		if (!StringUtils.isEmpty(qyAdmin)) {
			return qyAdmin;
		}
		qyAdmin = StringUtils.trim((String) getconfig().get("qyAdmin"));
		return qyAdmin;
	}

	public static String getqyPassword() {
		if (!StringUtils.isEmpty(qyPassword)) {
			return qyPassword;
		}
		qyPassword = StringUtils.trim((String) getconfig().get("qyPassword"));
		return qyPassword;
	}

	public static String getClient(String ip, String port, String restURL) {
		String baseUrl = "";
		if (ip.startsWith("http")) {
			baseUrl = ip + ":" + port;
		} else {
			baseUrl = "http://" + ip + ":" + port;
		}
		String returnstr = "";
		String tokenname = "";
		if (restURL.contains("?")) {
			tokenname = "&token=";
		} else {
			tokenname = "?token=";
		}
		if (baseUrl.startsWith("https")) {
			JSONObject jtoken = WeChatUtil.httpsRequest(baseUrl
					+ "/seeyon/rest/token/" + getrestname() + "/"
					+ getrestpassword(), "GET", null);
			returnstr = WeChatUtil
					.httpsRequestRest(baseUrl + "/seeyon/rest/" + restURL
							+ tokenname + jtoken.getString("id"), "GET", null);

		} else {
			JSONObject jtoken = WeChatUtil.httpRequest(baseUrl
					+ "/seeyon/rest/token/" + getrestname() + "/"
					+ getrestpassword(), "GET", null);
			returnstr = WeChatUtil
					.httpRequestRest(baseUrl + "/seeyon/rest/" + restURL
							+ tokenname + jtoken.getString("id"), "GET", null);

		}
		return returnstr;
	}

	/**
	 * Encode a string using Base64 encoding. Used when storing passwords as
	 * cookies.
	 *
	 * This is weak encoding in that anyone can use the decodeString routine to
	 * reverse the encoding.
	 *
	 * @param str
	 * @return String
	 * @throws IOException
	 */
	public static String encodeString(String str) throws IOException {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		String encodedStr = new String(encoder.encodeBuffer(str.getBytes()));

		return encodedStr.trim();
	}

	/**
	 * Decode a string using Base64 encoding.
	 *
	 * @param str
	 * @return String
	 * @throws IOException
	 */
	public static String decodeString(String str) throws IOException {
		sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
		String value = new String(dec.decodeBuffer(str));

		return value;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullCharacter(String str) {
		boolean flg = false;
		if (null != str && !"".equals(str.trim())) {
			flg = true;
		}
		return flg;
	}

	/**
	 * 生成随机数
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomNumberMessage(int length) {
		Random r = new Random();
		String ssource = "123456789";
		char[] src = ssource.toCharArray();
		char[] buf = new char[length];
		int rnd;
		for (int i = 0; i < length; i++) {
			rnd = Math.abs(r.nextInt()) % src.length;
			buf[i] = src[rnd];
		}
		return new String(buf);
	}

	/**
	 * 生成支付订单编号
	 * 
	 * @return
	 */
	public static synchronized String getPayOrderNo() {
		String strOrderNo = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		return "PO" + strOrderNo;
	}

	/**
	 * 字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 将null,"null"这种字符串进行转义
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceStringNULL(String str) {
		if (str == null) {
			return "";
		} else if (("\"null\"".equals(str.trim()))
				|| ("\"\"".equals(str.trim()))) {
			return str.replace("\"", "");
		}

		return str;
	}
	
	/**
	 * 将价格转换为小数位，保留2位
	 * 
	 * @param orderMoney
	 * @return
	 */
	public static String getTrunDecimals(String orderMoney) {
		DecimalFormat df = new DecimalFormat("0.00"); // 保留2位小数
		double price = Double.parseDouble(orderMoney);
		return df.format(price);
	}

	public static void main(String[] args) {

		System.out.println(replaceStringNULL("\"null\""));

	}

}
