package markup;

import java.util.List;

public class Paragraph extends TextElement {
    public Paragraph(List<ForParagraph> yourText) {
        super(yourText);
    }

    @Override
    public void toMarkdown(StringBuilder ans) {
        toMarkdown(ans, "");
    }

    @Override
    public void toHtml(StringBuilder htmlAns) {
        toHtml(htmlAns, "<p>", "</p>");
    }
}
