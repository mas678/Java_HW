package markup;

import java.util.List;

public class Header extends TextElement {
    private int level;

    public Header(List<ForParagraph> yourText, int level) {
        super(yourText);
        this.level = level;
    }

    @Override
    public void toMarkdown(StringBuilder ans) {
        toMarkdown(ans, "#".repeat(level));
    }

    @Override
    public void toHtml(StringBuilder htmlAns) {
        toHtml(htmlAns, "<h" + level + ">", "</h" + level + ">");
    }
}