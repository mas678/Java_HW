package mnk;

import java.util.List;

public class MnkBoard implements Position {
    private ServerBoard trueBoard;

    public MnkBoard() {
        trueBoard = new ServerBoard();
    }

    public int game(List<Player> PLAYERS_LIST) {
        final Game game = new Game(false, PLAYERS_LIST);
        return game.play(trueBoard);
    }

    @Override
    public boolean isValid(Move move) {
        return trueBoard.isValid(move);
    }

    @Override
    public Cell getCell(int r, int c) {
        return trueBoard.getCell(r, c);
    }
}
