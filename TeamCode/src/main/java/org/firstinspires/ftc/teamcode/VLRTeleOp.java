package org.firstinspires.ftc.teamcode;

import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.controls.PrimaryDriverTeleOpControls;
import org.firstinspires.ftc.teamcode.controls.SecondaryDriverTeleOpControls;
import org.firstinspires.ftc.teamcode.helpers.monitoring.LoopTimeMonitor;
import org.firstinspires.ftc.teamcode.helpers.opmode.VLRLinearOpMode;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.chassis.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;

/**
 * @noinspection unchecked
 */
@Photon
@TeleOp(name = "VLRTeleOp", group = "!TELEOP")
public class VLRTeleOp extends VLRLinearOpMode {
    // Controls
    PrimaryDriverTeleOpControls primaryDriver;
    SecondaryDriverTeleOpControls secondaryDriver;

    LoopTimeMonitor loopTimeMonitor = new LoopTimeMonitor();

    @Override
    public void run() {
        VLRSubsystem.requireSubsystems(Chassis.class, Intake.class, Shooter.class);
        VLRSubsystem.initializeAll(hardwareMap);

        primaryDriver = new PrimaryDriverTeleOpControls(gamepad1);

        waitForStart();
//        VLRSubsystem.initializeOne(hardwareMap, ClawSubsystem.class);
//        secondaryDriver = new SecondaryDriverTeleOpControls(gamepad2);

        while (opModeIsActive()) {
            VLRSubsystem.getInstance(Shooter.class).telemetry(telemetry);
            telemetry.update();
            primaryDriver.update();
            loopTimeMonitor.loopEnd();
            sleep(5);
        }
    }
}
