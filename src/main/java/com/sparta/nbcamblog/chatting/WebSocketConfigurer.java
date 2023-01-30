package com.sparta.nbcamblog.chatting;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // 메시지 처리 활성화
// @RequiredArgsConstructor
public class WebSocketConfigurer implements WebSocketMessageBrokerConfigurer {
	// private final StompHandler stompHandler; // jwt 인증

	// 메세지 도착 지점 url로 등록
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {

		registry.addEndpoint("/ws") // 웹소켓 연결을 위한 주소 배정
			.setAllowedOriginPatterns("*");
			// .withSockJS(); // sock.js를 통하여 낮은 버전의 브라우저에서도 websocket이 동작할수 있게 합니다.
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) { // broker 설정 부분

		config.enableSimpleBroker("/sub"); // 받을 때
		config.setApplicationDestinationPrefixes("/pub"); // send처리
	}

	// @Override
	// public void configureClientInboundChannel(ChannelRegistration registration) {
	// 	registration.interceptors(stompHandler);
	// }
}
