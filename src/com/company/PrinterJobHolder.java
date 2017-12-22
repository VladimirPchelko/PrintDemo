package com.company;

import javax.imageio.ImageIO;
import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PrinterJobHolder {
    private PageFormat pageFormat;
    private PrinterJob printJob;
    private Font font;

    /**
     * @param printerName - the system name of printer
     * @param mediaName - the name of media / page format
     */

    public PrinterJobHolder(String printerName, String mediaName) throws PrinterException, IOException, FontFormatException {
        font = Font.createFont(Font.TRUETYPE_FONT, new File("kawkab-mono-0.500/KawkabMono-Regular.ttf")).deriveFont(12f);
        printJob = PrinterJob.getPrinterJob();

        PrintService printService = PrinterUtils.findPrintService("Two Pilots Demo Printer");

        printJob.setPrintService(printService);

        Media media = PrinterUtils.findMedia("ThermalReceipt", printService);
        MediaPrintableArea mediaPrintableArea = PrinterUtils.findMediaPrintableArea(media, printService);


        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
        attributeSet.add(media);
        attributeSet.add(mediaPrintableArea);
        attributeSet.add(OrientationRequested.PORTRAIT);
        attributeSet.add(new Copies(1));

        pageFormat = printJob.getPageFormat(attributeSet);
    }

    /**
     * Render given lines to image according to {@link #pageFormat}, and print image.
     * @param lines - lines to print
     */
    public void printText(List<String> lines) throws IOException, PrinterException {
        Image image = ImageUtils.printLinesToPage(lines, font, pageFormat);

        File tempFile = new File(System.currentTimeMillis() + "-dump.png");
        ImageIO.write((RenderedImage) image, "PNG", tempFile);

        printJob.setPrintable((graphics, pageFormat1, pageIndex) -> {
            if (pageIndex != 0) {
                return Printable.NO_SUCH_PAGE;
            }

            graphics.drawImage(image, 0, 0, null);
            return Printable.PAGE_EXISTS;
        }, pageFormat);

        printJob.print();
    }
}
