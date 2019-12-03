package readfic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import javax.xml.transform.TransformerException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Whitelist;

public class TextGen {

    public TextGen(URL url, int numOfChap, String filename) throws IOException, TransformerException {
        if (url.toString().contains("archiveofourown.org")) {
            ArchiveofOurOwn(url, numOfChap, filename);
        } else if (url.toString().contains("www.fanfiction.net")) {
            FanFictionDotNet(url, numOfChap, filename);
        }

    }

    private void FanFictionDotNet(URL url, int numOfChap, String filename) throws IOException, TransformerException {

        if (numOfChap > 1) {
            for (int i = 1; i < numOfChap + 1; i++) {
                String urlTemp = url.toString() + "/" + i;
                Scanner html = new Scanner(new URL(urlTemp).openStream());
                String text = "";
                while (html.hasNext()) {
                    String t = html.nextLine();
                    text = text + t + "\n";
                }
                Document doc = Jsoup.parse(text, "", Parser.xmlParser());
                Element chapter = doc.getElementById("storytext");
                chapterGen(chapter.toString(), i, filename);
            }

        } else {
            Scanner html = new Scanner(url.openStream());
            String text = "";
            while (html.hasNext()) {
                String t = html.nextLine();
                text = text + t + "\n";
            }
            Document doc = Jsoup.parse(text, "", Parser.xmlParser());
            Element chapter = doc.getElementById("storytext");
            chapterGen(chapter.toString(), 1, filename);
        }

    }

    private void ArchiveofOurOwn(URL url, int numOfChap, String filename) throws IOException, TransformerException {
        Scanner html = new Scanner(url.openStream());
        String text = "";
        while (html.hasNext()) {
            String t = html.nextLine();
            text = text + t + "\n";
        }
        Document doc = Jsoup.parse(text, "", Parser.xmlParser());

        if (numOfChap > 1) {
            for (int i = 1; i < numOfChap + 1; i++) {
                Element chapter = doc.getElementById("chapters").getElementById("chapter-" + i);
                chapterGen(chapter.toString(), i, filename);
            }
        } else {
            Element chapter = doc.getElementById("chapters");
            chapterGen(chapter.toString(), 1, filename);
        }
    }

    private void chapterGen(String text, int num, String filename) throws IOException, TransformerException {
        BufferedWriter writer;
        //insert head tags
        text = Jsoup.clean(text, Whitelist.relaxed());
        text = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">" + text;
        //insert page break
        text = text.replaceAll("&nbsp;", "________________");
        text = text.replaceAll("<br>", "");
        text = text.replaceAll("Chapter Text", "");
        text = text.replaceAll("Work Text:", "");
        Document doc = Jsoup.parse(text);

        doc.select("h3[class=landmark heading]").remove();
        doc.outputSettings().escapeMode(Entities.EscapeMode.xhtml); //This will ensure the validity
        doc.outputSettings().charset("UTF-8");
        doc.head().append("<title></title>");

        doc.removeClass("landmark heading");

        // write the content into xhtml file
        writer = new BufferedWriter(new FileWriter(filename + "/OEBPS/chapter_" + num + ".xhtml"));
        writer.write(doc.toString());
        writer.close();

    }
}
