package org.firstinspires.ftc.teamcode.testOpModes;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.auto.pedroCommands.FollowPath;
import org.firstinspires.ftc.teamcode.helpers.opmode.VLRLinearOpMode;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.helpers.utils.GlobalConfig;
import org.firstinspires.ftc.teamcode.subsystems.arm.ArmState;
import org.firstinspires.ftc.teamcode.subsystems.arm.commands.MoveArmInToRobot;
import org.firstinspires.ftc.teamcode.subsystems.arm.commands.MoveArmToSpecimenDeposit;
import org.firstinspires.ftc.teamcode.subsystems.arm.commands.SetArmState;
import org.firstinspires.ftc.teamcode.subsystems.arm.commands.SetRotatorAngle;
import org.firstinspires.ftc.teamcode.subsystems.arm.commands.SetSlideExtension;
import org.firstinspires.ftc.teamcode.subsystems.arm.rotator.ArmRotatorConfiguration;
import org.firstinspires.ftc.teamcode.subsystems.arm.rotator.ArmRotatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.arm.slide.ArmSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.claw.ClawConfiguration;
import org.firstinspires.ftc.teamcode.subsystems.claw.ClawSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.claw.commands.SetClawAngle;
import org.firstinspires.ftc.teamcode.subsystems.claw.commands.SetClawState;
import org.firstinspires.ftc.teamcode.subsystems.claw.commands.SetClawTwist;

@Config
@Photon
@TeleOp(name = "SpecimenGrabCalibration", group = "Calibration")
public class SpecimenGrabCalibration extends VLRLinearOpMode {

    public static double rotatorAngle = 10;
    public static double slideExtension = 0.2;

    @Override
    public void run() {
        GlobalConfig.setIsInvertedMotors(true);
        VLRSubsystem.requireSubsystems(ArmSlideSubsystem.class, ArmRotatorSubsystem.class, ClawSubsystem.class);
        VLRSubsystem.initializeAll(hardwareMap);

        CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
                new SetArmState(ArmState.State.IN_ROBOT),
                new SequentialCommandGroup(
                        new SetClawTwist(ClawConfiguration.TargetTwist.QUARTER_TURN),
                        new WaitCommand(600),
                        new MoveArmToSpecimenDeposit()
                )

//                new SetSlideExtension(slideExtension),
//                new SetRotatorAngle(ArmRotatorConfiguration.TargetAngle.DEPOSIT_SPECIMEN),
//                new WaitUntilCommand(VLRSubsystem.getInstance(ArmRotatorSubsystem.class)::reachedTargetPosition),
//                new SetClawState(ClawConfiguration.TargetState.OPEN),
//                new SetClawTwist(ClawConfiguration.TargetTwist.NORMAL),
//                new SetClawAngle(ClawConfiguration.TargetAngle.UP)
        ));

        waitForStart();

        while (opModeIsActive()) {
            // ...
        }

    }
}
