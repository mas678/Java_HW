package markup;

import java.util.List;

public abstract class TextElement implements ListAble, Markdown {
    private List<ForParagraph> myText;

    protected TextElement(List<ForParagraph> yourText) {
        myText = yourText;
    }

    public void toMarkdown(StringBuilder ans, String border) {
        ans.append(border);
        for (ForParagraph element : myText) {
            element.toMarkdown(ans);
        }
    }

    public void toHtml(StringBuilder htmlAns, String left, String right) {
        htmlAns.append(left);
        for (ForParagraph element : myText) {
            element.toHtml(htmlAns);
        }
        htmlAns.append(right);
    }
}
