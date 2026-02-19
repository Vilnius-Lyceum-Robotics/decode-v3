package org.firstinspires.ftc.teamcode.subsystems.shooter.helpers;

public class AutoAimPoints {
    protected static class Point{
        double distance;
        double hood;
        double rpm;
        Point(double distance, double hood, double rpm){
            this.distance = distance;
            this.hood = hood;
            this.rpm = rpm;
        }
    }
    protected final static Point[] referencePoints = {
            new Point(46.24, 0.1, 1650),
            new Point(63.32, 0.15, 1800),
            new Point(80.81, 0.2, 2000),
            new Point(98.48, 0.35, 2150),
            new Point(116.25, 0.5, 2300),
            new Point(128.41, 0.55, 2350),
            new Point(138.31, 0.60, 2450),
            new Point(144.51, 0.65, 2500),
    };
}
