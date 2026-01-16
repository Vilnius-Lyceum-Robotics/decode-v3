package org.firstinspires.ftc.teamcode.subsystems.shooter;
import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class ShooterConfiguration {

    public static String SHOOTER_LEFT = "shooterLeft";
    public static String SHOOTER_RIGHT = "shooterRight";
    public static String BLOCKER_SERVO_NAME = "lift";
    public static String SHOOTER_HOOD = "hood";
    public static double BLOCKER_CLOSED_POS = 1;
    public static double BLOCKER_OPEN_POS = 0.8;
    public static double HOOD_MAX_ANGLE = 0.8;
    public static double HOOD_MIN_ANGLE = 0.1;
    public static double SHOOTING_RPM_P = 4.4;
    public static double SHOOTING_RPM_I = 0;
    public static double SHOOTING_RPM_D = 0;
    public static double SHOOTING_RPM_F = 0.64;
    public static double TARGET_RPM = 0;

    public enum ShootPreset {
        CLOSE(1600, HOOD_MIN_ANGLE), // x: 48; y: 96
        CENTER(2300, 0.55), // x: 65 y: 80
        FAR(2700, HOOD_MAX_ANGLE), // x: 55; y: 12
        STOP(0, HOOD_MIN_ANGLE);
        public final double rpm, hoodPos;
        ShootPreset(double rpm, double hoodPos){
            this.rpm = rpm;
            this.hoodPos = hoodPos;
        }
    }
}
