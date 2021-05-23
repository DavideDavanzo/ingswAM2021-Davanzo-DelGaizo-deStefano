package it.polimi.ingsw.model.lorenzo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.LossException;
import it.polimi.ingsw.network.messages.*;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TossDevCardsToken.class, name = "TossDevCardsToken"),
        @JsonSubTypes.Type(value = CrossToken.class, name = "CrossToken"),
        @JsonSubTypes.Type(value = CrossAndShuffleToken.class, name = "CrossAndShuffleToken")
})
/**
 * Class that represents the tokens used to play a solo match
 */
public abstract class LorenzoToken {

    private boolean flipped;

    public LorenzoToken() {
        this.flipped = false;
    }

    public void flip(){
        flipped = !flipped;
    }

    public abstract void activate(LorenzoIlMagnifico lorenzo) throws LossException;

    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }
}
