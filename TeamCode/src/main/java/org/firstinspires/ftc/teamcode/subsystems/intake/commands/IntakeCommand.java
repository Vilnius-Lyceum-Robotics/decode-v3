package org.firstinspires.ftc.teamcode.subsystems.intake.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;

public class IntakeCommand extends SequentialCommandGroup {
    public IntakeCommand(Intake intake) {
        addCommands(
              new InstantCommand(() -> intake.setIntakeSpeed(0.4)),
              new WaitCommand(1000),
              new InstantCommand(() -> intake.setIntakeSpeed(0))
        );
        addRequirements(intake);
    }
}
