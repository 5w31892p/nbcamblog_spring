package com.sparta.nbcamblog.chatting.entity;

import java.io.*;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRoomId implements Serializable {

	private Long room;
	private Long blogId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MessageRoomId messageRoomId = (MessageRoomId)o;
		return Objects.equals(getRoom(), messageRoomId.getRoom());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getRoom());
	}
}
