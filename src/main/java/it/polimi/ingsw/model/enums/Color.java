package it.polimi.ingsw.model.enums;

/**
 * Colors enumeration for the CLI.
 */
public enum Color {
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_PURPLE("\u001B[35m"),
    ANSI_GREY("\u001B[37m"),
    ANSI_BLACK("\033[1;30m"),
    ANSI_WHITE("\u001B[0m"),
    ANSI_DARK_GREY("\u001B[90m");


    public static final String RESET = "\u001B[0m";

    private String escape;

    Color(String escape) {
        this.escape = escape;
    }

    public String escape() {
        return escape;
    }
}
