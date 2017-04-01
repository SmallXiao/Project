package com.project.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class CommonUtils {

	private static final Logger LOG = LogManager.getLogger(CommonUtils.class);

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
			LOG.error("加载配置文件中的内容失败", e);
			e.printStackTrace();
		}
		return config;
	}

	public static String getwxurl() {
		if (wxURL != null && !"".equals(wxURL)) {
			return wxURL;
		}
		String wxUrl = getconfig().get("wx_url").toString();
		if (wxUrl != null) {
			wxURL = wxUrl.trim();
		}
		return wxURL;
	}

	public static String getDeployModel() {
		if (model != null && !"".equals(model)) {
			return model;
		}
		model = StringUtils.trim((String) getconfig().get("deploy_model"));
		return model;
	}

	public static String getDeployment() {
		if (deployment != null && !"".equals(deployment)) {
			return deployment;
		}
		deployment = StringUtils.trim((String) getconfig().get("deployment"));
		return deployment;
	}

	public static String getrestname() {
		if (RESTNAME != null && !"".equals(RESTNAME)) {
			return RESTNAME;
		}
		String restname = getconfig().get("rest_name").toString();
		if (restname != null) {
			RESTNAME = restname.trim();
		}
		return RESTNAME;
	}

	public static String getrestpassword() {
		if (RESTPASSWORD != null && !"".equals(RESTPASSWORD)) {
			return RESTPASSWORD;
		}
		String restPassword = getconfig().get("rest_password").toString();
		if (restPassword != null) {
			RESTPASSWORD = restPassword.trim();
		}
		return RESTPASSWORD;
	}

	public static String getappid() {
		if (APPID != null && !"".equals(APPID)) {
			return APPID;
		}
		String appId = getconfig().get("appid").toString();
		if (appId != null) {
			APPID = appId.trim();
		}
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

	/**
	 * 创建图片缩略图
	 * 
	 * @param filename
	 * @param thumbWidth
	 * @param thumbHeight
	 * @param quality
	 * @param outFilename
	 * @throws InterruptedException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void createThumbnail(String filename, int thumbWidth,
			int thumbHeight, int quality, String outFilename)
			throws InterruptedException, FileNotFoundException, IOException {
		// load image from filename
		Image image = Toolkit.getDefaultToolkit().getImage(filename);
		MediaTracker mediaTracker = new MediaTracker(new Container());
		mediaTracker.addImage(image, 0);
		mediaTracker.waitForID(0);
		// use this to test for errors at this point:
		// System.out.println(mediaTracker.isErrorAny());

		// determine thumbnail size from WIDTH and HEIGHT
		double thumbRatio = (double) thumbWidth / (double) thumbHeight;
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);
		double imageRatio = (double) imageWidth / (double) imageHeight;
		if (thumbRatio < imageRatio) {
			thumbHeight = (int) (thumbWidth / imageRatio);
		} else {
			thumbWidth = (int) (thumbHeight * imageRatio);
		}

		// draw original image to thumbnail image object and
		// scale it to the new size on-the-fly
		BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = thumbImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);

		// save thumbnail image to outFilename
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(outFilename));
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);
		quality = Math.max(0, Math.min(quality, 100));
		param.setQuality((float) quality / 100.0f, false);
		encoder.setJPEGEncodeParam(param);
		encoder.encode(thumbImage);
		out.close();
	}

	/**
	 * 抓屏程序
	 * @param fileName
	 * @throws Exception
	 */
	public static void captureScreen(String fileName) throws Exception {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenRectangle = new Rectangle(screenSize);
		Robot robot = new Robot();
		BufferedImage image = robot.createScreenCapture(screenRectangle);
		ImageIO.write(image, "png", new File(fileName));
	}

	public static void main(String[] args) {

		System.out.println(replaceStringNULL("\"null\""));

	}

}
