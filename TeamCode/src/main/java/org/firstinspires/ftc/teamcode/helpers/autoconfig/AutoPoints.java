package org.firstinspires.ftc.teamcode.helpers.autoconfig;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.subsystems.chassis.helpers.AutoAimHeading;

public class AutoPoints {
    public static final Pose FAR_START = new Pose(65, 9, Math.toRadians(90));
    public static final Pose FAR_SHOOT = AutoAimHeading.getAutoAimPose(55, 12);
    public static final Pose FAR_PARK = new Pose(30, 12, Math.toRadians(90));

    public static final Pose CLOSE_START = new Pose(20, 123, Math.toRadians(325));
    public static final Pose CLOSE_SHOOT = AutoAimHeading.getAutoAimPose(48, 96);
    public static final Pose CLOSE_PARK = new Pose(16, 104, Math.toRadians(270));

    public static final Pose[] SAMPLE_START = {
            new Pose(48, 84, Math.toRadians(180)),
            new Pose(48, 60, Math.toRadians(180)),
            new Pose(48, 36, Math.toRadians(180)),
            new Pose(8, 32, Math.toRadians(270))
    };
    public static final Pose[] SAMPLE_END = {
            new Pose(16, 84, Math.toRadians(180)),
            new Pose(9, 60, Math.toRadians(180)),
            new Pose(15, 36, Math.toRadians(180)),
            new Pose(8, 12, Math.toRadians(270))
    };
}
