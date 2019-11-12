package markup;

public class Text implements ForParagraph{
    private String myString;

    public Text(String yourString) {
        myString = yourString;
    }

    @Override
    public void toMarkdown(StringBuilder ans) {
        ans.append(myString);
    }

    @Override
    public void toHtml(StringBuilder htmlAns) {
        for (int i = 0; i < myString.length(); i++) {
            char c = myString.charAt(i);
            if (c == '>' || c == '<' || c == '&') {
                if (c == '>') {
                    htmlAns.append("&gt;");
                } else if (c == '<') {
                    htmlAns.append("&lt;");
                } else {
                    htmlAns.append("&amp;");
                }
            } else {
                htmlAns.append(c);
            }

        }
    }
}
