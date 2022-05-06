package com.example.emos.wx.exception;

import lombok.Data;

@Data
public class EmosException extends RuntimeException{
private String msg;
private int code = 500;

    public EmosException(String msg) {
        super(msg);
        this.msg = msg;
    }
}