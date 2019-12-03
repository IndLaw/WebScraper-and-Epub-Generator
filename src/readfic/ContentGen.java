package readfic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import org.jsoup.nodes.Document;

public class ContentGen {

    public ContentGen(URL url, Document doc, String filename) throws IOException {
        BufferedWriter writer;
        String content = "";

        content += GeneralHeadingData();
        // check website so appropriate method is used
        if (url.toString().contains("archiveofourown.org")) {
            content += AO3MetaData(url, doc);
        } else if (url.toString().contains("www.fanfiction.net")) {
            content += FFNMetaData(url, doc);
        }
        content += manifestAndSpine(url, doc);

        writer = new BufferedWriter(new FileWriter(filename + "/OEBPS/content.opf"));
        writer.write(content);
        writer.close();
    }

    public int getChapNum(URL url, Document doc) {
        // add conditionals for other websites
        // AO3 and ffn are the only ones here for now
        if (url.toString().contains("archiveofourown.org")) {
            return getNumofAO3Chap(doc);
        } else if (url.toString().contains("www.fanfiction.net")) {
            return getNumofFFNChap(doc);
        }
        return 0;
    }

    public String getTitle(URL url, Document doc) {
        // add conditionals for other websites
        // AO3 and ffn are the only ones here for now
        if (url.toString().contains("archiveofourown.org")) {
            return getAO3Title(doc);
        } else if (url.toString().contains("www.fanfiction.net")) {
            return getFFNTitle(doc);
        }
        return "";

    }

    // used by every website
    private String GeneralHeadingData() {

        return "<?xml version=\"1.0\"?>\n"
                + "<package version=\"2.0\" xmlns=\"http://www.idpf.org/2007/opf\"\n"
                + "       unique-identifier=\"etextno\">\n"
                + "  <metadata xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n"
                + "           xmlns:opf=\"http://www.idpf.org/2007/opf\">\n";

    }

    // used by every website
    private String manifestAndSpine(URL url, Document doc) {
        String text = "";
        int numChap = getChapNum(url, doc);

        text += " </metadata>\n"
                + "<manifest>\n"
                + "    <item id=\"ncx\" href=\"toc.ncx\" media-type=\"application/x-dtbncx+xml\"/>\n";
        for (int i = 1; i < numChap + 1; i++) {
            text += "<item id=\"chapter_" + i + "\" href=\"chapter_" + i + ".xhtml\" media-type=\"application/xhtml+xml\"/>\n";
        }
        text += "  </manifest>\n"
                + "  <spine toc=\"ncx\">\n";
        for (int i = 1; i < numChap + 1; i++) {
            text += "        <itemref idref=\"chapter_" + i + "\"/>\n";
        }
        text += "  </spine>\n"
                + "</package>";

        return text;
    }

    private String FFNMetaData(URL url, Document doc) {
        String text = "";

        text += "    <dc:title>" + doc.getElementById("profile_top").getElementsByTag("b").text() + "</dc:title> \n"
                + "    <dc:creator>" + doc.getElementById("profile_top").getElementsByTag("a").first().text() + "</dc:creator>\n"
                + "      <dc:language>" + "en" + "</dc:language> \n"
                + "    <dc:publisher>Fanfiction.net</dc:publisher> \n "
                + "    <dc:description>" + doc.getElementById("profile_top").getElementsByTag("div").last().text() + "</dc:description>\n"
                + "    <dc:identifier opf:scheme=\"URI\" id=\"etextno\">" + url.toString() + "</dc:identifier>\n"
                + "    <dc:date>" + doc.getElementById("profile_top").getElementsByTag("span").last().text() + "</dc:date>\n";

        return text;
    }

    private int getNumofFFNChap(Document doc) {
        int numChapters = 0;

        if (!doc.getElementById("profile_top").getElementsByClass("xgray xcontrast_txt").text().contains("Chapters:")) {
            numChapters = 1;
        } else {
            String segments[] = doc.getElementById("profile_top").getElementsByClass("xgray xcontrast_txt").text().split(" - ");
            numChapters = Integer.parseInt(segments[4].replaceAll("\\D+", ""));
        }
        return numChapters;
    }

    private String getFFNTitle(Document doc) {
        return doc.getElementById("profile_top").getElementsByTag("b").text();
    }

    private String AO3MetaData(URL url, Document doc) {
        String text = "";

        text += "    <dc:title>" + doc.getElementsByClass("title heading").text() + "</dc:title> \n"
                + "    <dc:creator>" + doc.getElementsByClass("byline heading").text() + "</dc:creator>\n"
                + "      <dc:language>" + "en" + "</dc:language> \n"
                + "    <dc:publisher>Archive of Our Own</dc:publisher> \n "
                + "    <dc:description>" + doc.getElementsByClass("summary module").text().substring(9) + "</dc:description>\n"
                + "    <dc:identifier opf:scheme=\"URI\" id=\"etextno\">" + url.toString() + "</dc:identifier>\n"
                + "    <dc:date>" + doc.getElementsByClass("published").text().substring(11) + "</dc:date>\n";

        return text;
    }

    private int getNumofAO3Chap(Document doc) {
        int iend = doc.getElementsByClass("chapters").text().indexOf("/"), numChapters = 0;

        if (iend != -1) {
            String subString = doc.getElementsByClass("chapters").text().substring(10, iend);
            numChapters = Integer.parseInt(subString);
        }
        return numChapters;
    }

    private String getAO3Title(Document doc) {
        return doc.getElementsByClass("title heading").text();
    }

}
