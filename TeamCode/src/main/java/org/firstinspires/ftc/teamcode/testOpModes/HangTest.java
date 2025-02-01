package org.firstinspires.ftc.teamcode.testOpModes;

import static org.firstinspires.ftc.teamcode.auto.pedroPathing.pathGeneration.MathFunctions.clamp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.roboctopi.cuttlefishftcbridge.devices.CuttleRevHub;
import com.roboctopi.cuttlefishftcbridge.devices.CuttleServo;

import org.firstinspires.ftc.teamcode.helpers.opmode.VLRLinearOpMode;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.arm.rotator.ArmRotatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.arm.slide.ArmSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.chassis.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.claw.ClawSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.hang.HangConfiguration;
import org.firstinspires.ftc.teamcode.subsystems.hang.HangSubsystem;


@Config
//@Disabled
@TeleOp(name="HangTest")
public class HangTest extends VLRLinearOpMode
{
    HangSubsystem hang;
    @Override
    public void run() {
        VLRSubsystem.requireSubsystems( HangSubsystem.class);
        VLRSubsystem.initializeAll(hardwareMap);

        hang = VLRSubsystem.getInstance(HangSubsystem.class);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.dpad_up){
                hang.setTargetPosition(HangConfiguration.TargetPosition.UP);
            }
            else if (gamepad1.dpad_down){
                hang.setTargetPosition(HangConfiguration.TargetPosition.DOWN);
            }
            telemetry.update();
        }
    }
}
