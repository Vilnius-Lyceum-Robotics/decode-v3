package org.firstinspires.ftc.teamcode.subsystems.shooter;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;

public class Shooter extends VLRSubsystem<Shooter> implements ShooterConfiguration {
    private MotorEx shooterLeft, shooterRight;
    private final Telemetry telemetry = FtcDashboard.getInstance().getTelemetry();
    private Servo hood;
    boolean isShooterOn = false;
    double shooter_rpm;
    private Servo blocker;
    private double liftAngle;
    private double hoodPos;
    private final double HOOD_STEP = 0.005;


    protected void initialize(HardwareMap hardwareMap) {

        blocker = hardwareMap.get(Servo.class, BLOCKER_SERVO_NAME);

        this.shooter_rpm = 0;

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

        setBlocker(BLOCKER_CLOSED_POS);
    }
    public void setShooter(double rpm) {
        shooter_rpm = rpm;
        if (rpm == 0) {
            stopShooter();
            return;
        }
        shooterRight.setVelocity(rpm);
        shooterLeft.setVelocity(rpm);
    }
    public void setShooterState(ShootPreset preset) {
        setShooter(preset.rpm);
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
    //TESTING
    public void hoodUp() {
        hoodPos = Range.clip(hoodPos + HOOD_STEP, 0, 1);
        hood.setPosition(hoodPos);
    }
    public void hoodDown() {
        hoodPos = Range.clip(hoodPos - HOOD_STEP, 0, 1);
        hood.setPosition(hoodPos);
    }
    public void stopShooter(){
        isShooterOn = false;
        shooterLeft.stopMotor();
        shooterRight.stopMotor();
    }
    //for testing
    public void upShooterLeft() {
        shooterLeft.setVelocity(1000);
    }
    public void upShooterRight() {
        shooterRight.setVelocity(-1000);
    }

    public void telemetry(Telemetry t)
    {
        t.addData("Shooter RPM: ", shooterRight.getVelocity());
        t.addData("Hood angle: ", hoodPos);
        t.addData("Lift angle: ", liftAngle);
        t.addData("Shooter Target RPM: ", shooter_rpm);
    }
    public boolean isShooterOn() {
        return isShooterOn;
    }

    public double getLiftAngle(){
        return liftAngle;
    }
    public double getTargetRPM() {
        return shooter_rpm;
    }
}
