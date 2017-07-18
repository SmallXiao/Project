/**
 * 
 */
package com.project.utils;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Macx
 *
 */
public class MessageUtil {
	
    public static Set<String> msgSet = new HashSet<String>();

	public static String sendMessage(InputMessage oms, String msg, WXBizMsgCrypt wxcpt, String timestamp, String nonce) {
        //发送文本消息 start
        XStream xstream = new XStream(new XppDriver() {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if (!text.startsWith("<![CDATA[")) {
                            text = "<![CDATA[" + text + "]]>";
                        }
                        writer.write(text);
                    }
                };
            }
        });
        //创建文本发送消息对象
        TextOutputMessage outputMsg = new TextOutputMessage();
        outputMsg.setContent(msg);

        try {
            setOutputMsgInfo(outputMsg, oms);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置对象转换的XML根节点为xml
        xstream.alias("xml", outputMsg.getClass());
        //将对象转换为XML字符串
        String xml = xstream.toXML(outputMsg);
        if (wxcpt != null && timestamp != null && nonce != null){
            try {
                //企业号将内容加密后发送给微信服务器，发送到用户手机
                return wxcpt.EncryptMsg(xml,timestamp,nonce);
            } catch (AesException e) {
                e.printStackTrace();
            }
        }
        //服务号将内容发送给微信服务器，发送到用户手机
        return xml;
    }
    public static String sendMessage_qy(InputMessage oms, String msg,WXBizMsgCrypt wxcpt,String timestamp,String nonce) {
        //发送文本消息 start
        XStream xstream = new XStream(new XppDriver() {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if (!text.startsWith("<![CDATA[")) {
                            text = "<![CDATA[" + text + "]]>";
                        }
                        writer.write(text);
                    }
                };
            }
        });
        //创建文本发送消息对象
        TextOutputMessage outputMsg = new TextOutputMessage();
        outputMsg.setContent(msg);

        try {
            setOutputMsgInfo(outputMsg, oms);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置对象转换的XML根节点为xml
        xstream.alias("xml", outputMsg.getClass());
        //将对象转换为XML字符串

        String xml = xstream.toXML(outputMsg);
        //将内容发送给微信服务器，发送到用户手机
        try {
            return wxcpt.EncryptMsg(xml,timestamp,nonce);
        } catch (AesException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendNewsMessage(InputMessage oms, OutputMessage outputMsg, WXBizMsgCrypt wxcpt, String timestamp, String nonce) {
        //发送文本消息 start
        XStream xstream = new XStream(new XppDriver() {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if (!text.startsWith("<![CDATA[")) {
                            text = "<![CDATA[" + text + "]]>";
                        }
                        writer.write(text);
                    }
                };
            }
        });
        try {
            setOutputMsgInfo(outputMsg, oms);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置对象转换的XML根节点为xml
        xstream.alias("xml", outputMsg.getClass());
        xstream.alias("item", ArticlesBo.class);
        //将对象转换为XML字符串
        String xml = xstream.toXML(outputMsg);
        if (wxcpt != null && timestamp != null && nonce != null){
            try {
                //企业号将内容加密后发送给微信服务器，发送到用户手机
                return wxcpt.EncryptMsg(xml,timestamp,nonce);
            } catch (AesException e) {
                e.printStackTrace();
            }
        }
        //将内容发送给微信服务器，发送到用户手机
        return xml;
    }//设置详细信息

    public static void setOutputMsgInfo(OutputMessage oms, InputMessage msg) throws Exception {
        // 设置发送信息
        Class<?> outMsg = oms.getClass().getSuperclass();
        Field CreateTime = outMsg.getDeclaredField("CreateTime");
        Field ToUserName = outMsg.getDeclaredField("ToUserName");
        Field FromUserName = outMsg.getDeclaredField("FromUserName");

        ToUserName.setAccessible(true);
        CreateTime.setAccessible(true);
        FromUserName.setAccessible(true);

        CreateTime.set(oms, new Date().getTime());
        ToUserName.set(oms, msg.getFromUserName());
        FromUserName.set(oms, msg.getToUserName());
    }
    
    /**
     * 调用客户接口发送图文消息
     * @param toUser
     * @param outputMsg
     * @return
     */
    public static int sendCustomNewsMessage(String toUser, NewsOutputMessage outputMsg){
        AccessToken at = WeChatUtil.getAccessToken(CommonUtils.getappid(), CommonUtils.getappsecret());
        int result = 0;
        if ((at != null) && (at.getToken() != null)){
            StringBuffer sb = new StringBuffer();
            sb.append("{\"touser\":\""+toUser+"\",");
            sb.append("\"msgtype\":\""+"news"+"\",");
            sb.append("\"news\":{\"articles\":[");
            for(int i=0;i<Integer.valueOf(outputMsg.getArticleCount());i++){
                sb.append("{\"title\":\""+outputMsg.getArticles().get(i).getTitle()+"\",\n");
                //sb.append("\"description\":\""+outputMsg.getArticles().get(i).getDescription()+"\",\n");
                sb.append("\"url\":\""+outputMsg.getArticles().get(i).getUrl()+"\",\n");
                sb.append("\"picurl\":\""+outputMsg.getArticles().get(i).getPicUrl()+"\"\n");
                sb.append("}");
                if(i!=Integer.valueOf(outputMsg.getArticleCount())-1){
                    sb.append(",");
                }
            }
            sb.append("]}");
            result = WeChatUtil.sendCustomMsg(sb.toString(), at.getToken());
        }
        return result;
    }
    
    /**
     * 调用客服接口发送普通消息
     * @param toUser
     * @param messageType
     * @param content
     * @return
     */
    public static int sendCustomMessage(String toUser, String messageType, String content){
        AccessToken at = WeChatUtil.getAccessToken(CommonUtils.getappid(), CommonUtils.getappsecret());
        int result = 0;
        if ((at != null) && (at.getToken() != null)){
            StringBuffer sb = new StringBuffer();
            sb.append("{\"touser\":\""+toUser+"\",");
            sb.append("\"msgtype\":\"text\",");
            sb.append("\"text\":{\"content\":\""+content+"\"}");
            result = WeChatUtil.sendCustomMsg(sb.toString(), at.getToken());
        }
        return result;
    }
    
    public static int sendTemplateMessage(String toUser, String url, String content, String firstData, String key1,
            String key2, String key3, String mark) {
        AccessToken at = WeChatUtil.getAccessToken(CommonUtils.getappid(), CommonUtils.getappsecret());
        int result = 0;
        if ((at != null) && (at.getToken() != null)) {
            StringBuffer sb = new StringBuffer();
            sb.append("{\"touser\":\"" + toUser + "\",");
            sb.append("\"template_id\":\"cu3-JWboMRDWQDMUcfjcV_76Yk8K2q2JgZwm50BmNhk\",");
            sb.append("\"url\":\"" + url + "\",");
            sb.append("\"topcolor\":\"#FF0000\",");
            
            String s = "\"data\":{\"first\": {\"value\":\"" + firstData
                    + "\",\"color\":\"#0A0A0A\"},\"keyword1\":{\"value\":\"" + key1
                    + "\",\"color\":\"#173177\"},\"keyword2\": {\"value\":\"" + key2
                    + "\",\"color\":\"#173177\"},\"keyword3\":{\"value\":\"" + key3
                    + "\",\"color\":\"#173177\"},\"remark\":{\"value\":\"" + mark + "\",\"color\":\"#173177\"}}}";
            sb.append(s);
            result = WeChatUtil.sendTemplateMsg(sb.toString(), at.getToken());
        }
        return result;
    }
    
}
