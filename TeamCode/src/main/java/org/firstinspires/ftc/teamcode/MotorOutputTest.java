package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.constants.RobotConstants;

@TeleOp
public class MotorOutputTest extends LinearOpMode {
    // Hardware variables
    private DcMotor leftBack;
    private DcMotor rightBack;
    private DcMotor rightFront;
    private DcMotor leftFront;

    // Speed
    private final double motorTestSpeed = 0.5;

    @Override
    public void runOpMode() {
        // Hardware maps
        leftBack = hardwareMap.get(DcMotor.class, RobotConstants.MOTOR_LEFT_BACK);
        rightBack = hardwareMap.get(DcMotor.class, RobotConstants.MOTOR_RIGHT_BACK);
        rightFront = hardwareMap.get(DcMotor.class, RobotConstants.MOTOR_RIGHT_FRONT);
        leftFront = hardwareMap.get(DcMotor.class, RobotConstants.MOTOR_LEFT_FRONT);

        // Robot is started
        telemetry.addData("Status", "Running");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Push button for each motor to rotate forward.
            if (gamepad1.x) {
                leftFront.setPower(motorTestSpeed);
            }
            if (gamepad1.y) {
                rightFront.setPower(motorTestSpeed);
            }
            if (gamepad1.a) {
                leftBack.setPower(motorTestSpeed);
            }
            if (gamepad1.b) {
                rightBack.setPower(motorTestSpeed);
            }
        }

        updateDriveTelemetry();
    }

    // Telemetry
    private void updateDriveTelemetry() {
        telemetry.addLine("Each motor should move forwards on the robot.");
        telemetry.addData("Front Left Motor ( X )", "%.2f", leftFront.getPower());
        telemetry.addData("Front Right Motor ( Y )", "%.2f", rightFront.getPower());
        telemetry.addData("Back Left Motor ( A )", "%.2f", leftBack.getPower());
        telemetry.addData("Back Right Motor ( B )", "%.2f", rightBack.getPower());
        telemetry.update();
    }
}