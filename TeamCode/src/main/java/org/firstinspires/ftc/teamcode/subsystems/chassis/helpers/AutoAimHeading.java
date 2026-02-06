package org.firstinspires.ftc.teamcode.subsystems.chassis.helpers;

import static org.firstinspires.ftc.teamcode.helpers.enums.Alliance.*;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.helpers.persistence.AllianceSaver;
import org.firstinspires.ftc.teamcode.subsystems.shooter.helpers.AutoAimCompensation;

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

    /**
     * Get target heading with velocity compensation for shooting while moving
     * @param follower Robot follower with pose and velocity data
     * @return Compensated heading in radians
     */
    public static double getTargetHeadingCompensated(Follower follower) {
        Pose goal = AllianceSaver.getAlliance() == RED ? redGoal : blueGoal;
        return AutoAimCompensation.getCompensatedHeading(
            follower.getPose(),
            follower.getVelocity(),
            goal
        );
    }

    public static double getDistanceToGoal(Follower follower) {
        Pose goal = AllianceSaver.getAlliance() == RED ? redGoal : blueGoal;
        double dx = goal.getX() - follower.getPose().getX();
        double dy = goal.getY() - follower.getPose().getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Get distance to goal with velocity compensation for shooting while moving
     * @param follower Robot follower with pose and velocity data
     * @return Compensated distance
     */
    public static double getDistanceToGoalCompensated(Follower follower) {
        Pose goal = AllianceSaver.getAlliance() == RED ? redGoal : blueGoal;
        return AutoAimCompensation.getCompensatedDistance(
            follower.getPose(),
            follower.getVelocity(),
            goal
        );
    }

    public static Pose getAutoAimPose(double x, double y) {
        if (AllianceSaver.getAlliance() == RED) {
            return new Pose(x, y, getTargetHeading(new Pose(x, y), redGoal));
        } else {
            return new Pose(x, y, getTargetHeading(new Pose(x, y), blueGoal));
        }
    }
}
