package readfic;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.transform.TransformerException;

public class ReadFic {

    public static void main(String[] args) throws MalformedURLException, IOException, TransformerException {
//        URL url = new URL("http://archiveofourown.org/works/8267503?view_full_work=true");
//        URL url = new URL("http://archiveofourown.org/works/1860492?view_full_work=true");
//        URL url = new URL("http://archiveofourown.org/works/173254?view_full_work=true");
//        URL url = new URL("https://www.fanfiction.net/s/6805609");
//        URL url = new URL("https://www.fanfiction.net/s/6640897");
        URL url = new URL("https://www.fanfiction.net/s/11600772");

        //check for valid website
        // make sure to add OR conditionals for other sites
        if (url.toString().contains("archiveofourown.org")
                || url.toString().contains("www.fanfiction.net")) {
            //check connection

            HttpURLConnection huc = (HttpURLConnection) url.openConnection();

            huc.setRequestMethod("HEAD");
            if ((huc.getResponseCode() == HttpURLConnection.HTTP_OK)) {
//                System.out.println(genFoldername(url));

                new FolderGen(genFoldername(url));
                new MimetypeGen(genFoldername(url));
                new MetaInfGen(genFoldername(url));
                new OEBPSGen(url, genFoldername(url));
                new ePubGen(genFoldername(url));
            } else {
                System.err.println("Story does not exist.");
            }
        } else {
            System.err.println("Invalid website.");
        }
    }

    // generates folder name based on website and story id
    private static String genFoldername(URL url) {
        if (url.toString().contains("archiveofourown.org")) {
            return "aoo" + url.toString().replaceAll("\\D+", "");
        } else if (url.toString().contains("www.fanfiction.net")) {
            return "ffn" + url.toString().replaceAll("\\D+", "");
        }
        return "";
    }

}
