package org.firstinspires.ftc.teamcode.helpers.autoconfig;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.subsystems.chassis.helpers.AutoAimHeading;

public class AutoPoints {
    public static final Pose[] FAR_START = {new Pose(65, 9, Math.toRadians(90)), new Pose(79, 9, Math.toRadians(90))};
    public static final Pose[] FAR_SHOOT = {AutoAimHeading.getAutoAimPose(55, 12), AutoAimHeading.getAutoAimPose(144-55, 12)};
    public static final Pose[] FAR_PARK = {new Pose(30, 12, Math.toRadians(90)), new Pose(144 - 30, 12, Math.toRadians(90))};

    public static final Pose[] CLOSE_START = {new Pose(20, 123, Math.toRadians(135)), new Pose(144-20, 123, Math.toRadians(45))};
    public static final Pose[] CLOSE_SHOOT = {new Pose(48, 96, Math.toRadians(135)), new Pose(144-48, 96, Math.toRadians(45))};
    public static final Pose[] CLOSE_PARK = {new Pose(16, 104, Math.toRadians(270)), new Pose(144 - 16, 104, Math.toRadians(270))};

    public static final Pose[][] SAMPLE_START = {
            new Pose[] {new Pose(48, 84, Math.toRadians(179.9)), new Pose(144-48, 84, Math.toRadians(0.1))},
            new Pose[] {new Pose(48, 60, Math.toRadians(180)), new Pose(144-48, 60, Math.toRadians(0))},
            new Pose[] {new Pose(48, 36, Math.toRadians(180)), new Pose(144 - 48, 36, Math.toRadians(0))},
            new Pose[] {new Pose(9, 32, Math.toRadians(270)), new Pose(144-9, 32, Math.toRadians(270))}
    };
    public static final Pose[][] SAMPLE_END = {
            new Pose[] {new Pose(16, 84, Math.toRadians(179.9)), new Pose(144-16, 84, Math.toRadians(0.1))},
            new Pose[] {new Pose(9, 60, Math.toRadians(180)), new Pose(144-9, 60, Math.toRadians(0))},
            new Pose[] {new Pose(15, 36, Math.toRadians(180)), new Pose(144 - 15, 36, Math.toRadians(0))},
            new Pose[] {new Pose(9, 32, Math.toRadians(270)), new Pose(144-9, 32, Math.toRadians(270))}
    };

}
