package expression.parser;

import expression.CommonExpression;

import java.util.ArrayList;
import java.util.List;

public class ComIntSymb {
    List<CommonExpression> commonList;
    List<Integer> bracketsCnt;
    List<Character> mathSymbols;

    public ComIntSymb() {
        commonList = new ArrayList<>();
        bracketsCnt = new ArrayList<>();
        mathSymbols = new ArrayList<>();
    }

   public void add(CommonExpression exp, Integer bracket) {
        commonList.add(exp);
        bracketsCnt.add(bracket);
   }

   public void addMathSymbol(Character s) {
        mathSymbols.add(s);
   }

   public CommonExpression getLastCommon() {
        return commonList.get(commonList.size() - 1);
   }

   public Integer getPreLastBracket() {
        if (bracketsCnt.size() < 2) {
            return -1;
        }
        return bracketsCnt.get(bracketsCnt.size() - 2);
   }

    public Character getLastSymbol() {
        return mathSymbols.get(mathSymbols.size() - 1);
    }

   public int size() {
        return commonList.size();
   }

   public void removeLastSymbol() {
        mathSymbols.remove(mathSymbols.size() - 1);
   }

    public void removeLastPair() {
        bracketsCnt.remove(bracketsCnt.size() - 1);
        commonList.remove(commonList.size() - 1);
    }

    public int getLastBracket() {
        return bracketsCnt.get(bracketsCnt.size() - 1);
    }
}
