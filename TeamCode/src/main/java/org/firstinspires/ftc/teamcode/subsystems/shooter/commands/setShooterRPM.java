package org.firstinspires.ftc.teamcode.subsystems.shooter.commands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;

public class setShooterRPM extends InstantCommand {
    public setShooterRPM(double rpm) {
        super(() -> VLRSubsystem.getInstance(Shooter.class).setShooter(rpm));
    }
}
