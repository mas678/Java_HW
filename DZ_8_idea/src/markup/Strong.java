package markup;

import java.util.List;

public class Strong extends UniteElement {
    public Strong(List<ForParagraph> yourElementsToStrong) {
        super(yourElementsToStrong);
    }

    public void toMarkdown(StringBuilder ans) {
        super.toMarkdown(ans, "__");
    }

    public void toHtml(StringBuilder htmlAns) {
        super.toHtml( htmlAns, "<strong>", "</strong>");
    }
}
