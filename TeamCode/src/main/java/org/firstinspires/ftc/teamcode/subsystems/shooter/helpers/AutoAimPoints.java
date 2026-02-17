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
            new Point(40.85, 0.1, 1650),
            new Point(54.45, 0.15, 1850),
            new Point(88.39, 0.25, 2200),
            new Point(94.43, 0.35, 2300),
            new Point(111.02, 0.4, 2400),
            new Point(124.92, 0.55, 2400),
            new Point(127.14, 0.60, 2500),
            new Point(140.13, 0.65, 2600),
    };
}
