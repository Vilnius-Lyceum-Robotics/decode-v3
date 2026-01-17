package org.firstinspires.ftc.teamcode.helpers.testOpmodes.auto;


import static org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoPoints.*;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.outoftheboxrobotics.photoncore.Photon;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.helpers.commands.FollowCommand;
import org.firstinspires.ftc.teamcode.helpers.enums.Alliance;
import org.firstinspires.ftc.teamcode.helpers.persistence.AllianceSaver;
import org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterConfiguration;
import org.firstinspires.ftc.teamcode.subsystems.shooter.commands.SetShooterState;
import org.firstinspires.ftc.teamcode.subsystems.shooter.commands.Shoot;

@Photon
@Autonomous(name = "CLOSE - Blue", group = "Auto")
public class AutoCloseTest1 extends AutoBaseTest {
    private int colourIndex;
    public Pose StartPose() {
        if (AllianceSaver.getAlliance() == Alliance.RED) {
            colourIndex = 1;
        } else colourIndex = 0;
        return CLOSE_START[colourIndex];
    }

    public Command AutoCommand() {
        return new SequentialCommandGroup(
                new SetShooterState(ShooterConfiguration.ShootPreset.CLOSE),
                new FollowCommand(f, p.buildPath(CLOSE_START[colourIndex], CLOSE_SHOOT[colourIndex])),
                new Shoot(),
                new FollowCommand(f, p.buildPath(CLOSE_SHOOT[colourIndex], SAMPLE_START[0][colourIndex])),
                p.intakeCommand(SAMPLE_START[0][colourIndex], SAMPLE_END[0][colourIndex]),
                new FollowCommand(f, p.buildPath(SAMPLE_END[0][colourIndex], CLOSE_SHOOT[colourIndex])),
                new Shoot(),
                new SetShooterState(ShooterConfiguration.ShootPreset.STOP),
                new FollowCommand(f, p.buildPath(CLOSE_SHOOT[colourIndex], CLOSE_PARK[colourIndex]))
        );
    }
}
