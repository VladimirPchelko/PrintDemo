package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.util.List;

public class ImageUtils {

    static public Image printLinesToPage(List<String> lines, Font font, PageFormat pageFormat) {

        JTextArea jep = new JTextArea();
        jep.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        FontMetrics fontMetrics = jep.getFontMetrics(font);

        List<String> wrappedLines = lines = StringUtils.wrap(lines, fontMetrics, (int)pageFormat.getImageableWidth());

        StringBuilder builder = new StringBuilder();

        for (String line : wrappedLines) {
            builder.append(line);
            builder.append("\n");
        }

        System.out.println(builder.toString());

        int desiredHeight = wrappedLines.size() * fontMetrics.getHeight();

        jep.setText(builder.toString());
        jep.setSize((int) pageFormat.getImageableWidth(), desiredHeight);
        jep.setFont(font);

        BufferedImage image = new BufferedImage((int) pageFormat.getWidth(), (int) pageFormat.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = image.createGraphics();

        graphics.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        // Create an `JEditorPane` and invoke `print(Graphics)`
        graphics.setPaint(Color.WHITE);
        graphics.fillRect(0,0, (int) pageFormat.getWidth(), (int) pageFormat.getHeight());
        graphics.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        jep.print(graphics);

        return image;
    }
}

