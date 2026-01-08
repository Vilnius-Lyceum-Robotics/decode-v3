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
    private Servo lift;
    private double liftAngle;
    private double hoodPos;
    private final double HOOD_STEP = 0.005;


    protected void initialize(HardwareMap hardwareMap) {

        lift = hardwareMap.get(Servo.class, LIFT_SERVO);

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

        lift.setPosition(LIFT_DOWN_POS);
    }
//    public void setShooter(int index)
//    {
//        shooterLeft.setVelocity(presets[index].rpm*multiplierRPM);
//        shooterRight.setVelocity(-presets[index].rpm*multiplierRPM);
//        hoodPos = presets[index].hoodPos;
//        hood.setPosition(hoodPos);
//    }

    public void setShooter(double rpm) {
        shooterRight.setVelocity(rpm);
        shooterLeft.setVelocity(rpm);
    }
    public void setShooterState(ShootPreset preset) {
        setShooter(preset.rpm);
        setHood(preset.hoodPos);
    }

    public void setHood(double pos) {
        hood.setPosition(pos);
    }
    public double getCurrentRPM() {
        return shooterRight.getVelocity();
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
//    public void shooterUp()
//    {
//        shooter_rpm = Range.clip(shooter_rpm + 50, 0, 2800);
//        shooterLeft.setVelocity(shooter_rpm*multiplierRPM);
//        shooterRight.setVelocity(shooter_rpm*multiplierRPM);
//    }
//    public void shooterDown()
//    {
//        shooter_rpm = Range.clip(shooter_rpm - 50, 0, 2800);
//        shooterLeft.setVelocity(shooter_rpm*multiplierRPM);
//        shooterRight.setVelocity(shooter_rpm*multiplierRPM);
//    }

    public void liftUp()
    {
        lift.setPosition(0.38);
    }
    public void liftDown()
    {
        lift.setPosition(0.3);
    }


    //    public void shootMax() {
    //        shooter.setVelocity(shooter.getMaxRPM());
    //    }
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
        t.addData("Shooter L motor velocity: ", shooterLeft.getVelocity());
        t.addData("Shooter R motor velocity: ", shooterRight.getVelocity());
        t.addData("Hood spin: ", hoodPos);
        t.addData("Lift angle: ", getMappedLift());
        t.addData("Lift angle raw: ", liftAngle);
        t.addData("Shooter rpm: ", shooter_rpm);
    }
    public boolean isShooterOn() {
        return isShooterOn;
    }

    public double getMappedLift(){
        return Range.scale(liftAngle, LIFT_MIN, LIFT_MAX, 0, 1);
    }
    public void setLift(double mappedAngle){
        double clippedMappedAngle = Range.clip(mappedAngle, 0, 1);
        liftAngle = Range.scale(clippedMappedAngle, 0, 1, LIFT_MIN, LIFT_MAX);
        lift.setPosition(liftAngle);
    }
    public void setLiftRel(double mappedAngleChange){
        setLift(getMappedLift()+mappedAngleChange);
    }
    public void setLiftRelRaw(double changedAngle){
        liftAngle += changedAngle;
        lift.setPosition(liftAngle);
    }
}
