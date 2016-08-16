package com.demo.mvpdemo.http.exception;

public class ApiException extends RuntimeException {
    public ApiException(Type type) {
        this(type.toString());
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    public enum Type {
        USER_NOT_EXIST(1, "用户不存在!");

        private int code;
        private String message;

        Type(int code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String toString() {
            return this.message;
        }
    }
}

