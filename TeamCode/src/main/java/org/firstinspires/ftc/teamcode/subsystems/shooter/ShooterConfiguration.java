package org.firstinspires.ftc.teamcode.subsystems.shooter;
import com.acmerobotics.dashboard.config.Config;

@Config
public interface ShooterConfiguration {

    String SHOOTER_LEFT = "shooterLeft";
    String SHOOTER_RIGHT = "shooterRight";
    String LIFT_SERVO = "lift";
    String SHOOTER_HOOD = "hood";
    public double multiplierRPM = 0.6;

    double LOW_SPIN_FORCE = 0.08;
    double LIFT_MIN = 0.0;
    double LIFT_MAX = 0.8;
    double LIFT_DOWN_POS = 0.3;
//    double LIFT_HOLD_POS = 0.4;
    double LIFT_UP_POS = 0.38;
}
