package org.firstinspires.ftc.teamcode.subsystems.shooter.commands;


import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

public class SetLift extends InstantCommand {
    public SetLift(double angle) {
        super(() -> VLRSubsystem.getInstance(Shooter.class).setLift(angle));
    }
}
