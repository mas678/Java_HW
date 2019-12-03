import expression.*;
import base.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(new Divide(new Variable("x"), new Const(-2)).toMiniString());
    }
}
