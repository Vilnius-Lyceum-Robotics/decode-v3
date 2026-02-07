package org.firstinspires.ftc.teamcode.helpers.testOpmodes;

import com.outoftheboxrobotics.photoncore.Photon;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.controls.PrimaryDriverTeleOpControls;
import org.firstinspires.ftc.teamcode.helpers.opmode.VLRLinearOpMode;
import org.firstinspires.ftc.teamcode.helpers.persistence.PoseSaver;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.chassis.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.chassis.helpers.AutoAimHeading;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.shooter.helpers.AutoAimCompensation;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

/**
 * Tuning OpMode for Auto Aim Compensation
 *
 * Use FTC Dashboard to adjust:
 * - AutoAimCompensation.HEADING_COMPENSATION_FACTOR
 * - AutoAimCompensation.DISTANCE_COMPENSATION_FACTOR
 *
 * Instructions:
 * 1. Enable auto-aim on the shooter
 * 2. Drive the robot at constant velocity past the goal
 * 3. Watch telemetry to see compensation values
 * 4. Test shooting while moving and adjust factors via Dashboard
 * 5. Increase factors if shots land behind target (need more lead)
 * 6. Decrease factors if shots overshoot target (too much lead)
 */
@Photon
@TeleOp(name = "AutoAim Compensation Tuning", group = "Tuning")
public class AutoAimCompensationTuning extends VLRLinearOpMode {
    PrimaryDriverTeleOpControls primaryDriver;
    Follower follower;

    @Override
    public void run() {
        VLRSubsystem.requireSubsystems(Chassis.class, Intake.class, Shooter.class, Transfer.class);
        VLRSubsystem.initializeAll(hardwareMap);

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(!PoseSaver.isPoseSaved() ?
            new Pose(65, 9, Math.toRadians(90)) :
            PoseSaver.getPedroPose());
        follower.updatePose();

        primaryDriver = new PrimaryDriverTeleOpControls(gamepad1);

        waitForStart();

        while (opModeIsActive()) {
            follower.updatePose();
            VLRSubsystem.getShooter().updateAutoAimCompensated(follower);
            VLRSubsystem.getShooter().periodic();

            // Get compensation factors
            double headingFactor = AutoAimCompensation.HEADING_COMPENSATION_FACTOR;
            double distanceFactor = AutoAimCompensation.DISTANCE_COMPENSATION_FACTOR;

            // Get velocities
            Vector velocity = follower.getVelocity();
            double velocityMagnitude = Math.sqrt(velocity.getXComponent() * velocity.getXComponent() +
                                                 velocity.getYComponent() * velocity.getYComponent());

            // Get headings
            double baseHeading = AutoAimHeading.getTargetHeading(follower);
            double compensatedHeading = AutoAimHeading.getTargetHeadingCompensated(follower);
            double headingDiff = Math.toDegrees(compensatedHeading - baseHeading);

            // Get distances
            double baseDistance = AutoAimHeading.getDistanceToGoal(follower);
            double compensatedDistance = AutoAimHeading.getDistanceToGoalCompensated(follower);
            double distanceDiff = compensatedDistance - baseDistance;

            // Telemetry
            telemetry.addLine("=== COMPENSATION FACTORS (tune via Dashboard) ===");
            telemetry.addData("Heading Factor", "%.4f", headingFactor);
            telemetry.addData("Distance Factor", "%.4f", distanceFactor);

            telemetry.addLine("\n=== ROBOT STATE ===");
            telemetry.addData("Position", "X: %.1f, Y: %.1f",
                follower.getPose().getX(), follower.getPose().getY());
            telemetry.addData("Velocity", "X: %.2f, Y: %.2f (mag: %.2f)",
                velocity.getXComponent(), velocity.getYComponent(), velocityMagnitude);
            telemetry.addData("Current Heading", "%.1f°", Math.toDegrees(follower.getHeading()));

            telemetry.addLine("\n=== HEADING COMPENSATION ===");
            telemetry.addData("Base Heading", "%.2f°", Math.toDegrees(baseHeading));
            telemetry.addData("Compensated Heading", "%.2f°", Math.toDegrees(compensatedHeading));
            telemetry.addData("Difference", "%.2f°", headingDiff);

            telemetry.addLine("\n=== DISTANCE COMPENSATION ===");
            telemetry.addData("Base Distance", "%.2f", baseDistance);
            telemetry.addData("Compensated Distance", "%.2f", compensatedDistance);
            telemetry.addData("Difference", "%.2f", distanceDiff);

            telemetry.addLine("\n=== SHOOTER STATE ===");
            telemetry.addData("Auto-aim Enabled", VLRSubsystem.getShooter().isAutoAimEnabled());
            telemetry.addData("Current RPM", "%.0f", VLRSubsystem.getShooter().getCurrentRPM());
            telemetry.addData("Target RPM", "%.0f", VLRSubsystem.getShooter().getTargetRPM());
            telemetry.addData("Hood Angle", "%.3f", VLRSubsystem.getShooter().getHoodPos());

            VLRSubsystem.getInstance(Chassis.class).setHeadingInputs(
                follower.getHeading(),
                compensatedHeading
            );

            telemetry.update();
            primaryDriver.update();
            sleep(20);
        }
        PoseSaver.resetSavedPose();
    }
}
