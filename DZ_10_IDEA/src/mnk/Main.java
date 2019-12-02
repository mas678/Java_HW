package mnk;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        int result;
        final List<Player> PLAYERS_LIST = List.of(new RandomPlayer(), new RandomPlayer(),
                new RandomPlayer(), new HumanPlayer());
        do {
            result = new MnkBoard().game(PLAYERS_LIST);
            System.out.println("Game result: " + result);
        } while (result != 0);
    }
}
