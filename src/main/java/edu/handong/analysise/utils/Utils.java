package edu.handong.analysise.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {

    public static ArrayList<String> getLines(String file,boolean removeHeader){

        /*try {
            File theDir = new File(file);
            if (!theDir.exists())
                throw new NotEnoughArgumentException(file);
        } catch (NotEnoughArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }*/

        ArrayList<String> lines = new ArrayList<String>();
        /*Scanner inputStream = null;
        System.out.println ("The file " + file + "\ncontains the following lines:\n");

        try {
            inputStream = new Scanner(new File(file));
            String line = null;
            if(removeHeader)
                line = inputStream.nextLine();
            while ((line = inputStream.nextLine()) != null) {
                System.out.println(line);
                lines.add(line);
            }
        }  catch (FileNotFoundException e) {
            System.out.println ("Error opening the file " + file);
            System.exit (0);
        }*/
        File inFile = new File(file);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(inFile));
            String line = null;

            if(removeHeader)
                line=br.readLine();
            while ((line = br.readLine()) != null) {
                System.out.println(line);
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
        if (!theDir.exists()) theDir.mkdirs();

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(theDir));
            for(int i = 0; i < lines.size() - 1; i++){
                bw.write(lines.get(i));
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bw != null) try {bw.close(); } catch (IOException e) {}
        }
    }

}
