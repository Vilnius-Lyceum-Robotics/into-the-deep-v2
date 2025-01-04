package org.firstinspires.ftc.teamcode.subsystems.arm.commands;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.helpers.commands.CustomConditionalCommand;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.arm.ArmState;
import org.firstinspires.ftc.teamcode.subsystems.arm.rotator.ArmRotatorConfiguration;
import org.firstinspires.ftc.teamcode.subsystems.arm.rotator.ArmRotatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.arm.slide.ArmSlideConfiguration;
import org.firstinspires.ftc.teamcode.subsystems.arm.slide.ArmSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.claw.ClawConfiguration.TargetAngle;
import org.firstinspires.ftc.teamcode.subsystems.claw.commands.SetClawAngle;

public class MoveArmToBucketDeposit extends CustomConditionalCommand {

    public MoveArmToBucketDeposit() {
        super(
                new SequentialCommandGroup(
                        new CustomConditionalCommand(
                                new MoveArmInToRobot(),
                                () -> (ArmState.get() == ArmState.State.INTAKE || ArmState.get() == ArmState.State.SECOND_STAGE_HANG)
                        ),

                        new SetRotatorAngle(ArmRotatorConfiguration.TargetAngle.DEPOSIT_BUCKET),
                        new WaitUntilCommand(() -> VLRSubsystem.getInstance(ArmRotatorSubsystem.class).getAngleDegrees() >= 30),
                        new SetClawAngle(TargetAngle.DOWN),

                        new WaitUntilCommand(VLRSubsystem.getInstance(ArmRotatorSubsystem.class)::reachedTargetPosition),
                        new SetSlideExtension(ArmSlideConfiguration.TargetPosition.DEPOSIT_BUCKET),

                        new WaitUntilCommand(VLRSubsystem.getInstance(ArmSlideSubsystem.class)::reachedTargetPosition),
                        new SetClawAngle(TargetAngle.DEPOSIT),
                        new SetArmState(ArmState.State.DEPOSIT_BUCKET)
                ),
                ()->ArmState.get() != ArmState.State.DEPOSIT_BUCKET
        );
        addRequirements(VLRSubsystem.getInstance(ArmRotatorSubsystem.class), VLRSubsystem.getInstance(ArmSlideSubsystem.class));
    }
}