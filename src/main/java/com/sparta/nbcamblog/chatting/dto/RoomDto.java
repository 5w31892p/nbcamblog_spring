package com.sparta.nbcamblog.chatting.dto;// package com.sparta.morningworkout.zzchat;
//
// import java.util.HashSet;
// import java.util.Set;
//
// import org.springframework.web.socket.WebSocketSession;
//
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
//
// @Getter
// @Builder
// @AllArgsConstructor
// @NoArgsConstructor
// public class RoomDto {
// 	private long roomId;
// 	private long productId;
// 	private Set<WebSocketSession> sessions = new HashSet<>();
//
// 	public RoomDto(ChatRoom room) {
// 		this.roomId = room.getId();
// 		this.productId = room.getProductId();
// 	}
//
// 	public void handlerActions(WebSocketSession session, MessageDto message, RoomService roomService) {
// 		if (message.getType().equals(MessageType.JOIN)) {
// 			sessions.add(session);
// 			MessageDto.builder().message(message.getSender() + "님이 입장했습니다.");
// 		}
// 		send(message, roomService);
//
// 	}
//
// 	private <T> void send(T message, RoomService roomService) {
// 		sessions.parallelStream()
// 			.forEach(session -> roomService.sendMessage(session, message));
// 	}
//
// 	// private List<MessageDto> messages;
// 	//
// 	// public RoomDto(ChatRoom room) {
// 	//     this.roomId = room.getId();
// 	//     this.productId = room.getProduct().getId();
// 	//     List<MessageDto> messageList = room.getMessages().stream().map(MessageDto::new).collect(Collectors.toList());
// 	//     this.messages = messageList;
// 	// }
// }
