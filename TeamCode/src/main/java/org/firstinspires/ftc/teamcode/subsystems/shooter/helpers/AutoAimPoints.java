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

    };
}
