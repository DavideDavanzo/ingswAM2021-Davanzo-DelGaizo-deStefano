package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.resources.Coin;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.utils.Parser;
import org.junit.jupiter.api.Test;

public class AppTest {
    @Test
    public void shouldAnswerWithTrue() { assertTrue(true); }

    @Test
    public void Parser() {
        Parser parser = new Parser();

        Resource coin = new Coin(3);

        System.out.println(parser.serialize(coin));

        Resource resource = (Resource) parser.deserialize(parser.serialize(coin), Resource.class);

        System.out.println(resource.toString());

        System.out.println("\uD83E\uDC17");
        System.out.println("\uD83D\uDE14");
        System.out.println("You lost to Lorenzo. He reached the end before you " + "🙁 🙁 🙁");
    }

}