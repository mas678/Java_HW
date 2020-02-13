package mnk;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        int result;
        final List<Player> playerList = List.of(new HumanPlayer(), new RandomPlayer(),
                new RandomPlayer());
        do {
            Game game = new Game(false, playerList);
            result = game.play(new ServerBoard());
            System.out.println("Game result: " + result);
        } while (result != 0);
    }
}
