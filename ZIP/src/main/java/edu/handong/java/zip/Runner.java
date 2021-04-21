package edu.handong.java.zip;

import org.apache.commons.cli.*;

public class Runner {
    //CLI commends
    boolean help;
    boolean unZip;
    String path;
    String toPath;
    String fileName;

    public void run(String[] args){
        Options options = createOptions();
        if(parseOptions(options, args)) {
            if (help) {
                printHelp(options);
                return;
            }
        }

        ZipBody zipBody = new ZipBody();
        if(unZip)
            zipBody.extractZipFiles(fileName, toPath);
        else
            zipBody.createZipFile(path, toPath, fileName);

    }
    private boolean parseOptions(Options options, String[] args) {
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);

            path = cmd.getOptionValue("p");
            toPath = cmd.getOptionValue("t");
            fileName = cmd.getOptionValue("n");

            unZip = cmd.hasOption("u");
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
        options.addOption(Option.builder("p").longOpt("path")
                .desc("Set an input file path")
                .hasArg()
                .argName("Path name to read")
                .required()
                .build());
        options.addOption(Option.builder("t").longOpt("toPath")
                .desc("Set an output file path")
                .hasArg()
                .argName("Path name to write")
                .required()
                .build());
        options.addOption(Option.builder("n").longOpt("fileName")
                .desc("Set an output file Name")
                .hasArg()
                .argName("File name to write")
                .required()
                .build());

        // add options by using OptionBuilder
        options.addOption(Option.builder("u").longOpt("unZip")
                .desc("UnZip")
                .build());
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
