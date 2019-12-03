package readfic;

import java.io.File;

public class FolderGen {

    public FolderGen(String folderName) {
 
        new File(folderName).mkdir();
    }

}
