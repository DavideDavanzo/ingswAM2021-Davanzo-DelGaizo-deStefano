package it.polimi.ingsw.model.effects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.requirements.BlankRequirement;
import it.polimi.ingsw.model.requirements.ColorLevelRequirement;
import it.polimi.ingsw.model.requirements.ColorRequirement;
import it.polimi.ingsw.model.requirements.ResourceRequirement;
import it.polimi.ingsw.model.resources.FaithPoint;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BlankEffect.class, name = "blankEff"),
        @JsonSubTypes.Type(value = DiscountEffect.class, name = "discountEff"),
        @JsonSubTypes.Type(value = ExtraDevEffect.class, name = "extraDevEff"),
        @JsonSubTypes.Type(value = ExtraShelfEffect.class, name = "extraShelfEff"),
        @JsonSubTypes.Type(value = WhiteMarbleEffect.class, name = "whiteMarbleEff")
})
/**
 * <h1>Effect</h1>
 * An Effect is a special special power that can be activated
 * through a {@link LeaderCard}. Once active, it lasts until the
 * end of the game
 */
public abstract class Effect {

    /**
     * Applies the effect on a Player
     * @param p is the {@link Player}
     */
    public abstract void applyOn(Player p);

    /**
     * Special Getter
     * @return is the attribute of an Effect
     * also see: {@link ExtraShelfEffect#returnAttribute()}
     */
    public abstract Object returnAttribute();
}
