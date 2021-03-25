package it.polimi.ingsw.model.resources;


/**
 * Abstract class which represents either a resource or faith points
 */
abstract public class Item {

    /**
     * Abstract method which increases or decreases the integer attribute of the subclass
     * @param num is the amount added (positive or negative)
     */
    abstract public void update(int num);

}
