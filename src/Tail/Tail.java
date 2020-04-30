package Tail;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

@SuppressWarnings("WeakerAccess")

public class Tail {

    private int lastSymbols;
    private int lastLines;

    public Tail(int lastSymbols, int lastLines) {
        this.lastSymbols = lastSymbols;
        this.lastLines = lastLines;
    }

    private void tail(BufferedReader reader, BufferedWriter writer) throws IOException {
        if (lastLines != 0) {
            extractLastLines(reader, writer);
        } else if (lastSymbols != 0) {
            extractLastSymbols(reader, writer);
        } else {
            lastLines = 10;
            extractLastLines(reader, writer);
        }
    }

    private void extractLastLines(BufferedReader reader, BufferedWriter writer) throws IOException {
        Deque<String> deque = new LinkedList<>();
        String string;
        while ((string = reader.readLine()) != null) {
            if (deque.size() == lastLines) {
                deque.pollFirst();
            }
            deque.add(string);
        }
        while (deque.peekFirst() != null) {
            writer.write(Objects.requireNonNull(deque.pollFirst()));
            if (!deque.isEmpty()) {
                writer.newLine();
            }
        }
    }

    private void extractLastSymbols(BufferedReader reader, BufferedWriter writer) throws IOException {
        Deque<Character> deque = new LinkedList<>();
        int symbols ;
        while ((symbols = reader.read()) > -1) {
            if (deque.size() == lastSymbols) {
                deque.pollFirst();
            }
            deque.add((char) symbols);
        }
        while (deque.peekFirst() != null) {
            writer.write(String.valueOf(Objects.requireNonNull(deque.pollFirst())));
        }
    }

    public void main(ArrayList<String> inputNames, String outputName) throws IOException {
        BufferedWriter writer = outputName == null ?
                new BufferedWriter(new OutputStreamWriter(System.out)) :
                Files.newBufferedWriter(Paths.get(outputName));
        if (inputNames == null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                    String line;
                System.out.println("enter your text:");
                    while (!(line = reader.readLine()).equals("exit")) {
                        writer.write(line);
                    }
                    tail(reader,writer);
            }
        }
        else {
            try (writer) {
                for (String str : inputNames) {
                    try (BufferedReader reader = Files.newBufferedReader(Paths.get(str))) {
                        writer.write(new File(str).getName());
                        writer.newLine();
                        tail(reader, writer);
                        writer.newLine();
                    }
                }
            }
        }
    }
}
