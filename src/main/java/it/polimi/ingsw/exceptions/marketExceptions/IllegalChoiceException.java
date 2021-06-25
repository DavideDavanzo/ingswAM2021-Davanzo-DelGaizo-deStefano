package it.polimi.ingsw.exceptions.marketExceptions;
/**
 * This class implements the exception for an invalid choice other than row or column in market
 */
public class IllegalChoiceException extends Throwable {
    public IllegalChoiceException(String message) { super(message); }
}
