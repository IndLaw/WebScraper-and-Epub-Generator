package readfic;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.xml.transform.TransformerException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

public class OEBPSGen {

    public OEBPSGen(URL url, String filename) throws MalformedURLException, IOException, TransformerException {

        new FolderGen(filename + "/OEBPS");

        Scanner html = new Scanner(url.openStream());
        String text = "";

        //get html and convert webpage to doc
        while (html.hasNext()) {
            String t = html.nextLine();
            text = text + t + "\n";
        }
        Document doc = Jsoup.parse(text, "", Parser.xmlParser());
        ContentGen content = new ContentGen(url, doc, filename);
        new TextGen(url, content.getChapNum(url, doc), filename);
        new tocGen(content.getTitle(url, doc), content.getChapNum(url, doc), filename);

        // doc element retrieval stuff for ffn  ===============================================/      
//        System.out.println(doc.title());
//        System.out.println(doc.getElementById("storytext"));
        // important for story data
//        System.out.println(doc.getElementById("profile_top").getElementsByTag("span"));
//        System.out.println(doc.getElementById("profile_top").toString() + "\n\n");
//        System.out.println(doc.getElementById("profile_top").getElementsByTag("b").text());
//        System.out.println(doc.getElementById("profile_top").getElementsByTag("a").first().text());
//        System.out.println(doc.getElementById("profile_top").getElementsByTag("div").last().text());
//        System.out.println(doc.getElementById("profile_top").getElementsByTag("span").last().text());
//        System.out.println(doc.getElementById("profile_top").getElementsByClass("xgray xcontrast_txt").text());
//        String segments[] = doc.getElementById("profile_top").getElementsByClass("xgray xcontrast_txt").text().split("-");
//        System.out.println(segments[4].replaceAll("\\D+", ""));


        ///==========================================//
        // doc element retrieval stuff for ao3
//        System.out.println(doc.getElementsByClass("title heading").text());
//        System.out.println(doc.getElementsByClass("byline heading").text());
//        System.out.println(doc.select("meta[name=language]"));
//        // the publisher can just be hard coded in, no need to look for it
//        System.out.println("Publisher: Archive of Our Own");
//        System.out.println("Identifier: " + url1.toString());
//        System.out.println(doc.getElementsByClass("published").text());
//        System.out.println(doc.getElementsByClass("words").text());
//        System.out.println(doc.getElementsByClass("chapters").get(1).text());
//        System.out.println("Comments: " + doc.getElementsByClass("comments").get(2).text());
//        System.out.println(doc.getElementsByClass("rating tags").text());
//        System.out.println(doc.getElementsByClass("fandom tags").text());
//        //if true add to file if not yeh
//        System.out.println(doc.getElementsByClass("character tags").hasClass("character tags"));
//        //if true add to file if not yeh
//        System.out.println(doc.getElementsByClass("freeform tags").hasClass("freeform tags"));
//        System.out.println("Kudos: " + doc.getElementsByClass("kudos").get(1).text());
//        System.out.println(doc.getElementsByClass("summary module").text());
//        System.out.println(doc.getElementsByClass("notes module").text());
    }

}
