package org.firstinspires.ftc.teamcode.helpers.testOpmodes.auto;


import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.outoftheboxrobotics.photoncore.Photon;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import static org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoPoints.*;


import org.firstinspires.ftc.teamcode.helpers.commands.FollowCommand;
import org.firstinspires.ftc.teamcode.helpers.persistence.AllianceSaver;
import org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterConfiguration.ShootPreset;
import org.firstinspires.ftc.teamcode.subsystems.shooter.commands.SetShooterState;
import org.firstinspires.ftc.teamcode.subsystems.shooter.commands.Shoot;
import org.firstinspires.ftc.teamcode.subsystems.shooter.commands.ShootAuto;

@Photon
@Autonomous(name = "FAR", group = "Auto")
public class AutoFarTest1 extends AutoBaseTest {
    public Pose StartPose() {
        System.out.println("StartPose alliance: " + AllianceSaver.getAlliance());
        return FAR_START;
    }

    public Command AutoCommand() {
        System.out.println("AutoCommand");

//        Pose shootPose =colourIndex == 0 ? AutoAimHeading.getAutoAimPose(55, 12) : AutoAimHeading.getAutoAimPose(144-55, 12);
        return new SequentialCommandGroup(
                new FollowCommand(f, p.buildPath(FAR_START, FAR_SHOOT)),
                new ShootAuto(),
                new FollowCommand(f, p.buildPath(FAR_SHOOT, SAMPLE_START[2])),
                p.intakeCommand(SAMPLE_START[2], SAMPLE_END[2]),
                new FollowCommand(f, p.buildPath(SAMPLE_END[2], FAR_SHOOT_NO_ANGLE)),
                new FollowCommand(f, p.buildPath(FAR_SHOOT_NO_ANGLE, FAR_SHOOT)),
                new ShootAuto(),
                new FollowCommand(f, p.buildPath(FAR_SHOOT, SAMPLE_START[3])),
                p.intakeCommand(SAMPLE_START[3], SAMPLE_END[3]),
                new FollowCommand(f, p.buildPath(SAMPLE_END[3], FAR_SHOOT_NO_ANGLE)),
                new FollowCommand(f, p.buildPath(FAR_SHOOT_NO_ANGLE, FAR_SHOOT)),
                new ShootAuto(),
                new FollowCommand(f, p.buildPath(FAR_SHOOT, FAR_PARK)),
                new SetShooterState(ShootPreset.STOP)
        );
    }
}
