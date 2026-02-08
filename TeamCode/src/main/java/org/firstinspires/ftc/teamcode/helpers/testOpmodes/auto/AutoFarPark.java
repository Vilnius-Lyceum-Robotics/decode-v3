package org.firstinspires.ftc.teamcode.helpers.testOpmodes.auto;


import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.outoftheboxrobotics.photoncore.Photon;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import static org.firstinspires.ftc.teamcode.helpers.autoconfig.AutoPoints.*;


import org.firstinspires.ftc.teamcode.helpers.commands.FollowCommand;
import org.firstinspires.ftc.teamcode.helpers.persistence.AllianceSaver;

@Photon
@Autonomous(name = "FAR PARK ONLY", group = "Auto")
public class AutoFarPark extends AutoBaseTest {
    public Pose StartPose() {
        System.out.println("StartPose alliance: " + AllianceSaver.getAlliance());
        return FAR_START;
    }

    public Command AutoCommand() {
        System.out.println("AutoCommand");

//        Pose shootPose =colourIndex == 0 ? AutoAimHeading.getAutoAimPose(55, 12) : AutoAimHeading.getAutoAimPose(144-55, 12);
        return new SequentialCommandGroup(
                new FollowCommand(f, p.buildPath(FAR_START, FAR_PARK))
        );
    }
}
