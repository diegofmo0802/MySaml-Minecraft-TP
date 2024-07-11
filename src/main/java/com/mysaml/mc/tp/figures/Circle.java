package com.mysaml.mc.tp.figures;

import org.bukkit.Location;
import org.bukkit.Particle;

import com.mysaml.mc.tp.geometry.Point;

public class Circle extends SummonableFigure {
    private double radius;
    private double density;
    public Circle(Location location, Particle particle, double radius, double density) {
        super(location, particle);
        this.radius = radius;
        this.density = density;
    }
    @Override
    public Point[] generate() {
        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();

        int quantity = ((int) ((2 * Math.PI * radius) / (density / 100))) * 5;
        double increment = ((2 * Math.PI) / (quantity));

        Point[] points = new Point[quantity];
        int NextItem = 0;
        for (int pointPos = 0; pointPos < quantity; pointPos++) {
            double angle = pointPos * increment;
            double initX = x + Math.cos(angle) * radius;
            double initZ = z + Math.sin(angle) * radius;
            Point summon = new Point(initX, y, initZ, x, y, z);
            points[NextItem++] = summon;
        }
        return points;
    }
}
