package markup;

import java.util.List;

public class Link extends UniteElement {
    private StringBuilder link = new StringBuilder();
    public Link(List<ForParagraph> yourElementsToStrikeout) {
        super(yourElementsToStrikeout.subList(0, yourElementsToStrikeout.size() - 1));
        yourElementsToStrikeout.get(yourElementsToStrikeout.size() - 1).toHtml(link);
    }

    @Override
    public void toMarkdown(StringBuilder ans) {
        toMarkdown(ans, "~");
    }

    @Override
    public void toHtml(StringBuilder htmlAns) {
        toHtml(htmlAns, "<a href='" + link + "'>", "</a>");
    }
}
