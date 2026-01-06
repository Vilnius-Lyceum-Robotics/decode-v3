package org.firstinspires.ftc.teamcode.subsystems.transfer.commands;

import com.arcrobotics.ftclib.command.ConditionalCommand;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

public class ToggleTransfer extends ConditionalCommand {
    public ToggleTransfer() {
        super(
                new SetTransfer(false),
                new SetTransfer(true),
                () -> VLRSubsystem.getInstance(Transfer.class).isTransferOn());
    }
}
