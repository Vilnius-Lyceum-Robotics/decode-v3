package org.firstinspires.ftc.teamcode.controls;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.helpers.controls.DriverControls;
import org.firstinspires.ftc.teamcode.helpers.controls.button.ButtonCtl;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.chassis.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.intake.commands.ToggleIntake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterConfiguration.ShootPreset;
import org.firstinspires.ftc.teamcode.subsystems.shooter.commands.SetShooterState;
import org.firstinspires.ftc.teamcode.subsystems.transfer.commands.ToggleTransfer;

/**
 * Abstraction for primary driver controls. All controls will be defined here.
 * For this to work well, all subsystems will be defined as singletons.
 */
public class PrimaryDriverTeleOpControls extends DriverControls {
    public PrimaryDriverTeleOpControls(Gamepad gamepad) {
        super(new GamepadEx(gamepad));

        CommandScheduler cs = CommandScheduler.getInstance();

        Chassis chassis = VLRSubsystem.getInstance(Chassis.class);

        addBothSticksHandler(
                (Double leftY, Double leftX, Double rightY, Double rightX) -> {
                    chassis.drive(leftY, -leftX, -rightX);
                }
        );
//        add(new ButtonCtl(GamepadKeys.Button.DPAD_UP, ButtonCtl.Trigger.WAS_JUST_PRESSED, true, (Boolean a) -> cs.schedule(new ThirdStageHangCommand(() -> (gamepad.left_bumper && gamepad.right_bumper), ()-> gamepad.left_trigger > 0.9))));
//        add(new ButtonCtl(GamepadKeys.Button.DPAD_LEFT, ButtonCtl.Trigger.WAS_JUST_PRESSED, true, (Boolean a) -> cs.schedule(new SecondStageHangCommand(() -> (gamepad.left_bumper && gamepad.right_bumper)))));
//        add(new ButtonCtl(GamepadKeys.Button.DPAD_DOWN, ButtonCtl.Trigger.WAS_JUST_PRESSED, true, (Boolean b) -> cs.schedule(new RetractArm())));
//        add(new ButtonCtl(GamepadKeys.Button.DPAD_RIGHT, ButtonCtl.Trigger.SIMPLE, false, this::hangDisable));



        add(new ButtonCtl(TRIANGLE, ButtonCtl.Trigger.WAS_JUST_PRESSED, true, (Boolean c) -> cs.schedule(new ToggleIntake())));
        add(new ButtonCtl(CROSS, ButtonCtl.Trigger.WAS_JUST_PRESSED, true, (Boolean c) -> cs.schedule(new SetShooterState(ShootPreset.FAR))));
        add(new ButtonCtl(CIRCLE, ButtonCtl.Trigger.WAS_JUST_PRESSED, true, (Boolean c) -> cs.schedule(new SetShooterState(ShootPreset.STOP))));
        add(new ButtonCtl(SQUARE, ButtonCtl.Trigger.WAS_JUST_PRESSED, true, (Boolean c) -> cs.schedule(new ToggleTransfer())));

//
//        addVibration(ArmLowState::wasJustToggled);
    }

    private boolean lastState = false;

//    private void hangDisable(Boolean pressed) {
//        if (lastState != pressed) {
//            lastState = pressed;
//
//            if (lastState) {
//                VLRSubsystem.getRotator().deactivateRotatorForHang();
//            } else {
//                VLRSubsystem.getRotator().reenableMotorForHang();
//            }
//        }
//    }
}
