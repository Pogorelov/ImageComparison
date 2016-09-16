package com.image.comparison.utils;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
//import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
//import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Pogorelov on 9/15/2016
 */
public class ImageUtils {

    public static BufferedImage read(final String filename) {
        BufferedImage image = null;
        try {
            final File file = new File(filename);
            image = ImageIO.read(file);
        } catch (IOException e) {
            //LOG handle IOException
        }

        return image;
    }

//    public static void save(BufferedImage img, final String filename) {
//        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
//        Graphics2D g2 = bi.createGraphics();
//        g2.drawImage(img, null, null);
//
//        FileOutputStream out = null;
//        try {
//            out = new FileOutputStream(filename);
//        } catch (java.io.FileNotFoundException io) {
//            //LOG Handle FileNotFound
//        }
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
//        param.setQuality(0.8f,false);
//        encoder.setJPEGEncodeParam(param);
//        try {
//            encoder.encode(bi);
//            out.close();
//        } catch (java.io.IOException io) {
//            //LOG handle IOException
//        }
//    }


}
