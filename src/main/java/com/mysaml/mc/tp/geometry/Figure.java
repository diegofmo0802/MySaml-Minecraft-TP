package com.mysaml.mc.tp.geometry;

public abstract class Figure {
    protected Position position = new Position();
    protected Position pivot = new Position();
    protected Point[] points = null;
    public abstract Point[] generate();
    private boolean initialized = false;
    public boolean isInitialized() { return initialized; }
    public Figure(Position position) {
        this.position = position.clone();
        this.pivot = position.clone();
    }
    public void initialize() {
        if (initialized) return;
        initialized = true;
        points = generate();
    }
    public void ForEachPoints(FigureCallback callback) { initialize();
        for (Point point : points) {
            callback.apply(point.getX(), point.getY(), point.getZ());
        }
    }
    public void setPivot(Position newPivot) { initialize();
        for (Point point : points) {
            point.setPivot(
                position.getX() + newPivot.getX(),
                position.getY() + newPivot.getY(),
                position.getZ() + newPivot.getZ()
            );
        }
        pivot = newPivot.clone();
    }
    public void setPosition(Position newPosition) { initialize();
        for (Point point : points) {
            point.setPosition(
                point.getX() - position.getX() + newPosition.getX(),
                point.getY() - position.getY() + newPosition.getY(),
                point.getZ() - position.getZ() + newPosition.getZ()
            );
        }
        position = newPosition.clone();
    }

    public void translate(double x, double y, double z) { initialize();
        for (Point point : points) {
            point.translate(x, y, z);
        }
        position.setAll(
            position.getX() + x,
            position.getY() + y,
            position.getZ() + z
        );
        pivot.setAll(
            pivot.getX() + x,
            pivot.getY() + y,
            pivot.getZ() + z
        );
    }
    public void rotate(double angleX, double angleY, double angleZ) { initialize();
        for (Point point : points) {
            point.rotate(angleX, angleY, angleZ);
        }
    }
    @FunctionalInterface
    public interface FigureCallback {
        void apply(double x, double y, double z);
    }
}
