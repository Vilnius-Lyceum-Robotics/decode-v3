package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;

public class Intake extends VLRSubsystem<Intake> implements IntakeConfiguration {
    private Motor intake;

    protected void initialize(HardwareMap hardwareMap) {
        intake = new MotorEx(hardwareMap, INTAKE_MOTOR, Motor.GoBILDA.BARE);
        intake.setInverted(true);

        intake.setRunMode(Motor.RunMode.RawPower);
        intake.set(0);
    }

    public void setIntake(boolean on) {
        intake.set(on ? INTAKE_SPEED : 0);
    }

    public void setIntakeSpeed(double speed) {
        intake.set(speed);
    }
    public boolean isIntakeOn() {
        return intake.get() > 0;
    }
}
