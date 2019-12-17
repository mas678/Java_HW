package expression.parser;

import expression.CommonExpression;

public class BinaryParserConst {
    final int level;
    final TwoVariables create;
    final String end;

    public BinaryParserConst(int level, TwoVariables create, String end) {
        this.level = level;
        this.create = create;
        this.end = end;
    }
}
