package org.firstinspires.ftc.teamcode.helpers.testOpmodes;

import static org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterConfiguration.TARGET_RPM;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.chassis.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.chassis.helpers.AutoAimHeading;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;

@TeleOp(name = "Shooting PID Tuning", group = "Utils")
@Photon
@Configurable
public class ShootingPIDTuning extends LinearOpMode {
    Telemetry t = new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), telemetry);

    @Override
    public void runOpMode() throws InterruptedException {
        //noinspection unchecked
        VLRSubsystem.requireSubsystems(Shooter.class);
        VLRSubsystem.initializeAll(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            t.addData("Current RPM: ", VLRSubsystem.getShooter().getCurrentRPM());
            t.update();
            VLRSubsystem.getInstance(Shooter.class).setShootingInputs(TARGET_RPM);
            VLRSubsystem.getInstance(Shooter.class).enableShootingPID();
            sleep(15);
        }
    }
}
