package com.sparta.nbcamblog.chatting;// package com.sparta.morningworkout.chatting;
//
// import org.springframework.messaging.Message;
// import org.springframework.messaging.MessageChannel;
// import org.springframework.messaging.simp.stomp.StompCommand;
// import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
// import org.springframework.messaging.support.ChannelInterceptor;
// import org.springframework.security.access.AccessDeniedException;
// import org.springframework.stereotype.Component;
//
// import com.sparta.morningworkout.jwtUtil.JwtUtil;
//
// import lombok.RequiredArgsConstructor;
//
// @RequiredArgsConstructor
// @Component
// public class StompHandler implements ChannelInterceptor {
// 	private final JwtUtil jwtUtil;
//
// 	@Override
// 	public org.springframework.messaging.Message<?> preSend(Message<?> message, MessageChannel channel) {
// 		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
// 		if (accessor.getCommand() == StompCommand.CONNECT) { // 웹소켓 연결
// 			jwtUtil.validateToken(accessor.getFirstNativeHeader("Authorization"));
// 			if (!jwtUtil.validateToken(accessor.getFirstNativeHeader("Authorization")))
// 			    throw new AccessDeniedException("유효한 토큰이 아닙니다.");
// 		}
// 		return message;
// 	}
// }
