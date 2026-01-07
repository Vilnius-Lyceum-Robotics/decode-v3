package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.acmerobotics.dashboard.config.Config;

@Config
public interface IntakeConfiguration {
    String INTAKE_MOTOR = "intake";
    String TRANSFER_MOTOR = "transfer";
    double INTAKE_SPEED = 1;
    double TRANSFER_SPEED = 0.6;
}
