package org.firstinspires.ftc.teamcode.helpers.testOpmodes.auto;


import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import static org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoPoints.*;
import org.firstinspires.ftc.teamcode.helpers.commands.FollowCommand;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;

@Autonomous(name = "Auto test far v1.0", group = "Auto")
public class AutoFarTest1 extends AutoBaseTest {

    public Pose StartPose() {
        return FAR_START;
    }

    public Command AutoCommand() {
        return new SequentialCommandGroup(
            new FollowCommand(f, p.buildPath(FAR_START, FAR_SHOOT)),
            p.shoot3balls(),
            new FollowCommand(f, p.buildPath(FAR_SHOOT, SAMPLE_START[2])),
            p.intakeCommand(SAMPLE_START[2], SAMPLE_END[2]),
            new FollowCommand(f, p.buildPath(SAMPLE_END[2], FAR_SHOOT)),
            new FollowCommand(f, p.buildPath(FAR_SHOOT, SAMPLE_START[3])),
            p.intakeCommand(SAMPLE_START[3], SAMPLE_END[3]),
            new FollowCommand(f, p.buildPath(SAMPLE_END[3], FAR_SHOOT)),
            p.shoot3balls(),
            new FollowCommand(f, p.buildPath(FAR_SHOOT, FAR_PARK))
        );
    }
}
