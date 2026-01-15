package org.firstinspires.ftc.teamcode.helpers.testOpmodes.auto;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoConfigurator;
import org.firstinspires.ftc.teamcode.helpers.commands.FollowCommand;
import org.firstinspires.ftc.teamcode.helpers.enums.Alliance;
import org.firstinspires.ftc.teamcode.helpers.opmode.VLRLinearOpMode;
import org.firstinspires.ftc.teamcode.helpers.persistence.AllianceSaver;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;

public abstract class AutoBaseTest extends VLRLinearOpMode {
    private AutoConfigurator ac;
    protected Follower f;
    protected boolean isRed;
    private Pose mirrorPose(Pose pose){
        return new Pose(144-pose.getX(), pose.getY(), Math.PI - pose.getHeading());
    }
    protected PathChain buildPath(Pose pos1, Pose pos2){
        return buildPath(pos1, pos2, isRed);
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
    protected Command intakeCommand(Pose pos1, Pose pos2) {
        Intake intake = VLRSubsystem.getInstance(Intake.class);
        return new SequentialCommandGroup(
                new InstantCommand(() -> intake.setIntake(true)),
                new FollowCommand(f, buildPath(pos1, pos2), 0.3),
                new InstantCommand(() -> intake.setIntake(false))
        );
    }
    protected Command shoot3balls(){
        return new SequentialCommandGroup(

        );
    }

    public void run() {
        telemetry = new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), telemetry);

        VLRSubsystem.requireSubsystems(Shooter.class, Intake.class);
        VLRSubsystem.initializeAll(hardwareMap);

        ac = new AutoConfigurator(telemetry, gamepad1, ()-> isStopRequested() || opModeIsActive());
        AutoConfigurator.Choice color = ac.multipleChoice("Select alliance:", new AutoConfigurator.Choice("Blue"),
                new AutoConfigurator.Choice("Red"));
        ac.review("Selected alliance: " + color.text);
        isRed = color.text.equals("Red");
        AllianceSaver.setAlliance(isRed ? Alliance.RED : Alliance.BLUE);

        f = Constants.createFollower(hardwareMap);
        f.setStartingPose(isRed ? mirrorPose(StartPose()) : StartPose());

        if (isStopRequested()) return;

        waitForStart();

        CommandScheduler.getInstance().schedule(AutoCommand());

        while (opModeIsActive()) {
            f.update();
        }
    }

    public abstract Pose StartPose();

    public abstract Command AutoCommand();
}
