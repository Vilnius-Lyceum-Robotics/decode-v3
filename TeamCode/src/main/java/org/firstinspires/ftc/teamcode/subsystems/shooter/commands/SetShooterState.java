package org.firstinspires.ftc.teamcode.subsystems.shooter.commands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterConfiguration.ShootPreset;

public class SetShooterState extends InstantCommand {
    public SetShooterState(ShootPreset preset) {
        super (() -> VLRSubsystem.getInstance(Shooter.class).setShooterState(preset));
    }
}
