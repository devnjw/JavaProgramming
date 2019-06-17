package edu.handong.merge;

import org.apache.commons.cli.*;

import java.io.*;
import java.util.*;
import java.util.zip.ZipInputStream;

public class ZipHandler {
    String input;
    String output;
    boolean help;

    public void run(String[] args) {
        Options options = createOptions();
        if(parseOptions(options, args)) {
            if (help) {
                printHelp(options);
                return;
            }
        }
        try(FileInputStream fin = new FileInputStream(input)){
            ZipInputStream firstZip = new ZipInputStream(fin);

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
