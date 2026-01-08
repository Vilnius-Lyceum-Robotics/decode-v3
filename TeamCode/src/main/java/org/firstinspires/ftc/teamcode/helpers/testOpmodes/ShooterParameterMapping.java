package org.firstinspires.ftc.teamcode.helpers.testOpmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.helpers.commands.FollowCommand;
import org.firstinspires.ftc.teamcode.helpers.commands.RepeatNTimesCommand;
import org.firstinspires.ftc.teamcode.helpers.commands.ScheduleRuntimeCommand;
import org.firstinspires.ftc.teamcode.helpers.opmode.VLRLinearOpMode;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.chassis.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.transfer.Transfer;

import java.util.function.BooleanSupplier;

@TeleOp(name = "ShooterParameterMapping", group = "Auto")
public class ShooterParameterMapping extends VLRLinearOpMode {
    int numberOfPoints = 10;
    double distance = 120;
    Pose startPose = new Pose(24, 120, Math.toRadians(135));
    Pose goalPose = new Pose(9, 133);
    double[][] data = new double[numberOfPoints][3];

    Follower f;
    Pose[] samplePoints;
    int currentPointIndex = 0;

    private PathChain buildPath(Pose pos1, Pose pos2){
        return f.pathBuilder()
                .addPath(new BezierLine(pos1, pos2))
                .setLinearHeadingInterpolation(pos1.getHeading(), pos2.getHeading())
                .build();
    }

    private void generateSamplePoints(){
        samplePoints = new Pose[numberOfPoints];
        double angle = Math.toRadians(-45);
        Pose offset = new Pose(distance * Math.cos(angle) / numberOfPoints, distance * Math.sin(angle) / numberOfPoints);

        for (int i = 0; i < numberOfPoints; i++){
            samplePoints[i] = startPose.plus(offset.times(i));
        }
    }

    private Command FollowToNextPoint(int point){
        return new FollowCommand(f, buildPath(samplePoints[point], samplePoints[point + 1]));
    }


    private Command LogHoodAndShooterCommand(double hoodAngle, double shooterSpeed, int currentPointIndex){
        return new org.firstinspires.ftc.teamcode.helpers.commands.InstantCommand() {
            @Override
            public void run() {
                double deltaX = goalPose.getX() - samplePoints[currentPointIndex].getX();
                double deltaY = goalPose.getY() - samplePoints[currentPointIndex].getY();
                double distance =  Math.hypot(deltaX, deltaY);

                System.out.println("logger: distance - " + distance);
                System.out.println("logger: hood angle - " + hoodAngle);
                System.out.println("logger: shooter - " + shooterSpeed);

                data[currentPointIndex][0] = distance;
                data[currentPointIndex][1] = hoodAngle;
                data[currentPointIndex][2] = shooterSpeed;
            }
        };
    }

    public Command AutoCommand(int numOfPoints, BooleanSupplier confirmation) {
        return new RepeatNTimesCommand(
                numOfPoints - 1,
                new WaitUntilCommand(confirmation),
                new ScheduleRuntimeCommand(()-> LogHoodAndShooterCommand(
                        VLRSubsystem.getInstance(Shooter.class).getHoodPos(),
                        VLRSubsystem.getInstance(Shooter.class).getCurrentRPM(),
                        currentPointIndex)),
                new WaitCommand(50),
                new ScheduleRuntimeCommand(()-> FollowToNextPoint(currentPointIndex)),
                new InstantCommand(()-> currentPointIndex++)
        );
    }

    public void run() {
        telemetry = new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), telemetry);
        generateSamplePoints();

        VLRSubsystem.requireSubsystems(Chassis.class, Intake.class, Shooter.class, Transfer.class);
        VLRSubsystem.initializeAll(hardwareMap);

        f = Constants.createFollower(hardwareMap);
        f.setStartingPose(startPose);

        waitForStart();

        CommandScheduler.getInstance().schedule(AutoCommand(numberOfPoints, ()-> gamepad1.triangle));

        while (opModeIsActive()) {
            f.update();
        }
    }
}
