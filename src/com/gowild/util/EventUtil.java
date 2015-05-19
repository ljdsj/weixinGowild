package com.gowild.util;

import java.util.Map;

public class EventUtil {
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "click";
	public static final String MESSAGE_VIEW = "view";
	
	public static String eventClickHandle(Map<String,String> map){
		String result = null;
		String eventType = map.get("EventKey");
		StringBuffer sb = new StringBuffer();
		System.out.println(" eventType : " + eventType);
		if("联系我们".equals(eventType)){
			sb.append("电话：0755-29105899\n");
			sb.append("官网：http://www.gowild.cn \n");
			sb.append("地址：深圳市宝安区82区新湖路华美居商务中心A区C座806");
			result = MessageUtil.sendText(map.get("ToUserName"), map.get("FromUserName"), sb.toString());
		}else if("公司动态".equals(eventType)){
			//推送相关新闻
			result = MessageUtil.sendImageANDText(map);
		}else if("与小白聊天".equals(eventType)){
			sb.append("你可以随便和我聊聊~");
			result = MessageUtil.sendText(map.get("ToUserName"), map.get("FromUserName"), sb.toString());
		}else if("人工客服".equals(eventType)){
			sb.append("您好，请回复一下序号我们将挑选相应的客服为您服务。\n");
			sb.append("[1]客服01\n");
			sb.append("[2]客服02\n");
			sb.append("[3]客服03\n");
			sb.append("[4]客服04\n");
			sb.append("[5]客服05\n");
			sb.append("[6]客服06\n");
			sb.append("[X]其他");
			result = MessageUtil.sendText(map.get("ToUserName"), map.get("FromUserName"), sb.toString());
		}else{
			sb.append("其他");
		}
		return result;
	}
	/**
	 * 事件处理
	 * @param eventKey
	 * @return
	 */
	public static String eventHandle(Map<String,String> map){
		String eventResult = null;
		String event = map.get("Event").toLowerCase();
		switch(event){
		case MESSAGE_SUBSCRIBE:
			eventResult = MessageUtil.sendText(map.get("ToUserName"),map.get("FromUserName"),"您好，我是小白，欢迎您的关注");
			break;
		case MESSAGE_CLICK:
			eventResult = eventClickHandle(map);
			break;
		case MESSAGE_VIEW:
			
			break;
		default:
			break;
		}
		return eventResult;
	}
	
}
