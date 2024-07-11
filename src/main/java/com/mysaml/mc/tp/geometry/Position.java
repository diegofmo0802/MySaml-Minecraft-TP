package com.mysaml.mc.tp.geometry;

public class Position {
    private double x;
    private double y;
    private double z;
    public Position(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Position()          { this(0, 0, 0); }
    public Position clone()    { return new Position(x, y, z); }
    public double getX()       { return x; }
    public double getY()       { return y; }
    public double getZ()       { return z; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setZ(double z) { this.z = z; }
    public void setAll(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
