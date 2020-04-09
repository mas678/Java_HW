package expression;

public interface BinaryFunction {
    String getSymbol();
    public <S extends Number, T extends Number> S function( T first, T second);
}
