package mnk;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ServerBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.',
            Cell.I, '|',
            Cell.S, '-'
    );

    private static final List<Cell> NEXT = List.of(
            Cell.X,
            Cell.O,
            Cell.I,
            Cell.S
    );


    private final Cell[][] cells;
    private Cell turn;
    private int emptyCnt;

    ServerBoard() {
        this.cells = new Cell[MnkConst.N][MnkConst.M];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
        emptyCnt = MnkConst.M * MnkConst.N;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move, int nxt) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        cells[move.getRow()][move.getColumn()] = move.getValue();
        emptyCnt--;

        if (won(move, 1, 0) || won(move, 1, 1) ||
                won(move, 1, -1) || won(move, 0, 1)) {
            return Result.WIN;
        }
        if (emptyCnt == 0) {
            return Result.DRAW;
        }

        turn = NEXT.get(nxt - 1);
        return Result.UNKNOWN;
    }

    private boolean won(Move move, int du, int dv) {
        return count(move, du, dv) >= MnkConst.K;
    }

    private int count(Move move, int du, int dv) {
        return count(move.getRow(), move.getColumn(), du, dv);
    }

    private int count(int u, int v, int dv, int du) {
        return checker(v, u, dv, du) +
                checker(v, u, -dv, -du) - 1;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < MnkConst.N
                && 0 <= move.getColumn() && move.getColumn() < MnkConst.M
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == getCell();
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(
                " ".repeat(Integer.toString(MnkConst.N).length() + 1));
        for (int c = 0; c < MnkConst.M; c++) {
            sb.append(c);
            sb.append(" ".repeat(Integer.toString(MnkConst.M).length() -
                    Integer.toString(c).length() + 1));
        }
        for (int r = 0; r < MnkConst.N; r++) {
            sb.append("\n");
            sb.append(r);
            sb.append(" ".repeat(Integer.toString(MnkConst.N).length() -
                    Integer.toString(r).length() + 1));
            for (int c = 0; c < MnkConst.M; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
                sb.append(" ".repeat(Integer.toString(MnkConst.M).length()));
            }
        }
        return sb.toString();
    }

    private int checker(int v, int u, int vDelta, int uDelta) {
        int cnt = 0;
        while (v >= 0 && u >= 0 && v < MnkConst.M && u < MnkConst.N
                && cells[u][v] == turn) {
            v += vDelta;
            u += uDelta;
            cnt++;
        }
        return cnt;
    }
}
