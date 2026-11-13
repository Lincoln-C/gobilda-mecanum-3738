package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.constants.RobotConstants;

/**
 * Represents a servo on a robot.
 */
public class ServoAttachment {
    // Initialize hardware variables
    private final CRServo servo;

    // This is a constructor, it gets the stuff from outside so it can do stuff inside this file.
    // Yes I know that is a bad explanation.

    /**
     * Sets up the motors and stuff using the provided robot hardware info.
     * @param hardwareMap Just say hardwareMap in the parenthesis for it to work.
     */
    public ServoAttachment(HardwareMap hardwareMap) {
        // Map motors to driver hub names
        servo = hardwareMap.get(CRServo.class, RobotConstants.SERVO_ATTACHMENT);
    }

    public void setServoOutput(double power) {
        // Set power if past controller deadzone
        if (Math.abs(power) < RobotConstants.CONTROLLER_DEADZONE) {
            servo.setPower(0.0);
        } else {
            servo.setPower(power);
        }
    }

    public void stop() {
        servo.setPower(0.0);
    }
}