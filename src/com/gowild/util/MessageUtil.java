package com.gowild.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.gowild.po.ArticlesMessage;
import com.gowild.po.BaseMessage;
import com.gowild.po.ImageMessage;
import com.gowild.po.ImageTextMessage;
import com.gowild.po.MediaMessage;
import com.gowild.po.TextMessage;
import com.gowild.po.VoiceMessage;
import com.thoughtworks.xstream.XStream;


public class MessageUtil {
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";

	/**
	 * xml转换为map集合
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String,String> xmpToMap(HttpServletRequest request) 
			throws IOException, DocumentException{
		Map<String,String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		InputStream ips = request.getInputStream();
		Document doc = reader.read(ips);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		System.out.println(" 请求信息：");
		for(Element e : list){
			map.put(e.getName(), e.getText());
			System.out.println(e.getName()+" : "+e.getText());
		}
		ips.close();
		return map;
	}
	public static String baseMessageToXML(BaseMessage message){
		XStream xstream = new XStream();
		xstream.alias("xml", message.getClass());
		xstream.alias("item", new ArticlesMessage().getClass());
		return xstream.toXML(message);
	}
	
	/**
	 * 文本信息处理
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String sendText(String toUserName,String fromUserName,String content){
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return baseMessageToXML(text);
	}
	/**
	 * 图片信息处理
	 * @param map
	 * @return
	 */
	public static String sendImage(Map<String,String> map){
		MediaMessage media = new MediaMessage();
		media.setMediaId(map.get("MediaId"));
		ImageMessage image = new ImageMessage();
		image.setFromUserName(map.get("ToUserName"));
		image.setToUserName(map.get("FromUserName"));
		image.setMsgType(MESSAGE_IMAGE);
		image.setCreateTime(new Date().getTime());
		image.setImage(media);
		return baseMessageToXML(image);
	}
	public static String sendVoice(Map<String,String> map){
		MediaMessage media = new MediaMessage();
		media.setMediaId(map.get("MediaId"));
		VoiceMessage voice = new VoiceMessage();
		voice.setFromUserName(map.get("ToUserName"));
		voice.setToUserName(map.get("FromUserName"));
		voice.setMsgType(MESSAGE_VOICE);
		voice.setCreateTime(new Date().getTime());
		voice.setVoice(media);
		return baseMessageToXML(voice);
	}
	/**
	 * 发送图文信息
	 * @param map
	 * @return
	 */
	public static String sendImageANDText(Map<String,String> map){
		ImageTextMessage imageText = new ImageTextMessage();
		imageText.setToUserName(map.get("FromUserName"));
		imageText.setFromUserName(map.get("ToUserName"));
		imageText.setMsgType("news");
		imageText.setCreateTime(new Date().getTime());
		imageText.setArticleCount(1);
		List<ArticlesMessage> items = new ArrayList<ArticlesMessage>();
		for(int i=0;i<1;i++){
			ArticlesMessage articles = new ArticlesMessage();
			if(i==0){
				articles.setTitle("小白");
				articles.setPicUrl("http://mmbiz.qpic.cn/mmbiz/Hq4Vzh2QkatUJtMoNbSS0pWMdTxp7VynPCm073f9Zf14Lt6LPOxib1eSapfVMK1Jp6pGlBK4iciaw0hPMkC3bRsYA/0");
				//articles.setPicUrl("https://api.weixin.qq.com/cgi-bin/media/get?access_token=IQyEDeBnzfnxeIcOg3Njhfwt4r27dAwXC_U0v66tW8Hl8a9N2BfAdaxEJd22_39pSzYtFQ1bcqxT-VcpV9mlVrNCyXOS4bT2dFuBXP8gLjc&media_id=OWtnTERVzeYpD0Fmfb-3psFDKeBBSxp2jT6wCbwuc3M");
				articles.setDescription("狗尾草家庭智能机器人");
				articles.setUrl("http://eqxiu.com/s/snEne71K");
			}
//				else{
//				articles.setTitle("狗尾草");
//				articles.setDescription("小白机器人");
//				articles.setPicUrl("http://mmbiz.qpic.cn/mmbiz/RVwt3N6PZQEOJXWCPmwlI48Nxub5ykQzQBvoqdZDxHkCFsB5I9akFVlAtUOvWlicYJMQXPicghB40l53MgvveibPA/0");
//				articles.setUrl("http://www.gowild.cn");
//			}
			items.add(articles);
		}
		imageText.setArticles(items);
		return baseMessageToXML(imageText);
	}
	
	
}
