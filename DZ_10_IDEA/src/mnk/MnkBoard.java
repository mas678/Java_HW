package mnk;

import java.util.List;

public class MnkBoard implements BoardAndPosition {
    private ServerBoard trueBoard;

    public MnkBoard() {
        trueBoard = new ServerBoard();
    }

    @Override
    public boolean isValid(Move move) {
        return trueBoard.isValid(move);
    }

    @Override
    public Cell getCell(int r, int c) {
        return trueBoard.getCell(r, c);
    }

    @Override
    public Cell getCell() {
        return trueBoard.getCell();
    }

    @Override
    public Result makeMove(Move move, int nxt) {
        return trueBoard.makeMove(move, nxt);
    }

    @Override
    public String toString() {
        return trueBoard.toString();
    }
}
