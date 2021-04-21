package edu.handong.java.zip;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipBody {
    public void createZipFile(String path, String toPath, String fileName) {
        try {
            File dir = new File(path);
            String[] list = dir.list();
            String _path;

            if (!dir.canRead() || !dir.canWrite())
                return;

            int len = list.length;

            if (path.charAt(path.length() - 1) != '/')
                _path = path + "/";
            else
                _path = path;


            if (!dir.exists())
                throw new CustomizedFileNotFoundException(path);
            ZipOutputStream zip_out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(toPath+"/"+fileName), 2048));

            for (int i = 0; i < len; i++)
                zip_folder("",new File(_path + list[i]), zip_out);

            zip_out.close();

        } catch (CustomizedFileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
    private void zip_folder(String parent, File file, ZipOutputStream zout) throws IOException {
        byte[] data = new byte[2048];
        int read;

        if (file.isFile()) {
            ZipEntry entry = new ZipEntry(parent + file.getName());
            zout.putNextEntry(entry);
            BufferedInputStream instream = new BufferedInputStream(new FileInputStream(file));

            while ((read = instream.read(data, 0, 2048)) != -1)
                zout.write(data, 0, read);

            zout.flush();
            zout.closeEntry();
            instream.close();

        } else if (file.isDirectory()) {
            String parentString = file.getPath();//.replace(path,"");
            parentString = parentString.substring(0,parentString.length() - file.getName().length());
            ZipEntry entry = new ZipEntry(parentString+file.getName()+"/");
            zout.putNextEntry(entry);

            String[] list = file.list();
            if (list != null) {
                int len = list.length;
                for (int i = 0; i < len; i++) {
                    zip_folder(entry.getName(),new File(file.getPath() + "/" + list[i]), zout);
                }
            }
        }
    }


    public boolean extractZipFiles(String zip_file, String directory) {
        boolean result = false;

        byte[] data = new byte[2048];
        ZipEntry entry = null;
        ZipInputStream zipstream = null;
        FileOutputStream out = null;

        if (!(directory.charAt(directory.length() - 1) == '/'))
            directory += "/";

        File destDir = new File(directory);
        boolean isDirExists = destDir.exists();
        boolean isDirMake = destDir.mkdirs();

        try {
            zipstream = new ZipInputStream(new FileInputStream(zip_file));

            while ((entry = zipstream.getNextEntry()) != null) {

                int read = 0;
                File entryFile;

                if (entry.isDirectory()) {
                    File folder = new File(directory+entry.getName());
                    if(!folder.exists()){
                        folder.mkdirs();
                    }
                    continue;
                }else {
                    entryFile = new File(directory + entry.getName());
                }

                if (!entryFile.exists()) {
                    boolean isFileMake = entryFile.createNewFile();
                }

                out = new FileOutputStream(entryFile);
                while ((read = zipstream.read(data, 0, 2048)) != -1)
                    out.write(data, 0, read);

                zipstream.closeEntry();

            }

            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result = false;
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (zipstream != null) {
                try {
                    zipstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
