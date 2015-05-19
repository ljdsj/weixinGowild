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
		if("��ϵ����".equals(eventType)){
			sb.append("�绰��0755-29105899\n");
			sb.append("������http://www.gowild.cn \n");
			sb.append("��ַ�������б�����82���º�·��������������A��C��806");
			result = MessageUtil.sendText(map.get("ToUserName"), map.get("FromUserName"), sb.toString());
		}else if("��˾��̬".equals(eventType)){
			//�����������
			result = MessageUtil.sendImageANDText(map);
		}else if("��С������".equals(eventType)){
			sb.append("���������������~");
			result = MessageUtil.sendText(map.get("ToUserName"), map.get("FromUserName"), sb.toString());
		}else if("�˹��ͷ�".equals(eventType)){
			sb.append("���ã���ظ�һ��������ǽ���ѡ��Ӧ�Ŀͷ�Ϊ������\n");
			sb.append("[1]�ͷ�01\n");
			sb.append("[2]�ͷ�02\n");
			sb.append("[3]�ͷ�03\n");
			sb.append("[4]�ͷ�04\n");
			sb.append("[5]�ͷ�05\n");
			sb.append("[6]�ͷ�06\n");
			sb.append("[X]����");
			result = MessageUtil.sendText(map.get("ToUserName"), map.get("FromUserName"), sb.toString());
		}else{
			sb.append("����");
		}
		return result;
	}
	/**
	 * �¼�����
	 * @param eventKey
	 * @return
	 */
	public static String eventHandle(Map<String,String> map){
		String eventResult = null;
		String event = map.get("Event").toLowerCase();
		switch(event){
		case MESSAGE_SUBSCRIBE:
			eventResult = MessageUtil.sendText(map.get("ToUserName"),map.get("FromUserName"),"���ã�����С�ף���ӭ���Ĺ�ע");
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
