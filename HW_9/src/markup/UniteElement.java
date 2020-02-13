package markup;

import java.util.List;

public abstract class UniteElement implements ForParagraph {
    private List<ForParagraph> myUniteElements;

    protected UniteElement(List<ForParagraph> yourUniteElements) {
        myUniteElements = yourUniteElements;
    }

    public void toMarkdown(StringBuilder ans, String border) {
        ans.append(border);
        for (ForParagraph element: myUniteElements) {
            element.toMarkdown(ans);
        }
        ans.append(border);
    }

    public void toHtml(StringBuilder htmlAns, String left, String right) {
        htmlAns.append(left);
        for (ForParagraph element: myUniteElements) {
            element.toHtml(htmlAns);
        }
        htmlAns.append(right);
    }
}
