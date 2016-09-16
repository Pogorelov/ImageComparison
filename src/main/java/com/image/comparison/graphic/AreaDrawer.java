package com.image.comparison.graphic;

import com.image.comparison.dto.Rectangle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.List;

/**
 * @author Pogorelov on 9/15/2016
 */
public class AreaDrawer {

    public static BufferedImage drawAreaOnImageModel(BufferedImage imageModel, List<Rectangle> rectangleList) {
        BufferedImage bufferedImage = deepCopy(imageModel);
        Graphics2D gc = bufferedImage.createGraphics();
        gc.setColor(Color.RED);

        for(Rectangle rectangle : rectangleList) {
            gc.drawRect(rectangle.getX(), rectangle.getY(), rectangle.getW(), rectangle.getH());
        }

        return bufferedImage;
    }

    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
