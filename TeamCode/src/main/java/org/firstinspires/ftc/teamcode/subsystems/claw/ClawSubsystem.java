package org.firstinspires.ftc.teamcode.subsystems.claw;

import static com.arcrobotics.ftclib.util.MathUtils.clamp;

import static org.firstinspires.ftc.teamcode.subsystems.arm.rotator.ArmRotatorSubsystem.mapToRange;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;


public class ClawSubsystem extends VLRSubsystem<ClawSubsystem> implements ClawConfiguration {
    private Servo angleServo, twistServo, grabServos;
    private AnalogInput analogLeft, analogRight;

    private double twistIncrement;

    private TargetAngle targetAngle = TargetAngle.UP;
    private TargetState clawState = TargetState.CLOSED;

    public TargetState getClawState() {
        return clawState;
    }

    protected void initialize(HardwareMap hardwareMap) {
        angleServo = hardwareMap.get(Servo.class, ANGLE_SERVO);
        twistServo = hardwareMap.get(Servo.class, TWIST_SERVO);
        grabServos = hardwareMap.get(Servo.class, GRAB_SERVO);

        analogLeft = hardwareMap.get(AnalogInput.class, ANALOG_ENCODER_LEFT);
        analogRight = hardwareMap.get(AnalogInput.class, ANALOG_ENCODER_RIGHT);

        setTargetAngle(TargetAngle.UP);
        setTargetTwist(TargetTwist.NORMAL);
        setTargetState(TargetState.CLOSED);
    }


    public void setTargetAngle(TargetAngle targetAngle) {
        switch (targetAngle) {
            case UP:
                angleServo.setPosition(angle_up_pos);
                this.targetAngle = TargetAngle.UP;
                break;
            case DOWN:
                angleServo.setPosition(angle_down_pos);
                this.targetAngle = TargetAngle.DOWN;
                break;
            case DEPOSIT:
                angleServo.setPosition(angle_deposit_pos);
                this.targetAngle = TargetAngle.UP;
                break;
        }
    }

    public TargetAngle getTargetAngle() {
        return targetAngle;
    }


    public void setTargetTwist(TargetTwist twistAngle) {
        switch (twistAngle) {
            case NORMAL:
                twistServo.setPosition(twist_normal_pos);
                break;
            case FLIPPED:
                twistServo.setPosition(twist_flipped_pos);
                break;
        }
    }


    public void setTargetTwist(double twistAngle) {
        //twist angle from 0 to 1, while 0.5 is normal pos, 0 is 90 degrees to one side, 1 is 90 degrees to another
        //map joystick to [-0.5; 0.5] and add 0.5
        double position = twist_normal_pos - (twistAngle / 2);
        twistServo.setPosition(clamp(position, TWIST_MIN, TWIST_MAX));
    }


    public void setTwistIncrement(double increment) {
        twistIncrement = increment;
    }


    public void setTargetState(TargetState targetState) {
        clawState = targetState;
        switch (targetState) {
            case OPEN:
                grabServos.setPosition(state_open_pos);
                break;
            case CLOSED:
                grabServos.setPosition(state_closed_normal_pos);
                break;
        }
    }


    @Override
    public void periodic() {
    }
}