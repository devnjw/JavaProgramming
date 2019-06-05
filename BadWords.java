import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BadWords {
    ArrayList<String> badWords;
    public BadWords(){
        badWords = readBadWords();
    }

    public void addBadWords(String badWord){
        badWords.add(badWord);
    }

    public ArrayList<String> getBadWords() {
        return badWords;
    }

    public ArrayList<String> readBadWords() {
        ArrayList<String> badWords = new ArrayList<>();

        File inFile = new File("badWords.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(inFile));
            String line = null;
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                badWords.add(line);
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (Exception e) {
            }
        }
        return badWords;
    }
    public void saveBadWords(){
        File theDir = new File("badWords.txt");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(theDir));
            //System.out.println("Writing File...");
            for(int i = 0; i < badWords.size(); i++){
                bw.write(badWords.get(i));
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
