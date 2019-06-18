package edu.handong.merge;

import edu.handong.merge.datamodel.FileData;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
//import java.util.zip.ZipFile;

public class ZipReader {

    ArrayList<FileData> fileDatas;

    public void readFileInZip(String path) {
        ZipFile zipFile;
        try {
            zipFile = new ZipFile(path);
            Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();

            while (entries.hasMoreElements()) {
                ZipArchiveEntry entry = entries.nextElement();
                InputStream stream = zipFile.getInputStream(entry);

                ExcelReader myReader = new ExcelReader();

                for (String value : myReader.getData(stream)) {
                    System.out.println(value);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void readFileInZip2(int id, String path){
        fileDatas = new ArrayList<>();
        ZipFile zipFile;
        try {
            zipFile = new ZipFile(path);
            Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();

            while (entries.hasMoreElements()) {
                ZipArchiveEntry entry = entries.nextElement();
                InputStream stream = zipFile.getInputStream(entry);

                FileData fileData = new FileData();

                fileData.saveRowData(id, stream);
                fileDatas.add(fileData);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ArrayList<FileData> getFileDatas() {
        return fileDatas;
    }

    public List<InputStream> getFileFromZip(String path) {
        ZipFile zipFile;
        List<InputStream> list = new ArrayList<InputStream>();
        try {
            zipFile = new ZipFile(path);
            Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();

            while (entries.hasMoreElements()) {
                ZipArchiveEntry entry = entries.nextElement();
                InputStream stream = zipFile.getInputStream(entry);

                list.add(stream);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        File file = new File("resultTest.csv");
        try {
            Utils.mergeExcelFiles(file, list);
        } catch (Exception e) {
            System.out.println(list.get(0));
        }
        return list;
    }
}
