package it.polimi.ingsw.model.resources;


/**
 * Abstract class which represents a generic resource of the four given in the game (coin, stone, shield, servant)
 */
public abstract class Resource extends Item{

    /**
     * Number of resources in the instance
     */
    private int volume;

    public Resource(){
    }

    public Resource(int volume){
        this.volume = volume;
    }

    /**
     * Inherited from abstract class "Item", it increases or decreases the attribute "volume"
     * @param num is the amount added (positive or negative)
     */
    @Override
    public void update(int num) {
        volume = volume + num > 0 ? volume + num : 0;
    }

    /**
     * Getter for volume attribute
     * @return
     */
    public int getVolume() {
        return volume;
    }

}
