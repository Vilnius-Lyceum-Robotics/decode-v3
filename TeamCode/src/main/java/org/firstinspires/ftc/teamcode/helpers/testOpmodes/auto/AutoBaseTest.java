package org.firstinspires.ftc.teamcode.helpers.testOpmodes.auto;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoConfigurator;
import org.firstinspires.ftc.teamcode.helpers.enums.Alliance;
import org.firstinspires.ftc.teamcode.helpers.opmode.VLRLinearOpMode;
import org.firstinspires.ftc.teamcode.helpers.persistence.AllianceSaver;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.helpers.utils.PathBuildHelper;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;

public abstract class AutoBaseTest extends VLRLinearOpMode {
    protected boolean isRed;
    private AutoConfigurator ac;
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
