package markup;

import java.util.List;

public class Strikeout extends UniteElement {
    public Strikeout(List<ForParagraph> yourElementsToStrikeout) {
        super(yourElementsToStrikeout);
    }

    @Override
    public void toMarkdown(StringBuilder ans) {
        toMarkdown(ans, "~");
    }

    @Override
    public void toHtml(StringBuilder htmlAns) {
        super.toHtml(htmlAns, "<s>", "</s>");
    }
}
