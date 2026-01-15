package org.firstinspires.ftc.teamcode.subsystems.shooter.commands;


import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;

public class SetBlocker extends InstantCommand {
    public SetBlocker(double angle) {
        super(() -> VLRSubsystem.getInstance(Shooter.class).setBlocker(angle));
    }
}
