package markup;

import java.util.List;

public abstract class AbleList implements ListAble{
    private List<ListItem> myListAbleElements;

    protected AbleList(List<ListItem> yourListAbleElements) {
        myListAbleElements = yourListAbleElements;
    }

    public void toHtml(StringBuilder htmlAns, String left, String right) {
        htmlAns.append(left);
        for (ListItem Element: myListAbleElements) {
            Element.toHtml(htmlAns);
        }
        htmlAns.append(right);
    }
}
