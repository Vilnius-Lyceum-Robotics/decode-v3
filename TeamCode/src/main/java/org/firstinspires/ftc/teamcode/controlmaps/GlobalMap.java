package org.firstinspires.ftc.teamcode.controlmaps;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.helpers.controls.DriverControls;
import org.firstinspires.ftc.teamcode.helpers.controls.rumble.RumbleControls;

public class GlobalMap extends ControlMap {
    public boolean isBlue;

    public Follower f;
    public RumbleControls rc;

    public GlobalMap(DriverControls driverControls, Follower f, RumbleControls rc, boolean isBlue) {
        super(driverControls);
        this.f = f;
        this.rc = rc;
        this.isBlue = isBlue;
    }

    @Override
    public void initialize() {

    }
}