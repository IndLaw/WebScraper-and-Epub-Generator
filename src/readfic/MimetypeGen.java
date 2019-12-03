package readfic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MimetypeGen {
    
    //creates mimetype file
    public MimetypeGen(String filename) throws IOException {
        BufferedWriter writer;
        String mimetype = "application/epub+zip";
        writer = new BufferedWriter(new FileWriter(filename + "/mimetype"));
        writer.write(mimetype);
        writer.close();
    }

}
