package markup;

import java.util.List;

public class Underline extends UniteElement {
    public Underline(List<ForParagraph> yourElementsToEmphasis) {
        super(yourElementsToEmphasis);
    }

    @Override
    public void toMarkdown(StringBuilder ans) {
        super.toMarkdown(ans, "++");
    }

    @Override
    public void toHtml(StringBuilder htmlAns) {
        super.toHtml(htmlAns, "<u>", "</u>");
    }
}
