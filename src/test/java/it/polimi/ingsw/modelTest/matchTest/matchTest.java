package it.polimi.ingsw.modelTest.matchTest;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.LinkedList;

public class matchTest {

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
    @DisplayName("")
    public void testRanking() {

        underTest.createRanking();
        for (int i = 0; i < underTest.getRanking().size() -1 ; i++) {
            assertTrue(underTest.getRanking().get(i).getVictoryPoints() >= underTest.getRanking().get(i + 1).getVictoryPoints());
        }
    }
}