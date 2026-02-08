package org.firstinspires.ftc.teamcode.helpers.autoconfig;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.subsystems.chassis.helpers.AutoAimHeading;

public class AutoPoints {
    public static final Pose FAR_START = new Pose(65, 9, Math.toRadians(90));

    public static final Pose FAR_SHOOT_NO_ANGLE = new Pose(50, 14, Math.toRadians(90));
    public static final Pose FAR_SHOOT_PRE_ANGLE = new Pose(50, 20, Math.toRadians(270));
    public static final Pose FAR_SHOOT = AutoAimHeading.getAutoAimPose(55, 12);
    public static final Pose FAR_PARK = new Pose(30, 12, Math.toRadians(90));

    public static final Pose CLOSE_START = new Pose(20, 123, Math.toRadians(135));
    public static final Pose CLOSE_SHOOT = new Pose(48, 96, Math.toRadians(135));
    public static final Pose CLOSE_PARK = new Pose(16, 104, Math.toRadians(270));
    public static final Pose CLOSE_OPEN_GATE_START = new Pose(24, 76, Math.toRadians(179.9));

    public static final Pose CLOSE_OPEN_GATE = new Pose(16, 76, Math.toRadians(179.9));

    public static final Pose[] SAMPLE_START = {
            new Pose(48, 84, Math.toRadians(179.9)),
            new Pose(48, 60, Math.toRadians(180)),
            new Pose(48, 36, Math.toRadians(180)),
            new Pose(6, 32, Math.toRadians(270))
    };
    // TODO tweak the positions
    public static final Pose[] SAMPLE_END = {
            new Pose(16, 84, Math.toRadians(179.9)),
            new Pose(12, 60, Math.toRadians(180)),
            new Pose(15, 36, Math.toRadians(180)),
            new Pose(6, 12, Math.toRadians(270))
    };

}
