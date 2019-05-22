package edu.handong.analysise.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {

    public static ArrayList<String> getLines(String file,boolean removeHeader){

        try {
            File theDir = new File(file);
            if (!theDir.exists())
                throw new NotEnoughArgumentException(file);
        } catch (NotEnoughArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        ArrayList<String> lines = new ArrayList<String>();

        File inFile = new File(file);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(inFile));
            String line = null;

            if(removeHeader)
                line=br.readLine();
            System.out.println("Reading File...");
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                lines.add(line);
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } finally {
            try {
                if (br!=null)
                    br.close();
            } catch (Exception e) {}
        }

        return lines;
    }

    public static void writeAFile(ArrayList<String> lines, String targetFileName){

        File theDir = new File(targetFileName);
        BufferedWriter bw = null;
        try {
            if(targetFileName.contains("\\"))
                theDir.getParentFile().mkdirs();//targetFileName이 \\을 포함하면, path를 포함하는 것으로 인식 -> 디렉토리 생성
            if(!theDir.exists())
                theDir.createNewFile();//파일 없으면 파일 생성
            bw = new BufferedWriter(new FileWriter(theDir));
            System.out.println("Writing File...");
            for(int i = 0; i < lines.size(); i++){
                bw.write(lines.get(i));
                bw.newLine();
            }
            bw.flush();
            System.out.println("File saved!!");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bw != null) try {bw.close(); } catch (IOException e) {}
        }
    }

}
