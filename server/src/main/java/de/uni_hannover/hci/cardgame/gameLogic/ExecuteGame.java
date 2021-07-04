package de.uni_hannover.hci.cardgame.gameLogic;

//TODO receive message with getMessage()
//TODO get names
public class ExecuteGame {

    public void runGame(int[] ids) {
        String[] names = {"Hansel", "Franzl", "Dansl", "Mansl"};
        GameManager game = new GameManager(ids, names);
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
