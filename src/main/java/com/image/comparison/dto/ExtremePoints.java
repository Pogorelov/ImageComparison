package com.image.comparison.dto;

/**
 * @author Pogorelov on 9/15/2016
 */
public class ExtremePoints {

    private int lx,ly;
    private int tx, ty;
    private int rx, ry;
    private int bx, by;

    public ExtremePoints(int[] leftP, int[] topP, int[] rightP, int[] botP) {
        this.lx = leftP[0];
        this.ly = leftP[1];

        this.tx = topP[0];
        this.ty = topP[1];

        this.rx = rightP[0];
        this.ry = rightP[1];

        this.bx = botP[0];
        this.by = botP[1];
    }

    public int getLx() {
        return lx;
    }

    public int getLy() {
        return ly;
    }

    public int getTx() {
        return tx;
    }

    public int getTy() {
        return ty;
    }

    public int getRx() {
        return rx;
    }

    public int getRy() {
        return ry;
    }

    public int getBx() {
        return bx;
    }

    public int getBy() {
        return by;
    }


    public ExtremePoints setLy(int ly) {
        this.ly = ly;
        return this;
    }

    public ExtremePoints setTx(int tx) {
        this.tx = tx;
        return this;
    }

    public ExtremePoints setRy(int ry) {
        this.ry = ry;
        return this;
    }

    public ExtremePoints setBx(int bx) {
        this.bx = bx;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExtremePoints that = (ExtremePoints) o;

        if (lx != that.lx) return false;
        if (ly != that.ly) return false;
        if (tx != that.tx) return false;
        if (ty != that.ty) return false;
        if (rx != that.rx) return false;
        if (ry != that.ry) return false;
        if (bx != that.bx) return false;
        return by == that.by;

    }

    @Override
    public int hashCode() {
        int result = lx;
        result = 31 * result + ly;
        result = 31 * result + tx;
        result = 31 * result + ty;
        result = 31 * result + rx;
        result = 31 * result + ry;
        result = 31 * result + bx;
        result = 31 * result + by;
        return result;
    }
}
