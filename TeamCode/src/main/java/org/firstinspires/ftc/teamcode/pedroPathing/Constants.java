package org.firstinspires.ftc.teamcode.pedroPathing;

import static org.firstinspires.ftc.teamcode.subsystems.chassis.ChassisConfiguration.*;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(8.1)
            .forwardZeroPowerAcceleration(-60)
            .lateralZeroPowerAcceleration(-72)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.4, 0, 0.035, 0.001))
            .headingPIDFCoefficients(new PIDFCoefficients(1.8, 0, 0.098, 0))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.008, 0, 0.00001, 0.6, 0.05))
            .centripetalScaling(0.001);

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName(MOTOR_RIGHT_FRONT)
            .rightRearMotorName(MOTOR_RIGHT_REAR)
            .leftRearMotorName(MOTOR_LEFT_REAR)
            .leftFrontMotorName(MOTOR_LEFT_FRONT)
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .xVelocity(84.166)
            .yVelocity(60.065);

    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(-2.559)
            .strafePodX(-4.6456)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1.2, 1.5);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .pinpointLocalizer(localizerConstants)
                .build();
    }
}
