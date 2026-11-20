package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.constants.RobotConstants;

import java.util.List;

/**
 * Gets info from robot LimeLight camera
 * <p>
 * Can also be used in TeleOp at a later time??
 */
public class VisionCalibration {
    /* TODO:
     *  Add distanceAlign method?
     *  Config in browser? - Get Jack's help
     *  Convert limelight vector to Pose2d? - pose2dOutput
     */

    // Initialize hardware variables
    private final IMU imu;
    private final Limelight3A limelight;

    // Speed multiplier
    double speedDegreeMultiplier = 0.04;
    double tagXTelem = 0.0;

    /**
     * Sets up the limelight and things using the provided robot hardware info.
     * @param hardwareMap Just say hardwareMap in the parenthesis for it to work.
     */
    public VisionCalibration(HardwareMap hardwareMap) {
        imu = hardwareMap.get(IMU.class, RobotConstants.IMU_NAME);
        limelight = hardwareMap.get(Limelight3A.class, RobotConstants.LIMELIGHT_NAME);

        // Initialize IMU
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RobotConstants.IMU_LOGO_DIRECTION,
                RobotConstants.IMU_USB_DIRECTION));
        imu.initialize(parameters);

        // Initialize Limelight
        limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        limelight.start(); // This tells Limelight to start looking!

        limelight.pipelineSwitch(0); // Pipeline number - "preset" of settings on limelight config
    }

    /**
     * Oh, gosh darn. I don't know if this is right please help
     */
    public boolean rotateAlign(MecanumDriveTrain drive, int targetId) {
        LLResult result = limelight.getLatestResult(); // Get latest picture
        FiducialResult targetTag = getTagById(result, targetId); // Get the target

        // If the robot can't see anything, stop
        if (result == null || !result.isValid() || targetTag == null) {
            drive.drive(0, 0, 0);
            return false; // Failure, oh no.
        }

        double tx = targetTag.getTargetXDegrees();

        // Telemetry
        tagXTelem = targetTag.getTargetXDegrees();

        // If tag is in the "good" window then it is aligned
        if (Math.abs(tx) <= RobotConstants.LL_DEGREES_ROTATION_ALIGNED) {
            drive.drive(0, 0, 0);
            return true; // Success, yay!
        }

        // Find direction to spin robot
        //if (tx > 0) { drive.drive(0, 0, 0.15); } // Spin right
        //else { drive.drive(0, 0, -0.15); }

        // Spin robot using curve (PID in a way)
        drive.drive(0, 0, speedDegreeMultiplier*tx); // Spin the way that tx is

        return false; // Still turning...
    }

    public double getVisionTurnSpeed() {
        return speedDegreeMultiplier*tagXTelem;
    }

    private FiducialResult getTagById(LLResult result, int targetId) {
        if (result == null || !result.isValid()) {
            return null;
        }

        List<FiducialResult> tags = result.getFiducialResults();
        for (FiducialResult tag : tags) {
            if (tag.getFiducialId() == targetId) {
                return tag; // Found tag
            }
        }

        return null; // Can't see the target tag
    }
}
