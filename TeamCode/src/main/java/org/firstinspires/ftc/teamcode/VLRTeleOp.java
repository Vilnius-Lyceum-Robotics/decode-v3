package org.firstinspires.ftc.teamcode;

import com.outoftheboxrobotics.photoncore.Photon;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.controls.PrimaryDriverTeleOpControls;
import org.firstinspires.ftc.teamcode.helpers.opmode.VLRLinearOpMode;
import org.firstinspires.ftc.teamcode.helpers.persistence.PoseSaver;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.chassis.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

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
            VLRSubsystem.getInstance(Shooter.class).telemetry(telemetry);
            telemetry.addData("x: ", follower.getPose().getX());
            telemetry.addData("y: ", follower.getPose().getY());
            telemetry.addData("Heading: ", follower.getHeading());
            telemetry.update();
            // x 10 y 120
            double yLength = 120 - follower.getPose().getY();
            double xLength = 10 - follower.getPose().getX();
            telemetry.addData("yLen", yLength);
            telemetry.addData("xLen", xLength);
            telemetry.addData("atan2", Math.atan2(yLength, xLength));
            VLRSubsystem.getInstance(Chassis.class).setHeadingInputs(follower.getHeading(), Math.atan2(yLength, xLength));
            primaryDriver.update();
            sleep(20);
        }
    }
}
