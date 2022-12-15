package com.example.nbcamblog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeletePostDto {
    private String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
