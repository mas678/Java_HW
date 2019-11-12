package ForParser;

import markup.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parsing implements AutoCloseable {
    private ArrayList<String> ans = new ArrayList<>();
    private ArrayList<String> text = new ArrayList<>();
    private ArrayList<TextElement> afterPars = new ArrayList<>();
    int del = System.lineSeparator().length();
    private BufferedReader in;
    public Parsing(BufferedReader in) {
        this.in = in;
    }

    public void parseParagraph() throws IOException {
        StringBuilder s = new StringBuilder();
        int a = this.in.read();
        while(a != -1) {
            char c = (char) a;
            a = this.in.read();
            s.append(c);
            if (c == '\n') {
                ans.add(s.toString());
                s = new StringBuilder();
            }
        }
        ans.add(s.toString() + System.lineSeparator());
        onParagraph();
    }

    private void onParagraph () {
        StringBuilder s = new StringBuilder();
        for (String element: ans) {
            if (element.isBlank()) {
                if (s.length() != 0) {
                    text.add(s.toString().substring(0, s.toString().length() - del));
                    s = new StringBuilder();
                }
            } else {
                s.append(element);
            }
        }
    }

    public void fromMarkdown(){
        for (String element: text) {
            afterPars.add(fromMarkdown(element));
        }
    }

    public void toHtml(BufferedWriter out) throws IOException {
        for (TextElement element: afterPars) {
            StringBuilder s = new StringBuilder();
            element.toHtml(s);
            out.write(s.toString());
            out.newLine();
        }
    }

    private TextElement fromMarkdown(String paragraph) {
        int ind = 0;
        while (paragraph.charAt(ind) == '#') {ind++;}
        if (ind != 0 && Character.isWhitespace(paragraph.charAt(ind))) {
            return new Header(oneMarkdownElement(paragraph.substring(ind + 1)), ind);
        }
        return new Paragraph(oneMarkdownElement(paragraph));
    }

    private List<ForParagraph> oneMarkdownElement(String element) {
        List<ForParagraph> helping = new ArrayList<>();
        StringBuilder fromMark = new StringBuilder();
        for (int i = 0; i < element.length(); i++) {
            if (element.charAt(i) == '\\') {
                i++;
                if (i != element.length()) {
                    fromMark.append(element.charAt(i));
                }
                continue;
            }
            int len = 1;
            if (i + 1 != element.length() && element.charAt(i) == element.charAt(i + 1)) {
                len = 2;
            }
            char c = element.charAt(i);
            if ((c == '-' && len == 2) || c == '*' || c == '_' || (c == '`'  && len == 1)) {
                int g = find(element.substring(i + 1), len, c);
                if (g != -1) {
                    helping.add(new Text(fromMark.toString()));
                    List<ForParagraph> tempList = oneMarkdownElement(element.substring(i + len, i + g + 1));
                    ForParagraph temp;
                    if (c == '-') {
                        temp = new Strikeout(tempList);
                    } else if (c == '`') {
                        temp = new Code(tempList);
                    } else if (len == 2) {
                        temp = new Strong(tempList);
                    } else {
                        temp = new Emphasis(tempList);
                    }
                    helping.add(temp);
                    fromMark = new StringBuilder();
                    i += g + len;
                    continue;
                } else if (len == 2) {
                    fromMark.append(element.charAt(i));
                    i++;
                    continue;
                }
            }
            fromMark.append(element.charAt(i));
        }
        helping.add(new Text(fromMark.toString()));
        return helping;
    }

    private int find(String subElement, int len, char quest) {
        for (int i = 0; i < subElement.length() - 1; i++) {
            if (subElement.charAt(i) == '\\') {
                i++;
                continue;
            }
            if (subElement.charAt(i) == quest) {
                if (subElement.charAt(i) == subElement.charAt(i + 1)) {
                    if (len == 2) {
                        return i;
                    }
                    i++;
                } else {
                    if (len == 1) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}
