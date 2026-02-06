package org.firstinspires.ftc.teamcode.helpers.testOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.helpers.enums.Alliance;
import org.firstinspires.ftc.teamcode.helpers.persistence.AllianceSaver;

@TeleOp(name = "Alliance Selector", group = "Utils")
public class AllianceSelector extends LinearOpMode {
    @Override
    public void runOpMode() {
        telemetry.addLine("Press X for BLUE | B for RED");
        telemetry.addData("Current alliance", AllianceSaver.getAlliance().name);
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.x) {
                AllianceSaver.setAlliance(Alliance.BLUE);
            } else if (gamepad1.b) {
                AllianceSaver.setAlliance(Alliance.RED);
            }

            telemetry.addLine("Press X for BLUE | B for RED");
            telemetry.addData("Selected alliance", AllianceSaver.getAlliance().name);
            telemetry.update();
        }
    }
}
