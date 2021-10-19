package com.jwyoon.www.common.util;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

public class StompHandler extends ChannelInterceptorAdapter {

	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		
        switch (accessor.getCommand()) {
            case CONNECT:
                // ?��??�? Websocket?���? connect()�? ?�� ?�� ?��출됨
            	//System.out.println("CONNECT USER : "+userName);
            	System.out.println("Connect : "+accessor.toString());
                break;
            case DISCONNECT:
                // ?��??�? Websocket?���? disconnect() �? ?�� ?�� ?��출됨 or ?��?��?�� ?��?��졌을 ?�� 발생?��(?��?���? ?��?��~ 브라?��?? ?���? ?��)
            	//System.out.println("DISCONNECT USER : "+userName);
            	System.out.println("disConnect : "+accessor.toString());
                break;
            default:
                break;
        }
	}
	
	
}
