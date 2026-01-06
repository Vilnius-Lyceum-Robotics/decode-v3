package org.firstinspires.ftc.teamcode.subsystems.transfer.commands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

public class SetTransfer extends InstantCommand {
    public SetTransfer(boolean on) {
        super(() -> VLRSubsystem.getInstance(Transfer.class).setTransfer(on));
    }
}
