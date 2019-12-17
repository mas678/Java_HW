package expression.parser;

import expression.*;

import java.util.Map;

class ExpressionMaps {
    static final Map<Character, BinaryParserConst> binaryOperationMap = Map.of(
            '*', new BinaryParserConst(3, Multiply::new, ""),
            '/', new BinaryParserConst(3, Divide::new, ""),
            '-', new BinaryParserConst(2, Subtract::new, ""),
            '+', new BinaryParserConst(2, Add::new, ""),
            '>', new BinaryParserConst(1, ShiftRight::new, ">"),
            '<', new BinaryParserConst(1, ShiftLeft::new, "<")
    );

    static final Map<Character, UnaryParserConst> unaryOperationMap = Map.of(
            'd', new UnaryParserConst(4, Digits::new, "igits"),
            'r', new UnaryParserConst(4, Reverse::new, "everse")
    );
}
