package it.polimi.ingsw.model.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.effects.*;
import it.polimi.ingsw.model.resources.Item;

import java.util.ArrayList;
import java.util.Random;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = YellowMarble.class, name = "yellow"),
        @JsonSubTypes.Type(value = BlueMarble.class, name = "blue"),
        @JsonSubTypes.Type(value = GreyMarble.class, name = "grey"),
        @JsonSubTypes.Type(value = RedMarble.class, name = "red"),
        @JsonSubTypes.Type(value = WhiteMarble.class, name = "white"),
        @JsonSubTypes.Type(value = PurpleMarble.class, name = "purple")
})
/**
 *Abstract class which represents Marble
 */
public abstract class Marble {


  public abstract Item returnItem();



}