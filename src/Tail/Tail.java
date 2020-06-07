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

    private void tail(ArrayList<String> reader, BufferedWriter writer) throws IOException {
        if (lastLines != 0) {
            extractLastLines(reader, writer);
        } else if (lastSymbols != 0) {
            extractLastSymbols(reader, writer);
        } else {
            lastLines = 10;
            extractLastLines(reader, writer);
        }
    }

    private void extractLastLines(ArrayList<String> reader, BufferedWriter writer) throws IOException {
        Deque<String> deque = new LinkedList<>();
        for (String string : reader) {
            if (string != null) {
                if (deque.size() == lastLines) {
                    deque.pollFirst();
                }
                deque.add(string);
            }
        }
        while (deque.peekFirst() != null) {
            writer.write(Objects.requireNonNull(deque.pollFirst()));
            if (!deque.isEmpty()) {
                writer.newLine();
            }
        }
    }

    private void extractLastSymbols(ArrayList<String> reader, BufferedWriter writer) throws IOException {
        Deque<Character> deque = new LinkedList<>();
        for (String string : reader) {
            for (int j = 0; j < string.length(); j++) {
                int symbols = string.charAt(j);
                if (deque.size() == lastSymbols) {
                    deque.pollFirst();
                }
                deque.add((char) symbols);
            }
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
                ArrayList<String> console = new ArrayList<>();
                console.add(String.valueOf(reader.readLine()));
                reader.close();
                tail(console,writer);
            }
        }
        else {
            try (writer) {
                for (String str : inputNames) {
                        writer.write(new File(str).getName());
                        writer.newLine();
                        ArrayList<String> text = (ArrayList<String>) Files.readAllLines(Paths.get(str));
                        tail(text, writer);
                        writer.newLine();
                }
            }
        }
    }
}