package com.codesquad.issue04.domain.issue;

public enum Emoji {

    THUMBSUP("U+1F44D", "thumbsup"),
    THUMBSDOWN("U+1F44E", "thumbsdown"),
    LAUGH("U+1F923", "laugh"),
    HOORAY("U+1F389", "hooray"),
    CONFUSED("U+1F615", "confused"),
    HEART("U+1F493", "heart"),
    ROCKET("U+1F680", "rocket"),
    EYES("U+1F644", "eyes");


    private final String unicode;
    private final String name;

    Emoji(String unicode, String name) {
        this.unicode = unicode;
        this.name = name;
    }
}
