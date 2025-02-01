package org.firstinspires.ftc.teamcode.testOpModes;

import static org.firstinspires.ftc.teamcode.auto.pedroPathing.pathGeneration.MathFunctions.clamp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.roboctopi.cuttlefishftcbridge.devices.CuttleAnalog;
import com.roboctopi.cuttlefishftcbridge.devices.CuttleRevHub;
import com.roboctopi.cuttlefishftcbridge.devices.CuttleServo;


@Config
//@Disabled
@TeleOp(name="BetterServoCalibration")
public class AxonCalibration extends OpMode
{
    private CuttleServo servo;
    private CuttleRevHub controlHub;

    private int currentPort = 0;
    private boolean prevState = false;
    private boolean prevUP = false;
    private boolean prevDown = false;
    private double location = 0;

    @Override
    public void init() {
        controlHub = new CuttleRevHub(hardwareMap, CuttleRevHub.HubTypes.CONTROL_HUB);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void loop() {

        if (gamepad1.a && !prevState){
            currentPort ++;
            if (currentPort > 5){
                currentPort = 0;
            }
        }
        prevState = gamepad1.a;


        if (gamepad1.dpad_up && !prevUP){
            location = clamp(location + 0.02, 0, 1);
        }
        prevUP = gamepad1.dpad_up;

        if (gamepad1.dpad_down && !prevDown){
            location = clamp(location - 0.02, 0, 1);
        }
        prevDown = gamepad1.dpad_down;


        servo = new CuttleServo(controlHub, currentPort);
        servo.setPosition(location);

        telemetry.addData("ACTIVE ON PORT: ", currentPort);
        telemetry.addData("TARGET POSITION: ", location);
    }
}
