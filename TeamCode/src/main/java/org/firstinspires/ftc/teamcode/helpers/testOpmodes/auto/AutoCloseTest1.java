package org.firstinspires.ftc.teamcode.helpers.testOpmodes.auto;


import static org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoPoints.CLOSE_START;
import static org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoPoints.CLOSE_PARK;
import static org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoPoints.CLOSE_SHOOT;
import static org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoPoints.SAMPLE_END;
import static org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoPoints.SAMPLE_START;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.outoftheboxrobotics.photoncore.Photon;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.helpers.commands.FollowCommand;
import org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterConfiguration;
import org.firstinspires.ftc.teamcode.subsystems.shooter.commands.SetShooterState;
import org.firstinspires.ftc.teamcode.subsystems.shooter.commands.Shoot;

@Photon
@Autonomous(name = "Auto test close v1.0", group = "Auto")
public class AutoCloseTest1 extends AutoBaseTest {

    public Pose StartPose() {
        return CLOSE_START;
    }

    public Command AutoCommand() {
        return new SequentialCommandGroup(
                new SetShooterState(ShooterConfiguration.ShootPreset.CLOSE),
                new FollowCommand(f, p.buildPath(CLOSE_START, CLOSE_SHOOT)),
                new Shoot(),
                new FollowCommand(f, p.buildPath(CLOSE_SHOOT, SAMPLE_START[0])),
                p.intakeCommand(SAMPLE_START[0], SAMPLE_END[0]),
                new FollowCommand(f, p.buildPath(SAMPLE_END[0], CLOSE_SHOOT)),
                new Shoot(),
                new SetShooterState(ShooterConfiguration.ShootPreset.STOP),
                new FollowCommand(f, p.buildPath(CLOSE_SHOOT, CLOSE_PARK))
        );
    }
}
