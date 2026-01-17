package org.firstinspires.ftc.teamcode.subsystems.chassis.helpers;

import static org.firstinspires.ftc.teamcode.helpers.enums.Alliance.*;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.helpers.enums.Alliance;
import org.firstinspires.ftc.teamcode.helpers.persistence.AllianceSaver;

@Config
public class AutoAimHeading {
    public static final Pose blueGoal = new Pose(10, 135);
    public static final Pose redGoal = new Pose(144-10, 135);
    public static double getTargetHeading(Follower follower) {
        return getTargetHeading(follower.getPose(), AllianceSaver.getAlliance() == RED ? redGoal : blueGoal);
    }
    public static double getTargetHeading(Pose self, Pose goal) {
        double xLength = goal.getX() - self.getX();
        double yLength = goal.getY() - self.getY();

        return Math.atan2(yLength, xLength);
    }

    public static Pose getAutoAimPose(double x, double y) {
        if (AllianceSaver.getAlliance() == RED) {
            return new Pose(x, y, getTargetHeading(new Pose(x, y), redGoal));
        } else {
            return new Pose(x, y, getTargetHeading(new Pose(x, y), blueGoal));
        }
    }
}
