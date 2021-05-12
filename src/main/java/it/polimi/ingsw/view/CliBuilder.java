package it.polimi.ingsw.view;

public class CliBuilder {

    public enum Color {
        ANSI_RED("\u001B[31m"),
        ANSI_GREEN("\u001B[32m"),
        ANSI_YELLOW("\u001B[33m"),
        ANSI_BLUE("\u001B[34m");

        static final String RESET = "\u001B[0m";

        private String escape;

        Color(String escape) {
            this.escape = escape;
        }

        public String escape() {
            return escape;
        }
    }

    static void shape(String[][] rectangle, int maxHorizTiles, int maxVertTiles) {
        rectangle[0][0] = "╔";
        for (int c = 1; c < maxHorizTiles - 1; c++) {
            rectangle[0][c] = "═";
        }

        rectangle[0][maxHorizTiles - 1] = "╗";

        for (int r = 1; r < maxVertTiles - 1; r++) {
            rectangle[r][0] = "║";
            for (int c = 1; c < maxHorizTiles - 1; c++) {
                rectangle[r][c] = " ";
            }

            rectangle[r][maxHorizTiles - 1] = "║";
        }

        rectangle[maxVertTiles - 1][0] = "╚";
        for (int c = 1; c < maxHorizTiles - 1; c++) {
            rectangle[maxVertTiles - 1][c] = "═";
        }

        rectangle[maxVertTiles - 1][maxHorizTiles - 1] = "╝";
    }

}





