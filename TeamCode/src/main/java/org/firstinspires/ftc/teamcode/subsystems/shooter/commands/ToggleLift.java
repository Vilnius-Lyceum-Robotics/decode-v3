package org.firstinspires.ftc.teamcode.subsystems.shooter.commands;

import static org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterConfiguration.*;

import com.arcrobotics.ftclib.command.ConditionalCommand;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;

public class ToggleLift extends ConditionalCommand {
    public ToggleLift() {
        super(
                new SetLift(LIFT_DOWN_POS),
                new SetLift(LIFT_UP_POS),
                () -> VLRSubsystem.getInstance(Shooter.class).getLiftAngle() == LIFT_UP_POS
        );
    }
}
