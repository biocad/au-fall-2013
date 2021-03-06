import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;

/**
 * Author: Oleg Yasnev (oyasnev@gmail.com)
 * Date: 25.11.13
 */

public class ParamParser {
    public static final String INPUT_TREE_FILE_KEY   = "--guidetree-in";
    public static final String INPUT_SEQ_FILE_KEY    = "--seq-in";
    public static final String OUTPUT_FILE_KEY  = "-o";

    public static final String README = "readme.txt";
    public static final String CONFIG = "config.ini";

    public boolean state = false;

    public String inputTreeFilename = "";
    public String inputSeqFilename = "";
    public String outputFilename = "";
    public String clustalo = "";

    public ParamParser(String[] args) {
        if (args.length == 0 || args[0].equals("--help") || args[0].equals("?") || args[0].equals("-h")) {
            printHelp();
            return;
        }

        for (int i = 0; i < args.length; i++) {
            String key = args[i];
            if        (key.equals(INPUT_TREE_FILE_KEY)) {
                i++;
                inputTreeFilename = args[i];
            } else if (key.equals(INPUT_SEQ_FILE_KEY)) {
                i++;
                inputSeqFilename = args[i];
            } else if (key.equals(OUTPUT_FILE_KEY)) {
                i++;
                outputFilename = args[i];
            }
        }

        try {
            Ini ini = new Ini(new File(CONFIG));
            clustalo = ini.get("tools", "clustalo");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // check state
        state = true;
        if (inputTreeFilename.isEmpty()) {
            state = false;
            StdOut.println("Input Newick guide tree file must be specified");
        }
        if (inputSeqFilename.isEmpty()) {
            state = false;
            StdOut.println("Input FASTA file with sequences must be specified");
        }
        if (outputFilename.isEmpty()) {
            state = false;
            StdOut.println("Output file must be specified");
        }
        if (clustalo.isEmpty()) {
            state = false;
            StdOut.println("Path to ClustalO must be specified in config.ini");
        }

        if (!state) {
            StdOut.println("For more information run the program with the key '--help'");
        }
    }

    public static void printHelp() {
        In in = new In(README);
        while (!in.isEmpty()) {
            StdOut.println(in.readLine());
        }
    }
}
