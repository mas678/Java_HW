package ForParser;

import markup.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Parsing {
    private ArrayList<TextElement> afterPars = new ArrayList<>();
    private final Set<String> noneOne = Set.of("[");

    private static final Map<String, Booking> HELLOS = Map.of("__", new Booking() {
        @Override
        public ForParagraph create(List<ForParagraph> children) {
            return new Strong(children);
        }
    });

    private final Map <String, String> negative = Map.of(
            "++", "++",
            "--", "--",
            "_", "_",
            "__", "__",
            "*", "*",
            "**", "**",
            "[", "](",
            "](", ")",
            "`", "`"
    );
    private final Map<String, Booking> markdownTags = Map.of(
            "++", new Booking() {
                @Override
                public ForParagraph create(List<ForParagraph> children) { return new Underline(children); }
            },
            "--", new Booking() {
                @Override
                public ForParagraph create(List<ForParagraph> children) {
                    return new Strikeout(children);
                }
            },
            "*", new Booking() {
                @Override
                public ForParagraph create(List<ForParagraph> children) {
                    return new Emphasis(children);
                }
            },
            "_", new Booking() {
                @Override
                public ForParagraph create(List<ForParagraph> children) {
                    return new Emphasis(children);
                }
            },
            "`", new Booking() {
                @Override
                public ForParagraph create(List<ForParagraph> children) {
                    return new Code(children);
                }
            },
            "**", new Booking() {
                @Override
                public ForParagraph create(List<ForParagraph> children) { return new Strong(children); }
            },
            "__", new Booking() {
                @Override
                public ForParagraph create(List<ForParagraph> children) {
                    return new Strong(children);
                }
            },
            "[", new Booking() {
                @Override
                public ForParagraph create(List<ForParagraph> children) { return new Link(children); }
            });

    public Parsing() {
    }

    public static List<String> splitParagraphs(BufferedReader in) throws IOException {
        List<String> paragraphs = new ArrayList<>();
        StringBuilder ans = new StringBuilder();
        String s = in.readLine();
        while (s != null) {
            if (s.isBlank()) {
                if (ans.length() != 0) {
                    paragraphs.add(ans.toString());
                    ans = new StringBuilder();
                }
            } else {
                if (ans.length() != 0) {
                    ans.append("\n");
                }
                ans.append(s);
            }
            s = in.readLine();
        }
        if (ans.length() != 0) {
            paragraphs.add(ans.toString());
        }
        return paragraphs;
    }

    public void fromMarkdown(List<String> paragraphs) {
        for (String paragraph : paragraphs) {
            afterPars.add(fromMarkdown(paragraph));
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
        while (ind != paragraph.length() && paragraph.charAt(ind) == '#') {ind++;}
        if (ind != paragraph.length() && ind != 0 && Character.isWhitespace(paragraph.charAt(ind))) {
            return new Header(oneMarkdownElement(paragraph, (ind + 1), paragraph.length()), ind);
        }
        return new Paragraph(oneMarkdownElement(paragraph, 0, paragraph.length()));
    }

    private List<ForParagraph> oneMarkdownElement(String element, int start, int end) {
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
            String s = element.substring(i, i + 1);
            if (i + 1 != element.length() && markdownTags.containsKey(element.substring(i, i + 2))) {
                s = element.substring(i, i + 2);
            }
            if (markdownTags.containsKey(s)) {
                int g = find(element, i + s.length(), end, negative.get(s));
                if (g != -1) {
                    List<ForParagraph> tempList = oneMarkdownElement(element, i + s.length(), g);
                    int g1 = g;
                    if (noneOne.contains(s)) {
                        g1 = find(element, g + 2, end, negative.get(negative.get(s)));
                        if (g1 == -1) {
                            fromMark.append(element.charAt(i));
                            continue;
                        }
                        tempList.add(new Text(element.substring(g + negative.get(s).length(), g1)));
                    }
                    i = g1 + s.length() - 1;
                    helping.add(new Text(fromMark.toString()));
                    helping.add(markdownTags.get(s).create(tempList));
                    fromMark = new StringBuilder();
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
                if (i + quest.length() + 1 < end && (negative.containsValue(subElement.substring(i, i + quest.length() + 1)))) {
                    i += quest.length();
                    continue;
                }
                return i;
            }
        }
        return -1;
    }
}

