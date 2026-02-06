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

    // Acceleration control
    private double currentVelocityTarget = 0; // Ramped velocity target
    private double previousVelocityTarget = 0;

    // Low pass filter states
    private double filteredEncoderVelocity = 0;
    private double filteredMotorOutput = 0;
    private boolean filtersInitialized = false;


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

    // Method to get filtered velocity with low pass filter
    private double getFilteredVelocity() {
        double rawVelocity = shooterRight.getVelocity();

        if (!filtersInitialized) {
            filteredEncoderVelocity = rawVelocity;
            filtersInitialized = true;
        }

        // Low pass filter: filtered = alpha * new + (1-alpha) * old
        filteredEncoderVelocity = ENCODER_FILTER_ALPHA * rawVelocity +
                                  (1 - ENCODER_FILTER_ALPHA) * filteredEncoderVelocity;

        return filteredEncoderVelocity;
    }

    @Override
    public void periodic() {
        // 1. Ramp current velocity target with constant acceleration
        double velocityError = targetRPM - currentVelocityTarget;

        if (Math.abs(velocityError) > MAX_ACCELERATION) {
            // Ramp with constant acceleration
            currentVelocityTarget += Math.signum(velocityError) * MAX_ACCELERATION;
        } else {
            // Close enough, just set to target
            currentVelocityTarget = targetRPM;
        }

        // 2. Calculate acceleration
        double acceleration = currentVelocityTarget - previousVelocityTarget;
        previousVelocityTarget = currentVelocityTarget;

        // 3. Get filtered encoder velocity
        currentRPM = getFilteredVelocity();

        // 4. Update PIDF
        shootingPID.setPIDF(SHOOTING_RPM_P, SHOOTING_RPM_I, SHOOTING_RPM_D,
                           currentRPM != 0 ? SHOOTING_RPM_F / currentRPM : 0);

        // 5. Calculate control output: power = acceleration*a + velocity*v + pidf
        double pidfOutput = shootingPID.calculate(currentRPM, currentVelocityTarget);
        double feedforwardAccel = acceleration * ACCELERATION_GAIN;
        double feedforwardVelocity = currentVelocityTarget * VELOCITY_GAIN;
        double rawOutput = feedforwardAccel + feedforwardVelocity + pidfOutput;

        // 6. Apply low pass filter to motor output
        if (!filtersInitialized) {
            filteredMotorOutput = rawOutput;
        }
        filteredMotorOutput = OUTPUT_FILTER_ALPHA * rawOutput +
                             (1 - OUTPUT_FILTER_ALPHA) * filteredMotorOutput;

        // 7. Set motors
        setShooter(filteredMotorOutput);
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
        return filteredEncoderVelocity; // Return filtered value
    }
    public void setBlocker(double angle) {
        blocker.setPosition(angle);
        liftAngle = angle;
    }
    public double getHoodPos(){return hoodPos;}
    public void stopShooter(){
        isShooterOn = false;
        currentVelocityTarget = 0;
        previousVelocityTarget = 0;
        targetRPM = 0;
        shooterLeft.stopMotor();
        shooterRight.stopMotor();
    }
    public void telemetry(Telemetry t)
    {
        t.addData("Shooter RPM: ", currentRPM);
        t.addData("Target RPM: ", targetRPM);
        t.addData("Ramped Target RPM: ", currentVelocityTarget);
        t.addData("Raw RPM: ", shooterRight.getVelocity());
        t.addData("Hood angle: ", hoodPos);
        t.addData("Lift angle: ", liftAngle);
        t.addData("Acceleration: ", currentVelocityTarget - previousVelocityTarget);
        t.addData("Filtered Output: ", filteredMotorOutput);
    }
    public double getLiftAngle(){
        return liftAngle;
    }
    public double getTargetRPM() {
        return targetRPM;
    }
}
