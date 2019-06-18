package edu.handong.merge;

import org.apache.commons.cli.*;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipHandler{
    String input;
    String output;
    boolean help;
    ArrayList<String> stringToSave;
    //ArrayList<String> unMergedString[];


    public void run(String[] args) {
        Options options = createOptions();
        if(parseOptions(options, args)) {
            if (help) {
                printHelp(options);
                return;
            }
        }

        ArrayList<ZipThread> sumThreads = new ArrayList<ZipThread>();
        for(int i = 1; i < 6; i++){
            String fullpath = input + "000" + i + ".zip";
            sumThreads.add(new ZipThread(fullpath));
        }
        ArrayList<Thread> threadsForAdd = new ArrayList<Thread>();

        for(ZipThread runner:sumThreads) {
            Thread thread = new Thread(runner);
            thread.start();
            threadsForAdd.add(thread);
        }
        List<InputStream> listsToMerge = new ArrayList<InputStream>();

        for(ZipThread runner:sumThreads){
            //listsToMerge.add(runner.list.get(0));
            //listsToMerge.add(runner.list.get(1));
            listsToMerge = runner.list;
        }

        File file = new File(output);
        try {
            Utils.mergeExcelFiles(file, listsToMerge);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private class ZipThread implements Runnable {
        private String fullPath;
        private List<InputStream> list;

        public ZipThread(String fullPath){
            this.fullPath = fullPath;
        }
        public void run() {
            ZipReader reader = new ZipReader();
            System.out.println("Debugging 1");
            list = reader.getFileFromZip(fullPath);
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
