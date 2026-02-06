package org.firstinspires.ftc.teamcode.controls;

import java.util.function.Consumer;

public class TriggerAsButton {
    double lastVal = 0;

    public TriggerAsButton() {}

    public void onUpdate(double val, Consumer<Boolean> onTrigger) {
        if (val > 0.8 && lastVal < 0.8) onTrigger.accept(true);
        lastVal = val;
    }
}
