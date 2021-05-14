package it.polimi.ingsw.model.enums;

import it.polimi.ingsw.CliPrinter;

public enum ECardColor implements CliPrinter {
    GREEN, BLUE, YELLOW, PURPLE;


    public String print(ECardColor color) {
        switch (color) {
            case GREEN:
               return Color.ANSI_GREEN.escape() + "█ " + Color.RESET;
            case PURPLE:
                Color.ANSI_PURPLE.escape();
                return "█";
            case BLUE:
                Color.ANSI_BLUE.escape();
                return "█";
            case YELLOW:
                Color.ANSI_YELLOW.escape();
                return "█";
        }
        return null;
    }

    @Override
    public String print() {
        return print(this);
    }



}