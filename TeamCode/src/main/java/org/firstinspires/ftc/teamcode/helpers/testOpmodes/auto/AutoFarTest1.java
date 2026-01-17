package org.firstinspires.ftc.teamcode.helpers.testOpmodes.auto;


import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.outoftheboxrobotics.photoncore.Photon;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import static org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoPoints.*;


import org.firstinspires.ftc.teamcode.helpers.commands.FollowCommand;
import org.firstinspires.ftc.teamcode.helpers.enums.Alliance;
import org.firstinspires.ftc.teamcode.helpers.persistence.AllianceSaver;
import org.firstinspires.ftc.teamcode.subsystems.chassis.helpers.AutoAimHeading;
import org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterConfiguration;
import org.firstinspires.ftc.teamcode.subsystems.shooter.commands.SetShooterState;
import org.firstinspires.ftc.teamcode.subsystems.shooter.commands.Shoot;

@Photon
@Autonomous(name = "FAR - Blue", group = "Auto")
public class AutoFarTest1 extends AutoBaseTest {
    private int colourIndex;
    public Pose StartPose() {
        System.out.println("StartPose alliance: " + AllianceSaver.getAlliance());
        if (AllianceSaver.getAlliance() == Alliance.RED) {
            colourIndex = 1;
        } else colourIndex = 0;
        System.out.println(colourIndex);
        return FAR_START[colourIndex];
    }

    public Command AutoCommand() {
        System.out.println("AutoCommand");
        System.out.println(colourIndex);
        System.out.println(FAR_START[0]);
        System.out.println(FAR_START[1]);
        System.out.println(FAR_START[colourIndex]);

        Pose shootPose =colourIndex == 0 ? AutoAimHeading.getAutoAimPose(55, 12) : AutoAimHeading.getAutoAimPose(144-55, 12);
        System.out.println(shootPose);
        return new SequentialCommandGroup(
                new SetShooterState(ShooterConfiguration.ShootPreset.FAR),
                new FollowCommand(f, p.buildPath(FAR_START[colourIndex], shootPose)),
                new Shoot(),
                new FollowCommand(f, p.buildPath(shootPose, SAMPLE_START[2][colourIndex])),
                p.intakeCommand(SAMPLE_START[2][colourIndex], SAMPLE_END[2][colourIndex]),
                new FollowCommand(f, p.buildPath(SAMPLE_END[2][colourIndex], shootPose)),
                new Shoot(),
                new FollowCommand(f, p.buildPath(shootPose, SAMPLE_START[3][colourIndex])),
                p.intakeCommand(SAMPLE_START[3][colourIndex], SAMPLE_END[3][colourIndex], 0.25),
                new FollowCommand(f, p.buildPath(SAMPLE_END[3][colourIndex], shootPose)),
                new Shoot(),
                new FollowCommand(f, p.buildPath(shootPose, FAR_PARK[colourIndex]))
        );
    }
}
