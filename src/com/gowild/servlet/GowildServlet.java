package com.gowild.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.gowild.po.BaseMessage;
import com.gowild.po.TextMessage;
import com.gowild.util.CheckUtil;
import com.gowild.util.EventUtil;
import com.gowild.util.MessageUtil;
import com.gowild.util.ReadFileUtil;
import com.gowild.util.StringUtil;

public class GowildServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		PrintWriter out = resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			Map<String,String> map = MessageUtil.xmpToMap(req);
			String msgType = map.get("MsgType");
			String message = null;
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				TextMessage text = new TextMessage();
				BaseMessage base = new BaseMessage();
				String customer_service[] = {"1","2","3","4","5","6","X","x"};
				String content = map.get("Content");
				boolean flag = StringUtil.isContains(content, customer_service);
				System.out.println("是否接入人工客服 ："+flag);
				if(flag){
					//客服
					base.setFromUserName(map.get("ToUserName"));
					base.setToUserName(map.get("FromUserName"));
					base.setCreateTime(new Date().getTime());
					base.setMsgType("transfer_customer_service");
					message = MessageUtil.baseMessageToXML(base);
				}else{
					String result = null;
					text.setFromUserName(map.get("ToUserName"));
					text.setToUserName(map.get("FromUserName"));
					text.setMsgType(map.get("MsgType"));
					text.setCreateTime(new Date().getTime());
					//关键字回复
					URL url = this.getClass().getResource("/");
					String keywordsPath = url.getPath().substring(0, url.getPath().indexOf("/WEB-INF/"))+
							File.separator+"files"+File.separator+"keywords.txt";
//					Map<String,String> keywords = ReadExcelUtil.readExcel(keywordsPath);
					Map<String,String> keywords = ReadFileUtil.readTxt(keywordsPath);
					if(keywords.containsKey(content)){
						result = keywords.get(content);
						System.out.println("关键字回复："+result);
					}else{
						//小白回复
						result = StringUtil.chatWithXiaoBai(content);
						System.out.println("小白回复："+result);
						if(result == null || result.length() <= 0){
							//图灵机器人回复
							result = StringUtil.textByTuling(content);
						}
					}
					text.setContent(result);
					message = MessageUtil.baseMessageToXML(text);
				}
			}else if(MessageUtil.MESSAGE_IMAGE.equals(msgType)){
				message = MessageUtil.sendImage(map);
			}else if(MessageUtil.MESSAGE_VOICE.equals(msgType)){
				message = MessageUtil.sendVoice(map);
			}else if(MessageUtil.MESSAGE_LINK.equals(msgType)){
				message = "暂时没有链接相关信息";
			}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				message = EventUtil.eventHandle(map);
			}else{
				message = "";
			}
			System.out.println(" result : \n" + message);
			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
}
