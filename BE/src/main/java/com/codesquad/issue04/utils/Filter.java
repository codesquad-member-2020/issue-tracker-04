package com.codesquad.issue04.utils;

public enum Filter {
    EMPTY("empty"),
    AUTHORED("authored"),
    ASSIGNED("assigned"),
    COMMENTED("commented"),
    AUTHOR("author"),
    MILESTONE("milestone"),
    LABEL("label"),
    ASSIGNEE("assignee");

    private final String param;

    Filter(String param) {
        this.param = param;
    }

    public String param() {
        return this.param;
    }
}
