package com.sparta.nbcamblog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteResponseDto {
    private String msg = "삭제가 완료되었습니다.";
    private int statuscode = 200;
}
