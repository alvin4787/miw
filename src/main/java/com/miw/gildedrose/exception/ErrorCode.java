package com.miw.gildedrose.exception;

public enum ErrorCode {

    ITEM_NOT_FOUND(1, "Item not found"),
    USER_NOT_FOUND(2, "User not found");

    private int code;
    private String description;

    ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
