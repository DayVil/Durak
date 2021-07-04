package de.uni_hannover.hci.cardgame.gameLogic;

public class sandBoxToBeDeleted {

    public static void main(String[] args) {
        int[] ids = {0, 3, 8, 4};
        String[] names = {"Hansel", "Franzl", "Dansl", "Mansl"};

        GameManager game = new GameManager(ids, names);

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");

        System.out.println(game.gameBoardState(3));

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
    }
}
