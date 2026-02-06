package org.firstinspires.ftc.teamcode.subsystems.shooter.helpers;

import org.firstinspires.ftc.teamcode.subsystems.shooter.helpers.AutoAimPoints.Point;

import java.util.ArrayList;

public class AutoAimPower {
    private static int getSmallestPoint(double dist, int ignore){
        int point = -1;
        for(int i=0; i<AutoAimPoints.referencePoints.length; i++){
            if(ignore == i) continue;
            if(point == -1 || Math.abs(AutoAimPoints.referencePoints[point].distance - dist) > Math.abs(AutoAimPoints.referencePoints[i].distance - dist)){
                point = i;
            }
        }
        return point;
    }
    private static double getValue(double x, double x1, double y1, double x2, double y2){
        double slope = (y2-y1) / (x2-x1);
        double constant = y1 - slope * x1;
        return slope * x + constant;
    }
    public static double getHood(double distance){
        int idx1 = getSmallestPoint(distance, -1);
        int idx2 = getSmallestPoint(distance, idx1);
        Point point1 = AutoAimPoints.referencePoints[idx1];
        Point point2 = AutoAimPoints.referencePoints[idx2];
        return getValue(distance, point1.distance, point1.hood, point2.distance, point2.hood);
    }
    public static double getRpm(double distance){
        int idx1 = getSmallestPoint(distance, -1);
        int idx2 = getSmallestPoint(distance, idx1);
        Point point1 = AutoAimPoints.referencePoints[idx1];
        Point point2 = AutoAimPoints.referencePoints[idx2];
        return getValue(distance, point1.distance, point1.rpm, point2.distance, point2.rpm);
    }
}
