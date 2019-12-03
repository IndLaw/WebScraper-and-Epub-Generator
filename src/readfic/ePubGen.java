package readfic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ePubGen {
    public ePubGen(String folderName) throws IOException{
        File directoryToZip = new File(folderName);

                List<File> fileList = new ArrayList<File>();
                System.out.println("---Getting references to all files in: " + directoryToZip.getCanonicalPath());
                getAllFiles(directoryToZip, fileList);
                System.out.println("---Creating zip file");
                File temp0 = fileList.get(2);
                File temp1 = fileList.get(0);
                File temp2 = fileList.get(1);
                // ensure mimetype is first
                fileList.remove(0);
                fileList.add(0, temp0);
                fileList.remove(1);
                fileList.add(1, temp1);
                fileList.remove(2);
                fileList.add(2, temp2);

                writeZipFile(directoryToZip, fileList);
                deleteRecursive(directoryToZip);
                System.out.println("---Done");
    }
    
     // delete original folder
    public static boolean deleteRecursive(File path) throws FileNotFoundException {
        if (!path.exists()) {
            throw new FileNotFoundException(path.getAbsolutePath());
        }
        boolean ret = true;
        if (path.isDirectory()) {
            for (File f : path.listFiles()) {
                ret = ret && deleteRecursive(f);
            }
        }
        return ret && path.delete();
    }

    public static void getAllFiles(File dir, List<File> fileList) {
        try {

            File[] files = dir.listFiles();
            for (File file : files) {

                fileList.add(file);
                if (file.isDirectory()) {
                    System.out.println("directory:" + file.getCanonicalPath());
                    getAllFiles(file, fileList);
                } else {
                    System.out.println("     file:" + file.getCanonicalPath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeZipFile(File directoryToZip, List<File> fileList) {
        try {
            FileOutputStream fos = new FileOutputStream(directoryToZip.getName() + ".epub");
            ZipOutputStream zos = new ZipOutputStream(fos);

            for (File file : fileList) {
                // zips files, not directories
                if (!file.isDirectory()) {
                    addToZip(directoryToZip, file, zos);
                }
            }

            zos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addToZip(File directoryToZip, File file, ZipOutputStream zos) throws FileNotFoundException,
            IOException {

        FileInputStream fis = new FileInputStream(file);

        // we want the zipEntry's path to be a relative path that is relative
        // to the directory being zipped, so chop off the rest of the path
        String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1,
                file.getCanonicalPath().length());
        if (zipFilePath.equals("mimetype")) {
            // store mimetype with no compresion
            zos.setLevel(ZipOutputStream.STORED);
        } else {
            // compress folders in directory
            zos.setLevel(ZipOutputStream.DEFLATED);
        }
        System.out.println("Writing '" + zipFilePath + "' to zip file");
        zipFilePath = zipFilePath.replaceAll("\\\\", "/"); //AUUGHGHGUHG GOD WHY WAS THIS SO SIMPLE?!?!??!!?!?
        final ZipEntry zipEntry = new ZipEntry(zipFilePath);
        zos.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }

}
