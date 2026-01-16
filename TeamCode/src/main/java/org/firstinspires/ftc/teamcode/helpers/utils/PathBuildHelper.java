package org.firstinspires.ftc.teamcode.helpers.utils;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.helpers.commands.FollowCommand;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;

public class PathBuildHelper {
    private Follower f;
    private boolean mirror;
    public PathBuildHelper(Follower f, boolean mirror){
        this.f = f;
        this.mirror = mirror;
    }

    public enum Interpolation{
        LINEAR,
        TANGENTIAL,
        CONSTANT
    };
    private Pose mirrorPose(Pose pose){
        return new Pose(144-pose.getX(), pose.getY(), Math.PI - pose.getHeading());
    }
    public Pose getPose(Pose pose){
        return mirror ? mirrorPose(pose) : pose;
    }
    public PathChain buildPath(Pose pos1, Pose pos2){
        return buildPath(pos1, pos2, Interpolation.LINEAR);
    }
    public PathChain buildPath(Pose pos1, Pose pos2, Interpolation interpolation){
        return buildPath(pos1, pos2, interpolation, mirror);
    }
    private PathChain buildPath(Pose pos1, Pose pos2, Interpolation interpolation, boolean mirror){
        if(mirror){
            pos1 = mirrorPose(pos1);
            pos2 = mirrorPose(pos2);
        }
        PathBuilder builder = f.pathBuilder()
                .addPath(new BezierLine(pos1, pos2));
        switch (interpolation){
            case LINEAR:
        }
        return builder.build();
    }
    public Command intakeCommand(Pose pos1, Pose pos2) {
        Intake intake = VLRSubsystem.getInstance(Intake.class);
        return new SequentialCommandGroup(
                new InstantCommand(() -> intake.setIntake(true)),
                new FollowCommand(f, buildPath(pos1, pos2), 0.3),
                new InstantCommand(() -> intake.setIntake(false))
        );
    }
    public Command shoot3balls(){
        return new SequentialCommandGroup(

        );
    }
}
