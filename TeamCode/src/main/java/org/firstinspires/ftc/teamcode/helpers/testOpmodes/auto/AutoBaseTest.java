package org.firstinspires.ftc.teamcode.helpers.testOpmodes.auto;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoConfigurator;
import org.firstinspires.ftc.teamcode.helpers.commands.ScheduleRuntimeCommand;
import org.firstinspires.ftc.teamcode.helpers.enums.Alliance;
import org.firstinspires.ftc.teamcode.helpers.opmode.VLRLinearOpMode;
import org.firstinspires.ftc.teamcode.helpers.persistence.AllianceSaver;
import org.firstinspires.ftc.teamcode.helpers.persistence.PoseSaver;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.helpers.utils.PathBuildHelper;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

public abstract class AutoBaseTest extends VLRLinearOpMode {
    protected boolean isRed;
    protected Follower f;
    protected PathBuildHelper p;

    public void run() {
        telemetry = new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), telemetry);

        //noinspection unchecked
        VLRSubsystem.requireSubsystems(Shooter.class, Intake.class, Transfer.class);
        VLRSubsystem.initializeAll(hardwareMap);

        AutoConfigurator ac = new AutoConfigurator(telemetry, gamepad1, () -> isStopRequested() || opModeIsActive());
        AutoConfigurator.Choice color = ac.multipleChoice("Select alliance:", new AutoConfigurator.Choice("Blue"),
                new AutoConfigurator.Choice("Red"));
        ac.review("Selected alliance: " + color.text);
        isRed = color.text.equals("Red");
        AllianceSaver.setAlliance(isRed ? Alliance.RED : Alliance.BLUE);

        f = Constants.createFollower(hardwareMap);
        p = new PathBuildHelper(f, isRed);
        f.setStartingPose(p.getPose(StartPose()));
        System.out.println("Start Pose: " + f.getPose());

        if (isStopRequested()) return;

        waitForStart();
        VLRSubsystem.getShooter().setAutoAimEnabled(true);

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        AutoCommand(),
                        new WaitCommand(5000),
                        new InstantCommand(() -> PoseSaver.setPedroPose(f.getPose()))
                ));

        while (opModeIsActive()) {
            f.update();
            VLRSubsystem.getShooter().updateAutoAim(f);
            VLRSubsystem.getShooter().periodic();
            VLRSubsystem.getShooter().telemetry(telemetry);
            telemetry.update();
            System.out.println("loop");
        }
    }

    public abstract Pose StartPose();

    public abstract Command AutoCommand();
}
