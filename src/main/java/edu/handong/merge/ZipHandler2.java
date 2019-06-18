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
    Map<String, Object[]> outputData;
    Map<String, Object[]> summaryOutputData;
    Map<String, Object[]> chartOutputData;


    public void run(String[] args) {
        Options options = createOptions();
        if(parseOptions(options, args)) {
            if (help) {
                printHelp(options);
                return;
            }
        }
        outputData = new HashMap<String, Object[]>();
        summaryOutputData = new HashMap<String, Object[]>();
        chartOutputData = new HashMap<String, Object[]>();

        HashMap<String, ThreadHandler> sumThreads = new HashMap<>();
        for(int k = 1; k < 6; k++){
            String path = input + "000" + k + ".zip";
            ThreadHandler thread = new ThreadHandler(path, k);
            thread.start();
            sumThreads.put(Integer.toString(k), thread);
        }
        for (int k = 1; k < 6; k++) {
            try {
                sumThreads.get(Integer.toString(k)).join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //outputData for results.csv
        int keyNum = 0, keyNum1 = 0, keyNum2 = 0;
        for(int k = 1; k < 6; k++){
            String key = Integer.toString(k);
            ArrayList<Object[]> summary = sumThreads.get(key).getSummary();
            for(Object [] data : summary){
                outputData.put(Integer.toString(keyNum++), data);
                summaryOutputData.put(Integer.toString(keyNum1++), data);
            }
            ArrayList<Object[]> chartAndImage = sumThreads.get(key).getChartAndImage();
            for(Object [] data : chartAndImage){
                outputData.put(Integer.toString(keyNum++), data);
                chartOutputData.put(Integer.toString(keyNum2++), data);
            }
        }

        saveExcel(outputData, output);
        String fileOutPutName = output.split("\\.")[0];
        String fileType = output.split("\\.")[1];
        saveExcel(summaryOutputData, fileOutPutName + "1." + fileType);
        saveExcel(chartOutputData,fileOutPutName + "2." + fileType);
    }


    public void saveExcel(Map<String, Object[]> data, String output){
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
            FileOutputStream out = new FileOutputStream(new File(output));
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

