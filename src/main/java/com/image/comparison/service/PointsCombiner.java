package com.image.comparison.service;

import com.image.comparison.dto.ExtremePoints;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pogorelov on 9/15/2016
 */
public class PointsCombiner {

    public static List<ExtremePoints> combinePoints(List<ExtremePoints> extremePoints, int w, int h) {
        List<ExtremePoints> result = new ArrayList<>();

        if (extremePoints.isEmpty()) return result;

        ExtremePoints pointsPrev = extremePoints.get(0);
        boolean shouldCombine;

        for (ExtremePoints points : extremePoints) {
            shouldCombine = combineIfClose(points, pointsPrev, w, h);

            if (!shouldCombine) {
                result.add(points);
                pointsPrev = points;
                continue;
            }


            int p = points.getLy();
            int pPrev = pointsPrev.getLy();
            int value = getMinOrMaxIfClose(p, pPrev, false);
            points.setLy(value);

            p = points.getTx();
            pPrev = pointsPrev.getTx();
            value = getMinOrMaxIfClose(p, pPrev, false);
            points.setTx(value);

            p = points.getRy();
            pPrev = pointsPrev.getRy();
            value = getMinOrMaxIfClose(p, pPrev, true);
            points.setRy(value);

            p = points.getBx();
            pPrev = pointsPrev.getBx();
            value = getMinOrMaxIfClose(p, pPrev, true);
            points.setBx(value);

            int index = result.indexOf(pointsPrev);
            result.set(index, points);

            pointsPrev = points;
        }

        return result;
    }

    private static boolean combineIfClose(ExtremePoints points1, ExtremePoints points2, double w, double h) {
        int p = points1.getLy();
        int pPrev = points2.getLy();
        double diff = Math.abs(p - pPrev);

        p = points1.getTx();
        pPrev = points2.getTx();
        diff += Math.abs(p - pPrev);

        p = points1.getRy();
        pPrev = points2.getRy();
        diff += Math.abs(p - pPrev);

        p = points1.getBx();
        pPrev = points2.getBx();
        diff += Math.abs(p - pPrev);

        double res = (diff / (h + w)) * 100.0;
        boolean shouldCombine = false;
        if (res > 0 && res < 10) {
            shouldCombine = true;
        }
        return shouldCombine;
    }

    private static int getMinOrMaxIfClose(int p, int pPrev, boolean isMax) {
        return isMax ? Math.max(p, pPrev) : Math.min(p, pPrev);
    }
}
