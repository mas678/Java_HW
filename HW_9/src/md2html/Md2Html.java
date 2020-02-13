package md2html;

import ForParser.Parsing;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class Md2Html {
    public static void main(String[] args) {
        Parsing middle = new Parsing();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                new FileInputStream(args[0]), "utf8"))) {
            middle.fromMarkdown(Parsing.splitParagraphs(in));
        } catch (FileNotFoundException e) {
            System.err.println("Input file cannot be found " + e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println("IOException " + e.getMessage());
            return;
        }

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(args[1]), "utf8"))) {
            middle.toHtml(out);
        } catch (FileNotFoundException e) {
            System.err.println("Output file cannot be found or create " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException " + e.getMessage());
        }
    }
}
