package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.constants.RobotConstants;

@TeleOp
public class MotorDirectionTest extends LinearOpMode {
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

        // Reverse motors
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        //rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        //rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Robot is started
        telemetry.addData("Status", "Running");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.x) {
                leftFront.setPower(motorTestSpeed);
            } else {
                leftFront.setPower(0.0);
            }

            if (gamepad1.y) {
                rightFront.setPower(motorTestSpeed);
            } else {
                rightFront.setPower(0.0);
            }

            if (gamepad1.a) {
                leftBack.setPower(motorTestSpeed);
            } else {
                leftBack.setPower(0.0);
            }

            if (gamepad1.b) {
                rightBack.setPower(motorTestSpeed);
            } else {
                rightBack.setPower(0.0);
            }

            updateDriveTelemetry();
        }
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