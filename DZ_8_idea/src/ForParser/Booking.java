package ForParser;

import markup.ForParagraph;

import java.util.List;

interface Booking {
    ForParagraph create(List<ForParagraph> children);
}
