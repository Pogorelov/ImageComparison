package com.image.comparison.service;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 * @author Pogorelov on 9/15/2016
 */
public class ImageComparator {

    private final BufferedImage img1;
    private final BufferedImage img2;

    private final int width;
    private final int height;

    public ImageComparator(BufferedImage img1, BufferedImage img2) {
        this.img1 = img1;
        this.img2 = img2;

        validation(img1, img2);

        this.width = img1.getWidth();
        this.height = img2.getHeight();
    }

    private void validation(BufferedImage image1, BufferedImage image2) {
        int width1 = image1.getWidth();
        int height1 = image1.getHeight();
        int width2 = image2.getWidth();
        int height2 = image2.getHeight();

        if(width1 != width2 && height1 != height2) {
            throw new RuntimeException("Dimension must be the same for both images.");
        }
    }

    public int[][] compare() {
        int array[][] = new int[width][height];

        int diff;

        int rgbN = 3;
        double maxCol = 255.0;
        double maxP = 100.0;

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                int rgb1 = img1.getRGB(x, y);
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = rgb1 & 0xff;

                int rgb2 = img2.getRGB(x, y);
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >> 8) & 0xff;
                int b2 = rgb2 & 0xff;

                diff = Math.abs(r1 - r2);
                diff += Math.abs(g1 - g2);
                diff += Math.abs(b1 - b2);

                double percentageDifference = (diff / rgbN / maxCol) * maxP;

                if(percentageDifference > 10) {
                    array[x][y] = 1;
                }
            }
        }

        return array;
    }

//    public static BufferedImage deepCopy(BufferedImage bi) {
//        ColorModel cm = bi.getColorModel();
//        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
//        WritableRaster raster = bi.copyData(null);
//        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
//    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
