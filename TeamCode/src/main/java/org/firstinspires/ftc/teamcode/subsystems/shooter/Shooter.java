package org.firstinspires.ftc.teamcode.subsystems.shooter;

import static org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterConfiguration.*;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;

public class Shooter extends VLRSubsystem<Shooter> {
    private MotorEx shooterLeft, shooterRight;
    private Servo hood;
    boolean isShooterOn = false;
    double targetRPM, currentRPM;
    private Servo blocker;
    private double liftAngle;
    private double hoodPos;
    PIDFController shootingPID = new PIDFController(SHOOTING_RPM_P, SHOOTING_RPM_I, SHOOTING_RPM_D, SHOOTING_RPM_F);


    protected void initialize(HardwareMap hardwareMap) {

        blocker = hardwareMap.get(Servo.class, BLOCKER_SERVO_NAME);

        this.targetRPM = 0;

        hood = hardwareMap.get(Servo.class, SHOOTER_HOOD);
        shooterLeft = new MotorEx(hardwareMap, SHOOTER_LEFT, Motor.GoBILDA.BARE);
        shooterRight = new MotorEx(hardwareMap, SHOOTER_RIGHT, Motor.GoBILDA.BARE);

        shooterLeft.setRunMode(Motor.RunMode.VelocityControl);
        shooterLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        shooterRight.setRunMode(Motor.RunMode.VelocityControl);
        shooterRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        hood.setPosition(0.1);
        hoodPos = 0.1;
        shooterLeft.setInverted(true);
        shootingPID.setPIDF(SHOOTING_RPM_P, SHOOTING_RPM_I, SHOOTING_RPM_D, 0);
        setBlocker(BLOCKER_CLOSED_POS);
    }
    public void setShooter(double rpm) {
        double clampedRPM = Math.max(-0.05, rpm);
        shooterRight.set(clampedRPM);
        shooterLeft.set(clampedRPM);
    }
    public void setTargetRPM(double rpm) {
        this.targetRPM = rpm;
    }
    public void setShootingInputs(double targetRPM) {
        this.targetRPM = targetRPM;
        shootingPID.setPIDF(SHOOTING_RPM_P, SHOOTING_RPM_I, SHOOTING_RPM_D,  getCurrentRPM() != 0 ? SHOOTING_RPM_F / getCurrentRPM() : 0);
    }
    public void setShootingInputs() {
        this.currentRPM = getCurrentRPM();
        shootingPID.setPIDF(SHOOTING_RPM_P, SHOOTING_RPM_I, SHOOTING_RPM_D,  getCurrentRPM() != 0 ? SHOOTING_RPM_F / getCurrentRPM() : 0);
    }

    @Override
    public void periodic() {
        shootingPID.setPIDF(SHOOTING_RPM_P, SHOOTING_RPM_I, SHOOTING_RPM_D,  getCurrentRPM() != 0 ? SHOOTING_RPM_F / getCurrentRPM() : 0);
        setShooter(shootingPID.calculate(getCurrentRPM(), targetRPM));
    }
    public void setShooterState(ShootPreset preset) {
        setTargetRPM(preset.rpm);
        setHood(preset.hoodPos);
    }

    public void setHood(double pos) {
        double clippedPos = Range.clip(pos, HOOD_MIN_ANGLE, HOOD_MAX_ANGLE);
        hood.setPosition(clippedPos);
        hoodPos = clippedPos;
    }
    public double getCurrentRPM() {
        return shooterRight.getVelocity();
    }
    public void setBlocker(double angle) {
        blocker.setPosition(angle);
        liftAngle = angle;
    }
    public double getHoodPos(){return hoodPos;}
    public void stopShooter(){
        isShooterOn = false;
        shooterLeft.stopMotor();
        shooterRight.stopMotor();
    }
    public void telemetry(Telemetry t)
    {
        t.addData("Shooter RPM: ", shooterRight.getVelocity());
        t.addData("Hood angle: ", hoodPos);
        t.addData("Lift angle: ", liftAngle);
        t.addData("Shooter Target RPM: ", targetRPM);
        t.addData("PID out", shootingPID.calculate(getCurrentRPM(), targetRPM));
    }
    public double getLiftAngle(){
        return liftAngle;
    }
    public double getTargetRPM() {
        return targetRPM;
    }
}
