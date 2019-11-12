package markup;

import java.util.List;

public class Emphasis extends UniteElement {
    public Emphasis(List<ForParagraph> yourElementsToEmphasis) {
        super(yourElementsToEmphasis);
    }

    @Override
    public void toMarkdown(StringBuilder ans) {
        super.toMarkdown(ans, "*");
    }

    @Override
    public void toHtml(StringBuilder htmlAns) {
        super.toHtml(htmlAns, "<em>", "</em>");
    }
}
