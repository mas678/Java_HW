package markup;

import java.util.List;

public class Code extends UniteElement {
    public Code(List<ForParagraph> yourElementsToStrong) {
        super(yourElementsToStrong);
    }

    public void toMarkdown(StringBuilder ans) {
        super.toMarkdown(ans, "`");
    }

    public void toHtml(StringBuilder htmlAns) {
        super.toHtml( htmlAns, "<code>", "</code>");
    }
}
