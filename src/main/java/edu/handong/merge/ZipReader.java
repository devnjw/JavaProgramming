package edu.handong.merge;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
//import java.util.zip.ZipFile;

public class ZipReader {

    public void readFileInZip(String path) {
        ZipFile zipFile;
        try {
            zipFile = new ZipFile(path);
            Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();

            while(entries.hasMoreElements()){
                ZipArchiveEntry entry = entries.nextElement();
                InputStream stream = zipFile.getInputStream(entry);

                ExcelReader myReader = new ExcelReader();

                for(String value:myReader.getData(stream)) {
                    System.out.println(value);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
