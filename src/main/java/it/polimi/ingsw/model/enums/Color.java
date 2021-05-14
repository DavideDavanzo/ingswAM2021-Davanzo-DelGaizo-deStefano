package it.polimi.ingsw.model.enums;

public enum Color {
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_PURPLE("\u001B[35m"),
    ANSI_GREY("\u001B[37m"),
    ANSI_WHITE("\u001B[0m");

    public static final String RESET = "\u001B[0m";

    private String escape;

    Color(String escape) {
        this.escape = escape;
    }

    public String escape() {
        return escape;
    }
}
