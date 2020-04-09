package expression.parser;

import expression.exceptions.*;

import java.util.List;
import java.util.Map;

class ExpressionMaps {
    static final Map<Character, List<BinaryParserConst>> BINARY_OPERATIONS = Map.of(
            '*', List.of(new BinaryParserConst(4, CheckedPower::new, "*"),
                    new BinaryParserConst(3, CheckedMultiply::new, "")),
            '/', List.of(new BinaryParserConst(4, CheckedLogarithm::new, "/"),
                    new BinaryParserConst(3, CheckedDivide::new, "")),
            '-', List.of(new BinaryParserConst(2, CheckedSubtract::new, "")),
            '+', List.of(new BinaryParserConst(2, CheckedAdd::new, ""))
    );

    static final Map<Character, String> Variables = Map.of(
            'x', "x",
            'y', "y",
            'z', "z"
    );
}
