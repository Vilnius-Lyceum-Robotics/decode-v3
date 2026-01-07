package org.firstinspires.ftc.teamcode.subsystems.chassis.helpers;

import static org.firstinspires.ftc.teamcode.helpers.enums.Alliance.*;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.helpers.enums.Alliance;
import org.firstinspires.ftc.teamcode.helpers.persistence.AllianceSaver;

@Config
public class AutoAimHeading {

    private final static Alliance alliance = AllianceSaver.getAlliance();

    public static double getTargetHeading(Follower follower) {

        Pose goalPose;
        if (alliance == RED) goalPose = new Pose(144 - 9, 133);
        else goalPose = new Pose(9, 133);

        double xLength = goalPose.getX() - follower.getPose().getX();
        double yLength = goalPose.getY() - follower.getPose().getY();

        return Math.atan2(yLength, xLength);
}}
