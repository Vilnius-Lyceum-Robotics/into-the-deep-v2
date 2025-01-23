package org.firstinspires.ftc.teamcode.subsystems.hang.commands;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.helpers.commands.CustomConditionalCommand;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.arm.ArmState;
import org.firstinspires.ftc.teamcode.subsystems.arm.commands.RetractArm;
import org.firstinspires.ftc.teamcode.subsystems.arm.commands.SetArmOperationMode;
import org.firstinspires.ftc.teamcode.subsystems.arm.commands.SetRotatorAngle;
import org.firstinspires.ftc.teamcode.subsystems.arm.commands.SetSlideExtension;
import org.firstinspires.ftc.teamcode.subsystems.arm.slide.ArmSlideConfiguration;
import org.firstinspires.ftc.teamcode.subsystems.claw.ClawConfiguration;
import org.firstinspires.ftc.teamcode.subsystems.claw.commands.SetClawAngle;
import org.firstinspires.ftc.teamcode.subsystems.hang.HangConfiguration;

import java.util.function.BooleanSupplier;

public class ThirdStageHangCommand extends SequentialCommandGroup {
    public ThirdStageHangCommand(BooleanSupplier gamepadCondition){
        addRequirements(VLRSubsystem.getRotator(), VLRSubsystem.getSlides());
        addCommands(
                new CustomConditionalCommand(
                        new RetractArm(),
                        () -> !ArmState.isCurrentState(ArmState.State.IN_ROBOT, ArmState.State.THIRD_STAGE_HANG)
                ),

                new ForceCalibrateSlides(),

                new SetClawAngle(ClawConfiguration.VerticalRotation.UP),
                new SetRotatorAngle(103.5),
                new WaitUntilCommand(()-> VLRSubsystem.getRotator().getAngleDegrees() >= 50),
                new SetSlideExtension(0.32),
                new WaitUntilCommand(()-> (VLRSubsystem.getRotator().reachedTargetPosition() && VLRSubsystem.getSlides().reachedTargetPosition())),
                new WaitUntilCommand(gamepadCondition),
                new SetArmOperationMode(ArmSlideConfiguration.OperationMode.HANG),

                new ParallelCommandGroup(
                        new SequentialCommandGroup(
                                new SetSlideExtension(0.251),
                                new WaitUntilCommand(()-> VLRSubsystem.getSlides().reachedTargetPosition()).withTimeout(1000),
                                new SetHangPosition(HangConfiguration.TargetPosition.UP)

                        ),

                        new SequentialCommandGroup(
                                new WaitCommand(50),
                                new SetRotatorAngle(96),
                                new WaitUntilCommand(()-> VLRSubsystem.getRotator().reachedTargetPosition()).withTimeout(1000)
                        )
                ),


                new WaitUntilCommand(gamepadCondition),
                new SetSlideExtension(0.265),
                new SetRotatorAngle(90),

                new WaitCommand(100),


                new SetArmOperationMode(ArmSlideConfiguration.OperationMode.NORMAL),

                new SetSlideExtension(0.29),
                new WaitCommand(300),
                new SetRotatorAngle(90),

                new WaitUntilCommand(()-> (VLRSubsystem.getRotator().reachedTargetPosition() && VLRSubsystem.getSlides().reachedTargetPosition())).withTimeout(1500),
                new WaitCommand(1000),
                new SetSlideExtension(0.881),
                new WaitUntilCommand(()-> VLRSubsystem.getSlides().reachedTargetPosition()).withTimeout(1500),
                new SetRotatorAngle(98.2),
                new WaitUntilCommand(()-> VLRSubsystem.getRotator().reachedTargetPosition()).withTimeout(1000),



                new WaitUntilCommand(gamepadCondition),
                new SetArmOperationMode(ArmSlideConfiguration.OperationMode.HANG),
                new SetSlideExtension(0.04),
                new WaitUntilCommand(()-> VLRSubsystem.getSlides().getExtension() < 0.78),
                new SetRotatorAngle(142),
                new WaitUntilCommand(()-> VLRSubsystem.getSlides().getExtension() < 0.38),
                new SetHangPosition(HangConfiguration.TargetPosition.DOWN),
                new SetRotatorAngle(38),
                new WaitUntilCommand(()-> VLRSubsystem.getSlides().reachedTargetPosition()).withTimeout(5000),



                new WaitUntilCommand(gamepadCondition),
                new SetRotatorAngle(140)
        );
    }
}