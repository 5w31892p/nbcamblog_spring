package com.sparta.nbcamblog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BlogRequestDto {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
}
