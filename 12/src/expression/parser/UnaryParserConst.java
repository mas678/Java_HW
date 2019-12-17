package expression.parser;

class UnaryParserConst {
    final int level;
    final OneVariable create;
    final String end;

    public UnaryParserConst(int level, OneVariable create, String end) {
        this.level = level;
        this.create = create;
        this.end = end;
    }
}