package org.firstinspires.ftc.teamcode.subsystems.intake.commands;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

public class IntakeUnblock extends SequentialCommandGroup {
    public IntakeUnblock() {
        super(
                new SetIntake(-0.5),
                new WaitCommand(500),
                new SetIntake(0)
        );
    }
}
