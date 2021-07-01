package it.polimi.ingsw.exceptions;

/**
 * This class implements the exception for a failed production
 */
public class ProductionFailException extends Exception{
    public ProductionFailException(String message) {
        super(message);
    }
}
