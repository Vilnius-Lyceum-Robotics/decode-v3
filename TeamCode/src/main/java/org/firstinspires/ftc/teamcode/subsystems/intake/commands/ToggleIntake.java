package org.firstinspires.ftc.teamcode.subsystems.intake.commands;

import com.arcrobotics.ftclib.command.ConditionalCommand;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;

public class ToggleIntake extends ConditionalCommand {
    public ToggleIntake() {
        super(
                new SetIntake(false),
                new SetIntake(true),
                () -> VLRSubsystem.getInstance(Intake.class).isIntakeOn()

        );
    }
}
