package markup;

import java.util.List;

public class OrderedList extends AbleList {
    public OrderedList(List<ListItem> items) {
        super(items);
    }

    @Override
    public void toHtml(StringBuilder htmlAns) {
        super.toHtml(htmlAns, "<ol>", "</ol>");
    }
}
