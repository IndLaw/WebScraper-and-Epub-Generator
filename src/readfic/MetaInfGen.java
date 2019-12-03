package readfic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MetaInfGen {
    
    // creates container.xml and inserts file into newly created META-INF folder 
   public MetaInfGen(String filename) throws IOException{
       BufferedWriter writer;
       String MetaInf = "<?xml version=\"1.0\"?>\n" +
               "<container version=\"1.0\" xmlns=\"urn:oasis:names:tc:opendocument:xmlns:container\">\n"+
               "    <rootfiles>\n"+
               "        <rootfile full-path=\"OEBPS/content.opf\" media-type=\"application/oebps-package+xml\"/>\n"+
               "   </rootfiles>\n"+
               "</container>";
       new FolderGen(filename + "/META-INF");
       writer = new BufferedWriter(new FileWriter(filename + "/META-INF/container.xml"));
        writer.write(MetaInf);
        writer.close();
   }
   
}
