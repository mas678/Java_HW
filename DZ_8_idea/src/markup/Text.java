package markup;

import java.util.Map;

public class Text implements ForParagraph{
    private String myString;
    private final Map<Character, String> html = Map.of('>', "&gt;", '<', "&lt;", '&', "&amp;");

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
            if (html.containsKey(c)) {
                htmlAns.append(html.get(c));
            } else {
                htmlAns.append(c);
            }
        }
    }
}
