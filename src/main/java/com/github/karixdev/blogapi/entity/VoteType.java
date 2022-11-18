package com.github.karixdev.blogapi.entity;

public enum VoteType {
    UP("up"),
    DOWN("down");

    private final String type;

    VoteType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
