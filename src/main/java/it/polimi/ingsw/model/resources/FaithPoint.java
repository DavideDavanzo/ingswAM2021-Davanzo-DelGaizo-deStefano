package it.polimi.ingsw.model.resources;

/**
 * Class which represents an amount of faith points
 */
public class FaithPoint extends Item {

    /**
     * Attribute which represents the number of the instance's faith points
     */
    private int numPoints;

    /**
     * Default constructor
     */
    public FaithPoint(){
    }

    /**
     * Parameterized constructor
     * @param numPoints
     */
    public FaithPoint(int numPoints) {
        this.numPoints = numPoints;
    }

    /**
     * Inherited from abstract class "Item", it increases or decreases the number of faith points
     * @param num is the amount added (positive or negative)
     */
    @Override
    public void update(int num) {
        numPoints = numPoints + num > 0 ? numPoints + num : 0;
    }

    /**
     * Getter method for the attribute "numPoints"
     * @return
     */
    public int getNumPoints() {
        return numPoints;
    }

}
