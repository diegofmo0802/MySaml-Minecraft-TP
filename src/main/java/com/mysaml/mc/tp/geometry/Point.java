package com.mysaml.mc.tp.geometry;

public class Point {
    protected Position position;
    protected Position pivot;
    protected Point[] points;

    public Point(double x, double y, double z, double pivotX, double pivotY, double pivotZ) {
        this.position = new Position(x, y, z);
        this.pivot = new Position(pivotX, pivotY, pivotZ);
    }
    public Point(double x, double y, double z) {
        this(x, y, z, x, y, x);
    }
    public Point() { this(0, 0, 0); }
    
    public Position getPosition() { return position; }
    public Position getPivot() { return pivot; }

    public double getX() { return position.getX(); }
    public double getY() { return position.getY(); }
    public double getZ() { return position.getZ(); }
    public double getPivotX() { return pivot.getX(); }
    public double getPivotY() { return pivot.getY(); }
    public double getPivotZ() { return pivot.getZ(); }

    public Point setPivot(Position pivot)       { this.pivot = pivot; return this; }
    public Point setPosition(Position position) { this.position = position; return this; }
    public Point setPivot(double pivotX, double pivotY, double pivotZ) {
        this.pivot.setAll(pivotX, pivotY, pivotZ);
        return this;
    }
    public Point setPosition(double x, double y, double z) {
        this.position.setAll(x, y, z);
        return this;
    }

    public Point setX(double x) { this.position.setX(x); return this; }
    public Point setY(double y) { this.position.setY(y); return this; }
    public Point setZ(double z) { this.position.setZ(z); return this; }
    public Point setPivotX(double pivotX) { this.pivot.setX(pivotX); return this; }
    public Point setPivotY(double pivotY) { this.pivot.setY(pivotY); return this; }
    public Point setPivotZ(double pivotZ) { this.pivot.setZ(pivotZ); return this; }

    public Point translate(double x, double y, double z) {
        this.position.setAll(
            this.position.getX() + x,
            this.position.getY() + y,
            this.position.getZ() + z
        );
        return this;
    }

    public Point rotate(double angleX, double angleY, double angleZ) {
        Point rotated = Point.rotate(
            this.position.getX(),
            this.position.getY(),
            this.position.getZ(),
            this.pivot.getX(),
            this.pivot.getY(),
            this.pivot.getZ(),
            angleX, angleY, angleZ
        );
        this.position = rotated.position.clone();;
        return this;
    }
    protected static Point rotate(
        double x,      double y,      double z,
        double pivotX, double pivotY, double pivotZ,
        double angleX, double angleY, double angleZ
    ) {
        angleX = angleX * Math.PI / 180;
        angleY = angleY * Math.PI / 180;
        angleZ = angleZ * Math.PI / 180;

        Matrix point = new Matrix(4, 1);
        Matrix rx = new Matrix(4, 4);
        Matrix ry = new Matrix(4, 4);
        Matrix rz = new Matrix(4, 4);
        Matrix translation = new Matrix(4, 4);
        Matrix reverseTranslation = new Matrix(4, 4);

        point.setAll(new double[][]{
            {x},
            {y},
            {z},
            {1}
        });
        rx.setAll(new double[][]{
            {1, 0, 0, 0},
            {0, Math.cos(angleX), -Math.sin(angleX), 0},
            {0, Math.sin(angleX), Math.cos(angleX), 0},
            {0, 0, 0, 1}
        });
        ry.setAll(new double[][]{
            {Math.cos(angleY), 0, Math.sin(angleY), 0},
            {0, 1, 0, 0},
            {-Math.sin(angleY), 0, Math.cos(angleY), 0},
            {0, 0, 0, 1}
        });
        rz.setAll(new double[][]{
            {Math.cos(angleZ), -Math.sin(angleZ), 0, 0},
            {Math.sin(angleZ), Math.cos(angleZ), 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
        });
        translation.setAll(new double[][]{
            {1, 0, 0, -pivotX},
            {0, 1, 0, -pivotY},
            {0, 0, 1, -pivotZ},
            {0, 0, 0, 1}
        });
        reverseTranslation.setAll(new double[][]{
            {1, 0, 0, pivotX},
            {0, 1, 0, pivotY},
            {0, 0, 1, pivotZ},
            {0, 0, 0, 1}
        });

        Matrix rotationMatrix = Matrix.multiply(rz, Matrix.multiply(ry, rx));
        Matrix transformMatrix = Matrix.multiply(reverseTranslation, Matrix.multiply(rotationMatrix, translation));
        Matrix transformedPoint = Matrix.multiply(transformMatrix, point);

        double newX = transformedPoint.get(0, 0);
        double newY = transformedPoint.get(1, 0);
        double newZ = transformedPoint.get(2, 0);

        return new Point(newX, newY, newZ, pivotX, pivotY, pivotZ);
    }
}
