package ForParser;

import markup.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Parsing implements AutoCloseable {
    private ArrayList<String> ans = new ArrayList<>();
    private ArrayList<String> text = new ArrayList<>();
    private ArrayList<TextElement> afterPars = new ArrayList<>();
    private int del = System.lineSeparator().length();
    private BufferedReader in;
    private Set<String> noneOne = Set.of("[");
    private Map <String, String> negative = Map.of("++", "++", "--", "--", "_", "_", "__", "__",
            "*", "*", "**", "**", "[", "](", "](", ")", "`", "`");
    private Map<String, ForParagraph> markdownTags = Map.of("++", new Underline(List.of()), "--", new Strikeout(List.of()),
            "*", new Emphasis(List.of()), "_", new Emphasis(List.of()), "`", new Code(List.of()),
            "**", new Strong(List.of()), "__", new Strong(List.of()), "[", new Link(List.of(new Text("link"))));
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
        ans.add(System.lineSeparator());
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

    public void fromMarkdown() throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
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

    private TextElement fromMarkdown(String paragraph) throws InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        int ind = 0;
        while (paragraph.charAt(ind) == '#') {ind++;}
        if (ind != 0 && Character.isWhitespace(paragraph.charAt(ind))) {
            return new Header(oneMarkdownElement(paragraph, (ind + 1), paragraph.length()), ind);
        }
        return new Paragraph(oneMarkdownElement(paragraph, 0, paragraph.length()));
    }

    private List<ForParagraph> oneMarkdownElement(String element, int start, int end) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        List<ForParagraph> helping = new ArrayList<>();
        StringBuilder fromMark = new StringBuilder();
        for (int i = start; i < end; i++) {
            if (element.charAt(i) == '\\') {
                i++;
                if (i != end) {
                    fromMark.append(element.charAt(i));
                }
                continue;
            }
            int len = 1;
            if (i + 1 != element.length() && element.charAt(i) == element.charAt(i + 1)) {
                len = 2;
            }
            char c = element.charAt(i);
            if (markdownTags.containsKey(Character.toString(c).repeat(len))) {
                int g = find(element, i + len, end, negative.get(Character.toString(c).repeat(len)));
                if (g != -1) {
                    List<ForParagraph> tempList = oneMarkdownElement(element, i + len, g);
                    if (noneOne.contains(Character.toString(c).repeat(len))) {
                        int g1 = find(element, g + 2, end, negative.get(negative.get(Character.toString(c).repeat(len))));
                        if (g1 == -1) {
                            fromMark.append(element.charAt(i));
                            continue;
                        }
                        tempList.add(new Text(element.substring(g + negative.get(Character.toString(c).repeat(len)).length(), g1)));
                        i = g1;
                    } else {
                        i = g + len - 1;
                    }
                    helping.add(new Text(fromMark.toString()));
                    helping.add(markdownTags.get(Character.toString(c).repeat(len)).getClass()
                            .getDeclaredConstructor(List.class).newInstance(tempList));
                    fromMark = new StringBuilder();
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

    private int find(String subElement, int start, int end, String quest) {
        for (int i = start; i < end - quest.length() + 1; i++) {
            if (subElement.charAt(i) == '\\') {
                i++;
                continue;
            }
            if (subElement.substring(i, i + quest.length()).equals(quest)) {
                if (i + 1 != end && (negative.containsValue(subElement.substring(i, i + quest.length() + 1)))) {
                    i += quest.length();
                    continue;
                }
                return i;
            }
        }
        return -1;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}
