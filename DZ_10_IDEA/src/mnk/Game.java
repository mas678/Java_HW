package mnk;

import java.util.List;

public class Game {
    private final boolean log;
    private final List<Player> players;

    public Game(final boolean log, List<Player> players)  {
        this.log = log;
        this.players = players;
    }

    public int play(BoardAndPosition board) {
        while (true) {
            for (int i = 0; i < players.size(); i++) {
                final int result = move(board, players.get(i), i + 1);
                if (result != -1) {
                    return result;
                }
            }
        }
    }

    private int move(final BoardAndPosition board, final Player player, final int no) {
        final Move move = player.move(board, board.getCell());
        final Result result = board.makeMove(move, no % players.size() + 1);
        log("Player " + no + " move: " + move);
        log("Position:\n" + board);
        if (result == Result.WIN) {
            log("Player " + no + " won");
            return no;
        } else if (result == Result.DRAW) {
            log("Draw");
            return 0;
        } else {
            return -1;
        }
    }

    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }
}
