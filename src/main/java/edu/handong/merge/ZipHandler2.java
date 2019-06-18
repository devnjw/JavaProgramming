package edu.handong.merge;

import edu.handong.merge.datamodel.FileData;
import org.apache.commons.cli.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

public class ZipHandler2 {
    String input;
    String output;
    boolean help;
    ArrayList<String> stringToSave;
    //ArrayList<String> unMergedString[];
    Map<String, Object[]> outputData;


    public void run(String[] args) {
        Options options = createOptions();
        if(parseOptions(options, args)) {
            if (help) {
                printHelp(options);
                return;
            }
        }
        outputData = new HashMap<String, Object[]>();

        ZipReader zipReader = new ZipReader();
        zipReader.readFileInZip2("0001.zip");

        ArrayList<FileData> fileDatas;
        zipReader.getFileDatas().get(0);

        int i = 0;
        ArrayList<Object[]> firstFileDatas = zipReader.getFileDatas().get(0).getData();
        for(Object [] data : firstFileDatas){
            outputData.put(Integer.toString(i++), data);
        }

        ArrayList<Object[]> secondFileDatas = zipReader.getFileDatas().get(1).getData();
        for(Object [] data : secondFileDatas){
            outputData.put(Integer.toString(i++), data);
        }

        saveExcel(outputData);
    }

    public void saveExcel(Map<String, Object[]> data){
        // Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Merging");

        // Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (int i = 0; i < data.size(); i++) {
            // this creates a new row in the sheet
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(Integer.toString(i));
            int cellnum = 0;
            for (Object obj : objArr) {
                // this line creates a cell in the next column of that row
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String)
                    cell.setCellValue((String)obj);
                else if (obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        try {
            // this Writes the workbook gfgcontribute
            FileOutputStream out = new FileOutputStream(new File("results.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("results.xlsx written successfully on disk.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean parseOptions(Options options, String[] args) {
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);

            input = cmd.getOptionValue("i");
            output = cmd.getOptionValue("o");

            help = cmd.hasOption("h");

        } catch (Exception e) {
            printHelp(options);
            return false;
        }

        return true;
    }

    // Definition Stage
    private Options createOptions() {
        Options options = new Options();

        // add options by using OptionBuilder
        options.addOption(Option.builder("i").longOpt("input")
                .desc("Set an input file path")
                .hasArg()
                .argName("Path name to read")
                .required()
                .build());
        options.addOption(Option.builder("o").longOpt("output")
                .desc("Set an output file path")
                .hasArg()
                .argName("Path name to write")
                .required()
                .build());

        // add options by using OptionBuilder
        options.addOption(Option.builder("h").longOpt("help")
                .desc("Help")
                .build());

        return options;
    }

    private void printHelp(Options options) {
        // automatically generate the help statement
        HelpFormatter formatter = new HelpFormatter();
        String header = "Java Final Project Zip and Excel Handler";
        String footer ="";
        formatter.printHelp("JavaFinalProject", header, options, footer, true);
    }
}
