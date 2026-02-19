package org.firstinspires.ftc.teamcode.subsystems.transfer;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeConfiguration;

public class Transfer extends VLRSubsystem<Transfer> implements IntakeConfiguration {

    private MotorEx transfer;

    protected void initialize(HardwareMap hardwareMap) {
        transfer = new MotorEx(hardwareMap, TRANSFER_MOTOR, Motor.GoBILDA.BARE);

        transfer.setRunMode(Motor.RunMode.RawPower);
        transfer.set(0);

//        transfer.setInverted(true);
    }

    public void setTransfer(boolean on) {
        transfer.set(on ? TRANSFER_SPEED : 0);
    }
    public void setTransfer(double speed) {transfer.set(speed);}

    public void setTransferSpeed(double speed) {
        transfer.set(speed);
    }
    public boolean isTransferOn() {
        return transfer.get() > 0;
    }
}
