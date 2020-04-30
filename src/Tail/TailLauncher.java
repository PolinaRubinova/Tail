package Tail;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import java.io.*;
import java.util.ArrayList;

public class TailLauncher {
    @Option(name = "-c", metaVar = "extractSymbols", usage = "the num of symbols to extract")
    private int lastSymbols;

    @Option(name = "-n", metaVar = "extractLines", usage = "the num of lines to extract")
    private int lastLines;

    @Option(name = "-o", metaVar = "OutputName", usage = "output file name")
    private String outputFileName;

    @Argument(metaVar = "InputName", usage = "input file name")
    private ArrayList<String> inputFileNames;

    public static void main(String[] args) {
        new TailLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("error! java -jar Tail.jar -o OutputName " +
                    "[-c extractSymbols || -n extractLines] InputName1 InputName2 ...");
            parser.printUsage(System.err);
            return;
        }

        if (lastLines != 0 && lastSymbols != 0) {
            throw new Error("error! flags \"-c\" and \"-n\" were entered at the same time!");
        }

        Tail tailMain = new Tail(lastSymbols, lastLines);

        try {
            tailMain.main(inputFileNames, outputFileName);
            System.out.println("all necessary symbols/lines are extracted.");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}