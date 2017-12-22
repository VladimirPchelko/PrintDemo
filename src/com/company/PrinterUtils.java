package com.company;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.MediaPrintableArea;
import java.awt.print.PrinterJob;

public class PrinterUtils {

    public static PrintService findPrintService(String printerName) {
        for (PrintService printService : PrinterJob.lookupPrintServices()) {

            if (printService.getName().equalsIgnoreCase(printerName)) {
                return printService;
            }
        }
        return PrintServiceLookup.lookupDefaultPrintService();
    }

    public static Media findMedia(String mediaName, PrintService printService) {
        Media[] medias = (Media[])printService.getSupportedAttributeValues(Media.class, null, null);

        for (Media media : medias) {
            if (media.toString().equalsIgnoreCase(mediaName)) {
                return media;
            }
        }
        return (Media) printService.getDefaultAttributeValue(Media.class);
    }

    public static MediaPrintableArea findMediaPrintableArea(Media media, PrintService printService) {
        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
        attributeSet.add(media);

        MediaPrintableArea[] mediaPrintableAreas = (MediaPrintableArea[])printService.getSupportedAttributeValues(MediaPrintableArea.class, null, attributeSet);
        if (mediaPrintableAreas.length > 1) {
            return mediaPrintableAreas[0];
        }

        return (MediaPrintableArea) printService.getDefaultAttributeValue(MediaPrintableArea.class);
    }

//    @SuppressWarnings("ConstantConditions")
//    public static void main(String[] args) {
//        PrintService printService = findPrintService("Two Pilots Demo Printer");
//        Media media = findMedia("ThermalReceipt", printService);
//        MediaPrintableArea mediaPrintableArea = findMediaPrintableArea(media, printService);
//    }
}
