package org.firstinspires.ftc.teamcode.subsystems.shooter;
import com.acmerobotics.dashboard.config.Config;

@Config
public interface ShooterConfiguration {

    String SHOOTER_LEFT = "shooterLeft";
    String SHOOTER_RIGHT = "shooterRight";
    String BLOCKER_SERVO_NAME = "lift";
    String SHOOTER_HOOD = "hood";
    double BLOCKER_CLOSED_POS = 1;
    double BLOCKER_OPEN_POS = 0.8;
    double HOOD_MAX_ANGLE = 0.9;
    double HOOD_MIN_ANGLE = 0.1;

    enum ShootPreset {
        CLOSE(800, HOOD_MAX_ANGLE),
        CENTER(1500, 0.34),
        FAR(2000, HOOD_MIN_ANGLE),
        STOP(0, HOOD_MIN_ANGLE);
        public final double rpm, hoodPos;
        ShootPreset(double rpm, double hoodPos){
            this.rpm = rpm;
            this.hoodPos = hoodPos;
        }
    }
}
