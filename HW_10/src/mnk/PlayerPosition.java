package mnk;

public class PlayerPosition implements Position {
    private Position serverPosition;

    PlayerPosition(Position serverPosition) {
        this.serverPosition = serverPosition;
    }

    @Override
    public boolean isValid(Move move) {
        return serverPosition.isValid(move);
    }

    @Override
    public Cell getCell(int r, int c) {
        return serverPosition.getCell(r, c);
    }

    @Override
    public String toString() {
        return serverPosition.toString();
    }
}
