package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.controls.PrimaryDriverTeleOpControls;
import org.firstinspires.ftc.teamcode.controls.SecondaryDriverTeleOpControls;
import org.firstinspires.ftc.teamcode.helpers.opmode.VLRLinearOpMode;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.helpers.utils.GlobalConfig;
import org.firstinspires.ftc.teamcode.subsystems.arm.ArmState;
import org.firstinspires.ftc.teamcode.subsystems.arm.rotator.ArmRotatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.arm.slide.ArmSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.chassis.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.claw.ClawSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.hang.HangSubsystem;


/**
 * @noinspection unchecked
 */
@Photon
@TeleOp(name = "VLRTeleOp", group = "!TELEOP")
public class VLRTeleOp extends VLRLinearOpMode {
    // Controls
    PrimaryDriverTeleOpControls primaryDriver;
    SecondaryDriverTeleOpControls secondaryDriver;

    @Override
    public void run() {
        VLRSubsystem.requireSubsystems(Chassis.class, ArmSlideSubsystem.class, ArmRotatorSubsystem.class, ClawSubsystem.class, HangSubsystem.class  );
        VLRSubsystem.initializeAll(hardwareMap);

//        VLRSubsystem.getInstance(Chassis.class).enableFieldCentric();
        ArmSlideSubsystem ass = VLRSubsystem.getInstance(ArmSlideSubsystem.class);
        primaryDriver = new PrimaryDriverTeleOpControls(gamepad1);
        secondaryDriver = new SecondaryDriverTeleOpControls(gamepad2);

        ass.setMotorPower(-0.3);
        ElapsedTime timeout = new ElapsedTime();
        while (!ass.getLimitSwitchState()) {
            sleep(10);
            if (timeout.milliseconds() > 1000) {
                break;
            }
        }
        ass.setMotorPower(0);
        ass.checkLimitSwitch();

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();
        // since judges are pizdabolai
        //ass.setPowerOverride(true);

        while (opModeIsActive()) {

            primaryDriver.update();
            secondaryDriver.update();

            //double power = gamepad2.left_stick_y;
            //ass.setMotorPower(power);


            if (GlobalConfig.DEBUG_MODE) {
                telemetry.addData("current state", ArmState.get());
//                telemetry.addData("Slide pos", ass.getPosition());
            }
            //telemetry.addData("POWER OVERRIDE FOR COOKED HANG: ", power);
            telemetry.update();

        }
    }
}
