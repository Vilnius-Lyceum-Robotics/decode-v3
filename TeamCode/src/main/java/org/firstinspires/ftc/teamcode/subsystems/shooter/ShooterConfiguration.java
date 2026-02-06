package org.firstinspires.ftc.teamcode.subsystems.shooter;
import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class ShooterConfiguration {

    public static String SHOOTER_LEFT = "shooterLeft";
    public static String SHOOTER_RIGHT = "shooterRight";
    public static String BLOCKER_SERVO_NAME = "lift";
    public static String SHOOTER_HOOD = "hood";
    public static double BLOCKER_CLOSED_POS = 1;
    public static double BLOCKER_OPEN_POS = 0.8;
    public static double HOOD_MAX_ANGLE = 0.65;
    public static double HOOD_MIN_ANGLE = 0.1;
    public static double SHOOTING_RPM_P = 0.001;
    public static double SHOOTING_RPM_I = 0;
    public static double SHOOTING_RPM_D = 2.0E-5;
    public static double SHOOTING_RPM_F = 0.2;
    public static double TARGET_RPM = 0;

    // Acceleration control
    public static double MAX_ACCELERATION = 100; // RPM per cycle (tune this)
    public static double ACCELERATION_GAIN = 5.0E-4; // 'a' coefficient
    public static double VELOCITY_GAIN = 1.0E-4; // 'v' coefficient

    // Low pass filter constants (0 < alpha <= 1, lower = more filtering)
    public static double ENCODER_FILTER_ALPHA = 0.3; // For velocity readings
    public static double OUTPUT_FILTER_ALPHA = 0.3; // For motor output

    public enum ShootPreset {
        CENTER(2300, 0.55), // x: 65 y: 80
        CLOSE(1600, HOOD_MIN_ANGLE), // x: 48; y: 96
        FAR(3300, HOOD_MAX_ANGLE), // x: 55; y: 12
        STOP(0, HOOD_MIN_ANGLE);
        public final double rpm, hoodPos;

        ShootPreset(double rpm, double hoodPos) {
            this.rpm = rpm;
            this.hoodPos = hoodPos;
        }
    }
}
