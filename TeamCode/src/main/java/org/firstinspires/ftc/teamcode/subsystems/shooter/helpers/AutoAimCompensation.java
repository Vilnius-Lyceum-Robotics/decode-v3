package org.firstinspires.ftc.teamcode.subsystems.shooter.helpers;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;

@Config
public class AutoAimCompensation {
    // Tuning constants - adjust via FTC Dashboard
    public static double HEADING_COMPENSATION_FACTOR = -0.003; // Radians per unit velocity
    public static double DISTANCE_COMPENSATION_FACTOR = 0.05; // Distance units per unit velocity

    /**
     * Calculates compensated heading to account for robot movement
     * @param robotPose Current robot position
     * @param robotVelocity Current robot velocity vector (from follower.getVelocity())
     * @param goalPose Target goal position
     * @return Adjusted heading in radians
     */
    public static double getCompensatedHeading(Pose robotPose, Vector robotVelocity, Pose goalPose) {
        // Calculate base heading to goal (stationary)
        double xLength = goalPose.getX() - robotPose.getX();
        double yLength = goalPose.getY() - robotPose.getY();
        double baseHeading = Math.atan2(yLength, xLength);

        // Calculate perpendicular velocity component (robot moving left/right relative to goal)
        double goalAngle = baseHeading;
        double perpendicularVelocity = -robotVelocity.getXComponent() * Math.sin(goalAngle) +
                                        robotVelocity.getYComponent() * Math.cos(goalAngle);

        // Apply simple linear compensation
        double headingOffset = perpendicularVelocity * HEADING_COMPENSATION_FACTOR;

        return baseHeading + headingOffset;
    }

    /**
     * Calculates compensated distance to account for robot movement
     * @param robotPose Current robot position
     * @param robotVelocity Current robot velocity vector (from follower.getVelocity())
     * @param goalPose Target goal position
     * @return Adjusted distance
     */
    public static double getCompensatedDistance(Pose robotPose, Vector robotVelocity, Pose goalPose) {
        // Calculate actual distance to goal
        double dx = goalPose.getX() - robotPose.getX();
        double dy = goalPose.getY() - robotPose.getY();
        double actualDistance = Math.sqrt(dx * dx + dy * dy);

        // Calculate forward velocity component (robot moving toward/away from goal)
        double goalAngle = Math.atan2(dy, dx);
        double forwardVelocity = robotVelocity.getXComponent() * Math.cos(goalAngle) +
                                 robotVelocity.getYComponent() * Math.sin(goalAngle);

        // Apply simple linear compensation
        // Moving toward goal (positive velocity) -> reduce effective distance
        // Moving away from goal (negative velocity) -> increase effective distance
        double distanceOffset = forwardVelocity * DISTANCE_COMPENSATION_FACTOR;

        return actualDistance - distanceOffset;
    }
}
