package mnk;

public interface Board {
    Cell getCell();
    Result makeMove(Move move, int nxt);
}
