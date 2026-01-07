package org.firstinspires.ftc.teamcode.subsystems.chassis.helpers;

import static org.firstinspires.ftc.teamcode.helpers.enums.Alliance.*;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.helpers.enums.Alliance;
import org.firstinspires.ftc.teamcode.helpers.persistence.AllianceSaver;

public class AutoAimHeading {

    private final static Alliance alliance = AllianceSaver.getAlliance();
    private static Pose goalPose;
    private static double xLength, yLength, targetHeading;
    public static double getTargetHeading(Follower follower) {

        if (alliance == RED) goalPose = new Pose(10, 130);
        else goalPose = new Pose(206, 130);

        xLength = goalPose.getX() - follower.getPose().getX();
        yLength = goalPose.getY() - follower.getPose().getY();
        targetHeading = Math.atan2(yLength, xLength);

        return targetHeading;
    }
}
