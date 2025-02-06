package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.controls.PrimaryDriverTeleOpControls;
import org.firstinspires.ftc.teamcode.controls.SecondaryDriverTeleOpControls;
import org.firstinspires.ftc.teamcode.helpers.monitoring.LoopTimeMonitor;
import org.firstinspires.ftc.teamcode.helpers.opmode.VLRLinearOpMode;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.arm.rotator.ArmRotatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.arm.slide.ArmSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.chassis.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.claw.ClawSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.hang.HangConfiguration;
import org.firstinspires.ftc.teamcode.subsystems.hang.HangSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.hang.commands.SetHangPosition;
import org.firstinspires.ftc.teamcode.subsystems.vision.Vision;


/**
 * @noinspection unchecked
 */
@Photon
@TeleOp(name = "VLRTeleOp", group = "!TELEOP")
public class VLRTeleOp extends VLRLinearOpMode {
    // Controls
    private PrimaryDriverTeleOpControls primaryDriver;
    private SecondaryDriverTeleOpControls secondaryDriver;
    private LoopTimeMonitor loopTimeMonitor;

    @Override
    public void run() {
        VLRSubsystem.requireSubsystems(Chassis.class, ArmSlideSubsystem.class, ArmRotatorSubsystem.class, ClawSubsystem.class);
        VLRSubsystem.initializeAll(hardwareMap);

        // for testing only, remove for prod!!! this will ruin the performance of teleop
        //VLRSubsystem.getInstance(Vision.class).setEnabled(true);


        loopTimeMonitor = new LoopTimeMonitor();

        ArmSlideSubsystem ass = VLRSubsystem.getInstance(ArmSlideSubsystem.class);
        System.out.println(ass);
        primaryDriver = new PrimaryDriverTeleOpControls(gamepad1);

        waitForStart();
        // since judges are pizdabolai
        // VLRSubsystem.initializeOne(hardwareMap, ClawSubsystem.class);
        // VLRSubsystem.initializeOne(hardwareMap, HangSubsystem.class);
        secondaryDriver = new SecondaryDriverTeleOpControls(gamepad2);

        ass.setMotorPower(-0.6);
        ElapsedTime timeout = new ElapsedTime();
        while (!ass.getLimitSwitchState() && timeout.milliseconds() < 500) {
            sleep(1);
        }
        ass.setMotorPower(0);
        ass.checkLimitSwitch();

        CommandScheduler.getInstance().schedule(new SetHangPosition(HangConfiguration.TargetPosition.DOWN));

        while (opModeIsActive()) {
            loopTimeMonitor.loopStart();

            primaryDriver.update();
            secondaryDriver.update();

            loopTimeMonitor.loopEnd();

            double slowestLoopTimeAverage = loopTimeMonitor.getAverageTime(10, LoopTimeMonitor.ElementSelectionType.TOP_PERCENTILE_ELEMENTS);
            System.out.println("MAIN THREAD SLOWEST LOOP TIME AVERAGE HZ: " + 1.0 / slowestLoopTimeAverage);
        }
    }
}
