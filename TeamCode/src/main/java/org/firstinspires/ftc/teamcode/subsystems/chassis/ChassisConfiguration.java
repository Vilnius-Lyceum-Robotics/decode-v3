package org.firstinspires.ftc.teamcode.subsystems.chassis;

import com.acmerobotics.dashboard.config.Config;

@Config
public class ChassisConfiguration {
    public static String MOTOR_LEFT_FRONT = "leftFront";
    public static String MOTOR_RIGHT_FRONT = "rightFront";
    public static String MOTOR_LEFT_REAR = "leftRear";
    public static String MOTOR_RIGHT_REAR = "rightRear";

    public static double HEADING_AUTOAIM_P = 0.8;
    public static double HEADING_AUTOAIM_I = 0.05;
    public static double HEADING_AUTOAIM_D = 0.08;
}
