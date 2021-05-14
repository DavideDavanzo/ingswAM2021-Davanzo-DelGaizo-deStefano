package it.polimi.ingsw.model.requirements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.view.CliPrinter;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.DevelopmentCard;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BlankRequirement.class, name = "blankReq"),
        @JsonSubTypes.Type(value = ColorLevelRequirement.class, name = "colorLevelReq"),
        @JsonSubTypes.Type(value = ColorRequirement.class, name = "colorReq"),
        @JsonSubTypes.Type(value = FaithPointsRequirement.class, name = "faithReq"),
        @JsonSubTypes.Type(value = ResourceRequirement.class, name = "resourceReq")
})
/**
 * <h1>Requirement</h1>
 * A Requirement is a set of attributes that the {@link Player} must match
 * in order to activate a certain {@link LeaderCard}. Types of Requirements
 * can be a set of {@link DevelopmentCard} of different color, having reached
 * a certain position on the path etc.
 */
public abstract class Requirement implements CliPrinter {

    /**
     * Returns true if the activation of a certain {@link LeaderCard} is valid.
     * @param p is the Player on which the control is done.
     * @return
     */
    public abstract boolean validateOn(Player p);

    /**
     * Special Getter that returns the unique attribute of a Requirement.
     * @return
     */
    public abstract Object returnAttribute();




}
