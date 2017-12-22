package com.company;

import java.awt.*;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrinterDemo {
        
    public static void main(String[] args) throws IOException, FontFormatException, PrinterException {
        String fileName = getFileName(args);

        List<String> lines = getLinesFromFile(fileName);

        PrinterJobHolder printerJobHolder = new PrinterJobHolder("Two Pilots Demo Printer", "ThermalReceipt");

        printerJobHolder.printText(lines);
    }

    private static String getFileName(String[] args) {
        if (args.length == 0) {
            return "Arabic-Lipsum.txt";
        } else {
            return args[0];
        }
    }

    public static List<String> getLinesFromFile(String fileName) throws IOException {
        List<String> lines = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
