package org.firstinspires.ftc.teamcode.subsystems.shooter.commands;

import static org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterConfiguration.*;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.intake.commands.SetIntake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterConfiguration;
import org.firstinspires.ftc.teamcode.subsystems.transfer.commands.SetTransfer;

public class Shoot extends SequentialCommandGroup {
    public Shoot() {
        addRequirements(VLRSubsystem.getShooter(), VLRSubsystem.getIntake(), VLRSubsystem.getTransfer());
        addCommands(
                // Ready-up and wait until shooter reaches needed speed
                new SetTransfer(false),
                new SetShooterState(ShooterConfiguration.ShootPreset.FAR),
                new SetBlocker(BLOCKER_OPEN_POS),
                new WaitCommand(1000),
                // Ball Nr.1
                new SetIntake(true),
                new SetTransfer(true),
                new WaitCommand(200),
                new SetTransfer(false),
                new WaitCommand(400),
                new SetIntake(false),
                // Ball Nr.2
                new WaitCommand(600),
                new SetTransfer(true),
                new SetIntake(true),
                new WaitCommand(200),
                new SetTransfer(false),
                new SetIntake(false),
                // Ball Nr.3
                new WaitCommand(600),
                new SetTransfer(true),
                new SetIntake(true),
                new WaitCommand(200),
                new SetTransfer(false),
                new SetIntake(false),
                // Finish
                new SetShooterState(ShooterConfiguration.ShootPreset.STOP),
                new SetBlocker(BLOCKER_CLOSED_POS)
        );
    }
}
