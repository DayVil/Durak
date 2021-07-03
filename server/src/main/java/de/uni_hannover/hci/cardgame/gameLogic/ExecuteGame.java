package de.uni_hannover.hci.cardgame.gameLogic;

import de.uni_hannover.hci.cardgame.gameLogic.GameManager;

import java.util.ArrayList;


//TODO receive message with getMessage()
//TODO get names
public class ExecuteGame {

    public void runGame(int[] ids) {
        GameManager game = new GameManager(ids);
        String str = "0";

        game.initGame();

        do {
            if (game.isVisibleCardsfull()) { //Defender won
                game.newTurn(true);
            } else if (str.equals("pass")) {

            } else if (str.equals("take")) {
                game.newTurn(false);
            } else { // This is a num

            }
        } while (false);
    }
}
