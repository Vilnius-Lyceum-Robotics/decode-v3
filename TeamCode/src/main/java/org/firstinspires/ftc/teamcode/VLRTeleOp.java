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
        VLRSubsystem.requireSubsystems(Chassis.class, Intake.class);
        VLRSubsystem.initializeAll(hardwareMap);

        primaryDriver = new PrimaryDriverTeleOpControls(gamepad1);

        waitForStart();
//        VLRSubsystem.initializeOne(hardwareMap, ClawSubsystem.class);
//        secondaryDriver = new SecondaryDriverTeleOpControls(gamepad2);

        while (opModeIsActive()) {
            loopTimeMonitor.loopStart();

            primaryDriver.update();
//            secondaryDriver.update();

            loopTimeMonitor.loopEnd();
//            double cycleTime = loopTimeMonitor.getAverageTime(5, LoopTimeMonitor.ElementSelectionType.TOP_PERCENTILE_ELEMENTS) / 1000;
//            System.out.println("MAIN THREAD CYCLE TIME: " + 1.0 / cycleTime);
        }
    }
}
