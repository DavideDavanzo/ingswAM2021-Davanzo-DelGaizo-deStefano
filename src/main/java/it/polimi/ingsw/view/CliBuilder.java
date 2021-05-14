package it.polimi.ingsw.view;

public class CliBuilder {

    public static String[][] shape(String[][] rectangle, int maxHorizTiles, int maxVertTiles) {
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

        return rectangle;

    }

}





