package org.firstinspires.ftc.teamcode.helpers.testOpmodes.auto;


import static org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoPoints.CLOSE_START;
import static org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoPoints.CLOSE_PARK;
import static org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoPoints.CLOSE_SHOOT;
import static org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoPoints.CLOSE_START;
import static org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoPoints.SAMPLE_END;
import static org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoPoints.SAMPLE_START;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.helpers.commands.FollowCommand;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;

@Autonomous(name = "Auto test close v1.0", group = "Auto")
public class AutoCloseTest1 extends AutoBaseTest {

    public Pose StartPose() {
        return CLOSE_START;
    }

    public Command AutoCommand() {
        return new SequentialCommandGroup(
            new FollowCommand(f, p.buildPath(CLOSE_START, CLOSE_SHOOT)),
            new FollowCommand(f, p.buildPath(CLOSE_SHOOT, SAMPLE_START[0])),
            p.intakeCommand(SAMPLE_START[0], SAMPLE_END[0]),
            new FollowCommand(f, p.buildPath(SAMPLE_END[0], CLOSE_SHOOT)),
            p.shoot3balls(),
            new FollowCommand(f, p.buildPath(CLOSE_SHOOT, CLOSE_PARK))
        );
    }
}
