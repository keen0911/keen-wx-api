package com.example.keen.wx.exception;

import lombok.Data;

@Data
public class KeenException extends RuntimeException{
private String msg;
private int code = 500;

    public KeenException(String msg) {
        super(msg);
        this.msg = msg;
    }
    public KeenException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public KeenException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public KeenException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
