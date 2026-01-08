package org.firstinspires.ftc.teamcode.subsystems.shooter;
import com.acmerobotics.dashboard.config.Config;

@Config
public interface ShooterConfiguration {

    String SHOOTER_LEFT = "shooterLeft";
    String SHOOTER_RIGHT = "shooterRight";
    String LIFT_SERVO = "lift";
    String SHOOTER_HOOD = "hood";
    double LOW_SPIN_FORCE = 0.08;
    double LIFT_MIN = 0.0;
    double LIFT_MAX = 0.8;
    double LIFT_DOWN_POS = 1;
    double LIFT_UP_POS = 0;
    double HOOD_MAX_ANGLE = 0.65;
    double HOOD_MIN_ANGLE = 0;

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
