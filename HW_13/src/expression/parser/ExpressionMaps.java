package expression.parser;

import expression.*;
import expression.exceptions.*;

import java.util.Map;

class ExpressionMaps {
    static final Map<Character, BinaryParserConst> binaryOperationMap = Map.of(
            '*', new BinaryParserConst(3, CheckedMultiply::new, ""),
            '/', new BinaryParserConst(3, CheckedDivide::new, ""),
            '-', new BinaryParserConst(2, CheckedSubtract::new, ""),
            '+', new BinaryParserConst(2, CheckedAdd::new, "")
    );

    static final Map<Character, String> Variables = Map.of(
            'x', "x",
            'y', "y",
            'z', "z"
    );
}
