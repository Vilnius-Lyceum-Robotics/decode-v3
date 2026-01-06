package org.firstinspires.ftc.teamcode.helpers.testOpmodes;


import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.helpers.commands.FollowCommand;
import org.firstinspires.ftc.teamcode.helpers.opmode.VLRLinearOpMode;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;

@Autonomous(name = "Auto test far v1.0", group = "Auto")
public class AutoFarTest1 extends VLRLinearOpMode {
    Follower f;
    private Pose[] poses = new Pose[]{
            new Pose(65, 9, 0.5 * Math.PI),
            new Pose(55, 12, 0.6 * Math.PI),
            new Pose(48, 36, Math.PI),
            new Pose(9, 36, Math.PI),
            new Pose(55, 12, 0.6 * Math.PI)
    };

    private Pose mirrorPose(Pose pose){
        return new Pose(144-pose.getX(), pose.getY(), Math.PI*2 - pose.getHeading());
    }
    protected PathChain buildPath(Pose pos1, Pose pos2){
        return buildPath(pos1, pos2, false);
    }
    private PathChain buildPath(Pose pos1, Pose pos2, boolean mirror){
        if(mirror){
            pos1 = mirrorPose(pos1);
            pos2 = mirrorPose(pos2);
        }
        return f.pathBuilder()
                .addPath(new BezierLine(pos1, pos2))
                .setLinearHeadingInterpolation(pos1.getHeading(), pos2.getHeading())
                .build();
    }

    public Pose StartPose() {
        return poses[0];
    }

    public Command AutoCommand() {
        return new SequentialCommandGroup(
            new FollowCommand(f, buildPath(poses[0], poses[1])),
            new FollowCommand(f, buildPath(poses[1], poses[2])),
            new FollowCommand(f, buildPath(poses[2], poses[3])),
            new FollowCommand(f, buildPath(poses[3], poses[4]))
        );
    }

    public void run() {
        telemetry = new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), telemetry);

        VLRSubsystem.requireSubsystems(Shooter.class);
        VLRSubsystem.initializeAll(hardwareMap);

        f = Constants.createFollower(hardwareMap);
        f.setStartingPose(StartPose());

        waitForStart();

        CommandScheduler.getInstance().schedule(AutoCommand());

        while (opModeIsActive()) {
            f.update();
        }
    }
}
