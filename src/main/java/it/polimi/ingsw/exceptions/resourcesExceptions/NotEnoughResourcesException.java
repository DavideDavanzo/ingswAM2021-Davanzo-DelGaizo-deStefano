package it.polimi.ingsw.exceptions.resourcesExceptions;

/**
 * Exception thrown when player's resources are not enough to pay a card or activate production
 */
public class NotEnoughResourcesException extends Exception{

    public NotEnoughResourcesException(String message){
        super(message);
    }

}
