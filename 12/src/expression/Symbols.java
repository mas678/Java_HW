package expression;

import java.util.Map;

class Symbols {
    static final Map<String, Integer> LEVEL = Map.of(
            ">>", 1,
            "<<", 1,
            "+", 2,
            "-", 2,
            "*", 3,
            "/", 3,
            "", 4
    );
}
