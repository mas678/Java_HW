package mnk;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");
            int a = 0, b = 0;
            boolean f = true;
            if (in.hasNextInt()) {
                a = in.nextInt();
                if (in.hasNextInt()) {
                    b = in.nextInt();
                    f = false;
                }
            }
            in.nextLine();
            if (f) {
                out.println("Input is incorrect");
                continue;
            }
            final Move move = new Move(a, b, cell);
            if (position.isValid(move)) {
                return move;
            }
            out.println("Move " + move + " is invalid");
        }
    }
}
