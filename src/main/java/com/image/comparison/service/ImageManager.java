package com.image.comparison.service;

import com.image.comparison.dto.ExtremePoints;
import com.image.comparison.dto.Rectangle;
import com.image.comparison.graphic.AreaDrawer;
import com.image.comparison.utils.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.css.Rect;

//import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Pogorelov on 9/15/2016
 */
@Service
public class ImageManager {

    private final int[] neighbor = {-1, 1, 0, 0};

    public BufferedImage uploadAndProcess(MultipartFile file1, MultipartFile file2) throws Exception {
        if (file1.getSize() == 0 || file2.getSize() == 0) {
            throw new RuntimeException("Images are empty!");
        }

        InputStream inputStream = file1.getInputStream();
        BufferedImage image1 = ImageIO.read(inputStream);

        inputStream = file2.getInputStream();
        BufferedImage image2 = ImageIO.read(inputStream);

        return processImages(image1, image2);
    }

    public void uploadAndProcess(final String picUrl1, final String picUrl2, final String outputPicUrl) throws Exception {
        BufferedImage image1 = ImageUtils.read(picUrl1);
        BufferedImage image2 = ImageUtils.read(picUrl2);

        BufferedImage bufferedImage = processImages(image1, image2);
//        ImageUtils.save(bufferedImage, outputPicUrl);
    }

    public BufferedImage processImages(BufferedImage image1, BufferedImage image2) throws Exception {

        ImageComparator matrixPresenter = new ImageComparator(image1, image2);

        int[][] matrix = matrixPresenter.compare();
        boolean[][] visited = visitedFalseDef(matrix);

        List<ExtremePoints> extremePointsList = new ArrayList<>();
        ExtremePoints extremePoints;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 1) {
                    //BFS
                    extremePoints = bfs(i,j,matrix,visited);
                    extremePointsList.add(extremePoints);
                }
            }
        }

        int w = matrixPresenter.getWidth();
        int h = matrixPresenter.getHeight();
        extremePointsList = PointsCombiner.combinePoints(extremePointsList, w, h);
        List<Rectangle>  rectangleList = processExtremePoints(extremePointsList);

        return AreaDrawer.drawAreaOnImageModel(image2, rectangleList);
    }

    private List<Rectangle> processExtremePoints(List<ExtremePoints> extremePointsList) {
       List<Rectangle> rectangles = new ArrayList<>();

        Rectangle rectangle;
        for(ExtremePoints points : extremePointsList) {
            int x = points.getTx();
            int y = points.getLy();
            int h = points.getRy() - points.getLy();
            int w = points.getBx() - points.getTx();
            rectangle = new Rectangle(x, y, w, h);
            rectangles.add(rectangle);
        }

        return rectangles;
    }

    private ExtremePoints bfs(int x, int y, int[][] matrix, boolean[][] visited) {
        int[] maxLeftPoint = {0, Integer.MAX_VALUE};
        int[] maxBotPoint = {Integer.MIN_VALUE, 0};
        int[] maxTopPoint = {Integer.MAX_VALUE, 0};
        int[] maxRightPoint = {0, Integer.MIN_VALUE};
        boolean isSkipped = true;

        Queue<Integer> queue_x = new LinkedList<>(), queue_y = new LinkedList<>();
        int curX, curY;

        visited[x][y] = true;
        queue_x.add(x);
        queue_y.add(y);

        while (!queue_x.isEmpty()) {
            curX = queue_x.poll();
            curY = queue_y.poll();

            if (matrix[curX][curY] == 0) {
                continue;
            }

            //leftPoint
            if(maxLeftPoint[1] > curY) {
                maxLeftPoint[0] = curX;
                maxLeftPoint[1] = curY;
            }

            //rightPoint
            if(maxRightPoint[1] < curY) {
                maxRightPoint[0] = curX;
                maxRightPoint[1] = curY;
            }

            //topPoint
            if(maxTopPoint[0] > curX) {
                maxTopPoint[0] = curX;
                maxTopPoint[1] = curY;
            }

            //botPoint
            if(maxBotPoint[0] < curX) {
                maxBotPoint[0] = curX;
                maxBotPoint[1] = curY;
            }

            matrix[curX][curY] = 0;

            int length = neighbor.length;
            for (int i = 0; i < length; i++) {
                int fX = curX + neighbor[i] < 0 ? 0 : curX + neighbor[i];
                int fY = curY + neighbor[length - 1 - i] < 0 ? 0 : curY + neighbor[length - 1 - i];
                if(fX >= matrix.length) fX = matrix.length - 1;
                if(fY >= matrix[0].length) fY = matrix[0].length - 1;

                try {
                    int node = matrix[fX][fY];

                    if (node == 1 && !visited[fX][fY]) {
                        queue_x.add(fX);
                        queue_y.add(fY);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(1);
                }
            }


            if(isSkipped) isSkipped = false;
        }
//
//        System.out.println("\n\nFinish BFS");
//        System.out.println(String.format("left:[%d,%d]", maxLeftPoint[0], maxLeftPoint[1]));
//        System.out.println(String.format("top:[%d,%d]", maxTopPoint[0], maxTopPoint[1]));
//        System.out.println(String.format("right:[%d,%d]", maxRightPoint[0], maxRightPoint[1]));
//        System.out.println(String.format("bot:[%d,%d]", maxBotPoint[0], maxBotPoint[1]));

        ExtremePoints extremePoints = null;

        if(isSkipped) return extremePoints;

        extremePoints = new ExtremePoints(maxLeftPoint, maxTopPoint, maxRightPoint, maxBotPoint);

        return extremePoints;
    }

    private static boolean[][] visitedFalseDef(int[][] matrix) {
        boolean[][] result = new boolean[matrix.length][matrix[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = false;
            }
        }
        return result;
    }

}
