package com.sparta.nbcamblog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BlogRequestDto {
    private String author;
    private String password;
    private String title;
    private String content;
}
