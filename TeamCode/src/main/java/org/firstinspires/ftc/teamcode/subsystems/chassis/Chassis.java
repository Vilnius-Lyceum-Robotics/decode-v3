package org.firstinspires.ftc.teamcode.subsystems.chassis;

import static org.firstinspires.ftc.teamcode.subsystems.chassis.ChassisConfiguration.*;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.geometry.Vector2d;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.chassis.helpers.AsymmetricLowPassFilter;
import org.firstinspires.ftc.teamcode.subsystems.chassis.helpers.MecanumDriveController;

@Config
public class Chassis extends VLRSubsystem<Chassis> {
    MotorEx MotorLeftFront;
    MotorEx MotorRightFront;
    MotorEx MotorLeftBack;
    MotorEx MotorRightBack; 

    public static double motorPower = 1;
    public static double acceleration_a = 0.9;
    public static double deceleration_a = 0.6;

    public static double forwardsMultiplier = 0.95;
    public static double strafeMultiplier = 0.7;


    public static double staticFrictionBar = 0.05;

    AsymmetricLowPassFilter x_filter = new AsymmetricLowPassFilter(acceleration_a, deceleration_a);
    AsymmetricLowPassFilter y_filter = new AsymmetricLowPassFilter(acceleration_a, deceleration_a);

    PIDController headingPid = new PIDController(HEADING_AUTOAIM_P, HEADING_AUTOAIM_I, HEADING_AUTOAIM_D);

    boolean autoAimEnabled = false;
    double currentHeading, targetHeading = 0;

    @Override
    protected void initialize(HardwareMap hardwareMap) {
        MotorLeftFront = new MotorEx(hardwareMap, MOTOR_LEFT_FRONT);
        MotorRightFront = new MotorEx(hardwareMap, MOTOR_RIGHT_FRONT);
        MotorLeftBack = new MotorEx(hardwareMap, MOTOR_LEFT_REAR);
        MotorRightBack = new MotorEx(hardwareMap, MOTOR_RIGHT_REAR);

        MotorLeftFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        MotorRightFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        MotorLeftBack.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        MotorRightBack.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        MotorLeftBack.setRunMode(Motor.RunMode.RawPower);
        MotorRightBack.setRunMode(Motor.RunMode.RawPower);
        MotorRightFront.setRunMode(Motor.RunMode.RawPower);
        MotorLeftFront.setRunMode(Motor.RunMode.RawPower);

        MotorRightBack.setInverted(true);
        MotorRightFront.setInverted(true);
    }

    public void toggleAutoAim() {
        autoAimEnabled = !autoAimEnabled;
    }

    public void setHeadingInputs(double currentHeading, double targetHeading) {
        this.currentHeading = currentHeading;
        this.targetHeading = targetHeading;
        headingPid.setPID(HEADING_AUTOAIM_P, HEADING_AUTOAIM_I, HEADING_AUTOAIM_D);
    }

    public void drive(double xSpeed, double ySpeed, double zRotation) {
        // This sometimes fails and causes the whole robot to die
        Vector2d vector = new Vector2d(x_filter.estimatePower(xSpeed) * forwardsMultiplier, y_filter.estimatePower(ySpeed) * strafeMultiplier);

        double heading = autoAimEnabled ? headingPid.calculate(currentHeading, targetHeading) : zRotation * 0.3;

        this.driveMotors(new MecanumDriveController(vector.getX(), vector.getY(), heading));
    }

    private void driveMotors(MecanumDriveController driveController) {
        driveController.normalize(1.0);

        MotorLeftFront.set(clampPower(driveController.frontLeftMetersPerSecond) * motorPower);
        MotorRightFront.set(clampPower(driveController.frontRightMetersPerSecond) * motorPower);
        MotorLeftBack.set(clampPower(driveController.rearLeftMetersPerSecond) * motorPower);
        MotorRightBack.set(clampPower(driveController.rearRightMetersPerSecond) * motorPower);
    }

    public double clampPower(double motorPower){
        if (Math.abs(motorPower) < staticFrictionBar) return 0;
        return motorPower;
    }

    public void stop() {
        MotorLeftFront.stopMotor();
        MotorRightFront.stopMotor();
        MotorLeftBack.stopMotor();
        MotorRightBack.stopMotor();

        motorPower = 0.0;
    }

    public void setPower(double power) {
        motorPower = Math.min(power, 1.0);
    }
}