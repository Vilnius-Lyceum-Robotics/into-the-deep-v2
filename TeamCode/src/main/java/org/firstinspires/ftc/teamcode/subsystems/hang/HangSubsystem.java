package org.firstinspires.ftc.teamcode.subsystems.hang;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;

@Config
public class HangSubsystem extends VLRSubsystem<HangSubsystem> implements HangConfiguration {
    private Servo left, right;
    private AnalogInput analogLeft, analogRight;

    @Override
    protected void initialize(HardwareMap hardwareMap) {
        left = hardwareMap.get(Servo.class, LEFT_AXON);
        right = hardwareMap.get(Servo.class, RIGHT_AXON);
        left.setPosition(TargetPosition.DOWN.pos);
        right.setPosition(TargetPosition.DOWN.pos);

        analogLeft = hardwareMap.get(AnalogInput.class, LEFT_ANALOG);
        analogRight = hardwareMap.get(AnalogInput.class, RIGHT_ANALOG);
    }

    public void setTargetPosition(TargetPosition target) {
        left.setPosition(target.pos);
        right.setPosition(target.pos);
    }


    private double getAngle(double voltage){
        return voltage / 3.3 * 360;
    }

    public boolean analogFeedbackThresholdReached(){
        return (
                getAngle(analogLeft.getVoltage()) > leftAnalogThreshold &&
                getAngle(analogRight.getVoltage()) > rightAnalogThreshold
        );
    }
}