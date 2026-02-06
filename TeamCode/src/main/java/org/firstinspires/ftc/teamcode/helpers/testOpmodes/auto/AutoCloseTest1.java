package org.firstinspires.ftc.teamcode.helpers.testOpmodes.auto;


import static org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoPoints.*;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.outoftheboxrobotics.photoncore.Photon;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.helpers.commands.FollowCommand;
import org.firstinspires.ftc.teamcode.subsystems.shooter.ShooterConfiguration.ShootPreset;
import org.firstinspires.ftc.teamcode.subsystems.shooter.commands.SetShooterState;
import org.firstinspires.ftc.teamcode.subsystems.shooter.commands.Shoot;
import org.firstinspires.ftc.teamcode.subsystems.shooter.commands.ShootAuto;

@Photon
@Autonomous(name = "CLOSE", group = "Auto")
public class AutoCloseTest1 extends AutoBaseTest {
    public Pose StartPose() {
        return CLOSE_START;
    }

    public Command AutoCommand() {
        return new SequentialCommandGroup(
                new FollowCommand(f, p.buildPath(CLOSE_START, CLOSE_SHOOT)),
                new ShootAuto(),
                new FollowCommand(f, p.buildPath(CLOSE_SHOOT, SAMPLE_START[0])),
                p.intakeCommand(SAMPLE_START[0], SAMPLE_END[0]),
                new FollowCommand(f, p.buildPath(SAMPLE_END[0], CLOSE_OPEN_GATE_START)),
                new FollowCommand(f, p.buildPath(CLOSE_OPEN_GATE_START, CLOSE_OPEN_GATE)),
                new FollowCommand(f, p.buildPath(CLOSE_OPEN_GATE, CLOSE_SHOOT)),
                new ShootAuto(),
                new FollowCommand(f, p.buildPath(CLOSE_SHOOT, SAMPLE_START[1])),
                p.intakeCommand(SAMPLE_START[1], SAMPLE_END[1]),
                new FollowCommand(f, p.buildPath(SAMPLE_END[1], CLOSE_SHOOT)),
                new ShootAuto(),
                new SetShooterState(ShootPreset.STOP),
                new FollowCommand(f, p.buildPath(CLOSE_SHOOT, CLOSE_PARK)),
                new SetShooterState(ShootPreset.STOP)
        );
    }
}
