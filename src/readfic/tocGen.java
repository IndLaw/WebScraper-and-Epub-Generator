package readfic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class tocGen {

    public tocGen(String title, int numChap, String filename) throws IOException {
        BufferedWriter writer;
        String toc = "";
        toc += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<ncx xmlns=\"http://www.daisy.org/z3986/2005/ncx/\" version=\"2005-1\">\n"
                + "  <head>\n"
                + "    <meta name=\"dtb:uid\" content=\"\"/>\n"
                + "    <meta name=\"dtb:depth\" content=\"1\"/>\n"
                + "    <meta name=\"dtb:totalPageCount\" content=\"0\"/>\n"
                + "    <meta name=\"dtb:maxPageNumber\" content=\"0\"/>\n"
                + "  </head>\n"
                + "<docTitle>\n"
                + "    <text>" + title + "</text>\n"
                + "  </docTitle>\n"
                + "<navMap>\n";
        for (int i = 1; i < numChap + 1; i++) {
            toc += "      <navPoint id=\"chapter_" + i + "\" playOrder=\"" + i + "\">\n"
                    + "        <navLabel>\n"
                    + "          <text>Chapter " + i + "</text>\n"
                    + "        </navLabel>\n"
                    + "        <content src=\"chapter_" + i + ".xhtml\"/>\n"
                    + "      </navPoint>\n";
        }
        toc += "  </navMap>\n"
                + "</ncx>";

        writer = new BufferedWriter(new FileWriter(filename + "/OEBPS/toc.ncx"));
        writer.write(toc);
        writer.close();

    }
}
