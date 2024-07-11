package com.mysaml.mc.tp.figures;

import org.bukkit.Location;
import org.bukkit.Particle;

import com.mysaml.mc.tp.geometry.Point;

public class Square extends SummonableFigure {
    private double size;
    private double density;
    public Square(Location location, Particle particle, double size, double density) {
        super(location, particle);
        this.size = size;
        this.density = density;
    }

    @Override
    public Point[] generate() {
        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();
        
        int sideQuantity     = (int) (size / (density / 100)) * 5;
        double increment = (size) / (sideQuantity);
        
        this.points = new Point[sideQuantity*4];
        int nextItem = 0;

        for (int pointIndex = 0; pointIndex < sideQuantity; pointIndex++) {
            double initX = x - (size / 2) + pointIndex * increment;
            double initZ = z - (size / 2);
            points[nextItem++] = new Point(initX, y, initZ, x, y, z);
        }
        for (int pointIndex = 0; pointIndex < sideQuantity; pointIndex++) {
            double initX = x + (size / 2);
            double initZ = z - (size / 2) + pointIndex * increment;
            points[nextItem++] = new Point(initX, y, initZ, x, y, z);
        }
        for (int pointIndex = 0; pointIndex < sideQuantity; pointIndex++) {
            double initX = x + (size / 2) - pointIndex * increment;
            double initZ = z + (size / 2);
            points[nextItem++] = new Point(initX, y, initZ, x, y, z);
        }
        for (int pointIndex = 0; pointIndex < sideQuantity; pointIndex++) {
            double initX = x - (size / 2);
            double initZ = z + (size / 2) - pointIndex * increment;
            points[nextItem++] = new Point(initX, y, initZ, x, y, z);
        }
        return points;
    }
}
