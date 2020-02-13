package markup;

import java.util.List;

public class UnorderedList extends AbleList {
    public UnorderedList(List<ListItem> yourElementsToUnorderedList) {
        super(yourElementsToUnorderedList);
    }

    @Override
    public void toHtml(StringBuilder htmlAns) {
        super.toHtml(htmlAns, "<ul>", "</ul>");
    }
}
