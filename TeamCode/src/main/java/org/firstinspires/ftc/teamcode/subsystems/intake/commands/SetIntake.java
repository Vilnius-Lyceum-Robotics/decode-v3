package org.firstinspires.ftc.teamcode.subsystems.intake.commands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;

public class SetIntake extends InstantCommand {
    public SetIntake(boolean on) {
        super (() -> VLRSubsystem.getInstance(Intake.class).setIntake(on));
    }
}
