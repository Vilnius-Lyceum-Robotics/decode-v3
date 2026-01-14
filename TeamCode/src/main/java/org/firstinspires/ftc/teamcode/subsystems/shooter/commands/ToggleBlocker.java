package org.firstinspires.ftc.teamcode.subsystems.shooter.commands;

import static org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterConfiguration.*;

import com.arcrobotics.ftclib.command.ConditionalCommand;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;

public class ToggleBlocker extends ConditionalCommand {
    public ToggleBlocker() {
        super(
                new SetBlocker(BLOCKER_CLOSED_POS),
                new SetBlocker(BLOCKER_OPEN_POS),
                () -> VLRSubsystem.getInstance(Shooter.class).getLiftAngle() == BLOCKER_OPEN_POS
        );
    }
}
