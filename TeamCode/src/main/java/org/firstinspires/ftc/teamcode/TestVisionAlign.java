package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.VisionCalibration;

@Autonomous
public class TestVisionAlign extends LinearOpMode {
    private MecanumDriveTrain drive;
    private VisionCalibration vision;
    private ElapsedTime alignTimer = new ElapsedTime();

    @Override
    public void runOpMode() {
        drive = new MecanumDriveTrain(hardwareMap);
        vision = new VisionCalibration(hardwareMap);

        telemetry.addData("Status", "Initialized - Ready for Start");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            alignToTagWithTimeout(22, 30.0);

            // Launch, do other things...
        }
    }

    /**
     * Method that loops rotateAlign until aligned or time runs out.
     */
    private void alignToTagWithTimeout(int targetTagId, double maxSeconds) {
        alignTimer.reset();

        while (opModeIsActive() && alignTimer.seconds() < maxSeconds) {
            boolean isAligned = vision.rotateAlign(drive, targetTagId);

            telemetry.addData("Target Tag", targetTagId);
            telemetry.addData("Aligned", isAligned);
            telemetry.addData("Time Left", "%.1f s", maxSeconds - alignTimer.seconds());
            telemetry.update();

            if (isAligned) {
                //break; // Exit once aligned
            }
        }

        // Always make sure motors are stopped when leaving the loop
        drive.drive(0, 0, 0);
    }
}
