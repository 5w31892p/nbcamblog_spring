package com.sparta.nbcamblog.dto;

import lombok.Getter;

@Getter
public class DeleteResponseDto {
    private int statusCode = 200;
    private String message = "삭제가 완료되었습니다.";
}
