package org.firstinspires.ftc.teamcode.subsystems.chassis;

import com.acmerobotics.dashboard.config.Config;

@Config
public interface ChassisConfiguration {
    String MOTOR_LEFT_FRONT = "leftFront";
    String MOTOR_RIGHT_FRONT = "rightFront";
    String MOTOR_LEFT_REAR = "leftRear";
    String MOTOR_RIGHT_REAR = "rightRear";
    double REAR_TRACK_RADIUS = 1.0;
    double FRONT_TRACK_RADIUS = 0.85;
}
