package ForParser;

import java.util.List;

public class MarkdownTags {
    private int level;
    private int index;

    public MarkdownTags(String s) {
        level = -1;
        List<String> tags = List.of("`", "~~", "**", "__", "*", "_");
    }

    public int getLevel() {
        return level;
    }

    public int getIndex() {
        return index;
    }
}
