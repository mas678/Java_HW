package markup;

import java.util.List;

public class ListItem {
    private List<ListAble> myListAble;

    public ListItem(List<ListAble> yourListAble) {
        myListAble = yourListAble;
    }

    public void toHtml(StringBuilder htmlAns) {
        htmlAns.append("<li>");
        for (ListAble element: myListAble) {
            element.toHtml(htmlAns);
        }
        htmlAns.append("</li>");
    }
}
