package com.mysaml.mc.tp.figures;

import org.bukkit.Location;
import org.bukkit.Particle;

import com.mysaml.mc.tp.geometry.Point;

public class Polygon extends SummonableFigure {
    private double radius;
    private int sides;
    private double density;
    public Polygon(Location location, Particle particle, double radius, int sides, double density) {
        super(location, particle);
        this.radius = radius;
        this.sides = sides > 3 ? sides : 3;
        this.density = density;
    }
    @Override
    public Point[] generate() {
        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();

        double sideSize = 2 * radius * Math.sin(Math.PI / sides);
        int sideQuantity = (int) ((sideSize / (density / 100)) * 5);
        int NextItem = 0;

        Point[] points = new Point[sides * sideQuantity];

        for (int side = 0; side < sides; side++) {
            double initAngle = 2 * Math.PI / sides * side;
            double endAngle = 2 * Math.PI / sides * (side +1);
            double startX = x + Math.cos(initAngle) * radius;
            double startZ = z + Math.sin(initAngle) * radius;
            double endX = x + Math.cos(endAngle) * radius;
            double endZ = z + Math.sin(endAngle) * radius;
            for (int pointIndex = 0; pointIndex < sideQuantity; pointIndex++) {
                double PointIncrement = (double) pointIndex / sideQuantity;

                double summonX = startX + PointIncrement * (endX - startX);
                double summonZ = startZ + PointIncrement * (endZ - startZ);
                
                points[NextItem++] = new Point(summonX, y, summonZ, x, y, z);
            }
        }
        return points;
    }
}
