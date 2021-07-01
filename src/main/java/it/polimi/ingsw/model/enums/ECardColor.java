package it.polimi.ingsw.model.enums;

import it.polimi.ingsw.view.cli.CliPrinter;

import java.util.HashMap;

/**
 * Enumeration for the colors of the cards
 */
public enum ECardColor implements CliPrinter {
    GREEN, BLUE, YELLOW, PURPLE;

    /**
     * Maps the {@link ECardColor} with its corresponding ANSI_COLOR.
     */
    private static final HashMap<ECardColor, Color> colorMap = new HashMap<ECardColor, Color>(){{
        put(GREEN, Color.ANSI_GREEN);
        put(BLUE, Color.ANSI_BLUE);
        put(YELLOW, Color.ANSI_YELLOW);
        put(PURPLE, Color.ANSI_PURPLE);
    }};


    public String print(ECardColor color) {
        return colorMap.get(color).escape() + " █" + Color.RESET;
    }

    @Override
    public String print() {
        return print(this);
    }

    public static HashMap<ECardColor, Color> getColorMap() {
        return colorMap;
    }
}