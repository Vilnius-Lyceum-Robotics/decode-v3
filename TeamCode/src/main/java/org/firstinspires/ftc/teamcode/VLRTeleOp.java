package org.firstinspires.ftc.teamcode;

import com.outoftheboxrobotics.photoncore.Photon;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.controls.PrimaryDriverTeleOpControls;
import org.firstinspires.ftc.teamcode.helpers.enums.Alliance;
import org.firstinspires.ftc.teamcode.helpers.opmode.VLRLinearOpMode;
import org.firstinspires.ftc.teamcode.helpers.persistence.AllianceSaver;
import org.firstinspires.ftc.teamcode.helpers.persistence.PoseSaver;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.chassis.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.chassis.helpers.AutoAimHeading;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

import java.util.Objects;

/**
 * @noinspection unchecked
 */
@Photon
@TeleOp(name = "VLRTeleOp", group = "!TELEOP")
public class VLRTeleOp extends VLRLinearOpMode {
    // Controls
    PrimaryDriverTeleOpControls primaryDriver;
    Pose startingPose;
    Follower follower;

    @Override
    public void run() {
        VLRSubsystem.requireSubsystems(Chassis.class, Intake.class, Shooter.class, Transfer.class);
        VLRSubsystem.initializeAll(hardwareMap);

        startingPose = PoseSaver.getPedroPose();
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startingPose == null ? new Pose() : startingPose);
        follower.updatePose();

        primaryDriver = new PrimaryDriverTeleOpControls(gamepad1);

        waitForStart();

        while (opModeIsActive()) {
            follower.updatePose();
            telemetry.addData("Alliance: ", Objects.requireNonNull(AllianceSaver.getAlliance()).name);
            VLRSubsystem.getInstance(Shooter.class).telemetry(telemetry);
            telemetry.addData("x: ", follower.getPose().getX());
            telemetry.addData("y: ", follower.getPose().getY());
            telemetry.addData("Heading: ", follower.getHeading());
            telemetry.update();
            VLRSubsystem.getInstance(Chassis.class).setHeadingInputs(follower.getHeading(), AutoAimHeading.getTargetHeading(follower));
            primaryDriver.update();
            sleep(20);
        }
    }
}
