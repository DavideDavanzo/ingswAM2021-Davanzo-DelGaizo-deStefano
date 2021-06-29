package it.polimi.ingsw.modelTest.matchTest;

import it.polimi.ingsw.exceptions.InvalidInputException;
import it.polimi.ingsw.exceptions.playerboardExceptions.resourcesExceptions.NotEnoughResourcesException;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.resources.Coin;
import it.polimi.ingsw.model.resources.Servant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.LinkedList;

public class MatchTest {

    Match underTest;

    LinkedList<Player> players = new LinkedList<>();


    @BeforeEach
    void setUp() {
        underTest = new Match();

        Player player1 = new Player("Alessandra");
        player1.setVictoryPoints(10);

        Player player2 = new Player("Dario");
        player2.setVictoryPoints(172);

        Player player3 = new Player("Davide");
        player3.setVictoryPoints(7);

        underTest.addPlayer(player1);
        underTest.addPlayer(player2);
        underTest.addPlayer(player3);
    }

    @Test
    public void testRanking() {

        LinkedList<Player> finalRanking = underTest.getRanking();
        for (int i = 0; i < finalRanking.size() -1 ; i++) {
            assertTrue(finalRanking.get(i).getVictoryPoints() >= finalRanking.get(i + 1).getVictoryPoints());
        }

    }

    @Test
    public void tieBreakerTest() throws InvalidInputException, NotEnoughResourcesException {

        Match match = new Match();

        Player p1 = new Player("ale");
        Player p2 = new Player("dario");
        Player p3 = new Player("lorenzo");
        Player p4 = new Player("dave");

        match.addPlayer(p1);
        match.addPlayer(p2);
        match.addPlayer(p3);
        match.addPlayer(p4);

        p1.moveForward(20); //17 points including pope tokens
        p1.getWarehouse().getFirstShelf().setShelfResource(new Coin(1));
        p1.getWarehouse().getThirdShelf().setShelfResource(new Servant(2)); //3 resources in the Wh
        p1.getPlayerBoard().getCoffer().updateCoffer(new Coin(2)); // 3 + 2 = 5 -> 1 extra point = 18
        //no cards

        p2.moveForward(20); //12 points
        p2.getPlayerBoard().getCoffer().updateCoffer(new Coin(6)); //6/5 = 1 extra point -> 13 BUT 6 resources (p1 has 5)
        //no cards

        p3.moveForward(21); //16 points
        p3.getPlayerBoard().getCoffer().updateCoffer(new Servant(4)); //4 items-> no extra point 16 points and 4 resources

        p4.moveForward(21); //16 points
        p4.getPlayerBoard().getCoffer().updateCoffer(new Coin(6)); //17 points

        //ranking should be: dave=18points(6 resources) dario=21points(6 resources) ale=21points(5 resources) lorenzo=21points(4 resources)

        LinkedList<Player> ranking = match.getRanking();
        for (Player p: ranking) System.out.println(p.getNickname() + " " + p.getCurrentVictoryPoints());

    }
}